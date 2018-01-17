package com.margarita.filmviewer.models

import com.google.gson.annotations.Expose

data class FullResponse(
		@Expose val page: Int? = null,
		@Expose val results: List<MovieResponse?>? = null,
		val totalPages: Int? = null,
		val totalResults: Int? = null
)