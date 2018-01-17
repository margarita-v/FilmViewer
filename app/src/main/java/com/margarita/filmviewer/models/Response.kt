package com.margarita.filmviewer.models

import com.google.gson.annotations.Expose

data class Response(
		@Expose val page: Int? = null,
		@Expose val results: List<ResultItem?>? = null,
		val totalPages: Int? = null,
		val totalResults: Int? = null
)