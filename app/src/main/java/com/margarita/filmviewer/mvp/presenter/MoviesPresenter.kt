package com.margarita.filmviewer.mvp.presenter

import com.margarita.filmviewer.models.MovieResponse
import com.margarita.filmviewer.mvp.view.MoviesView

class MoviesPresenter(private val moviesView: MoviesView) {

    private fun loadMovies(progressType: ProgressType, pageNumber: Int = 1) {

    }

    private fun showProgress(progressType: ProgressType) {

    }

    private fun hideProgress(progressType: ProgressType) {

    }

    //region All kinds of data loading
    fun loadStart() {

    }

    fun loadNext(pageNumber: Int) {

    }

    fun loadRefresh() {

    }
    //endregion

    //region Common loading actions
    private fun onLoadingStart(progressType: ProgressType) {
        showProgress(progressType)
    }

    private fun onLoadingFinish(progressType: ProgressType) {
        hideProgress(progressType)
    }

    private fun onLoadingSuccess(progressType: ProgressType, items: List<MovieResponse>) {

    }

    private fun onLoadingFailed(progressType: ProgressType, throwable: Throwable) {

    }
    //endregion

    /**
     * Progress types for different loading states
     */
    enum class ProgressType {
        Loading,
        Refreshing,
        Searching
    }
}