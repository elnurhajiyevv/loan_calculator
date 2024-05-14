package loan.calculator.save.view

import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import dagger.hilt.android.AndroidEntryPoint
import loan.calculator.core.base.BaseFragment
import loan.calculator.domain.entity.saved.ToDo
import loan.calculator.uikit.R
import loan.calculator.save.csvreader.CSVGenerator
import loan.calculator.save.databinding.FragmentSaveCsvPageBinding
import loan.calculator.save.effect.SaveCsvEffect
import loan.calculator.save.state.SaveCsvState
import loan.calculator.save.viewmodel.SaveCsvViewModel


/**
 * Created by Elnur on on 28.04.24, 18.
 * Copyright (c) 2024 . All rights reserved to Elnur.
 * This code is copyrighted and using this code without agreement from authors is forbidden
 **/
@AndroidEntryPoint
class SaveCsvFragment :
    BaseFragment<SaveCsvState, SaveCsvEffect, SaveCsvViewModel, FragmentSaveCsvPageBinding>() {

    lateinit var csvGenerator: CSVGenerator
    var toDos = arrayListOf<ToDo>()

    // Define a requestPermessionLauncher using the RequestPermission contract
    /*val requestPermessionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        // Check if the permission is granted
        if (isGranted) {
            // Show a toast message for permission granted
            generateCsv()
            //toast("Permission Granted")
        } else {
            // Show a toast message asking the user to grant the permission
            toast("Please grant permission")
        }
    }*/

    override val bindingCallback: (LayoutInflater, ViewGroup?, Boolean) -> FragmentSaveCsvPageBinding
        get() = FragmentSaveCsvPageBinding::inflate

    override fun getViewModelClass() = SaveCsvViewModel::class.java
    override fun getViewModelScope() = this

    override val bindViews: FragmentSaveCsvPageBinding.() -> Unit = {
        toolbar.setBackButtonVisibility(true)
        toolbar.setGravityLeft()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().window.setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE)
        checkWriteExternalPermission()
        // Check if the Android version is TIRAMISU or newer
    }


    private fun generateDummUser(): ArrayList<ToDo> {
        val toDos = ArrayList<ToDo>()
        for (i in 0..1) {
            toDos.add(ToDo(i, "Eat", "Eat everything", "-"))
        }
        return toDos
    }

    fun generateCsv() {
        toDos = generateDummUser()
        csvGenerator = CSVGenerator(requireContext(),"dirName", "SomeFile")
        csvGenerator.setTitleItem("Title")
        csvGenerator.setSubtitle("PROFILE")
        csvGenerator.addKeyValue("Name", "Abdy Malik Mulky")
        csvGenerator.addKeyValue("Email", "me@abdymalikmulky.com")
        csvGenerator.addNewLine()
        val exceptionUser = arrayOf("status")
        csvGenerator.addTable("Data ToDo", toDos, exceptionUser)
        val uri = csvGenerator.generate()
        val sendIntent = Intent(Intent.ACTION_SEND)
        sendIntent.putExtra(Intent.EXTRA_SUBJECT, R.string.summary)
        sendIntent.putExtra(Intent.EXTRA_STREAM, uri)
        sendIntent.setType("text/html")
        startActivity(sendIntent)
    }

    private fun checkWriteExternalPermission() {
        val readImagePermission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) WRITE_EXTERNAL_STORAGE else WRITE_EXTERNAL_STORAGE
        if (ActivityCompat.checkSelfPermission(requireContext(),readImagePermission) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(requireContext(),WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            writeExternalPermission.launch(readImagePermission)
        } else {
            generateCsv()
        }
    }

    private val writeExternalPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            // Handle Permission granted/rejected
            if (isGranted) {
                // Permission is granted
                generateCsv()
            } else {
                // Permission is denied
            }
        }

}