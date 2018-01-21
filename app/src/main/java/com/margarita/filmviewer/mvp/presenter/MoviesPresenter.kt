package com.margarita.filmviewer.mvp.presenter

import android.content.Context
import com.margarita.filmviewer.MainApplication
import com.margarita.filmviewer.R
import com.margarita.filmviewer.common.isOnline
import com.margarita.filmviewer.models.FullResponse
import com.margarita.filmviewer.models.Movie
import com.margarita.filmviewer.mvp.view.MoviesView
import com.margarita.filmviewer.rest.FilmsApi
import com.margarita.filmviewer.rest.RestClient
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MoviesPresenter(private val moviesView: MoviesView) {

    @Inject
    internal lateinit var filmsApi: FilmsApi

    @Inject
    internal lateinit var context: Context

    /**
     * Flag which shows if the loading is performing now
     */
    private var isLoading = false

    init {
        MainApplication.applicationComponent.inject(this)
        moviesView.setPresenter(this)
    }

    //region Common functions for movies loading
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
            getMoviesObservable(loadingState, pageNumber, query)
                    .flatMap { Observable.fromIterable(it.results) }
                    .toList()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSubscribe({ showProgress(loadingState) })
                    .doFinally({ onLoadingFinish(loadingState) })
                    .subscribe({
                        val result = it.requireNoNulls()
                        if (result.isNotEmpty()) {
                            // Configure URLs for images and parse dates
                            result.forEach {
                                it.posterPath = RestClient.BASE_IMAGE_URL + it.posterPath
                                it.parseDate()
                            }
                            onLoadingSuccess(loadingState, result)
                        }
                        else
                            onLoadingFailed(loadingState)
                    }, {
                        it.printStackTrace()
                        onLoadingFailed(loadingState)
                    })
        }
    }

    /**
     * Function for getting movies for different loading states
     * @param loadingState Current loading state
     * @param pageNumber Current page number
     * @param query Search query
     */
    private fun getMoviesObservable(loadingState: LoadingState,
                                    pageNumber: Int,
                                    query: String): Observable<FullResponse> {
        if (context.isOnline()) {
            return if (loadingState != LoadingState.Searching)
                filmsApi.discoverMovie(pageNumber)
            else
                filmsApi.searchMovie(pageNumber, query)
        }
        return Observable.empty()
    }
    //endregion

    /**
     * Function for showing a progress of a concrete loading state
     * @param loadingState Current loading state
     */
    private fun showProgress(loadingState: LoadingState) {
        when (loadingState) {
            LoadingState.LoadingFirst -> moviesView.showLoadingContent()
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
            LoadingState.Refreshing -> moviesView.hideRefreshing()
            LoadingState.Searching -> moviesView.hideSearchProgress()
        }
    }

    //region All kinds of data loading
    /**
     * Initial loading of the content
     */
    fun loadStart() = loadMovies(LoadingState.LoadingFirst)

    /**
     * Refresh content
     */
    fun loadRefresh() = loadMovies(LoadingState.Refreshing)

    /**
     * Search movies
     * @param pageNumber Number of page (search result could be large)
     * @param query Search query
     */
    fun loadForSearch(pageNumber: Int, query: String)
            = loadMovies(LoadingState.Searching, pageNumber, query)

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
    private fun onLoadingSuccess(loadingState: LoadingState, movies: List<Movie>) {
        when (loadingState) {
            LoadingState.LoadingFirst, LoadingState.Refreshing -> moviesView.setMovies(movies)
            LoadingState.Searching -> moviesView.setSearchResult(movies)
        }
    }

    /**
     * Function which will be called if the loading failed
     * @param loadingState Current loading state
     */
    private fun onLoadingFailed(loadingState: LoadingState) {
        if (moviesView.hasContent() && !context.isOnline()
                || loadingState == LoadingState.Refreshing) {
            // Show a simple connection error message if the device is offline
            showConnectionError()
        } else {
            when (loadingState) {
                LoadingState.LoadingFirst -> moviesView.showLoadingError()
                LoadingState.Searching -> moviesView.showSearchError()
                else -> return
            }
        }
    }
    //endregion

    /**
     * Function for showing a connection error message
     */
    private fun showConnectionError(): Unit
            = moviesView.showConnectionError(R.string.connection_error)

    /**
     * Enum for all loading states
     */
    enum class LoadingState {
        LoadingFirst,   // Initial loading of content
        Refreshing,     // Refresh content
        Searching
    }
}