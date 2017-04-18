Simple Equalizer Library for Android 
==================================

 This Android Library project is created to let you use a animated equalizer inside your music related apps. Origingal version
from @claucookie

#[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-Simple%20Equalizer%20Library-brightgreen.svg?style=flat)](https://android-arsenal.com/details/1/3715)

**Supported Android versions**: Android 4.0+

How to use it
----------------------

# Usage
Step 1. Add the JitPack repository to your build file
```groovy
allprojects {
        repositories {
            ...
            maven { url "https://jitpack.io" }
        }
    }
```
Step 2. Add the dependency
```groovy
dependencies {
  compile 'com.github.gsotti:EqualizerView:v0.2'
 }
 ```

## Layout

````xml
 <eu.gsottbauer.equalizerview.EqualizerView
         android:id="@+id/equalizer"
         android:layout_width="match_parent"
         android:layout_height="50dp"
         android:layout_above="@+id/play_button"
         android:layout_margin="30dp"
         app:barColor="@color/colorAccent"
         app:barCount="40"
         app:barAnimationDuration="2000"
         app:barWidth="5dp"
         app:marginLeft="1dp"
         app:marginRight="1dp"
         app:runInBatterySaveMode="true|false"
         />
````

### Attributes

There is some custom attributes you can adjust from the xml:
 - barColor : the equalizer bars color (default is black)
 - barAnimationDuration : (millisecs) the animation follows a pattern and the number of loops is infinite. To set the duration of each loop, use this attribute.
 - barCount :  number of the bars
 - barWidth : bar width
 - marginLeft/Right : bar margin
 - runInBatterySaveMode:  play animation if battery saver is on
 
## Activity

### Initialization + animation

To start animating the equalizer you should add:

````java
EqualizerView equalizer = (EqualizerView) findViewById(R.id.equalizer_view);
equalizer.animateBars(); // Whenever you want to tart the animation
equalizer.stopBars(); // When you want equalizer stops animating
````
And... that's it!, so simple.


Contribution
------------
Every idea, fork and suggestion is very welcome.

Author
--------
Stefan Gsottbauer @gsotti

License
-------
http://opensource.org/licenses/MIT
