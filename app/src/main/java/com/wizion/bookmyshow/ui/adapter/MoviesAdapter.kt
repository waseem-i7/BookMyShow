package com.wizion.bookmyshow.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.wizion.bookmyshow.interfaces.ItemClickListener
import com.wizion.bookmyshow.R
import com.wizion.bookmyshow.data.local.entity.Movie

class MoviesAdapter(private val context: Context,private val listener : ItemClickListener) : RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder>() {

    private val allMovies = ArrayList<Movie>()

    inner class MoviesViewHolder(itemView: View) : ViewHolder(itemView) {
        val movieTitle: TextView = itemView.findViewById(R.id.movieTitle)
        val releaseDate: TextView = itemView.findViewById(R.id.releaseDate)
        val avgVoting: TextView = itemView.findViewById(R.id.avgVoting)
        val totalVotes: TextView = itemView.findViewById(R.id.totalVotes)
        val moviePoster: ImageView = itemView.findViewById(R.id.moviePoster)
        val progressBar: ProgressBar = itemView.findViewById(R.id.progressBar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        val viewHolder =MoviesViewHolder(LayoutInflater.from(context).inflate(R.layout.movie_item_layout,parent,false))
        viewHolder.itemView.setOnClickListener{
            listener.onItemClick(allMovies[viewHolder.adapterPosition])
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        val currentNote = allMovies[position]
        holder.movieTitle.text= currentNote.title
        holder.releaseDate.text = currentNote.releaseDate
        holder.avgVoting.text = currentNote.voteAverage.toString()
        holder.totalVotes.text = currentNote.voteCount.toString()
//        Glide.with(holder.itemView.context).load(currentNote.posterPath).listener(object : RequestListener<Drawable>{
//            override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
//                holder.progressBar.visibility= View.GONE
//                return false
//            }
//
//            override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
//                holder.progressBar.visibility= View.GONE
//                return false
//            }
//
//        }).into(holder.moviePoster)
    }

    override fun getItemCount(): Int {
        return allMovies.size
    }

    fun updateList(newList : List<Movie>){
        allMovies.clear()
        allMovies.addAll(newList)
        notifyDataSetChanged()
    }

}

