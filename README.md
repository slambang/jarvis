## Jarvis: instant app configuration

### What is it?

Jarvis is a **development tool** that provides instant, easy **app configuration**. Feature flags, base URLs and other values can be directly injected into your app **at runtime**.

### Who is it for?


Jarvis is perfect for any **single developer** or **team** that needs dynamic and configurable app data.

*Why waste time developing and integrating a "debug only" or "developer only" hidden configuration menu?*  
*Why manually change values in-code, rebuild and run your app each time to experiment with data changes?*

* Staging or production base URL?
* Feature flag on or off?
* A/B test switch?

Just use Jarvis!

### How does it work?

You define a **Jarvis config** in your own app (in-code) that is pushed to the separate Jarvis App.  
The **Jarvis App** renders your config with a user-friendly UI, allowing you to alter the configuration on-the-fly!   
The Jarvis app being installed is **not mandatory**. Your app will still function without it, using default config values.

There are 2 parts to Jarvis:
1. [JarvisClient](jarvis-client)   
   **You integrate** this small library with your own app. **You define** a Jarvis config and push it to the Jarvis App using the client.
2. [Jarvis App]()  
   **You install** this on the same device as your own app. It displays the Jarvis config defined in your app with a **friendly UI**, allowing you to adjust the configuration as needed.

### What data types does Jarvis support?
String   
Long  
Double  
Boolean  
String list

### Key concepts
The Jarvis App provides some key features:

1. **Active/Inactive**  
   Enables/Disables the whole Jarvis App.  
   When **Active** The Jarvis App will serve config values to your app.  
   When **Inactive** the Jarvis App will act like it is not installed.  
   *Useful when you want to run your app with default values without having to uninstall/reinstall the Jarvis App.*
2. **Locked/Unlocked**  
   When **locked**, the Jarvis App will reject any new incoming configurations and maintain its current configuration state.  
   When **unlocked** the Jarvis App will accept new configurations, overwriting the existing configuration.  
   *Useful when your app has already pushed its config to the Jarvis App and you don't want to keep overwirting it.*
3. **Published/unpublished**  
   When a configuration field is **published** it will be returned by the Jarvis App.  
   When a configuration field is **unpublished** it will not be returned by the Jarvis App, causing the JarvisClient to return the default value.  
   *Useful when you want to enable/disable individual configuration fields without having to uninstall/reinstall the Jarvis App.*

### How do I get setup?

See the [jarvis-demo-simple](jarvis-demo-simple) app for a simple setup.  
See the [jarvis-demo-advanced](jarvis-demo-advanced) app for an advanced setup.

### Get the Jarvis App APK

A pre-built Jarvis App APK can be downloaded [here]().
