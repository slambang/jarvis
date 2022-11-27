# jarvis-demo-advanced

Integrates the [JarvisClient](../jarvis-client) into a **debug only** build. Totally excludes Jarvis from a **release** build.

Also be aware of [jarvis-demo-simple](../jarvis-demo-simple) for a simpler setup to get started.  

### Scenario

The demo app injects a common interface [ConfigRepository](src/main/java/com/jarvis/demo/advanced/ConfigRepository.kt) whose implementation depends on the `debug` or `release` build variants.

#### `debug` build variant

Implements [DebugConfigRepository](src/debug/java/com/jarvis/demo/advanced/repository/DebugConfigRepository.kt) which **does** use the [JarvisClient](../jarvis-client).
Returns config values from the [Jarvis App](../jarvis-app) if installed. Otherwise returns default values.  

#### `release` build variant

Implements [DebugConfigRepository](src/release/java/com/jarvis/demo/advanced/repository/ReleaseConfigRepository.kt) which **does not** use the [JarvisClient](../jarvis-client).  
Returns the release version of config values only.  

### Try it

Switch between `debug` and `release` build variants.  
For `debug` builds experiment with and without the [Jarvis App](../jarvis-app) being installed.
