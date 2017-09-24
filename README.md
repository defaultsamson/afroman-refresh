# afroman-refresh
A remake of afroman-client using libGDX


# Project Setup
1. [Download the LibGDX setup application](https://libgdx.badlogicgames.com/download.html)
2. Run the .jar file
3. Set the following fields to these values:

![](https://i.imgur.com/OGXKUMz.png)
![](https://i.imgur.com/Wzf3KF9.png)

4. (optional) Click on the "Advanced" button at the bottom and choose which IDE you will be using

![](https://i.imgur.com/EI5UcHF.png)
![](https://i.imgur.com/Gx9rtXM.png)

### IntelliJ IDEA
5. Open a new project from IntelliJ and choose the top-most node

![](https://i.imgur.com/vaPUz8V.png)

6. Open `android/AndroidManifest.xml` and add the line
```
<uses-permission android:name="android.permission.INTERNET"/>
```
to use first body like this

![](https://i.imgur.com/OmiySXa.png)

7. In IDEA go to *Run > Edit Configurations* and add a new Application configuration

![](https://i.imgur.com/j6C7Iol.png)

8. Set **Name** to `desktop`
Set **Main class** to `afroman.game.desktop.DesktopLauncher`
Set **Working directory** to the android/assets folder
Set **Use classpath of module** to `desktop`

9.

### Eclipse
Basically the same setup but with different menus

### NetBeans
Why do people even use this?

### Notepad
Good luck 
