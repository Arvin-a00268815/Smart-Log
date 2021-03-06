package com.project.smartlog.repository.database

import android.arch.persistence.room.*
import com.project.smartlog.repository.entity.LogEntry

/**
 * Created by arvin-2009 on Feb 2019.
 */
@Dao
interface LogEntryDao {

    @Query("Select * from log_entry where logBookTitle = :logBookTitle order by createdTime desc")
    fun getLogEntries(logBookTitle : String) : List<LogEntry>

    @Insert
    fun insert(logEntry: LogEntry) : Long

    @Query("select * from log_entry where logId = :logId")
    fun getLogDetails(logId: Long) : LogEntry

    @Query("update log_entry set description = :description where logId =:logId")
    fun updateDescription(description : String, logId: Long)

    @Update
    fun updateLogDetails(logEntry: LogEntry) : Int

    @Delete
    fun deleteLog(logEntry: LogEntry)
}