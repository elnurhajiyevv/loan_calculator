package loan.calculator.save.view

import android.graphics.Color
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import loan.calculator.common.extensions.getDoubleValue
import loan.calculator.common.extensions.getMonthAndYear
import loan.calculator.core.base.BaseFragment
import loan.calculator.core.extension.toast
import loan.calculator.uikit.R
import loan.calculator.save.databinding.FragmentSaveCsvPageBinding
import loan.calculator.save.effect.SaveCsvEffect
import loan.calculator.save.state.SaveCsvState
import loan.calculator.save.viewmodel.SaveCsvViewModel
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.NumberFormat
import java.util.Locale

/*
 * Created by Elnur on on 28.04.24, 18.
 * Copyright (c) 2024 . All rights reserved to Elnur.
 * This code is copyrighted and using this code without agreement from authors is forbidden
 */
@AndroidEntryPoint
class SaveCsvFragment :
    BaseFragment<SaveCsvState, SaveCsvEffect, SaveCsvViewModel, FragmentSaveCsvPageBinding>() {

    override val bindingCallback: (LayoutInflater, ViewGroup?, Boolean) -> FragmentSaveCsvPageBinding
        get() = FragmentSaveCsvPageBinding::inflate

    override fun getViewModelClass() = SaveCsvViewModel::class.java
    override fun getViewModelScope() = this

    private val args by navArgs<SaveCsvFragmentArgs>()

    override val bindViews: FragmentSaveCsvPageBinding.() -> Unit = {
        toolbar.setBackButtonVisibility(true)
        toolbar.setGravityLeft()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        generateCsv()
    }

    private fun loanCsvFileName(): String {
        val raw = args.getSavedLoanModel.name?.trim().orEmpty().ifBlank { "loan" }
        val safe = raw.replace(Regex("""[<>:"/\\|?*\u0000-\u001F]"""), "_").trim()
        val base = if (safe.isEmpty()) "loan" else safe
        return "$base.csv"
    }

    private fun generateCsv() {
        val outPath = requireActivity().getExternalFilesDir(null)
            .toString() + "/" + loanCsvFileName()
        val csvText = buildLoanCsvContent()
        val utf8Bom = "\uFEFF"
        try {
            FileOutputStream(outPath).use { it.write((utf8Bom + csvText).toByteArray(Charsets.UTF_8)) }
        } catch (e: IOException) {
            e.printStackTrace()
            toast("Could not create CSV")
            return
        }

        val file = File(outPath)
        showCsvPreview(csvText)

        binding.toolbar.setToolbarRightActionClick {
            if (!isExternalStorageAvailable() || isExternalStorageReadOnly()) {
                toast("Storage is not available")
                return@setToolbarRightActionClick
            }
            try {
                val downloadsDir =
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                val loanifyDir = File(downloadsDir, "loanify")
                if (!loanifyDir.exists()) {
                    loanifyDir.mkdirs()
                }
                val destFile = File(loanifyDir, loanCsvFileName())
                file.copyTo(destFile, overwrite = true)
                toast("${destFile.name} saved to Download/loanify/")
                findNavController().popBackStack()
            } catch (e: IOException) {
                e.printStackTrace()
                toast("Could not save CSV")
            }
        }
    }

    private fun showCsvPreview(csvText: String) {
        binding.csvPreview.settings.apply {
            javaScriptEnabled = false
            cacheMode = WebSettings.LOAD_NO_CACHE
        }
        val bg = ContextCompat.getColor(requireContext(), R.color.background_color)
        val fg = ContextCompat.getColor(requireContext(), R.color.text_primary)
        val bgHex = String.format("#%06X", 0xFFFFFF and bg)
        val fgHex = String.format("#%06X", 0xFFFFFF and fg)
        val html = """
            <!DOCTYPE html>
            <html>
            <head>
              <meta charset="UTF-8"/>
              <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
              <style>
                body { margin: 12px; background: $bgHex; color: $fgHex; }
                pre { font-size: 12px; line-height: 1.35; white-space: pre-wrap; word-break: break-word;
                      font-family: monospace, monospace; }
              </style>
            </head>
            <body><pre>${escapeHtml(csvText)}</pre></body>
            </html>
        """.trimIndent()
        binding.csvPreview.setBackgroundColor(Color.TRANSPARENT)
        binding.csvPreview.loadDataWithBaseURL(null, html, "text/html", "UTF-8", null)
    }

    private fun escapeHtml(text: String): String = buildString(text.length) {
        for (c in text) {
            when (c) {
                '&' -> append("&amp;")
                '<' -> append("&lt;")
                '>' -> append("&gt;")
                '"' -> append("&quot;")
                '\'' -> append("&#39;")
                else -> append(c)
            }
        }
    }

    private fun isExternalStorageReadOnly(): Boolean {
        val extStorageState = Environment.getExternalStorageState()
        return Environment.MEDIA_MOUNTED_READ_ONLY == extStorageState
    }

    private fun isExternalStorageAvailable(): Boolean {
        val extStorageState = Environment.getExternalStorageState()
        return Environment.MEDIA_MOUNTED == extStorageState
    }

    private fun numberFormatForCurrency(): NumberFormat {
        val locale = when (viewmodel.getCurrency()) {
            "₼" -> Locale("az", "AZ")
            "₺" -> Locale("tr", "TR")
            "$" -> Locale.US
            "₽" -> Locale("ru", "RU")
            "€" -> Locale("es", "ES")
            else -> Locale.US
        }
        return NumberFormat.getCurrencyInstance(locale)
    }

    private fun csvField(raw: String): String {
        val needsQuote =
            raw.contains(',') || raw.contains('"') || raw.contains('\n') || raw.contains('\r')
        val escaped = raw.replace("\"", "\"\"")
        return if (needsQuote) "\"$escaped\"" else escaped
    }

    private fun csvLine(values: List<String>): String =
        values.joinToString(",") { csvField(it) }

    private fun buildLoanCsvContent(): String {
        val m = args.getSavedLoanModel
        val nf = numberFormatForCurrency()
        val sb = StringBuilder()

        sb.appendLine(csvLine(listOf(m.name ?: "")))
        sb.appendLine()
        sb.appendLine(csvLine(listOf("Start date", m.startDate ?: "")))
        sb.appendLine(csvLine(listOf("Paid off", m.paidOff ?: "")))
        sb.appendLine(csvLine(listOf("Interest rate", "${m.interestRate ?: ""}%")))
        sb.appendLine(csvLine(listOf(getString(R.string.compounding_frequency), m.compoundingFrequency ?: "")))
        sb.appendLine(csvLine(listOf("Loan Name", m.name ?: "")))
        sb.appendLine()

        sb.appendLine(
            csvLine(
                listOf(
                    "#",
                    getString(R.string.amortization_col_period),
                    getString(R.string.amortization_col_interest),
                    getString(R.string.amortization_col_principal),
                    getString(R.string.amortization_col_balance)
                )
            )
        )

        for (item in viewmodel.formulaAmortization(
            loanAmount = m.loanAmount?.getDoubleValue() ?: 0.0,
            termInMonths = m.termInMonth ?: 0,
            annualInterestRate = m.interestRate?.getDoubleValue() ?: 0.0
        )) {
            if (item == null) continue
            sb.appendLine(
                csvLine(
                    listOf(
                        item.month.toString(),
                        item.month.toString().getMonthAndYear(),
                        nf.format(item.interest ?: 0.0),
                        nf.format(item.principal ?: 0.0),
                        nf.format(item.endingBalance ?: 0.0)
                    )
                )
            )
            if (item.month.rem(12) == 0) {
                sb.appendLine(
                    csvLine(
                        listOf("", "", "", "", "End of Year #${item.month / 12}")
                    )
                )
            }
        }

        sb.appendLine()
        sb.appendLine(csvLine(listOf(getString(R.string.loan_amount), viewmodel.getCurrency() + (m.loanAmount ?: ""))))
        sb.appendLine(
            csvLine(
                listOf(
                    getString(R.string.loan_result_interest_header),
                    viewmodel.getCurrency() + (m.totalInterest ?: "")
                )
            )
        )
        sb.appendLine(
            csvLine(
                listOf(
                    getString(R.string.total_repayment),
                    viewmodel.getCurrency() + (m.totalPayment ?: "")
                )
            )
        )

        return sb.toString()
    }
}
