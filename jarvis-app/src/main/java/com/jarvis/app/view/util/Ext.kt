package com.jarvis.app.view.util

fun String.toSafeLong(default: Long): Long =
    try {
        this.toLong()
    } catch (error: Throwable) {
        default
    }

fun String.toSafeDouble(default: Double): Double =
    try {
        this.toDouble()
    } catch (error: Throwable) {
        default
    }
