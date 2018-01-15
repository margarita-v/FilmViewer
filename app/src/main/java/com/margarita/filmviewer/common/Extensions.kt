package com.margarita.filmviewer.common

import android.content.Context
import android.support.annotation.ColorRes
import android.support.v4.content.ContextCompat

/**
 * Function for getting a color resource
 * @param colorResId Resource ID for color
 */
fun Context.getColorResource(@ColorRes colorResId: Int) =
        ContextCompat.getColor(this, colorResId)
