package com.margarita.filmviewer.common

import android.content.Context
import android.net.ConnectivityManager
import android.support.annotation.ColorRes
import android.support.annotation.LayoutRes
import android.support.annotation.StringRes
import android.support.design.widget.Snackbar
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
 * Function for checking if device is connected to the Internet
 * @return True if the Internet connection is available
 */
fun Context.isOnline(): Boolean {
    val manager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val networkInfo = manager.activeNetworkInfo
    return networkInfo != null && networkInfo.isConnected
}

/**
 * Function for simple inflating layout file to view group
 * @param layoutRes ID of layout file which will be inflated to the view group
 * @param attachToRoot Flag which shows if we need to attach to the root this view
 * @return View with inflated layout
 */
fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View
        = LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)

/**
 * Function for showing SnackBar
 * @param messageRes String resource ID for a message text
 * @param duration Duration of the SnackBar showing
 */
fun View.showSnackBar(@StringRes messageRes: Int, duration: Int = Snackbar.LENGTH_LONG): Unit
        = Snackbar.make(this, messageRes, duration).show()

/**
 * Function for showing SnackBar
 * @param message A text of message
 * @param duration Duration of the SnackBar showing
 */
fun View.showSnackBar(message: String, duration: Int = Snackbar.LENGTH_LONG): Unit
        = Snackbar.make(this, message, duration).show()

/**
 * Function for loading image to ImageView using image Uri
 * @param context Context of function call
 * @param url Image URL
 */
fun ImageView.loadImage(context: Context, url: String): Unit
        = Picasso.with(context).load(url).into(this)

//region Function for setting visibility of views
fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.GONE
}

fun View.becomeInvisible() {
    visibility = View.INVISIBLE
}
//endregion
