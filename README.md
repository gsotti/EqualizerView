
  
Simple Equalizer Library for Android
==================================  
  
This Android library project is developed to you use an animated equalizer for your music related apps. Original version from [@claucookie](https://github.com/claucookie/mini-equalizer-library-android).  
  
[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-Simple%20Equalizer%20Library-brightgreen.svg?style=flat)](https://android-arsenal.com/details/1/3715)  
  
Requirements  
--------  
Android 4.0 or higher.  
  
Installation  
--------  
Step 1: Add this in your `build.gradle` (Project) at the end of repositories
```groovy  
allprojects {  
  ...
  repositories {
    maven { url "https://jitpack.io" }
  }
}  
```  
Step 2: Add this in your `build.gradle` (Module)  
```groovy  
dependencies {  
  implementation "com.github.gsotti:EqualizerView:v0.2"
}  
 ```  
Layout  
--------  
````xml  
<eu.gsottbauer.equalizerview.EqualizerView
    android:id="@+id/equalizer"  
    android:layout_width="match_parent"  
    android:layout_height="50dp"  
    app:marginLeft="1dp"    
    app:marginRight="1dp"  
    app:barColor="@color/colorAccent"  
    app:barCount="40"  
    app:barAnimationDuration="2000"  
    app:barWidth="5dp"  
    app:runInBatterySaveMode="true|false" />  
````  
  
Attributes  
--------  
|Attribute|Description|Type|Default|  
|--|--|--|--|  
|barColor|Color of the bars|Color|Black|  
|barAnimationDuration|The animation follows a pattern and the number of loops is infinite. To set the duration of each loop, use this attribute|Integer (Miliseconds)|  
|barCount|Number of the bars|Integer|  
|barWidth|Width of the bars|Dimension|  
|marginLeft|Margin left between each bar|Dimension|  
|marginRight|Margin right between each bar|Dimension|  
|runInBatterySaveMode|Play animation if even battery saver is on|Boolean|  
  
  
  
Controlling the animation  
--------  
  
````java  
EqualizerView equalizer = findViewById(R.id.equalizer);  
equalizer.animateBars(); // To start the animation  
equalizer.stopBars(); // To stop the animation  
````  
  
Contribution  
--------  
Every idea, fork and suggestion is very welcome.  
  
Author  
--------  
Stefan Gsottbauer ([@gsotti](https://github.com/gsotti))
  
License  
--------  
[MIT](https://opensource.org/licenses/MIT)
