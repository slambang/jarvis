package com.jarvis.demo.advanced

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.jarvis.demo.advanced.repository.injectRepository

/**
 * Switch between debug and release build variants to change the [ConfigRepository] implementation.
 */
class MainActivity : AppCompatActivity() {

    private val className: TextView by lazy { findViewById(R.id.repository_class_name) }
    private val button: View by lazy { findViewById(R.id.get_value_button) }
    private val textView: TextView by lazy { findViewById(R.id.value_at_runtime) }

    private val configRepo: ConfigRepository by lazy { injectRepository(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        className.text = configRepo::class.simpleName

        button.setOnClickListener {
            textView.text = configRepo.getStringValue()
        }
    }
}
