package com.project.smartlog.repository.database

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.project.smartlog.repository.entity.LogAttachments

/**
 * Created by arvin-2009 on Feb 2019.
 */
@Dao
interface LogAttachmentsDao {

    @Insert
    fun insertFilePath(logAttachments: List<LogAttachments>) : LongArray

    @Query ("select * from log_attachments where logId = :logId")
    fun getAttachments(logId : Long) : List<LogAttachments>
}