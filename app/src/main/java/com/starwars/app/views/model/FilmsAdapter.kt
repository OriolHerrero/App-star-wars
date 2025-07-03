package com.starwars.app.views.model

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.starwars.app.R

class FilmsAdapter(private val context: Context, private val items: List<Film>, private val listener: ItemClickedListener):
    RecyclerView.Adapter<FilmsAdapter.FilmsViewHolder>() {

    class FilmsViewHolder(view: View): RecyclerView.ViewHolder(view) {

        private var titleCell: TextView? = null
        private var numberCell: TextView? = null
        private var directorCell: TextView? = null

        init {
            titleCell = view.findViewById(R.id.title)
            numberCell = view.findViewById(R.id.number)
            directorCell = view.findViewById(R.id.director)
        }

        fun setFilm(context: Context, film: Film) {
            titleCell?.text = film.title
            numberCell?.text = context.getString(R.string.film_count, film.episode_id)
            directorCell?.text = context.getString(R.string.directed, film.director)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmsViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.film_cell, parent, false)
        return FilmsViewHolder(view)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: FilmsViewHolder, position: Int) {
        holder.setFilm(context, items[position])
        holder.itemView.setOnClickListener { listener.onItemClick(items[position]) }
    }

    interface ItemClickedListener {
        fun onItemClick(film: Film)
    }
}