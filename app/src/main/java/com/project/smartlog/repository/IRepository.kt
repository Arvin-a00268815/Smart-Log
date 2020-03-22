package com.project.smartlog.repository

import com.project.smartlog.repository.entity.LogAttachments
import com.project.smartlog.repository.entity.LogBook
import com.project.smartlog.repository.entity.LogEntry

interface IRepository {

    fun getAllLogBooks() : List<LogBook>

    fun getLogBooksExcept(title : String) : List<LogBook>

    fun getLogEntries(title: String) : List<LogEntry>

    fun getLogDetails(id : Long) : LogEntry

    fun updateDescription(desc : String, id : Long)

    fun updateLogDetails(logEntry: LogEntry)

    fun insertLogBook(logBook: LogBook)

    fun insertLogEntry(logEntry: LogEntry) : Long

    fun insertFilePath(logAttachments: List<LogAttachments>)

    fun getAttachments(logId : Long) : List<LogAttachments>

    fun deleteLogBook(logBook: LogBook)

    fun deleteLog(logEntry: LogEntry)

}