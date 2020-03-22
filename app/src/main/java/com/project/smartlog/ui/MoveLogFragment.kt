package com.project.smartlog.ui

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomSheetDialogFragment
import android.support.v4.content.ContextCompat
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.project.smartlog.R
import com.project.smartlog.repository.entity.LogBook
import com.project.smartlog.repository.entity.LogEntry
import com.project.smartlog.viewmodel.MainActivityViewModel
import kotlinx.android.synthetic.main.fragment_move_log_layout.*
import kotlinx.android.synthetic.main.fragment_move_log_layout.view.*

class MoveLogFragment : BottomSheetDialogFragment(){

    var logEntry : LogEntry? = null
    var pos : Int = -1

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_move_log_layout, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        logEntry = arguments!!.getSerializable("logEntry")  as LogEntry
        pos = arguments!!.getInt("pos")
        recycler_view.layoutManager = LinearLayoutManager(context)
        val adapter = NewListAdapter()
        adapter.isMoveOperation = true
        recycler_view.adapter = adapter
        val dividerDecoration = DividerItemDecoration(context, LinearLayoutManager.VERTICAL)
        dividerDecoration.setDrawable(ContextCompat.getDrawable(context!!, R.drawable.line_divider)!!)

        recycler_view.addItemDecoration(dividerDecoration)

        val viewModel = ViewModelProviders.of(requireActivity())
            .get(MainActivityViewModel::class.java)

        viewModel.getLogBooksExcept(logEntry!!.logBookTitle).observe(this, Observer {
            adapter.clear()
            adapter.addAll(it as ArrayList<LogBook>)
            adapter.notifyDataSetChanged()

        })

        viewModel.onMoveLog().observe(this, Observer {
            view.progress_bar.visibility = View.VISIBLE
            val intent = Intent()
            intent.putExtra("pos", pos)
            targetFragment!!.onActivityResult(1222, Activity.RESULT_OK, intent)
            dismiss()
        })

        adapter.logMoveListener = object : NewListAdapter.LogMoveListener {
            override fun moveLogToThisBook(logBook: LogBook) {

                view.progress_bar.visibility = View.VISIBLE
                viewModel.moveLog(logEntry!!, logBook.title)
            }

        }

    }
}
