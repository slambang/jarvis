# jarvis-demo-simple

Basic [JarvisClient](../jarvis-client) setup in 2 steps.  

- [1. Gradle dependency and FileProvider](#1-gradle-dependency-and-fileprovider)
- [2. The code](#2-the-code)
- [Try it](#try-it)

#### 1. Gradle dependency and FileProvider

Add this dependency to your app's `build.gradle`:

```groovy
implementation 'com.github.slambang:jarvis:<LATEST>'
```

Copy this [FileProvider](https://developer.android.com/reference/androidx/core/content/FileProvider) into your app's `AndroidManifest.xml`. It is required only for the client to push your config to the [Jarvis App](../jarvis-app):

```xml
<provider
    android:name="androidx.core.content.FileProvider"
    android:authorities="${applicationId}.jarvis_config_provider"
    android:exported="false"
    android:grantUriPermissions="true">

    <meta-data
        android:name="android.support.FILE_PROVIDER_PATHS"
        android:resource="@xml/jarvis_client_file_paths" />
</provider>
```

#### 2. The code

Read [the client docs](https://htmlpreview.github.io/?https://github.com/slambang/jarvis/main/docs/index.html) for more information.

```kotlin
class MainActivity : AppCompatActivity() {

    /**
     * 1. Define your app's config
     */
    private val config = jarvisConfig {

        withLockAfterPush = true

        withStringField {
            name = STRING_FIELD_NAME
            value = "Config value"
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
        with (jarvis) {
            loggingEnabled = true
            pushConfigToJarvisApp(config)
        }

        button.setOnClickListener {
            /**
             * 4. Read config values
             */
            textView.text = jarvis.getString(STRING_FIELD_NAME, "Default value")
        }
    }

    companion object {
        private const val STRING_FIELD_NAME = "String field (simple demo)"
    }
}
```

### Try it

If the [Jarvis App](../jarvis-app) is installed then the config values are returned from there.
If the [Jarvis App](../jarvis-app) is not installed then default values are returned.

Experiment with and without the [Jarvis App](../jarvis-app) being installed.  
