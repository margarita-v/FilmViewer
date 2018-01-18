package com.margarita.filmviewer.mvp.view

import android.support.annotation.StringRes
import com.margarita.filmviewer.models.MovieResponse

/**
 * Interface for a view which shows a list of movies
 */
interface MoviesView {

    /**
     * Show loading animation
     */
    fun showLoadingContent()

    /**
     * Hide loading animation
     */
    fun hideLoadingContent()

    /**
     * Show view for a loading error
     */
    fun showLoadingError()

    /**
     * Function for setting a new list of movies
     * @param movies List of movies which will be set to the view's adapter
     */
    fun setMovies(movies: List<MovieResponse>)

    /**
     * Show refreshing animation
     */
    fun showRefreshing()

    /**
     * Hide refreshing animation
     */
    fun hideRefreshing()

    /**
     * Show message for a refreshing error
     * @param messageRes String resource ID for a message text
     */
    fun showRefreshingError(@StringRes messageRes: Int)

    /**
     * Add new movies to list
     * @param movies New movies which will be added to list
     */
    fun addMovies(movies: List<MovieResponse>)
}