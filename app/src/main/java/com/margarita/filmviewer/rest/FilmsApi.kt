package com.margarita.filmviewer.rest

import com.margarita.filmviewer.models.FullResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface FilmsApi {

    companion object {
        private const val DISCOVER_MOVIES = "discover/movies/"
        private const val SEARCH_MOVIE = "search/movie/"

        private const val SORT_NAME = "sort_by"
        private const val SORT_VALUE = "popularity.desc"

        private const val QUERY_PAGE = "page"
        private const val QUERY = "query"
    }

    /**
     * Function for getting a list of movies
     * @param sort Sort condition
     * @param page Page number
     */
    @GET(DISCOVER_MOVIES)
    fun discoverMovies(@Query(QUERY_PAGE) page: Int,
                       @Query(SORT_NAME) sort: String = SORT_VALUE): Observable<FullResponse>

    /**
     * Function for searching a movies by given search query
     * @param page Page number
     * @param query Query for search
     */
    @GET(SEARCH_MOVIE)
    fun searchMovie(@Query(QUERY_PAGE) page: Int,
                    @Query(QUERY) query: String): Observable<FullResponse>
}