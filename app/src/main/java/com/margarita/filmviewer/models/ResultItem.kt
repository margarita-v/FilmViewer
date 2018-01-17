package com.margarita.filmviewer.models

import com.google.gson.annotations.Expose

data class ResultItem(
		@Expose val id: Int? = null,
		@Expose val title: String? = null,
		@Expose val overview: String? = null,
		@Expose val posterPath: String? = null,
		@Expose val releaseDate: String? = null,
		val originalLanguage: String? = null,
		val originalTitle: String? = null,
		val video: Boolean? = null,
		val genreIds: List<Int?>? = null,
		val backdropPath: String? = null,
		val voteAverage: Double? = null,
		val popularity: Double? = null,
		val adult: Boolean? = null,
		val voteCount: Int? = null
)
