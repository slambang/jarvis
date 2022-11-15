# jarvis-demo-simple

Bare minimum JarvisClient setup in 3 steps:

#### 1. Gradle dependeny

```groovy
implementation 'com.github.slambang:jarvis:v1.0.3'
```

#### 2. Kotlin code

```kotlin
class MainActivity : AppCompatActivity() {

    /**
     * Step 1: Create the Jarvis Config
     */
    private val config = jarvisConfig {

        withLockAfterPush = false

        withStringField {
            name = SOME_STRING_NAME
            value = "Jarvis value"
        }
    }

    /**
     * Step 2: Create a JarvisClient instance
     */
    private val jarvis: JarvisClient by lazy { JarvisClient.newInstance(this) }

    private val button: View by lazy { findViewById(R.id.get_value_button) }
    private val textView: TextView by lazy { findViewById(R.id.value_at_runtime) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /**
         * Step 3: Push your app's Jarvis config to the Jarvis App.
         */
        with (jarvis) {
            loggingEnabled = true
            pushConfigToJarvisApp(config)
        }

        button.setOnClickListener {
            /**
             * Step 4: Read the config value
             */
            textView.text = jarvis.getString(SOME_STRING_NAME, "Default value")
        }
    }

    companion object {
        private const val SOME_STRING_NAME = "Some string (simple demo)"
    }
}
```

#### 3. Declare FileProvider in Manifest

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
