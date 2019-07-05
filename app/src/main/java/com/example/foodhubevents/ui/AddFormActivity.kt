package com.example.foodhubevents.ui

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.foodhubevents.R
import com.example.foodhubevents.api.EventsService
import com.example.foodhubevents.data.PostEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.ac_add_form.*
import org.koin.android.ext.android.inject
import java.text.SimpleDateFormat
import java.util.*

private const val START_TIME_DIALOG = 1
private const val END_TIME_DIALOG = 2
private const val DATE_DIALOG = 3

class AddFormActivity : AppCompatActivity() {

    private val eventService: EventsService by inject()

    private var cal: Calendar = Calendar.getInstance()

    private var startHour = 0
    private var endHour = 0

    private val dateSetListener = DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
        cal.set(Calendar.YEAR, year)
        cal.set(Calendar.MONTH, monthOfYear)
        cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
        val myFormat = "dd-MM-yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        form_date.setText(sdf.format(cal.time))
    }

    private val timeStartSetListener = TimePickerDialog.OnTimeSetListener { _, hour, minute ->
        cal.set(Calendar.HOUR, hour)
        cal.set(Calendar.MINUTE, minute)
        startHour = hour
        val myFormat = "HH:mm"
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        form_start_time.setText(sdf.format(cal.time))
    }

    private val timeEndSetListener = TimePickerDialog.OnTimeSetListener { _, hour, minute ->
        cal.set(Calendar.HOUR, hour)
        cal.set(Calendar.MINUTE, minute)
        endHour = hour
        val myFormat = "HH:mm"
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        form_end_time.setText(sdf.format(cal.time))
    }

//    private val evenDataSource: EventDataSource by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ac_add_form)


        form_btn_add.setOnClickListener {
            if (checkFieldsRight()) {
//                putNewEventToSP()
                insertEventToServer()
            }
        }

        form_date.setOnFocusChangeListener { _, isChecked ->
            if (isChecked)
                showPickDialog(DATE_DIALOG)
        }

        form_start_time.setOnFocusChangeListener { _, isChecked ->
            if (isChecked)
                showPickDialog(START_TIME_DIALOG)
        }

        form_end_time.setOnFocusChangeListener { _, isChecked ->
            if (isChecked)
                showPickDialog(END_TIME_DIALOG)
        }
    }

    private fun showPickDialog(id: Int) {
        when (id) {
            DATE_DIALOG -> {
                DatePickerDialog(
                    this, dateSetListener,
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)
                ).show()
            }
            START_TIME_DIALOG -> {
                TimePickerDialog(
                    this, timeStartSetListener,
                    cal.get(Calendar.HOUR),
                    cal.get(Calendar.MINUTE), true
                ).show()
            }
            END_TIME_DIALOG -> {
                TimePickerDialog(
                    this, timeEndSetListener,
                    cal.get(Calendar.HOUR),
                    cal.get(Calendar.MINUTE), true
                ).show()
            }
        }
    }

    private fun checkFieldsRight(): Boolean {
        var isChecked = true
        if (TextUtils.isEmpty(form_event_name.text.toString())) {
            form_event_name.error = getString(R.string.text_error)
            isChecked = false
        }
        if (TextUtils.isEmpty(form_date.text.toString())) {
            form_date.error = getString(R.string.text_error)
            isChecked = false
        }
        if (TextUtils.isEmpty(form_start_time.text.toString())) {
            form_start_time.error = getString(R.string.text_error)
            isChecked = false
        }
        if (TextUtils.isEmpty(form_end_time.text.toString())) {
            form_end_time.error = getString(R.string.text_error)
            isChecked = false
        }
        if (TextUtils.isEmpty(form_firstname.text.toString())) {
            form_firstname.error = getString(R.string.text_error)
            isChecked = false
        }
        if (TextUtils.isEmpty(form_lastname.text.toString())) {
            form_lastname.error = getString(R.string.text_error)
            isChecked = false
        }
        if (TextUtils.isEmpty(form_date.text.toString())) {
            form_date.error = getString(R.string.text_error)
            isChecked = false
        }
        if (TextUtils.isEmpty(form_phone.text.toString())) {
            form_phone.error = getString(R.string.text_error)
            isChecked = false
        }
        if (TextUtils.isEmpty(form_email.text.toString())) {
            form_email.error = getString(R.string.text_error)
            isChecked = false
        }
        if (TextUtils.isEmpty(form_event_name.text.toString())) {
            form_event_name.error = getString(R.string.text_error)
            isChecked = false
        }
        return isChecked
    }
//
//    private fun putNewEventToSP() {
//
//        val eventName = form_event_name.text.toString()
//        val start = form_date.text.toString() + " " + startHour
//        val end = form_date.text.toString() + " " + endHour
//        val name = form_firstname.text.toString()
//        val surname = form_lastname.text.toString()
//        val phone = form_phone.text.toString()
//        val email = form_email.text.toString()
//
//        val newEvent = Event (0,eventName, email, name, surname, start, end, phone)
////        val newEvent = PostEvent (eventName, email, name, surname, start, end, phone)
//
//        val listEvents = evenDataSource.events.toMutableList()
//        listEvents.add(newEvent)
//        evenDataSource.events = listEvents
//
//    }

    @SuppressLint("CheckResult")
    private fun insertEventToServer() {
        val eventName = form_event_name.text.toString()
        val start = form_date.text.toString() + " " + startHour
        val end = form_date.text.toString() + " " + endHour
        val name = form_firstname.text.toString()
        val surname = form_lastname.text.toString()
        val phone = form_phone.text.toString()
        val email = form_email.text.toString()

//        val newEvent = Event (0,eventName, email, name, surname, start, end, phone)
        val newEvent = PostEvent(eventName, email, name, surname, start, end, phone)
        eventService.postEvent(newEvent)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({

                Toast.makeText(this, "Мероприятие создано!", Toast.LENGTH_SHORT).show()
                clearField()
            }, {
                Toast.makeText(this, it.message.toString(), Toast.LENGTH_SHORT).show()
            })
    }

    private fun clearField() {
        form_event_name.setText("")
        form_date.setText("")
        form_start_time.setText("")
        form_end_time.setText("")
        form_firstname.setText("")
        form_lastname.setText("")
        form_phone.setText("")
        form_email.setText("")
    }
//        val listEvents = evenDataSource.events.toMutableList()
//        listEvents.add(newEvent)
//        evenDataSource.events = listEvents

}

