package com.project.smartlog.ui

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.project.smartlog.repository.entity.LogEntry
import com.project.smartlog.viewmodel.MainActivityViewModel
import io.reactivex.Completable
import io.reactivex.CompletableObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_add_log_entry.*

/**
 * Created by arvin-2009 on Feb 2019.
 */
class EditLogEntryFragment : AddLogEntryFragment() {

    var logEntry: LogEntry? = null

    var year = 0
    var month = 0
    var day = 0
    var hour = 0
    var minute = 0

    var viewModel : MainActivityViewModel ? = null
    var pos = -1
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val logId = arguments?.getLong("logId")

        pos = arguments?.getInt("pos")!!
        viewModel = ViewModelProviders.of(requireActivity())[MainActivityViewModel::class.java]

        toolbar_title.text = "Edit Log"
        var createdTime = 0L
        viewModel!!.onGetLogDetails().observe(this, Observer {
            logEntry = it
            title_editText.setText(it!!.title)
            description_editText.setText(it.description)
            logbook_title.text = "Log Book : ".plus(logEntry!!.logBookTitle)
            createdTime = it.createdTime
            val timeTemp = it.dateTime.split(" ")
            dateButton.text = timeTemp[0]
            timeButton.text = timeTemp[1]


            viewModel!!.getDateTime(it.createdTime).observe(this, Observer {

                year = it!![0].toInt()
                month = it[1].toInt()
                day = it[2].toInt()
                hour = it[3].toInt()
                minute = it[4].toInt()
            })

        })
        viewModel!!.getLogDetails(logId!!)

        toolbar.setNavigationOnClickListener {
            activity!!.onBackPressed()
        }
        dateButton.setOnClickListener {
            val datePicker = DatePickerFragment()
            datePicker.setTargetFragment(this, 1001)
            val bundle = Bundle()
            bundle.putString("date", dateButton.getText().toString())
            datePicker.arguments = bundle
            datePicker.show(fragmentManager, "date")

        }

        timeButton.setOnClickListener {
            val fromTimePicker = TimePickerFragment()
            fromTimePicker.setTargetFragment(this, 1002)
            val bundle = Bundle()
            val list = timeButton.text.split(":")
            bundle.putInt("hour", list[0].toInt())
            bundle.putInt("minute", list[1].toInt())
            fromTimePicker.arguments = bundle
            fromTimePicker.show(fragmentManager, "time")

        }


        submit_button.setOnClickListener {

            val title = title_editText.text.toString()
            val desc = description_editText.text.toString()
            if (title_editText.length() > 0) {
                progress_bar.visibility = View.VISIBLE
                title_textLayout.error = ""

                logEntry!!.title = title
                logEntry!!.description = desc

                viewModel!!.getDate(year, month, day, hour, minute).observe(this, Observer {

                    logEntry!!.createdTime = it as Long

                    Completable.fromAction {

                        viewModel!!.updateLogDetails(logEntry!!)

                    }.observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(object : CompletableObserver {
                            override fun onComplete() {
                                progress_bar.visibility = View.GONE
                                Toast.makeText(context, "Updated successfully", Toast.LENGTH_SHORT).show()
                                val intent = Intent()
                                intent.putExtra("isUpdated", true)
                                intent.putExtra("pos", pos)
                                intent.putExtra("logEntry", logEntry)

                                targetFragment!!.onActivityResult(1111, Activity.RESULT_OK, intent)
                                activity!!.onBackPressed()
                            }


                            override fun onSubscribe(d: Disposable) {

                            }

                            override fun onError(e: Throwable) {
                                Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show()
                            }

                        })
                })

            } else {
                title_textLayout.error = "Please enter title"
            }
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                1001 -> {
                    year = data!!.getIntExtra("year", 2000)
                    month = data.getIntExtra("month", 2000)
                    day = data.getIntExtra("day", 2000)
                    dateButton.text = year.toString().plus("-").plus(month + 1 ).plus("-").plus(day)
                }

                1002 -> {
                    hour = data!!.getIntExtra("hour", 1)
                    minute = data.getIntExtra("minute", 0)
                    timeButton.text = hour.toString().plus(":").plus(minute).plus(":").plus("0")
                }
            }
        }
    }

}
