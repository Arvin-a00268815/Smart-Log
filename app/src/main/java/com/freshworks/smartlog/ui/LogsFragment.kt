package com.freshworks.smartlog.ui

import android.app.Activity
import android.arch.lifecycle.Observer
import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.freshworks.smartlog.R
import com.freshworks.smartlog.repository.entity.LogEntry
import kotlinx.android.synthetic.main.fragment_list.*

/**
 * Created by arvin-2009 on Feb 2019.
 */
class LogsFragment : ParentFragment() {

    var logBookTitle  = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        help.visibility = View.GONE
        empty_textview.setText(R.string.no_log_entries)


        logBookTitle = arguments?.getString("logBookTitle")!!
        toolbar_title.text = logBookTitle
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24px)
        toolbar.contentInsetStartWithNavigation = 0



        val dividerDecoration = DividerItemDecoration(context, LinearLayoutManager.VERTICAL)
        dividerDecoration.setDrawable(ContextCompat.getDrawable(context!!, R.drawable.line_divider)!!)

        recycler_view.addItemDecoration(dividerDecoration)

        viewModel.onGetLogEntries().observe(this, android.arch.lifecycle.Observer {
            adapter.clear()
            adapter.addAll(it as ArrayList<LogEntry>)
            adapter.notifyDataSetChanged()
            checkEmptyState()

        })
        viewModel.getLogEntries(logBookTitle)

        viewModel.onInsertLogEntry().observe(this, android.arch.lifecycle.Observer {

            adapter.add(0, it)
            adapter.notifyItemInserted(0)
            recycler_view.scrollToPosition(0)
            checkEmptyState()

        })

        viewModel.onDeleteLog().observe(this, Observer {
            adapter.removeAt(it!!)
            adapter.notifyItemRemoved(it)
            checkEmptyState()

        })

        swipe_refresh.setOnRefreshListener {
            viewModel.getLogEntries(logBookTitle)
            swipe_refresh.isRefreshing = false
        }



        adapter.logEntryListener = object : NewListAdapter.LogEntryListener {

            override fun showLogDetails(id : Long, pos : Int) {

                val logDetailsfragment = LogDetailsFragment()
                logDetailsfragment.setTargetFragment(this@LogsFragment, 1222)
                val bundle = Bundle()
                bundle.putLong("id", id)
                bundle.putInt("pos", pos)
                logDetailsfragment.arguments = bundle
                (activity as MainActivity).loadFragment(logDetailsfragment, "logDetails")
            }

        }

        adapter.actionsListener = object : NewListAdapter.ActionsListener{

            override fun edit(logEntry : LogEntry, pos : Int) {
                val editLogFragment = EditLogEntryFragment()
                editLogFragment.setTargetFragment(this@LogsFragment, 1111)
                val bundle = Bundle()
                bundle.putLong("logId" ,logEntry.logId)
                bundle.putInt("pos", pos)
                editLogFragment.arguments = bundle

                (activity as MainActivity).loadFragment(editLogFragment, "editLog")

            }

            override fun delete(logEntry : LogEntry, pos : Int) {
                openAlertDialog(logEntry, pos)
            }

            override fun move(logEntry: LogEntry, pos : Int) {

                val moveFragment = MoveLogFragment()
                moveFragment.setTargetFragment(this@LogsFragment, 1222)
                val bundle = Bundle()
                bundle.putSerializable("logEntry", logEntry)
                bundle.putInt("pos", pos)
                moveFragment.arguments = bundle
                moveFragment.show(fragmentManager, "log")
            }
        }

        fab.setOnClickListener {

            val addLogEntryFragment = AddLogEntryFragment()
            addLogEntryFragment.setTargetFragment(this@LogsFragment, 2001)
            val bundle = Bundle()
            bundle.putString("logBookTitle", logBookTitle)
            addLogEntryFragment.arguments = bundle

            (activity as MainActivity).loadFragment(addLogEntryFragment, "addlog")

        }

        toolbar.setNavigationOnClickListener {
            activity!!.onBackPressed()
        }


    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK){

            if(requestCode == 2001) {

                val logEntry = data!!.getSerializableExtra("logEntry") as LogEntry
                adapter.add(0, logEntry)
                adapter.notifyItemInserted(0)
                recycler_view.scrollToPosition(0)
                checkEmptyState()
            }else if( requestCode == 1222){ // move & delete

                val pos = data!!.getIntExtra("pos", -1)
                adapter.removeAt(pos)
                adapter.notifyItemRemoved(pos)
                checkEmptyState()
            }else if( requestCode == 1111){ // edit

                val pos = data!!.getIntExtra("pos", -1)
                val logEntry = data!!.getSerializableExtra("logEntry")
                adapter.arraylist.set(pos, logEntry)
                adapter.notifyItemChanged(pos)
            }

        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id = item!!.itemId

        if(id == android.R.id.home){
            activity!!.onBackPressed()

            return true
        }
        return false
    }

    fun openAlertDialog(logEntry : LogEntry, pos :Int){
        val builder = AlertDialog.Builder(activity!!)
        builder.setMessage("Are you sure you want to delete?")
        builder.setPositiveButton("Yes") { p0, p1 ->
            viewModel.deleteLog(logEntry, pos)
            Toast.makeText(context, "Deleted successfully", Toast.LENGTH_LONG).show()
        }
        builder.setNegativeButton("No", null)
        builder.show()
    }


}
