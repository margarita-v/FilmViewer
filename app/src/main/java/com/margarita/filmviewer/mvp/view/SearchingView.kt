package com.margarita.filmviewer.mvp.view

import com.margarita.filmviewer.models.Movie

/**
 * Interface for a view which implements a search of movies
 */
interface SearchingView {

    /**
     * Show animation for a search progress
     */
    fun showSearchProgress()

    /**
     * Hide search progress animation
     */
    fun hideSearchProgress()

    /**
     * Show view for a search error
     */
    fun showSearchError()

    /**
     * Function for setting a search result to the search adapter
     * @param movies Result of search
     */
    fun setSearchResult(movies: List<Movie>)
}