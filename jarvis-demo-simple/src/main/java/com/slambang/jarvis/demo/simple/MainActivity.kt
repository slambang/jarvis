package com.slambang.jarvis.demo.simple

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.jarvis.client.JarvisClient
import com.jarvis.client.data.jarvisConfig

class MainActivity : AppCompatActivity() {

    /**
     * 1. Declare a Jarvis Config
     */
    private val config = jarvisConfig {

        withLockAfterPush = true

        withStringField {
            name = STRING_FIELD_NAME
            value = "Jarvis value"
        }
    }

    /**
     * 2. Create a JarvisClient instance
     */
    private val jarvis: JarvisClient by lazy { JarvisClient.newInstance(this) }

    private val button: View by lazy { findViewById(R.id.get_value_button) }
    private val textView: TextView by lazy { findViewById(R.id.value_at_runtime) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /**
         * 3. Push your app's Jarvis config to the Jarvis App.
         */
        with (jarvis) {
            loggingEnabled = true
            pushConfigToJarvisApp(config)
        }

        button.setOnClickListener {
            /**
             * 4. Read the config value
             */
            textView.text = jarvis.getString(STRING_FIELD_NAME, "Default value")
        }
    }

    companion object {
        private const val STRING_FIELD_NAME = "String field (simple demo)"
    }
}
