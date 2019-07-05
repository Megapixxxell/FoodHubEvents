package com.example.foodhubevents.ui.eventlist

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.foodhubevents.R
import com.example.foodhubevents.api.EventsService
import com.example.foodhubevents.ui.EventsDetailActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.ac_events_list.*
import org.koin.android.ext.android.inject
import java.text.SimpleDateFormat
import java.util.*

const val EVENT_ID: String = "EVENT_ID"

class EventsListActivity : AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener {

    private val eventService: EventsService by inject()

    private var cal: Calendar = Calendar.getInstance()

    private val dateSetListener = DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
        cal.set(Calendar.YEAR, year)
        cal.set(Calendar.MONTH, monthOfYear)
        cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
        val myFormat = "dd-MM-yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        val pickedDate = (sdf.format(cal.time))
        getDateEventList(pickedDate)
    }

    private val eventAdapter = EventAdapter {
        val detailEventIntent = Intent(this, EventsDetailActivity::class.java)
        detailEventIntent.putExtra(EVENT_ID, it.id)
        startActivity(detailEventIntent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ac_events_list)

        getAllEventList()

        recycler_event.layoutManager = LinearLayoutManager(this)
        recycler_event.adapter = eventAdapter

//        val intent = Intent(this, AddFormActivity::class.java)
        val intent = Intent(this, EventsDetailActivity::class.java)

        fab.setOnClickListener { startActivity(intent) }

        refresher.setOnRefreshListener(this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.pick_date -> {
                showPickDateDialog()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showPickDateDialog() {
        DatePickerDialog(
            this, dateSetListener,
            cal.get(Calendar.YEAR),
            cal.get(Calendar.MONTH),
            cal.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    override fun onRefresh() {
        getAllEventList()
        refresher.isRefreshing = false
    }

    @SuppressLint("CheckResult")
    private fun getAllEventList() {
        eventService.getAllEvent()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result ->
                    eventAdapter.addData(result)
                },
                { error ->
                    Toast.makeText(this, error.message, Toast.LENGTH_SHORT).show()
                    error.printStackTrace()
                }
            )
    }

    @SuppressLint("CheckResult")
    private fun getDateEventList(date: String) {
        Toast.makeText(this, date, Toast.LENGTH_SHORT).show()
        eventService.getEventFromDay(date)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result ->
                    eventAdapter.addData(result)
                },
                { error ->
                    Toast.makeText(this, error.message, Toast.LENGTH_SHORT).show()
                    error.printStackTrace()
                }
            )
    }

}
