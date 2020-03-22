package com.project.smartlog.repository.database

import android.arch.persistence.room.*
import android.content.Context
import com.project.smartlog.repository.entity.LogAttachments
import com.project.smartlog.repository.entity.LogBook
import com.project.smartlog.repository.entity.LogEntry


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
