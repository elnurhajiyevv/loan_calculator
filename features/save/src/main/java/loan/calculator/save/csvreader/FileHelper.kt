package loan.calculator.save.csvreader

import android.content.Context
import android.net.Uri
import android.os.Environment
import android.util.Log
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException

/*
 * Created by Elnur on on 09.05.24, 09.
 * Copyright (c) 2024 . All rights reserved to Elnur.
 * This code is copyrighted and using this code without agreement from authors is forbidden
 */

class FileHelper {
    var dirName: String
    var fileName: String

    constructor() {
        dirName = "AbdyCSV"
        fileName = "AbdyCSV"
    }

    constructor(dirName: String, fileName: String) {
        this.dirName = dirName
        this.fileName = fileName
    }

    fun getUriByApp(content: String, context: Context): Uri {
        return FileProvider.getUriForFile(
            context, "loan.calculator" + ".provider", storeFile(content)
        )
    }

    fun storeFile(content: String): File {
        var file = File("")
        val root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS + File.separator + "loanCalculator")
        if (root.canWrite()) {
            val dir = File(root.absolutePath + "/" + dirName)
            dir.mkdirs()
            file = File(dir, fileName + ".csv")
            var out: FileOutputStream? = null
            Log.d("CSV-FILE", dir.absolutePath)
            try {
                out = FileOutputStream(file)
                out.write(content.toByteArray())
                out.close()
            } catch (e: FileNotFoundException) {
                Log.e("CSV-ERROR", e.toString())
                e.printStackTrace()
            } catch (e: IOException) {
                Log.e("CSV-ERROR", e.toString())
                e.printStackTrace()
            }
        }
        return file
    }
}