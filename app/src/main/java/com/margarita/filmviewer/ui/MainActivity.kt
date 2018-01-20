package com.margarita.filmviewer.ui

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.widget.EditText

import kotlinx.android.synthetic.main.activity_main.*

import com.margarita.filmviewer.R
import com.margarita.filmviewer.common.*
import com.margarita.filmviewer.models.Movie
import com.margarita.filmviewer.mvp.presenter.MoviesPresenter
import com.margarita.filmviewer.mvp.view.MoviesView
import kotlinx.android.synthetic.main.empty_search_view.*
import kotlinx.android.synthetic.main.error_view.*
import kotlinx.android.synthetic.main.fragment_list.*

class MainActivity : AppCompatActivity(), MoviesView {

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
    private val presenter = MoviesPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvList.layoutManager = LinearLayoutManager(this)
        rvList.adapter = adapter

        swipeContainer.setColorSchemeResources(R.color.colorAccent)
        swipeContainer.setOnRefreshListener { presenter.loadRefresh() }

        setupSearchView()
        presenter.loadStart()
    }

    /**
     * Function which changes style of a search view
     */
    private fun setupSearchView() {
        searchView.setBackgroundResource(R.color.colorBackground)
        val editText = searchView.findViewById(R.id.search_src_text) as EditText
        editText.setHintTextColor(getColorResource(R.color.colorHint))
        editText.setTextColor(getColorResource(R.color.colorTitle))
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                presenter.loadForSearch(1, query)
                return true
            }
        })
    }

    //region Loading content
    override fun showLoadingContent() {
        layoutProgress.show()
        containerViews.hide()
    }

    override fun hideLoadingContent(): Unit = layoutProgress.hide()

    override fun showLoadingError(): Unit = showContainerViews(true)

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
            = container.showSnackBar(messageRes)
    //endregion

    //region Search
    override fun showSearchProgress(): Unit = progressSearch.show()

    override fun hideSearchProgress(): Unit = progressSearch.becomeInvisible()

    override fun showSearchError() {
        showContainerViews(false)
        hideKeyboard()
        tvEmpty.text = getString(R.string.empty_search, searchView.query)
    }

    override fun setSearchResult(movies: List<Movie>) {
        swipeContainer.show()
        containerViews.hide()
        hideKeyboard()
        adapter.setMovies(movies)
    }
    //endregion

    /**
     * Function for showing the layout with error and empty views
     * @param showError Flag which shows if the error view should be visible
     */
    private fun showContainerViews(showError: Boolean) {
        swipeContainer.hide()
        containerViews.show()
        if (showError) {
            layoutError.show()
        } else {
            layoutEmpty.show()
        }
    }
 }
