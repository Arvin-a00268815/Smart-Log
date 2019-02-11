package com.freshworks.smartlog.ui

import android.app.Activity
import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.Window

class TimePickerFragment : DialogFragment(), TimePickerDialog.OnTimeSetListener {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bundle = arguments
        val hour = bundle!!.getInt("hour")
        val minute = bundle.getInt("minute")
        val timePickerDialog = TimePickerDialog(context, this, hour, minute, true)

        timePickerDialog.setTitle("") // No i18n

        timePickerDialog.window!!.requestFeature(Window.FEATURE_NO_TITLE)
        return timePickerDialog
    }

    override fun onTimeSet(view: android.widget.TimePicker, hourOfDay: Int, minute: Int) {
        val intent = Intent()
        intent.putExtra("hour", hourOfDay)
        intent.putExtra("minute", minute)
        targetFragment!!.onActivityResult(1002, Activity.RESULT_OK, intent)
        dismiss()
    }
}