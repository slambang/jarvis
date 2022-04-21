package com.jarvis.demo.advanced

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.jarvis.demo.advanced.repository.ConfigRepository
import org.koin.android.ext.android.inject

/**
 * Switch the build variant between `debug` and `release` in the IDE.
 * The implementation of [ConfigRepository] will change at runtime.
 *
 * For the `debug` build variant, experiment with having the Jarvis app installed/uninstalled.
 * Also experiment with publishing/un-publishing Jarvis config fields.
 */
class MainActivity : AppCompatActivity() {

    /**
     * This single interface could be injected anywhere in your app.
     */
    private val configRepository: ConfigRepository by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<TextView>(R.id.repository_class_name).text =
            configRepository.javaClass.simpleName

        findViewById<View>(R.id.get_value_button).setOnClickListener {
            findViewById<TextView>(R.id.value_at_runtime).text = configRepository.someStringValue()
        }
    }
}
