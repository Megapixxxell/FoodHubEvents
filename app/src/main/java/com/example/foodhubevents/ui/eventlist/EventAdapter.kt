package com.example.foodhubevents.ui.eventlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.foodhubevents.data.Event
import com.example.foodhubevents.R
import kotlinx.android.synthetic.main.li_event.view.*

class EventAdapter(
    private val onEventClick: (Event) -> Unit = {}
) : RecyclerView.Adapter<EventHolder>() {

    private var eventList: List<Event> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventHolder =
        parent
            .inflate(R.layout.li_event)
            .let { EventHolder(it, onEventClick) }

    private fun ViewGroup.inflate(resource: Int): View =
        LayoutInflater.from(context).inflate(resource, this, false)

    override fun getItemCount(): Int {
        return eventList.size
    }

    override fun onBindViewHolder(holder: EventHolder, position: Int) {
        holder.bind(eventList[position])
    }

    fun addData(list: List<Event>) {
        eventList = list
        notifyDataSetChanged()
    }
}

class EventHolder(itemView: View, private val onEventClick: (Event) -> Unit) : RecyclerView.ViewHolder(itemView) {

    fun bind(item: Event) {
        itemView.tv_title.text = item.type
        itemView.tv_start_time.text = item.starts + ":00"
        itemView.tv_end_time.text = item.ends + ":00"
        itemView.setOnClickListener { onEventClick(item) }
    }
}

