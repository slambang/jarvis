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
            name = "Name of the string field"
            value = "Field value"
        }

        withLongField {
            name = "Name of the long field"
            value = 0L
        }

        withDoubleField {
            name = "Name of the double field"
            value = 1.0
        }

        withBooleanField {
            name = "Name of the boolean field"
            value = true
        }

        // Can also be used for enums
        withStringListField {
            name = "Name of the string list field"
            value = listOf("a", "b", "c")
            defaultSelection = 0
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
         * 3. Push your app's config to the Jarvis App.
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
