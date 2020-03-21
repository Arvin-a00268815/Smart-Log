package com.freshworks.smartlog.repository

import android.arch.persistence.room.*
import android.content.Context
import com.freshworks.smartlog.repository.database.LogAttachmentsDao
import com.freshworks.smartlog.repository.database.LogBookDao
import com.freshworks.smartlog.repository.database.LogEntryDao
import com.freshworks.smartlog.repository.entity.LogAttachments
import com.freshworks.smartlog.repository.entity.LogBook
import com.freshworks.smartlog.repository.entity.LogEntry


/**
 * Created by arvin-2009 on Feb 2019.
 */

@Database(entities = [LogEntry::class, LogBook::class, LogAttachments::class], version = 1)
abstract class DatabaseAccess : RoomDatabase(){


    abstract fun bookDao(): LogBookDao
    abstract fun logDao() : LogEntryDao
    abstract fun attachments() : LogAttachmentsDao

    companion object {
        private var INSTANCE: DatabaseAccess? = null
        fun getAppDatabase(context: Context): DatabaseAccess {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context.applicationContext, DatabaseAccess::class.java, "log-database")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return INSTANCE!!
        }
    }

}
