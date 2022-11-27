# jarvis-demo-advanced

Integrates the [JarvisClient](../jarvis-client) only into `debug` builds and excludes it from `release` builds by switching source sets.  

Please see [jarvis-demo-simple](../jarvis-demo-simple) for a basic setup to get started.  

### Scenario

The demo app injects the common interface [ConfigRepository](src/main/java/com/jarvis/demo/advanced/ConfigRepository.kt) whose implementation depends on the `debug` or `release` build variants.  

```kotlin
interface ConfigRepository {
    fun getStringValue(): String
}
```

#### `debug` build variant

Implements [DebugConfigRepository](src/debug/java/com/jarvis/demo/advanced/repository/DebugConfigRepository.kt) in `src/debug` which uses the [JarvisClient](../jarvis-client).  

```kotlin
/**
 * Only seen by the `debug` build variant
 */
class DebugConfigRepository(
    private val jarvis: JarvisClient
) : ConfigRepository {

    init {
        /**
         * Step 1: Declare your app's config
         */
        val config = jarvisConfig {

            withLockAfterPush = true

            withStringField {
                name = STRING_FIELD_NAME
                value = "Config value"
            }
        }

        /**
         * Step 2: Push the config to the Jarvis App
         */
        with (jarvis) {
            loggingEnabled = true
            pushConfigToJarvisApp(config)
        }
    }

    /**
     * Step 3: Read the value
     */
    override fun getStringValue(): String =
        jarvis.getString(STRING_FIELD_NAME, "Default value")

    companion object {
        private const val STRING_FIELD_NAME = "String field (advanced demo)"
    }
}
```

#### `release` build variant

Implements [ReleaseConfigRepository](src/release/java/com/jarvis/demo/advanced/repository/ReleaseConfigRepository.kt) in `src/release` which does not use the [JarvisClient](../jarvis-client).  

```kotlin
/**
 * Only seen by the `release` build variant
 */
class ReleaseConfigRepository : ConfigRepository {

    /**
     *  Returns the release string value
     */
    override fun getStringValue(): String = "Release value"
}
```

### Try it

Switch between `debug` and `release` build variants.  
For `debug` builds experiment with and without the [Jarvis App](../jarvis-app) being installed.
