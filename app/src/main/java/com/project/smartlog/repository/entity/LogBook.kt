package com.project.smartlog.repository.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.io.Serializable


@Entity(tableName = "log_book")
data class LogBook(

    @PrimaryKey
    var title : String,
    var createdTime: String

) : Serializable