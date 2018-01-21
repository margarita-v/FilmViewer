package com.margarita.filmviewer.ui.fragments

import android.os.Bundle
import android.view.View
import com.margarita.filmviewer.R
import kotlinx.android.synthetic.main.empty_search_view.*

/**
 * Fragment for showing an empty search view
 */
class EmptySearchFragment : BaseFragment() {

    /**
     * Search query
     */
    private var query: String? = null

    companion object {
        private const val QUERY_BUNDLE_KEY = "QUERY"

        /**
         * Function for creating a fragment instance
         * @param query Search query which will be shown
         */
        fun newInstance(query: String) : EmptySearchFragment {
            val emptySearchFragment = EmptySearchFragment()
            val args = Bundle()
            args.putString(QUERY_BUNDLE_KEY, query)
            emptySearchFragment.arguments = args
            return emptySearchFragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        query = arguments?.getString(QUERY_BUNDLE_KEY, "")
    }

    override fun getLayoutRes(): Int = R.layout.empty_search_view

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvEmpty.text = view.context.getString(R.string.empty_search, query)
    }
}