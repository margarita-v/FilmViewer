package com.margarita.filmviewer.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class MovieResponse(
		@Expose val id: Int? = null,
		@Expose val title: String? = null,
		@Expose val overview: String? = null,
		@Expose @SerializedName(POSTER_PATH) var posterPath: String? = null,
		@Expose @SerializedName(RELEASE_DATE) val releaseDate: String? = null,
		val originalLanguage: String? = null,
		val originalTitle: String? = null,
		val video: Boolean? = null,
		val genreIds: List<Int?>? = null,
		val backdropPath: String? = null,
		val voteAverage: Double? = null,
		val popularity: Double? = null,
		val adult: Boolean? = null,
		val voteCount: Int? = null
) {
	companion object {
	    private const val POSTER_PATH = "poster_path"
		private const val RELEASE_DATE = "release_date"
	}
}
