package com.margarita.filmviewer.ui.activities

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.SearchView
import android.widget.EditText

import kotlinx.android.synthetic.main.activity_main.*

import com.margarita.filmviewer.R
import com.margarita.filmviewer.common.getColorResource
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
                    .add(R.id.container, moviesFragment, MOVIE_FRAGMENT_TAG)
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
     * Function for reset the search view to its default state
     */
    fun resetSearchView() {
        searchView.setQuery("", false)
        searchView.clearFocus()
    }

    /**
     * Function which implements the fragment replacement
     * @param fragment New fragment which will be shown
     */
    private fun setFragment(fragment: Fragment) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.container, fragment).commit()
    }

    override fun showLoadingError(): Unit = setFragment(errorFragment)

    override fun showSearchError(): Unit = setFragment(emptySearchFragment)

    override fun showSearchResult(): Unit = setFragment(moviesFragment!!)
 }
