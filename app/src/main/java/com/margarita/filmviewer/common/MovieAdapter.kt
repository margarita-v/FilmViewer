package com.margarita.filmviewer.common

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.margarita.filmviewer.R
import com.margarita.filmviewer.models.Movie
import kotlinx.android.synthetic.main.film_item.view.*

/**
 * Adapter for list of movies
 */
class MovieAdapter: RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    /**
     * List of movies
     */
    private var movies = ArrayList<Movie>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder
            = MovieViewHolder(parent.inflate(R.layout.film_item))

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int): Unit
            = holder.bind(movies[position])

    override fun getItemCount() = movies.size

    /**
     * Function for setting a new list of movies to the adapter
     * @param movies List of movies which will be stored in the adapter
     */
    fun setMovies(movies: List<Movie>) {
        this.movies.clear()
        addMovies(movies)
    }

    /**
     * Function for addition a new list of movies to the adapter
     * @param movies List of movies which will be added to the adapter
     */
    fun addMovies(movies: List<Movie>) {
        this.movies.addAll(movies)
        notifyDataSetChanged()
    }

    /**
     * View holder for movies
     */
    inner class MovieViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        /**
         * Function for binding a movie item and showing its info
         * @param movie Movie which info will be shown
         */
        fun bind(movie: Movie): Unit = with(itemView) {
            tvName.text = movie.title
            tvDescription.text = movie.overview
            tvDate.text = movie.releaseDate
            imageView.loadImage(itemView.context, movie.posterPath!!)
        }
    }
}