package com.margarita.filmviewer.mvp.view

import android.support.annotation.StringRes
import com.margarita.filmviewer.models.Movie

/**
 * Interface for a base content view
 */
interface ContentView {

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
    fun setMovies(movies: List<Movie>)

    /**
     * Hide refreshing animation
     */
    fun hideRefreshing()

    /**
     * Show message for a connection error
     * @param messageRes String resource ID for a message text
     */
    fun showConnectionError(@StringRes messageRes: Int)
}