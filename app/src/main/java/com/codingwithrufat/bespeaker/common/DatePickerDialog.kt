package com.codingwithrufat.bespeaker.common

import android.app.Activity
import android.app.DatePickerDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import androidx.annotation.RequiresApi
import java.util.*

object DatePickerDialog {

    private var datePicker: DatePickerDialog? = null

    @RequiresApi(Build.VERSION_CODES.N)
    fun showAndListenDatePicker(
        activity: Activity,
        triple: (Triple<String, String, String>) -> Unit
    ) {
        val mCalendar = Calendar.getInstance()
        val year: Int = mCalendar.get(Calendar.YEAR)
        val month: Int = mCalendar.get(Calendar.MONTH)
        val dayOfMonth: Int = mCalendar.get(Calendar.DAY_OF_MONTH)

        if (datePicker == null) {
            datePicker = DatePickerDialog(
                activity,
                android.R.style.Theme_Holo_Light_Dialog,
                { view, year, month, dayOfMonth ->
                    triple(
                        Triple(
                            "$year",
                            if (month + 1 < 10) "0${month + 1}" else "${month + 1}",
                            if (dayOfMonth < 10) "0${dayOfMonth}" else "$dayOfMonth"
                        )
                    )
                },
                year,
                month,
                dayOfMonth
            )
            datePicker?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }

        datePicker?.show()
    }
}

