package com.project.smartlog.repository.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.PrimaryKey

/**
 * Created by arvin-2009 on Feb 2019.
 */

@Entity(tableName = "log_attachments", foreignKeys = [ForeignKey(entity = LogEntry::class,
    parentColumns = arrayOf("logId"),
    childColumns = arrayOf("logId"),
    onDelete = ForeignKey.CASCADE)])
data class LogAttachments(var path : String, var logId : Long){
    @PrimaryKey(autoGenerate = true)
    var autoNumberId : Int = 0
}