package com.margarita.filmviewer

import android.content.Context
import com.margarita.filmviewer.common.isOnline
import com.margarita.filmviewer.models.Movie
import com.margarita.filmviewer.mvp.presenter.MoviesPresenter
import com.margarita.filmviewer.mvp.view.MoviesView
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.Mockito.`when` as whenMock
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.junit.MockitoRule


@RunWith(MockitoJUnitRunner::class)
class MoviesPresenterTest {

    @Rule lateinit var mockitoRule: MockitoRule

    @Mock lateinit var moviesView: MoviesView

    @Mock lateinit var context: Context

    @Mock lateinit var movies: ArrayList<Movie>

    lateinit var presenter: MoviesPresenter

    private val messageRes = R.string.connection_error

    @Before fun setUp() {
        MockitoAnnotations.initMocks(this)
        mockitoRule = MockitoJUnit.rule()
        //whenMock(MainApplication.applicationComponent).thenReturn(context)
        presenter = MoviesPresenter(moviesView)
        moviesView.setPresenter(presenter)
    }

    @Test fun testStartLoading() {
        if (context.isOnline()) {
            Mockito.verify(moviesView).showLoadingContent()
            Mockito.verify(moviesView).setMovies(movies)
        } else {
            if (moviesView.hasContent()) {
                Mockito.verify(moviesView).showConnectionError(messageRes)
            } else {
                Mockito.verify(moviesView).showLoadingError()
            }
        }
    }
}