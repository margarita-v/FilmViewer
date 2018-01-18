package com.margarita.filmviewer.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.widget.EditText

import kotlinx.android.synthetic.main.activity_main.*

import com.margarita.filmviewer.R
import com.margarita.filmviewer.common.MovieAdapter
import com.margarita.filmviewer.common.getColorResource
import kotlinx.android.synthetic.main.fragment_list.*

class MainActivity : AppCompatActivity() {

    /**
     * Lazy initialization for RecyclerView which will be executed once
     */
    private val movieList by lazy {
        rvList.layoutManager = LinearLayoutManager(this)
        rvList
    }

    /**
     * Adapter for RecyclerView
     */
    private var movieAdapter = MovieAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupSearchView()
        setupAdapter()
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
     * Function for initialization of the RecyclerView's adapter
     */
    private fun setupAdapter() {

    }
 }
