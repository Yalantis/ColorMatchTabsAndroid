package com.yalantis.colormatchtabs.colormatchtabs.utils

/**
 * Created by anna on 24.05.17.
 */
class InvalidNumberOfTabsExeption : Exception() {
    override val message: String = "Your should add more than three and less then six tabs to use ArcMenu"
}