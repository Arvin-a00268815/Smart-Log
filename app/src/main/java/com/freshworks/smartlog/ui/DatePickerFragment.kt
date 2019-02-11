package com.freshworks.smartlog.ui

import android.app.Activity
import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.util.Log
import android.widget.DatePicker
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class DatePickerFragment : DialogFragment(), DatePickerDialog.OnDateSetListener {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val calendar = Calendar.getInstance(Locale.ENGLISH)
        val bundle = arguments
        val date = bundle!!.getString("date") // No i18n

        calendar.time = convertStringToDateFormat(date, "yyyy-MM-dd")

        return DatePickerDialog(
            activity!!, this, calendar.get(Calendar.YEAR), calendar.get(
                Calendar.MONTH
            ), calendar.get(Calendar.DATE)
        )
    }

    override fun onDateSet(view: DatePicker, year: Int, month: Int, day: Int) {

        val intent = Intent()
        intent.putExtra("day", day)
        intent.putExtra("year", year)
        intent.putExtra("month", month)
        targetFragment!!.onActivityResult(1001, Activity.RESULT_OK, intent)
        dismiss()
    }

    fun convertStringToDateFormat(dateStr: String, format: String): Date? {
        val toFormat = SimpleDateFormat(format, Locale.US)
        try {
            return toFormat.parse(dateStr)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return null
    }
}