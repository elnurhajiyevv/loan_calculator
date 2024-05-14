package loan.calculator.save.view

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.itextpdf.text.BaseColor
import com.itextpdf.text.Document
import com.itextpdf.text.Element
import com.itextpdf.text.Font
import com.itextpdf.text.Image
import com.itextpdf.text.PageSize.A4
import com.itextpdf.text.Paragraph
import com.itextpdf.text.Phrase
import com.itextpdf.text.Rectangle
import com.itextpdf.text.pdf.BaseFont
import com.itextpdf.text.pdf.PdfContentByte
import com.itextpdf.text.pdf.PdfPCell
import com.itextpdf.text.pdf.PdfPTable
import com.itextpdf.text.pdf.PdfWriter
import dagger.hilt.android.AndroidEntryPoint
import loan.calculator.common.extensions.getDoubleValue
import loan.calculator.common.extensions.getMonthAndYear
import loan.calculator.core.base.BaseFragment
import loan.calculator.core.extension.toast
import loan.calculator.uikit.R
import loan.calculator.save.databinding.FragmentSavePdfPageBinding
import loan.calculator.save.effect.SavePdfEffect
import loan.calculator.save.state.SavePdfState
import loan.calculator.save.viewmodel.SavePdfViewModel
import loan.calculator.uikit.util.getThemeColor
import java.io.BufferedInputStream
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
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
class SavePdfFragment :
    BaseFragment<SavePdfState, SavePdfEffect, SavePdfViewModel, FragmentSavePdfPageBinding>() {

    lateinit var colorPrimary: BaseColor
    val colorTable = BaseColor(239, 241, 245)
    override val bindingCallback: (LayoutInflater, ViewGroup?, Boolean) -> FragmentSavePdfPageBinding
        get() = FragmentSavePdfPageBinding::inflate

    override fun getViewModelClass() = SavePdfViewModel::class.java
    override fun getViewModelScope() = this

    private val args by navArgs<SavePdfFragmentArgs>()

    override val bindViews: FragmentSavePdfPageBinding.() -> Unit = {
        toolbar.setBackButtonVisibility(true)
        toolbar.setGravityLeft()
    }

    val PADDING_EDGE = 20f
    val TEXT_TOP_PADDING = 3f
    val TABLE_TOP_PADDING = 10f
    val TEXT_TOP_PADDING_EXTRA = 20f
    val BILL_DETAILS_TOP_PADDING = 40f

    val FONT_SIZE_DEFAULT = 18f
    val FONT_SIZE_SMALL = 12f
    val FONT_SIZE_BIG = 24f

    var basfontRegular: BaseFont = BaseFont.createFont()
    var appFontRegular = Font(basfontRegular, FONT_SIZE_DEFAULT)

    var basfontBold: BaseFont = BaseFont.createFont()

    //BaseFont.createFont("assets/fonts/app_font_bold.ttf", "UTF-8", BaseFont.EMBEDDED)
    var appFontBold = Font(basfontBold, FONT_SIZE_DEFAULT)

    var basfontLight: BaseFont = BaseFont.createFont()

    //BaseFont.createFont("assets/fonts/app_font_light.ttf", "UTF-8", BaseFont.EMBEDDED)
    var appFontLight = Font(basfontLight, FONT_SIZE_SMALL)

    var basfontSemiBold: BaseFont = BaseFont.createFont()

    //BaseFont.createFont("assets/fonts/app_font_semi_bold.ttf", "UTF-8", BaseFont.EMBEDDED)
    var appFontSemiBold = Font(basfontSemiBold, 30f)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        generatePDF()
        //checkWriteExternalPermission()
    }

    private fun generatePDF(){
        colorPrimary = BaseColor(getThemeColor(requireContext()))
        appFontRegular.color = BaseColor.WHITE
        val doc = Document(A4, 0f, 0f, 0f, 0f)
        val outPath = requireActivity().getExternalFilesDir(null)
            .toString() + "/${args.getSavedLoanModel.name}.pdf" //location where the pdf will store
        Log.d("loc", outPath)
        val writer = PdfWriter.getInstance(doc, FileOutputStream(outPath))
        doc.open()
        //Header Column Init with width nad no. of columns
        initInvoiceHeader(doc)
        doc.setMargins(0f, 0f, 40f, 40f)
        initBillDetails(doc)
        addLine(writer)
        initTableHeader(doc)
        initItemsTable(doc)
        initPriceDetails(doc)
        //initFooter(doc)
        doc.close()

        val file = File(outPath)
        val path: Uri = FileProvider.getUriForFile(
            requireContext(), "loan.calculator" + ".provider", file
        )
        try {
            binding.pdfView.fromUri(path)
                .spacing(20)
                .load()
        } catch (e: Exception) {
            toast("There is no PDF Viewer ")
        }
        binding.toolbar.setToolbarRightActionClick {
            if(isExternalStorageAvailable() && !isExternalStorageReadOnly()){
                try {
                    val size = file.length().toInt()
                    val bytes = ByteArray(size)
                    try {
                        val buf = BufferedInputStream(FileInputStream(file))
                        buf.read(bytes, 0, bytes.size)
                        buf.close()
                    } catch (e: FileNotFoundException) {
                        // TODO Auto-generated catch block
                        e.printStackTrace()
                    } catch (e: IOException) {
                        // TODO Auto-generated catch block
                        e.printStackTrace()
                    }
                    var filePath = Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_DOWNLOADS + resources.getString(R.string.app_name) + File.separator + "loanCalculator")
                    if(!filePath.exists())
                        filePath.mkdirs()

                    var test = File(filePath,file.name)
                    if(!test.exists())
                        test.createNewFile()

                    val fos = FileOutputStream(test)
                    fos.write(bytes)
                    fos.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                } finally {
                    toast(file.name + " is created!")
                    findNavController().popBackStack()
                }
            }
        }
    }
    private fun checkWriteExternalPermission() {
        val readImagePermission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) Manifest.permission.WRITE_EXTERNAL_STORAGE else Manifest.permission.WRITE_EXTERNAL_STORAGE
        if (ActivityCompat.checkSelfPermission(requireContext(),readImagePermission) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(requireContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED) {
            writeExternalPermission.launch(readImagePermission)
        } else {
            generatePDF()
        }
    }

    private val writeExternalPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            // Handle Permission granted/rejected
            if (isGranted) {
                // Permission is granted
                generatePDF()
            } else {
                // Permission is denied
                findNavController().popBackStack()
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


    private fun addLine(writer: PdfWriter) {
        val canvas: PdfContentByte = writer.directContent
        canvas.setColorStroke(colorPrimary)
        canvas.moveTo(30.0, 470.0)

        // Drawing the line
        canvas.lineTo(570.0, 470.0)
        canvas.setLineWidth(1f)

        // Closing the path stroke
        canvas.closePathStroke()
    }

    private fun initItemsTable(doc: Document) {
        val itemsTable = PdfPTable(5)
        itemsTable.isLockedWidth = true
        itemsTable.totalWidth = A4.width
        itemsTable.setWidths(floatArrayOf(.5f, 1.5f, 1.5f, 1.5f, 2.5f))

        var nf: NumberFormat = NumberFormat.getCurrencyInstance(Locale.US)
        for (item in viewmodel.formulaAmortization(
            loanAmount = args.getSavedLoanModel.loanAmount?.getDoubleValue()?:0.0,
            termInMonths = args.getSavedLoanModel.termInMonth ?: 0,
            annualInterestRate = args.getSavedLoanModel.interestRate?.getDoubleValue()?:0.0
        )) {
            itemsTable.deleteBodyRows()

            appFontRegular.size = FONT_SIZE_DEFAULT
            appFontRegular.color = BaseColor.BLACK
            val itemName = PdfPCell(Phrase(item?.month.toString(), appFontRegular))
            itemName.border = Rectangle.NO_BORDER
            itemName.horizontalAlignment = Rectangle.ALIGN_RIGHT
            itemName.paddingTop = TABLE_TOP_PADDING
            itemName.paddingBottom = TABLE_TOP_PADDING
            if((item?.month?:0) %2 == 0)
                itemName.backgroundColor = colorTable
            itemsTable.addCell(itemName)

            val quantityCell =
                PdfPCell(Phrase(item?.month.toString().getMonthAndYear(), appFontRegular))
            quantityCell.border = Rectangle.NO_BORDER
            quantityCell.horizontalAlignment = Rectangle.ALIGN_RIGHT
            quantityCell.paddingTop = TABLE_TOP_PADDING
            quantityCell.paddingBottom = TABLE_TOP_PADDING
            if((item?.month?:0) %2 == 0)
                quantityCell.backgroundColor = colorTable
            itemsTable.addCell(quantityCell)

            val disAmount = PdfPCell(Phrase(nf.format(item?.interest), appFontRegular))
            disAmount.border = Rectangle.NO_BORDER
            disAmount.horizontalAlignment = Rectangle.ALIGN_RIGHT
            disAmount.paddingTop = TABLE_TOP_PADDING
            disAmount.paddingBottom = TABLE_TOP_PADDING
            if((item?.month?:0) %2 == 0)
                disAmount.backgroundColor = colorTable
            itemsTable.addCell(disAmount)

            val vat = PdfPCell(Phrase(nf.format(item?.principal), appFontRegular))
            vat.border = Rectangle.NO_BORDER
            vat.horizontalAlignment = Rectangle.ALIGN_RIGHT
            vat.paddingTop = TABLE_TOP_PADDING
            vat.paddingBottom = TABLE_TOP_PADDING
            if((item?.month?:0) %2 == 0)
                vat.backgroundColor = colorTable
            itemsTable.addCell(vat)

            val netAmount = PdfPCell(Phrase(nf.format(item?.endingBalance), appFontRegular))
            netAmount.horizontalAlignment = Rectangle.ALIGN_RIGHT
            netAmount.border = Rectangle.NO_BORDER
            netAmount.paddingTop = TABLE_TOP_PADDING
            netAmount.paddingBottom = TABLE_TOP_PADDING
            netAmount.paddingRight = PADDING_EDGE
            if((item?.month?:0) %2 == 0)
                netAmount.backgroundColor = colorTable
            itemsTable.addCell(netAmount)
            doc.add(itemsTable)

            if((item?.month)?.rem(12) ?: 0 == 0){
                initEndOfYear(doc, ((item?.month)?.div(12)).toString())
            }
        }
    }

    private fun initEndOfYear(doc: Document,year: String) {
        val footerTable = PdfPTable(1)
        footerTable.totalWidth = A4.width
        footerTable.isLockedWidth = true
        val thankYouCell = PdfPCell(Phrase("End of Year #$year", appFontBold))
        thankYouCell.border = Rectangle.NO_BORDER
        thankYouCell.paddingTop = 20f
        thankYouCell.paddingBottom = 20f
        thankYouCell.horizontalAlignment = Rectangle.ALIGN_CENTER
        footerTable.addCell(thankYouCell)
        doc.add(footerTable)

    }

    private fun initBillDetails(doc: Document) {
        val billDetailsTable =
            PdfPTable(3)  // table to show customer address, invoice, date and total amount
        billDetailsTable.setWidths(
            floatArrayOf(
                4f,
                4f,
                4f
            )
        )
        billDetailsTable.isLockedWidth = true
        billDetailsTable.paddingTop = 10f

        billDetailsTable.totalWidth =
            A4.width // set content width to fill document
        val customerAddressTable = PdfPTable(1)
        val txtBilledToCell = PdfPCell(
            Phrase(
                "Start date",
                appFontLight
            )
        )
        txtBilledToCell.border = Rectangle.NO_BORDER
        customerAddressTable.addCell(
            txtBilledToCell
        )
        appFontRegular.size = FONT_SIZE_BIG
        appFontRegular.color = BaseColor.BLACK
        val clientAddressCell1 = PdfPCell(
            Paragraph(
                args.getSavedLoanModel.startDate,
                appFontRegular
            )
        )
        clientAddressCell1.border = Rectangle.NO_BORDER
        clientAddressCell1.paddingTop = TEXT_TOP_PADDING
        customerAddressTable.addCell(clientAddressCell1)


        val paidoff = PdfPCell(Phrase("Paid off", appFontLight))
        paidoff.paddingTop = TEXT_TOP_PADDING_EXTRA
        paidoff.border = Rectangle.NO_BORDER
        customerAddressTable.addCell(paidoff)


        val paidCell = PdfPCell(Phrase(args.getSavedLoanModel.paidOff, appFontRegular))
        paidCell.border = Rectangle.NO_BORDER
        customerAddressTable.addCell(paidCell)



        val billDetailsCell1 = PdfPCell(customerAddressTable)
        billDetailsCell1.border = Rectangle.NO_BORDER

        billDetailsCell1.paddingTop = BILL_DETAILS_TOP_PADDING

        billDetailsCell1.paddingLeft = PADDING_EDGE

        billDetailsTable.addCell(billDetailsCell1)


        val invoiceNumAndData = PdfPTable(1)

        val txtInvoiceNumber = PdfPCell(Phrase("Interest rate", appFontLight))
        txtInvoiceNumber.paddingTop = BILL_DETAILS_TOP_PADDING
        txtInvoiceNumber.border = Rectangle.NO_BORDER
        invoiceNumAndData.addCell(txtInvoiceNumber)

        val invoiceNumber = PdfPCell(Phrase("${args.getSavedLoanModel.interestRate}%", appFontRegular))
        invoiceNumber.border = Rectangle.NO_BORDER
        invoiceNumber.paddingTop = TEXT_TOP_PADDING
        invoiceNumAndData.addCell(invoiceNumber)


        val txtDate = PdfPCell(Phrase("Compounding frequency", appFontLight))
        txtDate.paddingTop = TEXT_TOP_PADDING_EXTRA
        txtDate.border = Rectangle.NO_BORDER
        invoiceNumAndData.addCell(txtDate)


        val dateCell = PdfPCell(Phrase(args.getSavedLoanModel.compoundingFrequency, appFontRegular))
        dateCell.border = Rectangle.NO_BORDER
        invoiceNumAndData.addCell(dateCell)

        val dataInvoiceNumAndData = PdfPCell(invoiceNumAndData)
        dataInvoiceNumAndData.border = Rectangle.NO_BORDER
        billDetailsTable.addCell(dataInvoiceNumAndData)

        val totalPriceTable = PdfPTable(1)
        val txtInvoiceTotal = PdfPCell(Phrase("Loan Name", appFontLight))
        txtInvoiceTotal.paddingTop = BILL_DETAILS_TOP_PADDING
        txtInvoiceTotal.horizontalAlignment = Rectangle.ALIGN_RIGHT
        txtInvoiceTotal.border = Rectangle.NO_BORDER
        totalPriceTable.addCell(txtInvoiceTotal)

        appFontSemiBold.color = colorPrimary
        val totalAomountCell = PdfPCell(Phrase(args.getSavedLoanModel.name, appFontRegular))
        totalAomountCell.border = Rectangle.NO_BORDER
        totalAomountCell.horizontalAlignment = Rectangle.ALIGN_RIGHT
        totalPriceTable.addCell(totalAomountCell)
        val dataTotalAmount = PdfPCell(totalPriceTable)
        dataTotalAmount.border = Rectangle.NO_BORDER
        dataTotalAmount.paddingRight = PADDING_EDGE
        dataTotalAmount.verticalAlignment = Rectangle.ALIGN_BOTTOM

        billDetailsTable.addCell(dataTotalAmount)
        doc.add(billDetailsTable)
    }

    private fun initTableHeader(doc: Document) {

        doc.add(Paragraph("\n\n\n")) //adds blank line to place table header below the line

        val titleTable = PdfPTable(5)
        titleTable.isLockedWidth = true
        titleTable.totalWidth = A4.width
        titleTable.setWidths(floatArrayOf(.5f, 1.5f, 1.5f, 1.5f, 2.5f))
        appFontBold.color = colorPrimary

        val itemCell = PdfPCell(Phrase("#", appFontBold))
        itemCell.border = Rectangle.NO_BORDER
        itemCell.horizontalAlignment = Rectangle.ALIGN_RIGHT
        itemCell.paddingBottom = TABLE_TOP_PADDING
        titleTable.addCell(itemCell)


        val quantityCell = PdfPCell(Phrase("Period", appFontBold))
        quantityCell.border = Rectangle.NO_BORDER
        quantityCell.horizontalAlignment = Rectangle.ALIGN_RIGHT
        quantityCell.paddingBottom = TABLE_TOP_PADDING
        titleTable.addCell(quantityCell)

        val disAmount = PdfPCell(Phrase("Interest", appFontBold))
        disAmount.border = Rectangle.NO_BORDER
        disAmount.horizontalAlignment = Rectangle.ALIGN_RIGHT
        disAmount.paddingBottom = TABLE_TOP_PADDING
        titleTable.addCell(disAmount)

        val vat = PdfPCell(Phrase("Principal", appFontBold))
        vat.border = Rectangle.NO_BORDER
        vat.horizontalAlignment = Rectangle.ALIGN_RIGHT
        vat.paddingBottom = TABLE_TOP_PADDING
        titleTable.addCell(vat)

        val netAmount = PdfPCell(Phrase("Balance", appFontBold))
        netAmount.horizontalAlignment = Rectangle.ALIGN_RIGHT
        netAmount.border = Rectangle.NO_BORDER
        netAmount.paddingBottom = TABLE_TOP_PADDING
        netAmount.paddingRight = PADDING_EDGE
        titleTable.addCell(netAmount)
        doc.add(titleTable)
    }

    private fun initPriceDetails(doc: Document) {
        val priceDetailsTable = PdfPTable(2)
        priceDetailsTable.totalWidth = A4.width
        priceDetailsTable.setWidths(floatArrayOf(5f, 2f))
        priceDetailsTable.isLockedWidth = true

        appFontRegular.color = colorPrimary
        val txtSubTotalCell = PdfPCell(Phrase("Loan Amount : ", appFontRegular))
        txtSubTotalCell.border = Rectangle.NO_BORDER
        txtSubTotalCell.horizontalAlignment = Rectangle.ALIGN_RIGHT
        txtSubTotalCell.paddingTop = 60f
        priceDetailsTable.addCell(txtSubTotalCell)
        appFontBold.color = BaseColor.BLACK
        val totalPriceCell = PdfPCell(Phrase("$" + args.getSavedLoanModel.loanAmount, appFontRegular))
        totalPriceCell.border = Rectangle.NO_BORDER
        totalPriceCell.horizontalAlignment = Rectangle.ALIGN_RIGHT
        totalPriceCell.paddingTop = 60f
        totalPriceCell.paddingRight = PADDING_EDGE
        priceDetailsTable.addCell(totalPriceCell)


        val txtTaxCell = PdfPCell(Phrase("Total Interest : ", appFontRegular))
        txtTaxCell.border = Rectangle.NO_BORDER
        txtTaxCell.horizontalAlignment = Rectangle.ALIGN_RIGHT
        txtTaxCell.paddingTop = TEXT_TOP_PADDING
        priceDetailsTable.addCell(txtTaxCell)

        val totalTaxCell = PdfPCell(Phrase("$" + args.getSavedLoanModel.totalInterest, appFontRegular))
        totalTaxCell.border = Rectangle.NO_BORDER
        totalTaxCell.horizontalAlignment = Rectangle.ALIGN_RIGHT
        totalTaxCell.paddingTop = TEXT_TOP_PADDING
        totalTaxCell.paddingRight = PADDING_EDGE
        priceDetailsTable.addCell(totalTaxCell)

        val txtTotalCell = PdfPCell(Phrase("Total Repayment : ", appFontRegular))
        txtTotalCell.border = Rectangle.NO_BORDER
        txtTotalCell.horizontalAlignment = Rectangle.ALIGN_RIGHT
        txtTotalCell.paddingTop = TEXT_TOP_PADDING
        txtTotalCell.paddingBottom = TEXT_TOP_PADDING
        txtTotalCell.paddingLeft = PADDING_EDGE
        priceDetailsTable.addCell(txtTotalCell)
        appFontBold.color = colorPrimary
        val totalCell = PdfPCell(Phrase("$" + args.getSavedLoanModel.totalPayment, appFontRegular))
        totalCell.border = Rectangle.NO_BORDER
        totalCell.horizontalAlignment = Rectangle.ALIGN_RIGHT
        totalCell.paddingTop = TEXT_TOP_PADDING
        totalCell.paddingBottom = TEXT_TOP_PADDING
        totalCell.paddingRight = PADDING_EDGE
        priceDetailsTable.addCell(totalCell)

        doc.add(priceDetailsTable)
    }


    private fun initInvoiceHeader(doc: Document) {
        val d = resources.getDrawable(R.drawable.ic_pdf_header_logo)
        val bitDw = d as BitmapDrawable
        val bmp = bitDw.bitmap
        val stream = ByteArrayOutputStream()
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream)
        val image = Image.getInstance(stream.toByteArray())
        val headerTable = PdfPTable(2)
        headerTable.setWidths(
            floatArrayOf(
                1f, 4f
            )
        ) // adds 2 colomn horizontally
        headerTable.isLockedWidth = true
        headerTable.totalWidth = A4.width // set content width to fill document
        val cell = PdfPCell(Image.getInstance(image)) // Logo Cell
        cell.border = Rectangle.NO_BORDER // Removes border
        cell.paddingTop = TEXT_TOP_PADDING_EXTRA // sets padding
        cell.paddingRight = TABLE_TOP_PADDING
        cell.paddingLeft = TEXT_TOP_PADDING_EXTRA
        cell.horizontalAlignment = Rectangle.ALIGN_LEFT
        cell.paddingBottom = TEXT_TOP_PADDING_EXTRA

        cell.backgroundColor = colorPrimary // sets background color
        cell.horizontalAlignment = Element.ALIGN_CENTER
        headerTable.addCell(cell) // Adds first cell with logo

        val contactTable = PdfPTable(1) // new vertical table for contact details
        val phoneCell = PdfPCell(
            Paragraph(
                "+994 51 403 26 96", appFontRegular
            )
        )
        phoneCell.border = Rectangle.NO_BORDER
        phoneCell.horizontalAlignment = Element.ALIGN_RIGHT
        phoneCell.paddingTop = TEXT_TOP_PADDING
        phoneCell.paddingRight = TEXT_TOP_PADDING_EXTRA
        contactTable.addCell(phoneCell)

        val emailCellCell = PdfPCell(Phrase("elnurhajiyevv@gmail.com", appFontRegular))
        emailCellCell.border = Rectangle.NO_BORDER
        emailCellCell.horizontalAlignment = Element.ALIGN_RIGHT
        emailCellCell.paddingTop = TEXT_TOP_PADDING
        emailCellCell.paddingRight = TEXT_TOP_PADDING_EXTRA
        contactTable.addCell(emailCellCell)

        val webCell = PdfPCell(Phrase("by Elnur Hajiyev", appFontRegular))
        webCell.border = Rectangle.NO_BORDER
        webCell.paddingTop = TEXT_TOP_PADDING
        webCell.paddingRight = TEXT_TOP_PADDING_EXTRA
        webCell.horizontalAlignment = Element.ALIGN_RIGHT

        contactTable.addCell(webCell)


        val headCell = PdfPCell(contactTable)
        headCell.border = Rectangle.NO_BORDER
        headCell.horizontalAlignment = Element.ALIGN_RIGHT
        headCell.verticalAlignment = Element.ALIGN_MIDDLE
        headCell.backgroundColor = colorPrimary
        headerTable.addCell(headCell)


        doc.add(headerTable)
    }

}