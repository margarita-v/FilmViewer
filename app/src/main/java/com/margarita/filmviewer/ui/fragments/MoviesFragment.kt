package com.margarita.filmviewer.ui.fragments

import android.content.Context
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.ProgressBar
import com.margarita.filmviewer.R
import com.margarita.filmviewer.common.*
import com.margarita.filmviewer.models.Movie
import com.margarita.filmviewer.mvp.presenter.MoviesPresenter
import com.margarita.filmviewer.mvp.view.MoviesView
import com.margarita.filmviewer.mvp.view.SearchingView
import kotlinx.android.synthetic.main.fragment_list.*
import kotlinx.android.synthetic.main.fragment_list.view.*

/**
 * Fragment for showing a list of movies
 */
class MoviesFragment : BaseFragment(), MoviesView {

    /**
     * Presenter of movies
     */
    private lateinit var presenter: MoviesPresenter

    /**
     * Listener for all activity callbacks
     */
    private lateinit var activityCallback: OnActivityCallback

    /**
     * Adapter for RecyclerView
     */
    private val adapter: MovieAdapter by lazy {
        MovieAdapter(object: MovieAdapter.OnMovieClickListener {
            override fun onMovieClick(movie: Movie) {
                swipeContainer.showSnackBar(movie.title!!, Snackbar.LENGTH_SHORT)
            }

            override fun like(id: Int, position: Int) {
                context!!.getPreferences().like(id)
                adapter.notifyItemChanged(position)
            }
        })
    }

    /**
     * Progress bar for a content loading animation
     */
    private lateinit var progressBarLoading: ProgressBar

    companion object {
        /**
         * Message for a class cast exception
         */
        private const val CLASS_CAST_MESSAGE = " must implement OnActivityCallback"
    }

    override fun getLayoutRes(): Int = R.layout.fragment_list

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        progressBarLoading = view.progressBar

        rvList.layoutManager = LinearLayoutManager(context)
        rvList.adapter = adapter

        swipeContainer.setColorSchemeResources(R.color.colorAccent)
        swipeContainer.setOnRefreshListener {
            presenter.loadRefresh()
            activityCallback.resetSearchView()
        }

        // Check if adapter has a content
        if (adapter.itemCount == 0) {
            presenter.loadStart()
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        try {
            activityCallback = activity as OnActivityCallback
        } catch (e: ClassCastException) {
            throw ClassCastException(activity.toString() + CLASS_CAST_MESSAGE)
        }
    }

    override fun setPresenter(presenter: MoviesPresenter) {
        this.presenter = presenter
    }

    //region Loading content
    override fun showLoadingContent(): Unit = progressBarLoading.show()

    override fun hideLoadingContent(): Unit = progressBarLoading.hide()

    override fun showLoadingError(): Unit = activityCallback.showLoadingError()

    override fun setMovies(movies: List<Movie>): Unit = adapter.setMovies(movies)
    //endregion

    //region Loading next or refreshing
    override fun showLoadingNextContent() {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun hideLoadingNextContent() {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun addMovies(movies: List<Movie>): Unit = adapter.addMovies(movies)

    override fun hideRefreshing() {
        swipeContainer.isRefreshing = false
    }

    override fun showRefreshingError(messageRes: Int): Unit
            = swipeContainer.showSnackBar(messageRes)
    //endregion

    //region Search
    override fun showSearchProgress(): Unit = activityCallback.showSearchProgress()

    override fun hideSearchProgress(): Unit = activityCallback.hideSearchProgress()

    override fun showSearchError(): Unit = activityCallback.showSearchError()

    override fun setSearchResult(movies: List<Movie>) {
        adapter.setMovies(movies)
        activityCallback.setSearchResult(movies)
    }
    //endregion

    /**
     * Interface for sending callbacks to the activity
     */
    interface OnActivityCallback: SearchingView {

        /**
         * Function for showing error for the first content loading
         */
        fun showLoadingError()

        /**
         * Function for resetting a search view to its default state
         */
        fun resetSearchView()
    }
}