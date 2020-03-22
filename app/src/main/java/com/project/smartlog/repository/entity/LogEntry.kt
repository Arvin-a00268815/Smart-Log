package com.project.smartlog.repository.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.PrimaryKey
import java.io.Serializable

/**
 * Created by arvin-2009 on Feb 2019.
 */

@Entity(tableName = "log_entry",foreignKeys = [ForeignKey(entity = LogBook::class,
    parentColumns = arrayOf("title"),
    childColumns = arrayOf("logBookTitle"),
    onDelete = ForeignKey.CASCADE)]
)
data class LogEntry(
    var logBookTitle: String,
    var title : String,
    var createdTime : Long
) : Serializable{

    @PrimaryKey(autoGenerate = true)
    var logId: Long = 0
    var description = ""
    var dateTime = ""


}
