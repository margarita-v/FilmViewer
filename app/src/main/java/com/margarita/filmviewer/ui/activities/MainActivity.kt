package com.margarita.filmviewer.ui.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.SearchView
import android.widget.EditText

import kotlinx.android.synthetic.main.activity_main.*

import com.margarita.filmviewer.R
import com.margarita.filmviewer.common.*
import com.margarita.filmviewer.models.Movie
import com.margarita.filmviewer.mvp.presenter.MoviesPresenter
import com.margarita.filmviewer.ui.fragments.BaseFragment
import com.margarita.filmviewer.ui.fragments.EmptySearchFragment
import com.margarita.filmviewer.ui.fragments.ErrorFragment
import com.margarita.filmviewer.ui.fragments.MoviesFragment

class MainActivity :
        AppCompatActivity(),
        MoviesFragment.OnActivityCallback,
        ErrorFragment.OnRefreshClickListener {

    /**
     * All fragments
     */
    private val errorFragment by lazy { ErrorFragment() }
    private lateinit var moviesFragment: MoviesFragment
    private lateinit var emptySearchFragment: EmptySearchFragment

    /**
     * Presenter of movies
     */
    private lateinit var presenter: MoviesPresenter

    companion object {
        private const val MOVIE_FRAGMENT_TAG = "Movies"
        private const val CONTAINER_ID = R.id.container
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupSearchView()
        // Try to restore fragment
        val foundFragment = supportFragmentManager.findFragmentByTag(MOVIE_FRAGMENT_TAG)
        moviesFragment = foundFragment as MoviesFragment? ?: MoviesFragment()
        setContentFragment()
        presenter = MoviesPresenter(moviesFragment)
    }

    override fun onBackPressed() {
        val containsEmpty = containsFragment(emptySearchFragment)
        if (containsEmpty ||
                containsFragment(moviesFragment) &&
                moviesFragment.hasSearchedContent()) {
            // Show the main content
            moviesFragment.clearSearchResult()
            resetSearchView()
            if (containsEmpty) {
                setContentFragment()
            }
        } else {
            super.onBackPressed()
        }
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
                emptySearchFragment = EmptySearchFragment.newInstance(query)
                return true
            }
        })
    }

    /**
     * Function which implements the fragment replacement
     * @param fragment New fragment which will be shown
     */
    private fun setFragment(fragment: BaseFragment): Unit
        = supportFragmentManager.replace(CONTAINER_ID, fragment)

    /**
     * Function for showing the main fragment with a list of movies
     */
    private fun setContentFragment(): Unit
         = supportFragmentManager.replace(CONTAINER_ID, moviesFragment, MOVIE_FRAGMENT_TAG)

    // OnRefreshClickListener implementation
    override fun onRefreshClick(): Unit = setContentFragment()

    //region OnActivityCallback implementation
    override fun showLoadingError(): Unit = setFragment(errorFragment)

    override fun resetSearchView(): Unit = searchView.reset()

    override fun showSearchError(): Unit = setFragment(emptySearchFragment)

    override fun showSearchProgress() {
        progressSearch.show()
        hideKeyboard()
    }

    override fun hideSearchProgress(): Unit = progressSearch.becomeInvisible()

    override fun setSearchResult(movies: List<Movie>): Unit = setContentFragment()
    //endregion
}
