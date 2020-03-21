package com.freshworks.smartlog.repository.database

import android.arch.persistence.room.*
import com.freshworks.smartlog.repository.entity.LogBook

/**
 * Created by arvin-2009 on Feb 2019.
 */

@Dao
interface LogBookDao  {

    @Query("SELECT * FROM log_book ORDER By createdTime desc")
    fun getAllLogBooks():List<LogBook>

    @Insert
    fun insertLogBook(logBook: LogBook)

    @Delete
    fun deleteLogBook(logBook: LogBook)

    @Query("SELECT * FROM log_book where not title = :title ORDER By createdTime desc")
    fun getLogBooksExcept(title : String):List<LogBook>

}