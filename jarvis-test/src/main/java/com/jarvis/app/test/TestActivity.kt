package com.jarvis.app.test

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatSpinner
import com.google.android.material.snackbar.Snackbar
import com.jarvis.client.JarvisClient
import com.jarvis.client.data.*
import java.lang.IllegalStateException

class TestActivity : AppCompatActivity() {

    private val jarvisClient: JarvisClient by lazy {
        JarvisClient.newInstance(applicationContext).apply {
            loggingEnabled = BuildConfig.DEBUG
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val selections = jarvisConfig.fields.map { it.name }

        val fieldAdapter =
            ArrayAdapter<Any?>(this, R.layout.support_simple_spinner_dropdown_item, selections)
        val spinner = findViewById<AppCompatSpinner>(R.id.app_spinner).apply {
            adapter = fieldAdapter
        }

        findViewById<View>(R.id.get_config_value_button).setOnClickListener {
            val value = getValue(spinner.selectedItemPosition)
            showSnackBar(value)
        }

        findViewById<View>(R.id.push_config_button).setOnClickListener {
            val message = when (jarvisClient.pushConfigToJarvisApp(jarvisConfig)) {
                JarvisClient.PushConfigResult.SUCCESS -> "Success"
                JarvisClient.PushConfigResult.FAILURE -> "Failure (is the Jarvis app installed?)"
                JarvisClient.PushConfigResult.LOCKED -> "Failure: Jarvis app is locked"
            }

            showSnackBar(message)
        }
    }

    private fun getValue(index: Int): Any {
        val field = jarvisConfig.fields[index]
        return when (field::class) {
            StringField::class -> jarvisClient.getString(field.name, "Default string value")
            LongField::class -> jarvisClient.getLong(field.name, 1234321L)
            DoubleField::class -> jarvisClient.getDouble(field.name, 123.321)
            BooleanField::class -> jarvisClient.getBoolean(field.name, false)
            StringListField::class -> getStringField(field as StringListField)
            else -> throw IllegalStateException("Unexpected field class ${field::class}.")
        }
    }

    private fun getStringField(field: StringListField): String {
        val selection = jarvisClient.getStringListSelection(field.name, field.defaultSelection)
        return field.value[selection]
    }

    private fun showSnackBar(value: Any) =
        Snackbar.make(findViewById(R.id.main_activity_root), value.toString(), Snackbar.LENGTH_LONG)
            .show()
}

enum class ClientHeader {
    ANDROID,
    IOS
}
