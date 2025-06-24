package com.starwars.app.views.model

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.starwars.app.R

class PlanetsAdapter(private val context: Context, private val items: List<Planet>): RecyclerView.Adapter<PlanetsAdapter.PlanetsViewHolder>() {

    class PlanetsViewHolder(view: View): RecyclerView.ViewHolder(view) {

        private var nameCell: TextView? = null
        private var inhabitantsCell: TextView? = null
        private var diameterCell: TextView? = null

        init {
            nameCell = view.findViewById(R.id.name)
            inhabitantsCell = view.findViewById(R.id.inhabitants)
            diameterCell = view.findViewById(R.id.diameter)
        }

        fun setPlanet(context: Context, planet: Planet) {
            nameCell?.text = planet.name
            inhabitantsCell?.text = planet.inhabitants.toIntOrNull()?.let { inhabitants-> context.getString(R.string.inhabitants, inhabitants) } ?:
                context.getString(R.string.inhabitants_unknown)
            diameterCell?.text = context.getString(R.string.diameter, planet.diameter)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlanetsViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.main_cell, parent, false)
        return PlanetsViewHolder(view)
    }

    override fun onBindViewHolder(holder: PlanetsViewHolder, position: Int) {
        holder.setPlanet(context, items[position])
    }

    override fun getItemCount(): Int = items.count()
}

