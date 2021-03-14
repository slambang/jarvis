package com.jarvis.app.test

import com.jarvis.client.data.jarvisConfig

val jarvisConfig = jarvisConfig {

    withLockAfterPush = false

    withStringField {
        name = "API Base URL"
        description =
            "Override this value to point the app at different development environments."
        value = "https://www.staging.com/end/point"
        regex = "https?://(www\\.)?[-a-zA-Z0-9@:%._+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}\\b([-a-zA-Z0-9()@:%_+.~#?&/=]*)"
        hint = "Https url"
    }

    withStringField {
        name = "Force username"
        description = "Overrides the logged in username."
        value = "user.name"
        minLength = 5
        maxLength = 10
    }

    withBooleanField {
        name = "Force dark-mode"
        value = true
        description = "Forces the app into dark-mode.\nThis is independent of the OS (system) setting."
    }

    withLongField {
        name = "Login screen retry count"
        description =
            "The number of times the login screen will retry if the API call fails."
        value = 3
        min = 0
        max = 10
    }

    withLongField {
        name = "Screen brightness"
        description =
            "The brightness of the screen as a percentage.\n\n0 = off\n100 = full"
        value = 75
        min = 0
        max = 100
        asRange = true
    }

    withDoubleField {
        name = "Super complicated calculation delta."
        description = "Useful for testing outputs without having to rebuild & run.\n> HIGHLY EXPERIMENTAL <"
        value = 2.67
        min = 1.0
        max = 5.93
        asRange = true
    }

    withStringListField {
        name = "Force client header"
        description =
            "Force the client header in API calls.\n\nUseful for testing differences in API responses between the apps."
        value = ClientHeader.values().map { it.name }
        defaultSelection = 0
    }

    withStringListField {
        name = "Bitmap buffer size"
        description =
            "The size of the internal bitmap buffer.\n\nLower values are slower but use less memory.\nHigher values are faster but use more memory."
        value = listOf(256, 512, 1024, 2048, 4096, 8192).map { it.toString() }
        defaultSelection = 3
    }
}
