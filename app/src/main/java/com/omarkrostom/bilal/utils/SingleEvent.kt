package com.omarkrostom.bilal.utils

/**
 * This is a single emitter class for livedata
 */
class SingleEvent<T> constructor(private var content: T? = null) {
    var hasBeenHandled: Boolean = false

    fun getContentIfNotHandled(): T? {
        if (hasBeenHandled) return null

        hasBeenHandled = true
        return content
    }

    fun peekContent(): T? = content
}