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
import com.margarita.filmviewer.ui.activities.MainActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_list.*
import kotlinx.android.synthetic.main.fragment_list.view.*

/**
 * Fragment for showing a list of movies
 */
class MoviesFragment : BaseFragment(), MoviesView {

    /**
     * Listener for all activity callbacks
     */
    private lateinit var activityCallback: OnActivityCallback

    /**
     * Activity which will contain that fragment
     */
    private lateinit var mainActivity: MainActivity

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
     * Presenter of movies
     */
    val presenter by lazy { MoviesPresenter(this) }

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
        rvList.layoutManager = LinearLayoutManager(context)
        rvList.adapter = adapter

        swipeContainer.setColorSchemeResources(R.color.colorAccent)
        swipeContainer.setOnRefreshListener { presenter.loadRefresh() }

        progressBarLoading = view.progressBar
        presenter.loadStart()
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        try {
            activityCallback = activity as OnActivityCallback
            mainActivity = activity as MainActivity
        } catch (e: ClassCastException) {
            throw ClassCastException(activity.toString() + CLASS_CAST_MESSAGE)
        }
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
    override fun showSearchProgress() {
        mainActivity.progressSearch.show()
        mainActivity.hideKeyboard()
    }

    override fun hideSearchProgress() {
        mainActivity.progressSearch.becomeInvisible()
    }

    override fun showSearchError(): Unit = activityCallback.showSearchError()

    override fun setSearchResult(movies: List<Movie>) {
        activityCallback.showSearchResult()
        adapter.setMovies(movies)
    }
    //endregion

    /**
     * Interface for sending callbacks to the activity
     */
    interface OnActivityCallback {

        /**
         * Function for showing error for the first content loading
         */
        fun showLoadingError()

        /**
         * Function for showing error for searching
         */
        fun showSearchError()

        /**
         * Function for showing a result of search
         */
        fun showSearchResult()
    }
}