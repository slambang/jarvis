package com.slambang.jarvis.demo.simple

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.jarvis.client.JarvisClient
import com.jarvis.client.data.builders.jarvisConfig

class MainActivity : AppCompatActivity() {

    /**
     * 1. Define your app's config
     */
    private val config = jarvisConfig {

        lockAfterPush = true

        withGroup {
            name = "Group 1"
            isCollapsable = true
            startCollapsed = false

            withStringField {
                name = STRING_FIELD_NAME_1
                value = "Config value 1"
            }
        }

        withGroup {
            name = "Group 2"
            isCollapsable = true
            startCollapsed = true

            withStringField {
                name = STRING_FIELD_NAME_2
                value = "Config value 2"
            }
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
         * 3. Push your app's config to the Jarvis App
         */
        jarvis.pushConfigToJarvisApp(config)

        button.setOnClickListener {
            /**
             * 4. Read config values
             */
            textView.text = jarvis.getString(STRING_FIELD_NAME_2, "Default value")
        }
    }

    companion object {
        private const val STRING_FIELD_NAME_1 = "String 1"
        private const val STRING_FIELD_NAME_2 = "String 2"
    }
}
