package com.margarita.filmviewer.ui.activities

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.SearchView
import android.widget.EditText

import kotlinx.android.synthetic.main.activity_main.*

import com.margarita.filmviewer.R
import com.margarita.filmviewer.common.*
import com.margarita.filmviewer.models.Movie
import com.margarita.filmviewer.ui.fragments.EmptySearchFragment
import com.margarita.filmviewer.ui.fragments.ErrorFragment
import com.margarita.filmviewer.ui.fragments.MoviesFragment

class MainActivity : AppCompatActivity(), MoviesFragment.OnActivityCallback {

    /**
     * All fragments
     */
    private var moviesFragment: MoviesFragment? = null
    private val errorFragment by lazy { ErrorFragment() }
    private lateinit var emptySearchFragment: EmptySearchFragment

    companion object {
        private const val MOVIE_FRAGMENT_TAG = "Movies"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupSearchView()
        moviesFragment = supportFragmentManager
                .findFragmentByTag(MOVIE_FRAGMENT_TAG) as MoviesFragment?
        if (moviesFragment == null) {
            moviesFragment = MoviesFragment()
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, moviesFragment, MOVIE_FRAGMENT_TAG)
                    .commit()
        } else {
            setFragment(moviesFragment!!)
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
                moviesFragment!!.presenter.loadForSearch(1, query)
                emptySearchFragment = EmptySearchFragment.newInstance(query)
                return true
            }
        })
    }

    /**
     * Function which implements the fragment replacement
     * @param fragment New fragment which will be shown
     */
    private fun setFragment(fragment: Fragment) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.container, fragment).commit()
    }

    //region OnActivityCallback implementation
    override fun showLoadingError(): Unit = setFragment(errorFragment)

    override fun resetSearchView(): Unit = searchView.reset()

    override fun showSearchError(): Unit = setFragment(emptySearchFragment)

    override fun showSearchProgress() {
        progressSearch.show()
        hideKeyboard()
    }

    override fun hideSearchProgress(): Unit = progressSearch.becomeInvisible()

    override fun setSearchResult(movies: List<Movie>): Unit = setFragment(moviesFragment!!)
    //endregion
}
