# afroman-refresh
An Android and Desktop remake of [afroman-client](https://github.com/qwertysam/afroman-client) using [libGDX](https://libgdx.badlogicgames.com/index.html).

## Contributing

1. If not already installed, install [IntelliJ IDEA](https://www.jetbrains.com/idea/download), [Eclipse](https://www.eclipse.org/downloads/), [Android Studio](https://developer.android.com/studio), or any IDE of your choice 
2. If not already installed, install [OpenJDK 8+](https://openjdk.java.net/install/) or the [Oracle Java SE JDK 8+](https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
3. If not already installed, install the [Android SDK](https://developer.android.com/studio/index.html#downloads) (can be found under Command line tools only)
4. Clone this repository with `git clone https://github.com/qwertysam/afroman-refresh.git`
5. Open the project in an IDE, and sync Gradle to download all the dependencies
6. The main source files are found in `afroman-refresh/core/src`

Now you can run the game!

### Troubleshooting
- If you are getting errors from Gradle about not being able to compile the :android project, you must either configure your system's ANDROID_HOME or create the file `afroman-refresh/local.properties` and add the line `sdk.dir=/path/to/your/Android/Sdk`
- If running the game from an IDE is failing to load the assets, you may need to change your Run/Debug configuration to have the working directory set to `afroman-refresh/android/assets` (this is because the desktop and android modules both rely on the same Android assets folder)
- When exporting as a JAR file, you may need to...
  - Copy the ***contents*** of the `afroman-refresh/android/assets` into the JAR archive
  - In the JAR archive, edit `META-INF/MANIFEST.MF` and add the following line if it doesn't exist `Main-Class: afroman.game.desktop.DesktopLauncher`

