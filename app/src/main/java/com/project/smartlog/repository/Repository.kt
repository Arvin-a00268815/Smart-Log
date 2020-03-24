package com.project.smartlog.repository

import com.project.smartlog.repository.database.DatabaseAccess
import com.project.smartlog.repository.entity.LogAttachments
import com.project.smartlog.repository.entity.LogBook
import com.project.smartlog.repository.entity.LogEntry
import javax.inject.Inject

class Repository @Inject constructor(private val databaseAccess: DatabaseAccess) : IRepository {

    override fun getAllLogBooks() : List<LogBook>{
        return databaseAccess.bookDao().getAllLogBooks()
    }

    override fun getLogBooksExcept(title : String) : List<LogBook>{
        val list = databaseAccess.bookDao().getLogBooksExcept(title)
        return list

    }


    override fun getLogEntries(title: String) : List<LogEntry>{
        val list = databaseAccess.logDao().getLogEntries(title)
        return list
    }


    override fun getLogDetails(id : Long) : LogEntry{
        val logEntry = databaseAccess.logDao().getLogDetails(id)
        return logEntry
    }


    override fun updateDescription(desc : String, id : Long){
        databaseAccess.logDao().updateDescription(desc, id)
    }


    override fun updateLogDetails(logEntry: LogEntry){
        databaseAccess.logDao().updateLogDetails(logEntry)
    }

    override fun insertLogBook(logBook: LogBook){
        databaseAccess.bookDao().insertLogBook(logBook)
    }

    override fun insertLogEntry(logEntry: LogEntry) : Long{
        val id = databaseAccess.logDao().insert(logEntry)
        return id
    }

    override fun insertFilePath(logAttachments: List<LogAttachments>){
        databaseAccess.attachments().insertFilePath(logAttachments)
    }

    override fun getAttachments(logId : Long) : List<LogAttachments>{
        val list = databaseAccess.attachments().getAttachments(logId)
        return list
    }

    override fun deleteLogBook(logBook: LogBook){
        databaseAccess.bookDao().deleteLogBook(logBook)
    }

    override fun deleteLog(logEntry: LogEntry){
        databaseAccess.logDao().deleteLog(logEntry)
    }

}