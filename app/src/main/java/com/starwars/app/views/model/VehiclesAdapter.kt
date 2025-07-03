package com.starwars.app.views.model

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.starwars.app.R

class VehiclesAdapter(private val context: Context, private val items: List<VehicleAdapterItem>):
    RecyclerView.Adapter<VehiclesAdapter.VehiclesViewHolder>() {

    class VehiclesViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private var sectionHeader: TextView? = null
        private var nameCell: TextView? = null
        private var cargoCell: TextView? = null
        private var speedCell: TextView? = null

        init {
            sectionHeader = view.findViewById(R.id.vehicle_class)
            nameCell = view.findViewById(R.id.name)
            cargoCell = view.findViewById(R.id.cargo)
            speedCell = view.findViewById(R.id.speed)
        }

        fun setHeader(header: VehicleAdapterItem.Header) {
            sectionHeader?.text = header.title
        }

        fun setItem(context: Context, item: VehicleAdapterItem.Content) {
            nameCell?.text = item.name
            cargoCell?.text = context.getString(R.string.common_capacity, item.capacity)
            speedCell?.text = context.getString(R.string.common_max_speed, item.speed)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is VehicleAdapterItem.Header -> 0
            is VehicleAdapterItem.Content -> 1
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VehiclesViewHolder {
        val view = when (viewType) {
            0 -> LayoutInflater.from(context).inflate(R.layout.vehicle_section_header, parent, false)
            else -> LayoutInflater.from(context).inflate(R.layout.vehicle_cell, parent, false)
        }
        return VehiclesViewHolder(view)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: VehiclesViewHolder, position: Int) {
        when (items[position]) {
            is VehicleAdapterItem.Header -> holder.setHeader(items[position] as VehicleAdapterItem.Header)
            is VehicleAdapterItem.Content -> holder.setItem(context, items[position] as VehicleAdapterItem.Content)
        }
    }

}

sealed class VehicleAdapterItem {
    data class Header(val title: String): VehicleAdapterItem()
    data class Content(val name: String, val capacity: String, val speed: String): VehicleAdapterItem()
}