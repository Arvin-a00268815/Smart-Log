package com.project.smartlog.repository

import com.project.smartlog.repository.database.DatabaseAccess
import com.project.smartlog.repository.entity.LogAttachments
import com.project.smartlog.repository.entity.LogBook
import com.project.smartlog.repository.entity.LogEntry

class Repository(private val databaseAccess: DatabaseAccess) {

    fun getAllLogBooks() : List<LogBook>{
        return databaseAccess.bookDao().getAllLogBooks()
    }

    fun getLogBooksExcept(title : String) : List<LogBook>{
        val list = databaseAccess.bookDao().getLogBooksExcept(title)
        return list

    }


    fun getLogEntries(title: String) : List<LogEntry>{
        val list = databaseAccess.logDao().getLogEntries(title)
        return list
    }


    fun getLogDetails(id : Long) : LogEntry{
        val logEntry = databaseAccess.logDao().getLogDetails(id)
        return logEntry
    }


    fun updateDescription(desc : String, id : Long){
        databaseAccess.logDao().updateDescription(desc, id)
    }


    fun updateLogDetails(logEntry: LogEntry){
        databaseAccess.logDao().updateLogDetails(logEntry)
    }

    fun insertLogBook(logBook: LogBook){
        databaseAccess.bookDao().insertLogBook(logBook)
    }

    fun insert(logEntry: LogEntry) : Long{
        val id = databaseAccess.logDao().insert(logEntry)
        return id
    }

    fun insertFilePath(logAttachments: List<LogAttachments>){
        databaseAccess.attachments().insertFilePath(logAttachments)
    }

    fun getAttachments(logId : Long) : List<LogAttachments>{
        val list = databaseAccess.attachments().getAttachments(logId)
        return list
    }

    fun deleteLogBook(logBook: LogBook){
        databaseAccess.bookDao().deleteLogBook(logBook)
    }

    fun deleteLog(logEntry: LogEntry){
        databaseAccess.logDao().deleteLog(logEntry)
    }

}