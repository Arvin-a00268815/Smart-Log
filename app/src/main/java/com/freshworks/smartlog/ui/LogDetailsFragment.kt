package com.freshworks.smartlog.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.freshworks.smartlog.R
import com.freshworks.smartlog.database.entity.LogAttachments
import com.freshworks.smartlog.database.entity.LogEntry
import com.freshworks.smartlog.viewmodel.MainActivityViewModel
import kotlinx.android.synthetic.main.fragment_log_details.*

/**
 * Created by arvin-2009 on Feb 2019.
 */
class LogDetailsFragment : Fragment() {

    var logEntry : LogEntry ? = null
    lateinit var viewModel: MainActivityViewModel


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_log_details, container, false)
        setHasOptionsMenu(true)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val logId = arguments?.getLong("id")

        viewModel = ViewModelProviders.of(this)[MainActivityViewModel::class.java]
        toolbar_title.text = "Log Details"
        viewModel.getLogDetails(logId!!).observe(this , Observer {
            title.text = it!!.title
            description.text = it?.description
            val timeTemp = it?.dateTime.split(" ")
            dateButton.text = timeTemp[0]
            timeButton.text = timeTemp[1]
            logBook_title_content.text = it!!.logBookTitle
            logEntry = it

        })

//        viewModel.updateLogDetails().observe(this, Observer {
//            Log.e("updated", "=="+it!!.description)
//        })


        viewModel.getImageFiles(logId).observe(this, Observer {
            val list = it as ArrayList<LogAttachments>

            for (attachement in list ){

                val bm = BitmapFactory.decodeStream(activity!!.contentResolver.openInputStream(Uri.parse(attachement.path)))
                imageView.setImageBitmap(bm)
            }
        })

        viewModel.onDeleteLog().observe(this, Observer {

                activity!!.onBackPressed()
        })

        delete_log.setOnClickListener {
            viewModel.deleteLog(logEntry!!, -1)
        }

        edit_log.setOnClickListener {

            val addFragment = EditLogEntryFragment()
            addFragment.setTargetFragment(this, 1111)
            val bundle = Bundle()
            bundle.putLong("logId" ,logEntry!!.logId)
            addFragment.arguments = bundle

            (activity as MainActivity).loadFragment(addFragment, "addFragment")
        }

        toolbar.setNavigationOnClickListener {
            activity!!.onBackPressed()
        }

    }


}
