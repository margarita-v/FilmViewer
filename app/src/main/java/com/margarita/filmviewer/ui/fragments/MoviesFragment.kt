package com.margarita.filmviewer.ui.fragments

import android.content.Context
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.margarita.filmviewer.R
import com.margarita.filmviewer.common.MovieAdapter
import com.margarita.filmviewer.common.inflate
import com.margarita.filmviewer.common.showSnackBar
import com.margarita.filmviewer.models.Movie
import com.margarita.filmviewer.mvp.presenter.MoviesPresenter
import com.margarita.filmviewer.mvp.view.MoviesView
import com.margarita.filmviewer.ui.activities.MainActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_list.*
import kotlinx.android.synthetic.main.progress_bar.*

/**
 * Fragment for showing a list of movies
 */
class MoviesFragment : Fragment(), MoviesView {

    /**
     * Listener for all content loading errors
     */
    private lateinit var contentErrorListener: OnContentErrorListener

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
                container.showSnackBar(movie.title!!, Snackbar.LENGTH_SHORT)
            }

            override fun like(movie: Movie) {
                //TODO
            }
        })
    }


    /**
     * Presenter of movies
     */
    private val presenter by lazy { MoviesPresenter(this) }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View?
            = container?.inflate(R.layout.fragment_list)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvList.layoutManager = LinearLayoutManager(context)
        rvList.adapter = adapter

        swipeContainer.setColorSchemeResources(R.color.colorAccent)
        swipeContainer.setOnRefreshListener { presenter.loadRefresh() }

        presenter.loadStart()
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        try {
            contentErrorListener = activity as OnContentErrorListener
            mainActivity = activity as MainActivity
        } catch (e: ClassCastException) {
            throw ClassCastException(activity.toString() + " must implement OnContentErrorListener")
        }
    }

    //region Loading content
    override fun showLoadingContent() {
        progressBar.visibility = View.VISIBLE
    }

    override fun hideLoadingContent() {
        progressBar.visibility = View.GONE
    }

    override fun showLoadingError(): Unit = contentErrorListener.showLoadingError()

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
        mainActivity.progressSearch.visibility = View.VISIBLE
    }

    override fun hideSearchProgress() {
        mainActivity.progressSearch.visibility = View.GONE
    }

    override fun showSearchError(): Unit = contentErrorListener.showSearchError()

    override fun setSearchResult(movies: List<Movie>) {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
    //endregion

    /**
     * Interface for a content loading errors handling
     */
    interface OnContentErrorListener {

        /**
         * Function for showing error for the first content loading
         */
        fun showLoadingError()

        /**
         * Function for showing error for searching
         */
        fun showSearchError()
    }
}