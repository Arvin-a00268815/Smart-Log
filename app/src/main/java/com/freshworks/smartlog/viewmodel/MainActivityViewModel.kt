package com.freshworks.smartlog.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import com.freshworks.smartlog.database.DatabaseAccess
import com.freshworks.smartlog.database.entity.LogAttachments
import com.freshworks.smartlog.database.entity.LogBook
import com.freshworks.smartlog.database.entity.LogEntry
import java.text.SimpleDateFormat
import java.time.Year
import java.util.*
import kotlin.collections.ArrayList


/**
 * Created by arvin-2009 on Feb 2019.
 */
class MainActivityViewModel(application: Application) : AndroidViewModel(application) {

    private val mutableLiveDataLogEntries = MutableLiveData<List<LogEntry>>()

    private val mutableLiveDataLogBooks = MutableLiveData<List<LogBook>>()

    private val mutableLiveDataLogEntry = MutableLiveData<LogEntry>()
    private val mutableLiveDataLogBook = MutableLiveData<LogBook>()

    private val mutableLiveDataLogAttachments = MutableLiveData<Boolean>()

    private var liveDataLogId = MutableLiveData<Long>()

    fun getLogBooks(): MutableLiveData<List<LogBook>> {

        val list = DatabaseAccess.getAppDatabase(getApplication()).bookDao().getAllLogBooks()
        mutableLiveDataLogBooks.postValue(list)
        return mutableLiveDataLogBooks
    }

    fun getLogBooksExcept(title: String): MutableLiveData<List<LogBook>> {

        val list = DatabaseAccess.getAppDatabase(getApplication()).bookDao().getLogBooksExcept(title)
        mutableLiveDataLogBooks.postValue(list)
        return mutableLiveDataLogBooks
    }
    fun getLogEntries(logBookTitle: String): MutableLiveData<List<LogEntry>> {

        val list = DatabaseAccess.getAppDatabase(getApplication()).logDao().getLogEntries(logBookTitle)
        mutableLiveDataLogEntries.postValue(list)
        return mutableLiveDataLogEntries
    }

    fun getLogDetails(id: Long): MutableLiveData<LogEntry> {

        val logEntry = DatabaseAccess.getAppDatabase(getApplication()).logDao().getLogDetails(id)
        mutableLiveDataLogEntry.postValue(logEntry)
        return mutableLiveDataLogEntry
    }

    fun onUpdateLogDetails(): MutableLiveData<LogEntry> {
        return mutableLiveDataLogEntry
    }

    fun updateLogDescription(desc: String, id: Long) {
        DatabaseAccess.getAppDatabase(getApplication()).logDao().updateDescription(desc, id)
        liveDataLogId.postValue(id)

    }


    fun updateLogDetails(logEntry: LogEntry) {
        logEntry.dateTime = convertTimeToDateString(logEntry.createdTime)
        DatabaseAccess.getAppDatabase(getApplication()).logDao().updateLogDetails(logEntry)
        mutableLiveDataLogEntry.postValue(logEntry)
    }

    private val liveDataMoveLog = MutableLiveData<LogEntry>()
    fun moveLog(logEntry: LogEntry, logBookTitle: String) {
        logEntry.logBookTitle = logBookTitle
        DatabaseAccess.getAppDatabase(getApplication()).logDao().updateLogDetails(logEntry)
        liveDataMoveLog.postValue(logEntry)
    }

    fun onMoveLog() : MutableLiveData<LogEntry>{
        return liveDataMoveLog
    }

    fun onInsertLogBook(): MutableLiveData<LogBook> {


        return mutableLiveDataLogBook
    }

    private val mutableLiveDataDateTime = MutableLiveData<List<String>>()

