package com.jarvis.demo.advanced

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.jarvis.demo.advanced.repository.getRepository

/**
 * Switch between `debug` and `release` build variants
 * to change the implementation of [ConfigRepository].
 */
class MainActivity : AppCompatActivity() {

    private val className: TextView by lazy { findViewById(R.id.repository_class_name) }
    private val button: View by lazy { findViewById(R.id.get_value_button) }
    private val textView: TextView by lazy { findViewById(R.id.value_at_runtime) }

    /**
     * The interface whose implementation changes depending on te build variant.
     */
    private val configRepo: ConfigRepository by lazy { getRepository(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        className.text = configRepo.javaClass.simpleName

        button.setOnClickListener {
            textView.text = configRepo.getStringValue()
        }
    }
}
