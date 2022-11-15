package com.slambang.jarvis.demo.simple

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.jarvis.client.JarvisClient
import com.jarvis.client.data.jarvisConfig

/**
 * Absolute minimal code setup, obtaining an instance of [JarvisClient] to read a string.
 * See the FileProvider that is declared in the manifest.
 */
class MainActivity : AppCompatActivity() {

    private val jarvis: JarvisClient by lazy { JarvisClient.newInstance(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /**
         * Push your app's config.
         * This can be done wherever you like, but the config must be pushed before your
         * app tries to read any config values.
         */
        with (jarvis) {
            loggingEnabled = true
            pushConfigToJarvisApp(JARVIS_CONFIG)
        }

        findViewById<View>(R.id.get_value_button).setOnClickListener {
            findViewById<TextView>(R.id.value_at_runtime).text =
                jarvis.getString(SOME_STRING_NAME, "Default value")
        }
    }

    companion object {
        const val SOME_STRING_NAME = "Some string (simple demo)"

        val JARVIS_CONFIG = jarvisConfig {

            withLockAfterPush = false

            withStringField {
                name = SOME_STRING_NAME
                value = "Jarvis value"
            }
        }
    }
}