    fun getDateTime(): MutableLiveData<List<String>> {
        val date = Calendar.getInstance().time
        val timeInMillSecs = Calendar.getInstance().timeInMillis
        val dateFormat = SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.US)
        val strDate = dateFormat.format(date)
        val list = strDate.split(" ") as ArrayList<String>
        list.add(timeInMillSecs.toString())
        mutableLiveDataDateTime.postValue(list)
        return mutableLiveDataDateTime
    }

    fun getDateTime(createdTime: String): MutableLiveData<List<String>> {
        val list = createdTime.split(" ")
        mutableLiveDataDateTime.postValue(list)
        return mutableLiveDataDateTime
    }

    fun getDateTime(milliSeconds: Long): MutableLiveData<List<String>> {


        val calendar = Calendar.getInstance()
        calendar.timeInMillis = milliSeconds

        val list = ArrayList<String>()
        list.add(calendar.get(Calendar.YEAR).toString())
        list.add(calendar.get(Calendar.MONTH).toString())
        list.add(calendar.get(Calendar.DAY_OF_MONTH).toString())
        list.add(calendar.get(Calendar.HOUR_OF_DAY).toString())
        list.add(calendar.get(Calendar.MINUTE).toString())

        mutableLiveDataDateTime.postValue(list)
        return mutableLiveDataDateTime
    }


    private val dateLiveDate = MutableLiveData<Long>()

    fun getDate(year: Int, month: Int, day: Int, hour: Int, minute: Int): MutableLiveData<Long> {
        val cal = Calendar.getInstance()
        cal.set(Calendar.YEAR, year)
        cal.set(Calendar.MONTH, month)
        cal.set(Calendar.DAY_OF_MONTH, day)
        cal.set(Calendar.HOUR_OF_DAY, hour)
        cal.set(Calendar.MINUTE, minute)
        cal.set(Calendar.SECOND, 0)
        cal.set(Calendar.MILLISECOND, 0)
        dateLiveDate.postValue(cal.timeInMillis)
        return dateLiveDate
    }

    fun insertLogBook(title: String, createdTime: String) {

        val logBook = LogBook(title = title, createdTime = createdTime)

        DatabaseAccess.getAppDatabase(getApplication()).bookDao().insertLogBook(logBook)
        mutableLiveDataLogBook.postValue(logBook)

    }


    fun onInsertLogEntry(): MutableLiveData<LogEntry> {
        return mutableLiveDataLogEntry
    }

    fun insertLogEntry(logBookId: String, title: String, desc: String, milliSeconds: Long) {


        val logEntry = LogEntry(logBookId, title, milliSeconds)
        logEntry.description = desc
        logEntry.dateTime = convertTimeToDateString(milliSeconds)


        val id = DatabaseAccess.getAppDatabase(getApplication()).logDao().insert(logEntry) as Long

        logEntry.logId = id
        mutableLiveDataLogEntry.postValue(logEntry)
    }

    fun onInsertFiles(): MutableLiveData<Boolean> {

        return mutableLiveDataLogAttachments
    }

    fun insertFiles(list: ArrayList<LogAttachments>) {
        val size = DatabaseAccess.getAppDatabase(getApplication()).attachments().insertFilePath(list)

        mutableLiveDataLogAttachments.postValue(true)
    }

    private val filesLiveData = MutableLiveData<List<LogAttachments>>()

    fun getImageFiles(logId: Long): MutableLiveData<List<LogAttachments>> {
        val list = DatabaseAccess.getAppDatabase(getApplication()).attachments().getAttachments(logId)

        filesLiveData.postValue(list)
        return filesLiveData
    }

    val deleteBookLiveData = MutableLiveData<Int>()
    fun deleteLogBook(logBook: LogBook, pos : Int) {
        DatabaseAccess.getAppDatabase(getApplication()).bookDao().deleteLogBook(logBook)
        deleteBookLiveData.postValue(pos)
    }
    fun onDeleteBook(): MutableLiveData<Int> {
        return deleteBookLiveData
    }


    val deleteLogLiveData = MutableLiveData<Int>()
    fun deleteLog(logEntry: LogEntry, pos : Int) {
        DatabaseAccess.getAppDatabase(getApplication()).logDao().deleteLog(logEntry)
        deleteLogLiveData.postValue(pos)
    }
    fun onDeleteLog(): MutableLiveData<Int> {
        return deleteLogLiveData
    }

    fun convertTimeToDateString(milliSeconds: Long) : String{
        val cal = Calendar.getInstance()
        cal.timeInMillis = milliSeconds

        val dateFormat = SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.US)
        val strDate = dateFormat.format(cal.time)

        return strDate
    }

}
