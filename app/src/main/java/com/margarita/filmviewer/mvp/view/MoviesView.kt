package com.margarita.filmviewer.mvp.view

import com.margarita.filmviewer.mvp.presenter.MoviesPresenter

/**
 * Interface for a view which shows a list of movies
 * */
interface MoviesView: ContentView, SearchingView {

    /**
     * Function for setting presenter to the view
     * @param presenter Presenter which will be set to the view
     */
    fun setPresenter(presenter: MoviesPresenter)
}