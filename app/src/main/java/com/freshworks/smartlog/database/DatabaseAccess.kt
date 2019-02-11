package com.freshworks.smartlog.database

import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.db.SupportSQLiteOpenHelper
import android.arch.persistence.room.*
import android.arch.persistence.room.migration.Migration
import android.content.Context
import com.freshworks.smartlog.database.entity.LogAttachments
import com.freshworks.smartlog.database.entity.LogBook
import com.freshworks.smartlog.database.entity.LogEntry


/**
 * Created by arvin-2009 on Feb 2019.
 */

@Database(entities = [LogEntry::class, LogBook::class, LogAttachments::class], version = 8)
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
    override fun createInvalidationTracker(): InvalidationTracker {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun clearAllTables() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun createOpenHelper(config: DatabaseConfiguration?): SupportSQLiteOpenHelper {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}