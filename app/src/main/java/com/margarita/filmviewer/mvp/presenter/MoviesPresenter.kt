package com.margarita.filmviewer.mvp.presenter

import com.margarita.filmviewer.MainApplication
import com.margarita.filmviewer.R
import com.margarita.filmviewer.models.MovieResponse
import com.margarita.filmviewer.mvp.view.MoviesView
import com.margarita.filmviewer.rest.FilmsApi
import javax.inject.Inject

class MoviesPresenter(private val moviesView: MoviesView) {

    @Inject
    internal lateinit var filmsApi: FilmsApi

    /**
     * Flag which shows if the loading is performing now
     */
    private var isLoading = false

    init {
        MainApplication.applicationComponent.inject(this)
    }

    /**
     * Common function which configures all kinds of data loading
     * @param loadingState Current loading state
     * @param pageNumber Number of content page which will be loaded
     * @param query Search query (optional parameter)
     */
    private fun loadMovies(loadingState: LoadingState,
                           pageNumber: Int = 1,
                           query: String = "It") {
        if (!isLoading) {
            isLoading = true
        }
    }

    /*
    private fun discoverMovies(pageNumber: Int): Observable<MovieResponse> {

    }

    private fun searchMovies(pageNumber: Int, query: String): Observable<MovieResponse> {

    }*/

    /**
     * Function for showing a progress of a concrete loading state
     * @param loadingState Current loading state
     */
    private fun showProgress(loadingState: LoadingState) {
        when (loadingState) {
            LoadingState.LoadingFirst -> moviesView.showLoadingContent()
            LoadingState.LoadingNext -> moviesView.showLoadingNextContent()
            LoadingState.Searching -> moviesView.showSearchProgress()
            else -> return
        }
    }

    /**
     * Function for hiding a progress of a concrete loading state
     * @param loadingState Current loading state
     */
    private fun hideProgress(loadingState: LoadingState) {
        when (loadingState) {
            LoadingState.LoadingFirst -> moviesView.hideLoadingContent()
            LoadingState.LoadingNext -> moviesView.hideLoadingNextContent()
            LoadingState.Refreshing -> moviesView.hideRefreshing()
            LoadingState.Searching -> moviesView.hideSearchProgress()
        }
    }

    //region All kinds of data loading
    /**
     * Initial loading of the content
     */
    fun loadStart() {

    }

    /**
     * Loading of the next content page
     * @param pageNumber Number of page which will be loaded
     */
    fun loadNext(pageNumber: Int) {

    }

    /**
     * Refresh content
     */
    fun loadRefresh() {

    }

    /**
     * Search movies
     * @param pageNumber Number of page (search result could be large)
     * @param query Search query
     */
    fun loadForSearch(pageNumber: Int, query: String) {

    }
    //endregion

    //region Common loading actions
    /**
     * Function which will be called on loading finish
     * @param loadingState Current loading state
     */
    private fun onLoadingFinish(loadingState: LoadingState) {
        isLoading = false
        hideProgress(loadingState)
    }

    /**
     * Function which will be called if the loading finished successfully
     * @param loadingState Current loading state
     * @param movies List of movies which was loaded
     */
    private fun onLoadingSuccess(loadingState: LoadingState, movies: List<MovieResponse>) {
        when (loadingState) {
            LoadingState.LoadingFirst, LoadingState.Refreshing -> moviesView.setMovies(movies)
            LoadingState.LoadingNext -> moviesView.addMovies(movies)
            LoadingState.Searching -> moviesView.setSearchResult(movies)
        }
    }

    /**
     * Function which will be called if the loading failed
     * @param loadingState Current loading state
     */
    private fun onLoadingFailed(loadingState: LoadingState) {
        when (loadingState) {
            LoadingState.LoadingFirst -> moviesView.showLoadingError()
            LoadingState.Searching -> moviesView.showSearchError()
            else -> moviesView.showRefreshingError(R.string.refresh_error)
        }
    }
    //endregion

    /**
     * Enum for all loading states
     */
    enum class LoadingState {
        LoadingFirst,   // Initial loading of content
        LoadingNext,    // LoadingFirst of the next content page
        Refreshing,     // Refresh content
        Searching
    }
}