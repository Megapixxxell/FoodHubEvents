package com.example.foodhubevents.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.foodhubevents.R
import com.example.foodhubevents.api.EventsService
import com.example.foodhubevents.data.Event
import com.example.foodhubevents.ui.eventlist.EVENT_ID
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.ac_events_detail.*
import org.koin.android.ext.android.inject

class EventsDetailActivity : AppCompatActivity() {

    private val eventService: EventsService by inject()

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ac_events_detail)

        val id = intent.getLongExtra(EVENT_ID, 0)
        eventService.getEventFromId(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result ->
                    fillFields(result)
                },
                { error ->
                    Toast.makeText(this, error.message, Toast.LENGTH_SHORT).show()
                    error.printStackTrace()
                }
            )
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.dial_number -> {
                dialPhoneNumber()
                true
            }
            R.id.send_mail -> {
                sendMail()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun sendMail() {
        if (text_email_detail.text != "") {
            val intent = Intent(Intent.ACTION_SENDTO)
            intent.data = Uri.parse("mailto:")
            intent.putExtra(Intent.EXTRA_EMAIL, text_email_detail.text)
            intent.putExtra(Intent.EXTRA_SUBJECT, "Event in FoodHub")
            if (intent.resolveActivity(this.packageManager) != null) {
                startActivity(intent)
            }
        }
    }

    private fun dialPhoneNumber() {
        if (text_phone_detail.text != "") {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse(
                "tel:" + text_phone_detail.text
            )
            startActivity(intent)
        }
    }

    private fun fillFields(event: Event) {
        tv_title_detail.text = event.type
        text_start_detail.text = event.starts + ":00"
        text_end_detail.text = event.ends + ":00"
        text_name_detail.text = event.name
        text_surname_detail.text = event.lastname
        text_email_detail.text = event.email
        text_phone_detail.text = event.phone
    }
}


