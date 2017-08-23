package com.cyco.ciceronekotlin

import ru.terrakok.cicerone.Router

/**
 * Created by cy on 8/23/17.
 */
class SamplePresenter(private var router: Router = SampleApplication.instance.router, private var screenNumber: Int = 0) {

    fun onBackCommandClick() {
        router.exit()
    }

    fun onForwardCommandClick() {
        router.navigateTo(Screens.SAMPLE_SCREEN + (screenNumber + 1), screenNumber + 1)
    }

    fun onReplaceCommandClick() {
        router.replaceScreen(Screens.SAMPLE_SCREEN + (screenNumber + 1), screenNumber + 1)
    }

    fun onNewChainCommandClick() {
        router.newScreenChain(Screens.SAMPLE_SCREEN + (screenNumber + 1), screenNumber + 1)
    }

    fun onNewRootCommandClick() {
        router.newRootScreen(Screens.SAMPLE_SCREEN + (screenNumber + 1), screenNumber + 1)
    }

    fun onBackWithMessageCommandClick() {
        router.exitWithMessage("Exit from 'Screen $screenNumber'")
    }

    fun onBackToCommandClick() {
        router.backTo(Screens.SAMPLE_SCREEN + 3)
    }

    companion object {
        private var screenNumber: Int = 0
    }
}
