package com.slambang.jarvis.demo.simple

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.jarvis.client.JarvisClient

/**
 * Absolute minimal code setup, instantiating an instance of [JarvisClient] to read a string.
 * See the FileProvider that is declared in the manifest.
 */
class MainActivity : AppCompatActivity() {

    private lateinit var jarvis: JarvisClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /**
         * Instantiate the [JarvisClient] and push your app's config.
         * This can be done wherever you like, but the config must be pushed before your
         * app tries to read any config values.
         */
        jarvis = JarvisClient.newInstance(this)
        jarvis.pushConfigToJarvisApp(jarvisConfig)

        findViewById<View>(R.id.get_value_button).setOnClickListener {
            findViewById<TextView>(R.id.value_at_runtime).text =
                jarvis.getString(SOME_STRING_NAME, "Default value")
        }
    }
}
