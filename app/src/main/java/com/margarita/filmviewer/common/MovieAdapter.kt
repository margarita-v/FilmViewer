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
class MovieAdapter(private val movieClickListener: OnMovieClickListener)
    : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    /**
     * List of movies
     */
    private var movies = ArrayList<Movie>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder
            = MovieViewHolder(parent.inflate(R.layout.film_item))

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int): Unit
            = holder.bind(movies[position], position, movieClickListener)

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
         * @param position Movie's position in the adapter
         * @param onMovieClickListener Listener of movie click events
         */
        fun bind(movie: Movie,
                 position: Int,
                 onMovieClickListener: OnMovieClickListener): Unit = with(itemView) {
            tvName.text = movie.title
            tvDescription.text = movie.overview
            tvDate.text = movie.releaseDate
            imageView.loadImage(itemView.context, movie.posterPath!!)
            setOnClickListener{ onMovieClickListener.onMovieClick(movie) }
            val imageIcon =
                    if (!context.getPreferences().isUserLiked(movie.id!!))
                        R.drawable.ic_heart
                    else
                        R.drawable.ic_heart_fill
            imgBtnLike.setImageResource(imageIcon)
            imgBtnLike.setOnClickListener { onMovieClickListener.like(movie.id, position) }
        }
    }

    /**
     * Interface for a note click event handling
     */
    interface OnMovieClickListener {

        /**
         * Function for performing a movie click event
         * @param movie Movie which was clicked
         */
        fun onMovieClick(movie: Movie)

        /**
         * Function for adding a movie to favorites
         * @param id Movie's ID
         * @param position Movie's position in the adapter
         */
        fun like(id: Int, position: Int)
    }
}