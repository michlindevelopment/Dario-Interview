package com.michlindev.dariointerview.fragments

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.michlindev.dariointerview.Movie
import com.michlindev.dariointerview.databinding.FragmentItemBinding


class MovieListRecyclerViewAdapter(private val values: ArrayList<Movie>, private val itemClickListener: OnItemClickListener) :
    RecyclerView.Adapter<MovieListRecyclerViewAdapter.ViewHolder>() {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding = FragmentItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        context = parent.context
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.bind(item,position,context,itemClickListener)
    }

    override fun getItemCount(): Int = values.size

    class ViewHolder(private val itemBinding: FragmentItemBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(movie: Movie, position: Int, context:Context, clickListener: OnItemClickListener) {

            Glide.with(context).load(movie.posterUrl).into(itemBinding.imageViewMovieIcon)
            itemBinding.textViewMovieName.text = movie.title
            itemView.setOnClickListener {
                clickListener.onItemClicked(position)
            }
        }
    }
}

interface OnItemClickListener {
    fun onItemClicked(position: Int)
}


