package com.slambang.jarvis.demo.simple

import com.jarvis.client.data.jarvisConfig

const val SOME_STRING_NAME = "Some string (simple demo)"

val jarvisConfig = jarvisConfig {

    withLockAfterPush = false

    withStringField {
        name = SOME_STRING_NAME
        value = "Jarvis value"
    }
}
