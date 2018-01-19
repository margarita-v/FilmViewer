package com.margarita.filmviewer.ui

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.EditText
import android.widget.Toast

import kotlinx.android.synthetic.main.activity_main.*

import com.margarita.filmviewer.R
import com.margarita.filmviewer.common.MovieAdapter
import com.margarita.filmviewer.common.getColorResource
import com.margarita.filmviewer.common.showSnackBar
import com.margarita.filmviewer.models.Movie
import com.margarita.filmviewer.mvp.presenter.MoviesPresenter
import com.margarita.filmviewer.mvp.view.MoviesView
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
    }

    //region MoviesView implementation
    override fun showSearchProgress() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showLoadingContent() {
        progressBar.visibility = View.VISIBLE
    }

    override fun hideLoadingContent() {
        progressBar.visibility = View.GONE
    }

    override fun hideSearchProgress() {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showSearchError() {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showLoadingError() {
        //TODO SnackBar
        Toast.makeText(this, "Hell!", Toast.LENGTH_SHORT).show()
    }

    override fun setSearchResult(movies: List<Movie>) {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setMovies(movies: List<Movie>): Unit = adapter.setMovies(movies)

    override fun showLoadingNextContent() {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun hideLoadingNextContent() {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun hideRefreshing() {
        swipeContainer.isRefreshing = false
    }

    override fun showRefreshingError(messageRes: Int): Unit
            = container.showSnackBar(messageRes)

    override fun addMovies(movies: List<Movie>): Unit = adapter.addMovies(movies)
    //endregion
 }
