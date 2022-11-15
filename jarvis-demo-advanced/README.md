# jarvis-demo-advanced
Integrates the `JarvisClient` into a **debug only** build.

You have a (hypothetical) `RealRemoteConfigService` in production (Firebase et al.) which provides the _same_ configuration values for _every_ connected user (devs, QA, PO,  etc.).
You need the ability to set _individual_ configuration values for _every_ connected user (internally, on staging, etc.).

The `debug` app uses Jarvis as _man-in-the-middle_, providing configuration values if it is installed, otherwise defaulting to the hypothetical `RealRemoteConfigService`.

### Architecture

The app uses a common interface (injected `ConfigRepository`) whose implementation depends on the `debug` or `release` build variants:

#### `debug` build variant
Implements `DebugConfigRepository` which **does** use the `JarvisClient`.
If the Jarvis App is not installed it falls back to the (hypothetical) `RealRemoteConfigService` as the default value.
There is **no hard dependency** on the Jarvis App being installed. It is totally optional.

#### `release` build variant
Implements `ReleaseConfigRepository` which **does not** use the `JarvisClient`.
Calls the (hypothetical) `RealRemoteConfigService` directly.

The following diagram visualises the simple pattern:
[diagram-here]

# jarvis-demo-advanced
This app demonstrates how to integrate the `JarvisClient` into a **debug** build, but totally excludes it from a **release** build. Imagine:
* You have integrated something like [Firebase Remote Config](https://firebase.google.com/docs/remote-config/get-started?platform=android) into your app which provides the _same configuration values for every type of user_ (real users, devs, QA/Testers and managers). All values are read from the service in the cloud. Development and testing like this is difficult.
* You need the ability for _every internal user_ (devs, QA/Testers and managers) to be able to _set their own configuration values as needed_.

This app uses `JarvisClient` as _man-in-the-middle_ to provide configuration values (if the `Jarvis App` is installed), otherwise defaulting to Firebase.

### Architecture
The app uses [Koin](https://insert-koin.io/docs/quickstart/android/) (simple dependency injection, replace with your preferred DI framework) to inject a common interface (`ConfigRepository`) whose implementation depends on the `debug` or `release` build variants:

* `release` implements `ReleaseConfigRepository` which **does not depend on** the `JarvisClient`. It calls Firebase directly.
* `debug` implements `DebugConfigRepository` which **does depend on** the `JarvisClient`.
  If the `Jarvis App` is installed then the value is read from there, otherwise it falls back to Firebase.
  There is **no hard dependency** on the `Jarvis App` being installed. It is optional.

The following diagram visualises the simple pattern:
[diagram-here]

```groovy
debugImplementation 'com.github.slambang:jarvis:v1.0.3'
```
