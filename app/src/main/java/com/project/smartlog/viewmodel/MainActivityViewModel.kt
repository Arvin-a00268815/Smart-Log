package com.project.smartlog.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.project.smartlog.Util
import com.project.smartlog.repository.Repository
import com.project.smartlog.repository.database.DatabaseAccess
import com.project.smartlog.repository.entity.LogAttachments
import com.project.smartlog.repository.entity.LogBook
import com.project.smartlog.repository.entity.LogEntry
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


/**
 * Created by arvin-2009 on Feb 2019.
 */
class MainActivityViewModel(val repository: Repository) : ViewModel() {

    private val mutableLiveDataLogEntries = MutableLiveData<List<LogEntry>>()

    private val mutableLiveDataLogBooks = MutableLiveData<List<LogBook>>()

    private val mutableLiveDataLogEntry = MutableLiveData<LogEntry>()
    private val mutableLiveDataLogBook = MutableLiveData<LogBook>()

    private val mutableLiveDataLogAttachments = MutableLiveData<Boolean>()

    private var liveDataLogId = MutableLiveData<Long>()

    private val deleteLogLiveData = MutableLiveData<Int>()

    private val deleteBookLiveData = MutableLiveData<Int>()

    private val filesLiveData = MutableLiveData<List<LogAttachments>>()

    private val dateLiveDate = MutableLiveData<Long>()

    private val mutableLiveDataDateTime = MutableLiveData<List<String>>()

    private val mutableLiveDataLogEntriesPdf = MutableLiveData<List<LogEntry>>()
    private val liveDataMoveLog = MutableLiveData<LogEntry>()

    fun getLogBooks(){

        mutableLiveDataLogBooks.postValue(repository.getAllLogBooks())
    }
    fun onGetLogBooks(): LiveData<List<LogBook>> {
        return mutableLiveDataLogBooks
    }

    fun getLogBooksExcept(title: String): LiveData<List<LogBook>> {

        mutableLiveDataLogBooks.postValue(repository.getLogBooksExcept(title))
        return mutableLiveDataLogBooks
    }
    fun onGetLogEntries(): LiveData<List<LogEntry>> {

        return mutableLiveDataLogEntries
    }
    fun getLogEntries(logBookTitle: String){

        mutableLiveDataLogEntries.postValue(repository.getLogEntries(logBookTitle))
    }
    fun onGetLogEntriesForPdf(): LiveData<List<LogEntry>> {

        return mutableLiveDataLogEntriesPdf
    }
    fun getLogEntriesForPdf(logBookTitle: String){

        val list = repository.getLogEntries(logBookTitle)
        mutableLiveDataLogEntriesPdf.postValue(list)
    }
    fun onGetLogDetails(): LiveData<LogEntry> {
        return mutableLiveDataLogEntry
    }
    fun getLogDetails(id: Long){

        val logEntry = repository.getLogDetails(id)
        mutableLiveDataLogEntry.postValue(logEntry)
    }

    fun onUpdateLogDetails(): LiveData<LogEntry> {
        return mutableLiveDataLogEntry
    }

    fun updateLogDescription(desc: String, id: Long) {
        repository.updateDescription(desc, id)
        liveDataLogId.postValue(id)

    }


    fun updateLogDetails(logEntry: LogEntry) {
        logEntry.dateTime = Util.convertTimeToDateString(logEntry.createdTime)
        repository.updateLogDetails(logEntry)
        mutableLiveDataLogEntry.postValue(logEntry)
    }

    fun moveLog(logEntry: LogEntry, logBookTitle: String) {
        logEntry.logBookTitle = logBookTitle
       repository.updateLogDetails(logEntry)
        liveDataMoveLog.postValue(logEntry)
    }

    fun onMoveLog() : LiveData<LogEntry>{
        return liveDataMoveLog
    }

    fun onInsertLogBook(): LiveData<LogBook> {
        return mutableLiveDataLogBook
    }


    fun getDateTime(): LiveData<List<String>> {
        val date = Calendar.getInstance().time
        val timeInMillSecs = Calendar.getInstance().timeInMillis
        val dateFormat = SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.US)
        val strDate = dateFormat.format(date)
        val list = strDate.split(" ") as ArrayList<String>
        list.add(timeInMillSecs.toString())
        mutableLiveDataDateTime.postValue(list)
        return mutableLiveDataDateTime
    }

    fun getDateTime(createdTime: String): LiveData<List<String>> {
        val list = createdTime.split(" ")
        mutableLiveDataDateTime.postValue(list)
        return mutableLiveDataDateTime
    }

    fun getDateTime(milliSeconds: Long): LiveData<List<String>> {


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



    fun getDate(year: Int, month: Int, day: Int, hour: Int, minute: Int): LiveData<Long> {
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
        repository.insertLogBook(logBook)
        mutableLiveDataLogBook.postValue(logBook)

    }


    fun onInsertLogEntry(): LiveData<LogEntry> {
        return mutableLiveDataLogEntry
    }

    fun insertLogEntry(logBookId: String, title: String, desc: String, milliSeconds: Long) {
        val logEntry = LogEntry(logBookId, title, milliSeconds)
        logEntry.description = desc
        logEntry.dateTime = Util.convertTimeToDateString(milliSeconds)
        logEntry.logId = repository.insert(logEntry)
        mutableLiveDataLogEntry.postValue(logEntry)
    }

    fun onInsertFiles(): LiveData<Boolean> {

        return mutableLiveDataLogAttachments
    }

    fun insertFiles(list: List<LogAttachments>) {
       repository.insertFilePath(list)
        mutableLiveDataLogAttachments.postValue(true)
    }


    fun getImageFiles(logId: Long): LiveData<List<LogAttachments>> {
        val list = repository.getAttachments(logId)
        filesLiveData.postValue(list)
        return filesLiveData
    }

    fun deleteLogBook(logBook: LogBook, pos : Int) {
        repository.deleteLogBook(logBook)
        deleteBookLiveData.postValue(pos)
    }
    fun onDeleteBook(): LiveData<Int> {
        return deleteBookLiveData
    }


    fun deleteLog(logEntry: LogEntry, pos : Int) {
        repository.deleteLog(logEntry)
        deleteLogLiveData.postValue(pos)
    }
    fun onDeleteLog(): LiveData<Int> {
        return deleteLogLiveData
    }



}
