package com.project.smartlog.ui

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.project.smartlog.R
import com.project.smartlog.repository.entity.LogAttachments
import com.project.smartlog.repository.entity.LogEntry
import com.project.smartlog.viewmodel.MainActivityViewModel
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

    var logId = 0L
    var pos = 0
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        logId = arguments?.getLong("id")!!
        pos = arguments?.getInt("id")!!

        viewModel = ViewModelProviders.of(this)[MainActivityViewModel::class.java]
        toolbar_title.text = "Log Details"
        viewModel.onGetLogDetails().observe(this , Observer {
            title.text = it!!.title
            description.text = it?.description
            val timeTemp = it?.dateTime.split(" ")
            dateButton.text = timeTemp[0]
            timeButton.text = timeTemp[1]
            logBook_title_content.text = it!!.logBookTitle
            logEntry = it

        })
        viewModel.getLogDetails(logId)

        viewModel.getImageFiles(logId).observe(this, Observer {
            val list = it as ArrayList<LogAttachments>

            for (attachement in list ){

                val bm = BitmapFactory.decodeStream(activity!!.contentResolver.openInputStream(Uri.parse(attachement.path)))
                imageView.setImageBitmap(bm)
            }
        })

        viewModel.onDeleteLog().observe(this, Observer {

            val intent = Intent()
            intent.putExtra("pos", pos)
            targetFragment?.onActivityResult(1222, Activity.RESULT_OK,  intent)
                activity!!.onBackPressed()
        })

        delete_log.setOnClickListener {

            openAlertDialog()

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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK && requestCode == 1111){
            viewModel.getLogDetails(logId)
        }
    }

    fun openAlertDialog(){
        val builder = AlertDialog.Builder(activity!!)
        builder.setMessage("Are you sure you want to delete?")
        builder.setPositiveButton("Yes") { p0, p1 ->
            viewModel.deleteLog(logEntry!!, -1)
            Toast.makeText(context, "Deleted successfully", Toast.LENGTH_LONG).show()
        }
        builder.setNegativeButton("No", null)
        builder.show()
    }


}
