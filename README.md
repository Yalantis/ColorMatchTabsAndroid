# ColorMatchTabsAndroid

[![License](http://img.shields.io/badge/license-MIT-green.svg?style=flat)]()
[![](https://jitpack.io/v/yalantis/todolist.svg)](https://jitpack.io/#yalantis/todolist)
[![Yalantis](https://raw.githubusercontent.com/Yalantis/PullToRefresh/develop/PullToRefreshDemo/Resources/badge_dark.png)](https://yalantis.com/?utm_source=github)

Check this [project on dribbble](https://dribbble.com/shots/2702517-Review-App-Concept)

<img src="color-tabs-gif.gif"/>

## Requirements
- Android SDK 16+

## Usage

Add to your root build.gradle:
```Groovy
allprojects {
	repositories {
	...
	maven { url 'https://jitpack.io' }
	}
}
```

Add the dependency:
```Groovy
dependencies {
	compile 'com.github.Yalantis:ColorMatchTabsAndroid:v0.0.1'
}
```

## How to use this library in your project?

To use ```ColorMatchTabs```, add ```ColorMatchTabLayout``` to the XML layout of your activity. Also, please note that a ```ViewPager``` is also added, which should be attached to ```ColorMatchTabs``` in the activity below through the following code:

```xml
<RelativeLayout
xmlns:android="http://schemas.android.com/apk/res/android"
   xmlns:app="http://schemas.android.com/apk/res-auto"
   xmlns:tools="http://schemas.android.com/tools"
   android:layout_width="match_parent"
   android:layout_height="match_parent">
 
	<com.yalantis.colormatchtabs.colormatchtabs.colortabs.ColorMatchTabLayout
	   android:id="@+id/tabLayout"
	   android:layout_width="match_parent"
	   android:layout_height="wrap_content"
	   android:layout_below="@+id/toolbar"
	   android:background="?attr/colorPrimary"
	   android:elevation="6dp"
	   android:minHeight="?attr/actionBarSize"
	   android:theme="@style/ThemeOverlay.AppCompat.Dark.ActionBar" />
 
	<android.support.v4.view.ViewPager
	   android:id="@+id/pager"
	   android:layout_width="match_parent"
	   android:layout_height="fill_parent"
	   android:layout_below="@id/tabLayout" />
 
</RelativeLayout>
```
In your ```onCreate()``` method bind the ```ColorMatchTabs``` and add to it tabs using the ```.addTab()``` method. 

```kotlin
colorMatchTabLayout.addTab(ColorTabAdapter.createColorTab(colorMatchTabLayout, tabName, selectedColor, icon))
```
If you use an ```OnPageChangeListener``` with your ```ViewPager```, you should set ```ColorTabLayoutOnPageChangeListener(colorMatchTabLayout)``` in the ```ViewPager``` rather than on the pager directly.

```kotlin
viewPager.addOnPageChangeListener(ColorTabLayoutOnPageChangeListener(tabLayout))
```

## Customization
One of the most important feature of every custom view is the ability to customize its look as needed. By calling the following methods (or attributes), you can customize ```ColorMatchTabLayout``` as you need.

```kotlin
// to change unselected tab color you can change background color for all layout
setBackgroundColor(color: Int) 
 
//Change selected tab color, icon and title of tab:
tab.selectedColor = color : Int
tab.icon = icon : Drawable
tab.text = iconTitle : String
 
//or change all these parameters
ColorTabAdapter.createColorTab(colorMatchTabLayout, tabName, selectedColor, icon)
 
//Set selected ColorTab width in portrait and horizontal orientation
selectedTabWidth, selectedTabHorizontalWidth
 
addArcMenu(arcMenu)
```
To change the selected tab, you can use the methods below:

```kotlin
//Set selected tab
selectedTabIndex: Int
selectedTab: Int
 
//Get the position of the current selected tab
selectedTabPosition : Int
 
//Return ColorTab at the specified index 
colorMatchTabLayout.getTabAt(index : Int)
```
```ArcMenu``` is an addition to the ```ColorMatchTabLayout```. To use ```ArcMenu```, add it to the XML layout of your activity with the color tabs as shown below:

```xml
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
   xmlns:app="http://schemas.android.com/apk/res-auto"
   xmlns:tools="http://schemas.android.com/tools"
   android:layout_width="match_parent"
   android:layout_height="match_parent"
   tools:context="com.yalantis.colormatchtabs.colortabs.MainActivity">
 
   <com.yalantis.colormatchtabs.colormatchtabs.colortabs.ColorMatchTabLayout
       android:id="@+id/colorMatchTabLayout"
       android:layout_width="match_parent"
       android:layout_height="wrap_content"
       android:layout_below="@+id/toolbar"
       android:background="?attr/colorPrimary"
       android:elevation="6dp"
       android:minHeight="?attr/actionBarSize"
       android:theme="@style/ThemeOverlay.AppCompat.Dark.ActionBar" />
 
   <android.support.v4.view.ViewPager
       android:id="@+id/viewPager"
       android:layout_width="match_parent"
       android:layout_height="fill_parent"
       android:layout_below="@id/colorMatchTabLayout" />
 
   <com.yalantis.colormatchtabs.colormatchtabs.menu.ArcMenu
       android:id="@+id/arcMenu"
       android:layout_width="match_parent"
       android:layout_height="wrap_content"
       android:layout_alignParentBottom="true"
       android:layout_centerHorizontal="true" />
 
</RelativeLayout>
```
After that, bind your ```ArcMenu``` and add it to ```ColorMatchTabLayout```:


```kotlin
colorMatchTabLayout.addArcMenu(arcMenu)
```

**Remember** It’s important to note that the ```ArcMenu``` doesn’t work as an independent element. A user needs to add this component to ```ColorMatchTabLayout```. Also, this part of the animation works only with 3–5 tabs in ```ColorMatchTabLayout```. With a different number of tabs, you get an ```InvalidNumberOfTabsException```.

## Let us know!

We’d be really happy if you could send us links to your projects where you use our component. Just send an email to github@yalantis.com And do let us know if you have any questions or suggestion regarding the animation. 

P.S. We’re going to publish more awesomeness wrapped in code and a tutorial on how to make UI for iOS (Android) better than better. Stay tuned!

## License

	The MIT License (MIT)

	Copyright © 2017 Yalantis, https://yalantis.com

	Permission is hereby granted, free of charge, to any person obtaining a copy
	of this software and associated documentation files (the "Software"), to deal
	in the Software without restriction, including without limitation the rights
	to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
	copies of the Software, and to permit persons to whom the Software is
	furnished to do so, subject to the following conditions:

	The above copyright notice and this permission notice shall be included in
	all copies or substantial portions of the Software.

	THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
	IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
	FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
	AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
	LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
	OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
	THE SOFTWARE.

