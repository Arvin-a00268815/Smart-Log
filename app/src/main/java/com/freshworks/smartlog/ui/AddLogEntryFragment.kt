package com.freshworks.smartlog.ui

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.coursion.freakycoder.mediapicker.galleries.Gallery
import com.freshworks.smartlog.R
import com.freshworks.smartlog.Util
import com.freshworks.smartlog.repository.entity.LogAttachments
import com.freshworks.smartlog.viewmodel.MainActivityViewModel
//import com.zhihu.matisse.Matisse
//import com.zhihu.matisse.MimeType
//import com.zhihu.matisse.engine.impl.GlideEngine
import kotlinx.android.synthetic.main.fragment_add_log_entry.*
import java.io.File
import java.io.FileNotFoundException


/**
 * Created by arvin-2009 on Feb 2019.
 */
open class AddLogEntryFragment : Fragment() {


    var currentTime = 0L
    var fileUriList = ArrayList<String>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_add_log_entry, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val logBookTitle = arguments?.getString("logBookTitle")

        logbook_title.text = "Log Book : ".plus(logBookTitle)

        val viewModel = ViewModelProviders.of(this)[MainActivityViewModel::class.java]
        toolbar_title.text = "Add Log"

        viewModel.getDateTime().observe(this, Observer {
            val list = it as ArrayList<String>
            dateButton.text = list[0]
            timeButton.text = list[1]
            currentTime = list[2].toLong()
        })

        viewModel.onInsertLogEntry().observe(this, Observer {


            val list = ArrayList<LogAttachments>()
            if (fileUriList.size > 0) {
                    for (i in fileUriList) {
                        val logAttachments = LogAttachments(i, it!!.logId)
                        list.add(logAttachments)
                    }

                    viewModel.insertFiles(list)
                }

                progress_bar.visibility = View.GONE
                val intent = Intent()
                intent.putExtra("logEntry", it)
                targetFragment!!.onActivityResult(targetRequestCode, Activity.RESULT_OK, intent)
                activity!!.onBackPressed()

            })


        viewModel.onInsertFiles().observe(this, Observer {

            }
        )



        submit_button.setOnClickListener {

            val title = title_editText.text.toString()
            val desc = description_editText.text.toString()
            if(title_editText.length() > 0){
                progress_bar.visibility = View.VISIBLE
                title_textLayout.error = ""
                Handler().postDelayed(Runnable {
                    viewModel.insertLogEntry(logBookTitle!!, title, desc, currentTime)

                }, 2000)

            }else{
                title_textLayout.error = "Please enter title"
            }
        }


        //permissionCheck()
        imageAttachment.setOnClickListener {

            if(Util.permissionCheck(activity!!)) {
                openGallery()
            }

        }
        imageView.setOnClickListener {
            if(Util.permissionCheck(activity!!)) {
                openGallery()
            }

        }
    }


    fun openGallery(){


        val intent = Intent(context, Gallery::class.java)

        intent.putExtra("title", "Select Images")
// Mode 1 for both images and videos selection, 2 for images only and 3 for videos!
        intent.putExtra("mode", 2)
        intent.putExtra("maxSelection", 1) // Optional
        startActivityForResult(intent, 1)

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (requestCode == 1) {

            if (resultCode == Activity.RESULT_OK && data != null) {
                val selectionResult = data.getStringArrayListExtra("result")
                fileUriList.clear()
                selectionResult.forEach {
                    try {

                        val uriFromPath = Uri.fromFile(File(it))

                        fileUriList.add(uriFromPath.toString())

                        // Convert URI to Bitmap
                        val bm = BitmapFactory.decodeStream(activity!!.contentResolver.openInputStream(uriFromPath))
                        imageView.setImageBitmap(bm)
                    } catch (e: FileNotFoundException) {
                        e.printStackTrace()
                    }
                }
            }
        }


    }



}
