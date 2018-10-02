package com.margarita.filmviewer.mock

import android.content.Context
import com.margarita.filmviewer.MainApplication
import com.margarita.filmviewer.di.components.DaggerApplicationComponent
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

/**
 * Class which is using in order to ensure that the TestComponent
 * is set in the Application class before starting any test
 */
class TestComponentRule(private val context: Context): TestRule {

    private lateinit var testApplicationComponent: TestApplicationComponent

    init {
        val application = context.applicationContext as MainApplication
    }

    override fun apply(base: Statement?, description: Description?): Statement {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}