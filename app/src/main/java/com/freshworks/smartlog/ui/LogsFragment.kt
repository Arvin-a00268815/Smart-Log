package com.freshworks.smartlog.ui

import android.app.Activity
import android.arch.lifecycle.Observer
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import android.view.View
import com.freshworks.smartlog.R
import com.freshworks.smartlog.database.entity.LogEntry
import kotlinx.android.synthetic.main.fragment_list.*

/**
 * Created by arvin-2009 on Feb 2019.
 */
class LogsFragment : LogBooksFragment() {

    var logBookTitle  = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        logBookTitle = arguments?.getString("logBookTitle")!!
        toolbar_title.text = logBookTitle
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24px)
        toolbar.contentInsetStartWithNavigation = 0


        val dividerDecoration = DividerItemDecoration(context, LinearLayoutManager.VERTICAL)
        dividerDecoration.setDrawable(ContextCompat.getDrawable(context!!, R.drawable.line_divider)!!)

        recycler_view.addItemDecoration(dividerDecoration)

        viewModel.getLogEntries(logBookTitle).observe(this, android.arch.lifecycle.Observer {
            adapter.clear()
            adapter.addAll(it as ArrayList<LogEntry>)
            adapter.notifyDataSetChanged()

        })

        viewModel.onInsertLogEntry().observe(this, android.arch.lifecycle.Observer {

            adapter.add(0, it)
            adapter.notifyItemInserted(0)
            recycler_view.scrollToPosition(0)

        })

        viewModel.onDeleteLog().observe(this, Observer {
            adapter.removeAt(it!!)
            adapter.notifyItemRemoved(it)

        })



        adapter.logEntryListener = object : NAdapter.LogEntryListener {

            override fun showLogDetails(id : Long) {

                val logDetailsfragment = LogDetailsFragment()
                logDetailsfragment.setTargetFragment(this@LogsFragment, 2001)
                val bundle = Bundle()
                bundle.putLong("id", id)
                logDetailsfragment.arguments = bundle
                loadFragment(logDetailsfragment, "logDetails")
            }

        }

        adapter.actionsListener = object : NAdapter.ActionsListener{
            override fun edit(logEntry : LogEntry) {
                val addFragment = EditLogEntryFragment()
                addFragment.setTargetFragment(this@LogsFragment, 1111)
                val bundle = Bundle()
                bundle.putLong("logId" ,logEntry.logId)
                addFragment.arguments = bundle

                loadFragment(addFragment, "editLog")

            }

            override fun delete(logEntry : LogEntry, pos : Int) {
                viewModel.deleteLog(logEntry, pos)
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

            loadFragment(addLogEntryFragment, "addlog")

        }

        toolbar.setNavigationOnClickListener {
            activity!!.onBackPressed()
        }


    }

    private fun loadFragment(fragment : Fragment, flag : String){
        val fragmentManager = activity?.supportFragmentManager
        val fragmentTransaction = fragmentManager?.beginTransaction()
        fragmentTransaction?.add(R.id.content_frame, fragment)
        fragmentTransaction?.addToBackStack(flag)
        fragmentTransaction?.commit()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK){

            if(requestCode == 2001) {

                val logEntry = data!!.getSerializableExtra("logEntry") as LogEntry
                adapter.add(0, logEntry)
                adapter.notifyItemInserted(0)
                recycler_view.scrollToPosition(0)
            }else if( requestCode == 1222){

                val pos = data!!.getIntExtra("pos", -1)
                adapter.removeAt(pos)
                adapter.notifyItemRemoved(pos)
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


}