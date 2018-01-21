package com.margarita.filmviewer.common

import android.content.Context
import android.content.SharedPreferences

private const val PREFERENCE_FILE_NAME = "com.margarita.filmviewer.PREFERENCE_FILE_KEY"

/**
 * Function for getting shared preferences for the context
 */
fun Context.getPreferences(): SharedPreferences
        = getSharedPreferences(PREFERENCE_FILE_NAME, Context.MODE_PRIVATE)

/**
 * Function which returns True if the current user liked movie with given id, False otherwise
 * @param id Movie's ID
 */
fun SharedPreferences.isUserLiked(id: Int) = getBoolean(id.toString(), false)

/**
 * Function for adding movie to favorites or for removing movie from favorites
 * @param id Movie's ID
 */
fun SharedPreferences.like(id: Int): Unit
        = edit().putBoolean(id.toString(), !isUserLiked(id)).apply()
