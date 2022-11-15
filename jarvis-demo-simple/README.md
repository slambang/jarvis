# jarvis-demo-simple

Bare minimum JarvisClient setup in 3 steps:

#### 1. Gradle dependeny

```groovy
implementation 'com.github.slambang:jarvis:v1.0.3'
```

#### 2. The code

```kotlin
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
```

#### 3. Copy FileProvider into Manifest

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
