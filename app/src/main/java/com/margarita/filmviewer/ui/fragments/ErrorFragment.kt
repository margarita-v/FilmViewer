package com.margarita.filmviewer.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.View
import com.margarita.filmviewer.R
import kotlinx.android.synthetic.main.error_view.*

/**
 * Fragment for showing a loading error message
 */
class ErrorFragment : BaseFragment() {

    /**
     * Listener for the FAB click event
     */
    private lateinit var onRefreshClickListener: OnRefreshClickListener

    companion object {
        /**
         * Message for a class cast exception
         */
        private const val CLASS_CAST_MESSAGE = " must implement OnRefreshClickListener"
    }

    override fun getLayoutRes(): Int  = R.layout.error_view

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fab.setOnClickListener { onRefreshClickListener.onRefreshClick() }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        try {
            onRefreshClickListener = activity as OnRefreshClickListener
        } catch (e: ClassCastException) {
            throw ClassCastException(activity.toString() + CLASS_CAST_MESSAGE)
        }
    }

    /**
     * Interface for the FAB click listener
     */
    interface OnRefreshClickListener {

        fun onRefreshClick()
    }
}