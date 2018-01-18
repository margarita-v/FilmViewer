package com.margarita.filmviewer.models

import com.google.gson.annotations.Expose

data class FullResponse(
		@Expose val page: Int? = null,
		@Expose val results: List<Movie?>? = null,
		val totalPages: Int? = null,
		val totalResults: Int? = null
)