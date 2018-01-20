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
import kotlinx.android.synthetic.main.progress_bar.*

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
        progressBar.show()
        layoutError.hide()
    }

    override fun hideLoadingContent(): Unit = progressBar.hide()

    override fun showLoadingError() {
        layoutError.show()
        swipeContainer.hide()
    }

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
    override fun showSearchProgress() {
        progressSearch.show()
        layoutEmpty.hide()
    }

    override fun hideSearchProgress(): Unit = progressSearch.becomeInvisible()

    override fun showSearchError() {
        layoutEmpty.show()
        hideKeyboard()
        tvEmpty.text = getString(R.string.empty_search, searchView.query)
    }

    override fun setSearchResult(movies: List<Movie>) {
        hideKeyboard()
        adapter.setMovies(movies)
    }
    //endregion
 }
