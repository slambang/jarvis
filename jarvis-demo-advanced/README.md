# jarvis-demo-advanced
Integrates the `JarvisClient` into a **debug only** build. Totally excludes Jarvis from a **release** build.  
The motivation here is to demonstrate that Jarvis doesn't need to intrude on release builds.  

Returns a String field to the test app, whose value depends on the build variant and Jarvis App config setup.  

Please refer to the [jarvis-demo-simple]() demo for bare minimum setup and example usage.  

### Architecture

The app injects a common interface (`ConfigRepository`) whose implementation depends on the `debug` or `release` build variants:

#### `debug` build variant
Implements `DebugConfigRepository` which **does** use the `JarvisClient`.  
If the Jarvis app is installed then the String value returned is as configured in the Jarvis App.  
If the Jarvis App is not installed then it falls back to the default value as defined in the config.  
There is **no hard dependency** on the Jarvis App being installed. It is totally optional.

#### `release` build variant
Implements `ReleaseConfigRepository` which **does not** use the `JarvisClient`.  
Returns the release version of the String field only.
