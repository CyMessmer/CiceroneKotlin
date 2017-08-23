package com.cyco.ciceronekotlin

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import android.widget.Toast
import ru.terrakok.cicerone.android.SupportFragmentNavigator
import ru.terrakok.cicerone.commands.*
import java.io.Serializable
import java.util.*

/**
 * Created by cy on 8/23/17.
 */
class SampleActivity : AppCompatActivity() {
    private var screenNames: MutableList<String> = ArrayList()
    // initialized in onCreate
    private lateinit var screensSchemeTV: TextView

    private val navigator = object : SupportFragmentNavigator(supportFragmentManager, R.id.main_container) {
        override fun createFragment(screenKey: String, data: Any): Fragment {
            return SampleFragment.newInstance
        }

        override fun showSystemMessage(message: String) {
            Toast.makeText(this@SampleActivity, message, Toast.LENGTH_SHORT).show()
        }

        override fun exit() {
            finish()
        }

        override fun applyCommand(command: Command?) {
            super.applyCommand(command)
            updateScreenNames(command)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        screensSchemeTV = findViewById(R.id.screens_scheme) as TextView

        if (savedInstanceState == null) {
            navigator.applyCommand(Replace(Screens.SAMPLE_SCREEN, 1))
        } else {
            screenNames = savedInstanceState.getSerializable(STATE_SCREEN_NAMES) as MutableList<String>
            printScreensScheme()
        }
    }

    override fun onResume() {
        super.onResume()
        SampleApplication.instance.navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        SampleApplication.instance.navigatorHolder.removeNavigator()
    }

    override fun onBackPressed() {
        val fragment = supportFragmentManager.findFragmentById(R.id.main_container)
        super.onBackPressed()
        if (fragment != null
                && fragment is BackButtonListener
                && (fragment as BackButtonListener).onBackPressed()) {
            return
        } else {
            super.onBackPressed()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSerializable(STATE_SCREEN_NAMES, screenNames as Serializable)
    }

    private fun updateScreenNames(command: Command?) {
        if (command is Back) {
            if (screenNames.size > 0) {
                screenNames.removeAt(screenNames.size - 1)
            }
        } else if (command is Forward) {
            val i = command.transitionData as Int
            screenNames.add(i.toString() + "")
        } else if (command is Replace) {
            val i = command.transitionData as Int
            if (screenNames.size > 0) {
                screenNames.removeAt(screenNames.size - 1)
            }
            screenNames.add(i.toString() + "")
        } else if (command is BackTo) {
            screenNames = ArrayList(screenNames.subList(0, supportFragmentManager.backStackEntryCount + 1))
        }
        printScreensScheme()
    }

    private fun printScreensScheme() {
        var str = ""
        for (name in screenNames) {
            if (!str.isEmpty()) {
                str += "➔" + name
            } else {
                str = "[$name]"
            }
        }
        screensSchemeTV.text = "Chain: " + str + ""
    }

    companion object {
        private val STATE_SCREEN_NAMES = "state_screen_names"
    }
}
