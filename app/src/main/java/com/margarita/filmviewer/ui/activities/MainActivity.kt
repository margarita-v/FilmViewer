package com.margarita.filmviewer.ui.activities

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.widget.EditText

import kotlinx.android.synthetic.main.activity_main.*

import com.margarita.filmviewer.R
import com.margarita.filmviewer.common.getColorResource
import com.margarita.filmviewer.ui.fragments.EmptySearchFragment
import com.margarita.filmviewer.ui.fragments.ErrorFragment
import com.margarita.filmviewer.ui.fragments.MoviesFragment

class MainActivity : AppCompatActivity(), MoviesFragment.OnContentErrorListener {

    /**
     * All fragments
     */
    private val moviesFragment by lazy { MoviesFragment() }
    private val errorFragment by lazy { ErrorFragment() }
    private val emptySearchFragment by lazy { EmptySearchFragment() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupSearchView()
        setFragment(moviesFragment)
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
 }
