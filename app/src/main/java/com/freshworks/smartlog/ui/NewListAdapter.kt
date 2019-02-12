package com.freshworks.smartlog.ui

import android.support.v7.widget.CardView
import android.support.v7.widget.PopupMenu
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import com.freshworks.smartlog.R
import com.freshworks.smartlog.database.entity.LogBook
import com.freshworks.smartlog.database.entity.LogEntry
import kotlinx.android.synthetic.main.row_log_book_item.view.*
import kotlinx.android.synthetic.main.row_log_entry_item.view.*
import kotlinx.android.synthetic.main.row_log_move_item.view.*


class NewListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>(){


    var arraylist = ArrayList<Any?>()
    var isMoveOperation = false


    interface LogBookListener{
        fun showLogEntries(logBookTitle : String)
        fun deleteBook(logBook : LogBook, pos : Int)
        fun saveAsPDF(logBook: LogBook, pos : Int)
    }
    interface LogEntryListener{
        fun showLogDetails(id : Long, pos : Int)
    }

    interface LogMoveListener{
        fun moveLogToThisBook(logBook : LogBook)
    }

    interface ActionsListener{
        fun edit(logEntry : LogEntry, pos: Int)
        fun delete(logEntry : LogEntry, pos : Int)
        fun move(logEntry: LogEntry, pos : Int)
    }


    var actionsListener : ActionsListener ? = null
    var logBookListener : LogBookListener? = null
    var logEntryListener : LogEntryListener? = null
    var logMoveListener : LogMoveListener ? = null


    class LogEntryViewHolder(view: View) : RecyclerView.ViewHolder(view){

        val descriptionTextView = view.logEntry_description as TextView
        val titleTextView = view.logEntry_title as TextView
        val containerLayout = view.container_layout as RelativeLayout
        val dateTextView = view.date
        val moreActionView = view.more_action_view


    }
    class LogBookViewHolder(view: View) : RecyclerView.ViewHolder(view){

        val logBookDate = view.logbook_date as TextView
        val logBookTitle = view.logbook_title as TextView
        val cardView = view.cardview as CardView
        val moreActionView = view.book_more_action_view as ImageView

    }

    class LogBookTypeViewHolder(view: View) : RecyclerView.ViewHolder(view){

        val logBookDate = view.book_date as TextView
        val logBookTitle = view.book_title as TextView
        val parentContainer = view.parent_container as LinearLayout

    }


    override fun getItemViewType(position: Int): Int {
        val type = arraylist[position]
        return if(isMoveOperation){
            2
        }else if(type is LogBook){
            0
        }else{
            1
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        if(viewType == 2){
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.row_log_move_item, parent, false)
            return LogBookTypeViewHolder(view)

        } else if(viewType == 1) {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.row_log_entry_item, parent, false)
            return LogEntryViewHolder(view)
        }else{
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.row_log_book_item, parent, false)
            return LogBookViewHolder(view)

        }
    }

    override fun getItemCount(): Int {

        return arraylist.size
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when(getItemViewType(position)) {
            2 ->{
                val logBookTypeViewHolder = holder as LogBookTypeViewHolder
                val logBook = arraylist[position] as LogBook
                logBookTypeViewHolder.logBookTitle.text = logBook.title
                logBookTypeViewHolder.logBookDate.text = logBook.createdTime
                logBookTypeViewHolder.parentContainer.setOnClickListener {
                    logMoveListener?.moveLogToThisBook(logBook)

                }
            }
            1 -> {
                val logEntryViewHolder = holder as LogEntryViewHolder
                val logEntry = arraylist[position] as LogEntry
                logEntryViewHolder.titleTextView.text = logEntry.title
                logEntryViewHolder.titleTextView.contentDescription = logEntry.title
                if(logEntry.description.isEmpty()){
                    logEntryViewHolder.descriptionTextView.text = "-"
                }else{
                    logEntryViewHolder.descriptionTextView.text = logEntry.description
                }

                logEntryViewHolder.dateTextView.text = logEntry.dateTime
                logEntryViewHolder.containerLayout.setOnClickListener {
                    logEntryListener?.showLogDetails(logEntry.logId, logEntryViewHolder.adapterPosition)
                }

                logEntryViewHolder.moreActionView.setOnClickListener {
                    val popup = PopupMenu(logEntryViewHolder.moreActionView.context, logEntryViewHolder.moreActionView)
                    val inflater = popup.menuInflater
                    inflater.inflate(com.freshworks.smartlog.R.menu.menu_log, popup.menu)
                    popup.show()
                    popup.setOnMenuItemClickListener(object : PopupMenu.OnMenuItemClickListener {
                        override fun onMenuItemClick(item: MenuItem): Boolean {
                            when (item.itemId) {

                                com.freshworks.smartlog.R.id.edit -> {
                                    actionsListener?.edit(logEntry, logEntryViewHolder.adapterPosition)
                                }


                                com.freshworks.smartlog.R.id.delete -> {
                                    actionsListener?.delete(logEntry, logEntryViewHolder.adapterPosition)
                                }

                                com.freshworks.smartlog.R.id.move -> {

                                    actionsListener?.move(logEntry, logEntryViewHolder.adapterPosition)
                                }
                            }
                            return false
                        }
                    })
                }
            }
            else -> {
                val logBookViewHolder = holder as LogBookViewHolder
                val logBook = arraylist[position] as LogBook
                logBookViewHolder.logBookTitle.text = logBook.title
                logBookViewHolder.logBookDate.text = logBook.createdTime
                logBookViewHolder.cardView.setOnClickListener {
                    logBookListener?.showLogEntries(logBook.title)
                    //callbackListener?.deleteBook(logBook)
                }
                logBookViewHolder.moreActionView.setOnClickListener {
                    val popup = PopupMenu(logBookViewHolder.moreActionView.context, logBookViewHolder.moreActionView)
                    val inflater = popup.menuInflater
                    inflater.inflate(com.freshworks.smartlog.R.menu.menu_book, popup.menu)
                    popup.show()
                    popup.setOnMenuItemClickListener(object : PopupMenu.OnMenuItemClickListener {
                        override fun onMenuItemClick(item: MenuItem): Boolean {
                            when (item.itemId) {


                                com.freshworks.smartlog.R.id.delete -> {

                                    logBookListener?.deleteBook(logBook, logBookViewHolder.adapterPosition)
                                }

                                com.freshworks.smartlog.R.id.save_pdf -> {

                                    logBookListener?.saveAsPDF(logBook, logBookViewHolder.adapterPosition)
                                }
                            }
                            return false
                        }
                    })
                }
            }
        }
    }


    fun clear(){
        arraylist.clear()
    }

    fun addAll(elements : List<Any>){
        arraylist.addAll(elements)
    }

    fun add(item : Any?){
        arraylist.add(item)
    }

    fun add(pos : Int, item : Any?){
        arraylist.add(pos, item)
    }
    fun removeAt(pos : Int) : Any?{
        return arraylist.removeAt(pos)
    }

}
