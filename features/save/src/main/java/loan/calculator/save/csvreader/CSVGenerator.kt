package loan.calculator.save.csvreader

import android.content.Context
import android.net.Uri
import android.util.Log
import java.util.Locale

/**
 * Created by Elnur on on 09.05.24, 09.
 * Copyright (c) 2024 . All rights reserved to Elnur.
 * This code is copyrighted and using this code without agreement from authors is forbidden
 **/


/**
 * Bismillahirrahmanirrahim
 * Created by abdymalikmulky on 3/6/17.
 */
class CSVGenerator : CSVContent {
    var fileHelper: FileHelper
    lateinit var exceptionColumn: Array<String>
    var content = ""
    lateinit var context: Context

    constructor() {
        fileHelper = FileHelper()
        clearContent()
    }

    constructor(context: Context,dirName: String, fileName: String) {
        fileHelper = FileHelper(dirName, fileName)
        exceptionColumn = arrayOf()
        this.context = context
        clearContent()
    }

    fun setTitleItem(title: String) {
        super.title = title
        val titleString = "\"" + title + "\""
        appendContent(titleString)
        addNewLine()
    }

    fun setSubtitle(subtitle: String) {
        appendContent(subtitle)
    }

    /**
     * Split a toString content
     * @param content
     * @return
     */
    private fun splitContent(content: String): String {
        var output = ""
        val bracketSplit = content.split("\\{".toRegex()).dropLastWhile { it.isEmpty() }
            .toTypedArray()
        output = bracketSplit[1]
        output = output.replace(CSVProperties.BRACKET_CLOSE, "")
        output = output.replace("\\s+".toRegex(), "")
        output = output.replace(CSVProperties.APOSTROPHE.toRegex(), "")
        return output
    }

    /**
     * Header table like <th> parse by string content
     * @param content
     * @return
    </th> */
    private fun getHeaderTable(content: String): String {
        return getContentTable(content, true)
    }

    /**
     * Header table like <td> parse by string content
     * @param content
     * @return
    </td> */
    private fun getDataTable(content: String): String {
        return getContentTable(content, false)
    }

    /**
     * Content genartor for getHeaderTable() and getDataTable
     * @param content
     * @return
     */
    private fun getContentTable(content: String, isHeader: Boolean): String {
        var contentTable = ""
        var contentStr = ""
        var contentIndex = 0
        if (!isHeader) {
            contentIndex = 1
        }
        val commSplit = content.split(CSVProperties.COMMA.toRegex()).dropLastWhile { it.isEmpty() }
            .toTypedArray()
        for (i in commSplit.indices) {
            if (!stringContainsItemFromList(commSplit[i], exceptionColumn)) {
                val equalSplit = commSplit[i].split("=".toRegex()).dropLastWhile { it.isEmpty() }
                    .toTypedArray()
                contentStr = equalSplit[contentIndex]
                if (isHeader) {
                    contentStr = contentStr.uppercase(Locale.getDefault())
                }
                if (i > 0 && contentTable != "") {
                    contentTable = contentTable + CSVProperties.COMMA
                }
                contentTable += appendQuote(contentStr)
            }
        }
        return contentTable
    }

    /**
     * Setup Table yang akan di generate
     * @param tableTitle title table
     * @param datas data table header and row data
     * @param <T> generic object
     * @param exceptionColumn exception column
    </T> */
    fun <T> addTable(tableTitle: String, datas: ArrayList<T>, exceptionColumn: Array<String>) {
        addTableRoot(tableTitle, datas, exceptionColumn)
    }

    fun <T> addTable(tableTitle: String, datas: ArrayList<T>) {
        addTableRoot(tableTitle, datas, exceptionColumn)
    }

    private fun <T> addTableRoot(
        tableTitle: String,
        datas: ArrayList<T>,
        exceptionColumn: Array<String>
    ) {
        this.exceptionColumn = exceptionColumn
        val titleString = "\"" + tableTitle + "\""
        appendContent(titleString)
        var header = ""
        var data = ""
        for (i in datas.indices) {
            var dataObjtoString = datas[i].toString()
            dataObjtoString = splitContent(dataObjtoString)
            if (i == 0) {
                header = getHeaderTable(dataObjtoString)
                appendContent(header)
            }
            data = getDataTable(dataObjtoString)
            appendContent(data)
        }
        addNewLine()
    }

    fun addKeyValue(key: String, value: String) {
        val keyValue = key + CSVProperties.COMMA + value + CSVProperties.COMMA
        appendContent(keyValue)
    }

    fun addNewLine() {
        content += CSVProperties.COMMA + CSVProperties.NEW_LINE
    }

    fun generate(): Uri {
        /*Log.d("DATA-content", content)
        val file = fileHelper.storeFile(content)
        val uri = Uri.fromFile(file)*/
        clearContent()
        return fileHelper.getUriByApp(content = content, context =context)
    }

    fun clearContent() {
        content = ""
    }

    private fun appendContent(addedContent: String): String {
        content += addedContent + CSVProperties.NEW_LINE
        return content
    }

    private fun appendQuote(content: String): String {
        return "\"" + content + "\""
    }

    companion object {
        fun stringContainsItemFromList(inputStr: String, items: Array<String>): Boolean {
            for (i in items.indices) {
                if (inputStr.contains(items[i]!!)) {
                    return true
                }
            }
            return false
        }

        fun indexArrayFromAnotherArray(
            items: Array<String?>?,
            otherItems: Array<String?>?
        ): Boolean {
            return false
        }
    }
}