package com.freshworks.smartlog.ui

import android.app.Activity
import android.arch.lifecycle.Observer
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.View
import android.widget.Toast
import com.freshworks.smartlog.R
import com.freshworks.smartlog.Util
import com.freshworks.smartlog.database.entity.LogBook
import com.freshworks.smartlog.database.entity.LogEntry
import io.reactivex.Completable
import io.reactivex.CompletableObserver
import io.reactivex.Maybe
import io.reactivex.MaybeObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_list.*
import java.util.concurrent.Callable
import kotlin.collections.ArrayList


/**
 * Created by arvin-2009 on Feb 2019.
 */
open class LogBooksFragment : ParentFragment() {


    var pdfLogBookTitile = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        empty_textview.setText(R.string.no_logbooks_entries)
        val type = arguments?.getString("type")
        toolbar_title.setText(type)
        toolbar.setNavigationIcon(R.drawable.ic_outline_library_books_24px)
        toolbar.contentInsetStartWithNavigation = 0

        viewModel.onGetLogBooks().observe(this, android.arch.lifecycle.Observer {
            adapter.clear()
            adapter.addAll(it as ArrayList<LogBook>)
            adapter.notifyDataSetChanged()
            checkEmptyState()

        })
        viewModel.getLogBooks()

        viewModel.onDeleteBook().observe(this, android.arch.lifecycle.Observer {
            adapter.removeAt(it!!)
            adapter.notifyItemRemoved(it)
            checkEmptyState()
        })

        swipe_refresh.setOnRefreshListener {

            viewModel.getLogBooks()
            swipe_refresh.isRefreshing = false
        }



        fab.setOnClickListener {

            val fragment = AddLogBookDialogFragment()
            fragment.setTargetFragment(this@LogBooksFragment, 1201)
            fragment.show(fragmentManager, "createbook")
        }


        viewModel.onGetLogEntriesForPdf().observe(this@LogBooksFragment, Observer {

            Util.createPdf(pdfLogBookTitile)
            val hyphen = "--"
            for (logEntry in it as ArrayList<LogEntry>){
                val para = hyphen.plus(logEntry.title).plus("\n---").plus(logEntry.description).plus(" ").plus(logEntry.dateTime)

                Util.addContent(para)
            }
            Util.endContent()
            Util.openFile(activity!!, pdfLogBookTitile)

        })

        adapter.logBookListener = object : NewListAdapter.LogBookListener {

            override fun deleteBook(logBook: LogBook, pos : Int) {
                openAlertDialog(logBook, pos)
            }

            override fun saveAsPDF(logBook: LogBook, pos: Int) {

                pdfLogBookTitile = logBook.title
                viewModel.getLogEntriesForPdf(logBook.title)

            }


            override fun showLogEntries(logBookTitle : String) {
                val fragment = LogsFragment()
                fragment.setTargetFragment(this@LogBooksFragment, 1001)
                val bundle = Bundle()
                bundle.putString("logBookTitle", logBookTitle)
                fragment.arguments =  bundle
                (activity as MainActivity).loadFragment(fragment, "entries")
            }

        }

        help.setOnClickListener {
            (activity as MainActivity).loadFragment(HelpFragment(), "help", true)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if(resultCode == Activity.RESULT_OK) {

            if (requestCode == 1201) {
                val logBook = data?.getSerializableExtra("logBook") as LogBook
                adapter.add(0, logBook)
                adapter.notifyItemInserted(0)
                recycler_view.scrollToPosition(0)
                checkEmptyState()

            }
        }
    }

    fun openAlertDialog(logBook: LogBook, pos :Int){
        val builder = AlertDialog.Builder(activity!!)
        builder.setMessage("Are you sure you want to delete?")
        builder.setPositiveButton("Yes") { p0, p1 ->
            viewModel.deleteLogBook(logBook, pos)
            Toast.makeText(context, "Deleted successfully", Toast.LENGTH_LONG).show()
        }
        builder.setNegativeButton("No", null)
        builder.show()
    }




}
