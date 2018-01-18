package com.margarita.filmviewer.common

import android.content.Context
import android.support.annotation.ColorRes
import android.support.annotation.LayoutRes
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.squareup.picasso.Picasso

/**
 * Function for getting a color resource
 * @param colorResId Resource ID for color
 */
fun Context.getColorResource(@ColorRes colorResId: Int): Int
        = ContextCompat.getColor(this, colorResId)

/**
 * Function for simple inflating layout file to view group
 * @param layoutRes ID of layout file which will be inflated to the view group
 * @param attachToRoot Flag which shows if we need to attach to the root this view
 * @return View with inflated layout
 */
fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View
        = LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)

/**
 * Function for loading image to ImageView using image Uri
 * @param context Context of function call
 * @param url Image URL
 */
fun ImageView.loadImage(context: Context, url: String): Unit
        = Picasso.with(context).load(url).into(this)
