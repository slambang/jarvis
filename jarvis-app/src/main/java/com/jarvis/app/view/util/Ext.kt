package com.jarvis.app.view.util

fun String.toSafeLong(default: Long = 0): Long =
    try {
        this.toLong()
    } catch (error: Throwable) {
        default
    }

fun String.toSafeDouble(default: Double = 0.0): Double =
    try {
        this.toDouble()
    } catch (error: Throwable) {
        default
    }
