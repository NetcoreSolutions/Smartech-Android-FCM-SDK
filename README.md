## [![Netcore Logo](https://netcore.in/wp-content/themes/netcore/img/Netcore-new-Logo.png)](http:www.netcore.in) SDK For Native Android Application Using FCM

##### (for cordova android Application follow above cordova PDF )

### Prerequisites

##### 1. Get google-services.json file and FCM Server Key from Firebase Developer console

##### 2. Get App ID from Smartech Panel

### For FCM  Setup in Application:

##### 1. Add google-services.json file in app directory of project

##### 2. Add below code in dependencies of buildscript in project build.gradle
```java
 classpath 'com.google.gms:google-services:3.0.0'
```

##### 3. Add below code at last line in app build.gradle
```java
apply plugin: 'com.google.gms.google-services'
```

### For Integrate SDK:    
###### (supported android minsdkversion 15 or more (i.e. next all versions after ice cream sandwich))

##### Add below code in dependencies below code: (not in child dependencies)
```java
    compile 'in.netcore.smartechfcm:smartech-fcm:1.0.7'
    compile 'com.google.firebase:firebase-messaging:11.6.0'
```
### For Push Notification as well as inbuilt activities
Add below code in launcher Activity in method onCreate (above the super.onCreate line)
```java
    NetcoreSDK.register(getApplication(),”<AppID>”, “<identity>”);
    //Identity must be “”(blank) or Primary key which defined on smartech Panel
    //With this code, SDK will start sending App Launch and First Launch activities by default.
```
### For Login Activity 
add below code for login activity to make user Identified:
```java
    NetcoreSDK.login( context, “<identity>” );
```
### For Logout Activity 
add below code for logout activity:
```java
    NetcoreSDK.logout( context, “<identity>” );
```
### For Activity tracking 
add below code for every activity:
```java
    NetcoreSDK.track( context, “<identity>”,<eventId>,payload);
    //Activity tracking code can be generated from Smartech panel
```
### For Profile Update
Add below code in activity where you passing all details for profile:-
```java
    NetcoreSDK.profile(this, “<identity>”, newprofile); 
    //newProfile is JSONObject with all details of User
    E.g. 
    JSONObject newProfile = new JSONObject();
    try {
    newProfile.put( "NAME", "Developer" );
    newProfile.put( "AGE", 25 );
    newProfile.put( "MOBILE", "1234567890" );
    NetcoreSDK.profile(this, “<identity>”, newprofile);
    }
    catch ( JSONException e ) {
    e.printStackTrace();
    }
    //Attribute name must be in Capital such as NAME, AGE etc.
```
### To set custom push notification icon
##### Add below code in launching activity:-
```java
NetcoreSDK.setPushIcon(context, "<path to drawable icon>");

e.g. NetcoreSDK.setPushIcon(getApplicationContext(), R.mipmap.ic_launcher);
```
Note: The notification icon should be strictly in .png format as per Google's guidelines & Preferable size for push notification icons is mentioned below:

drawable-mdpi :- 24 x 24 <br/>
drawable-hdpi :- 36 x 36 <br/>
drawable-xhdpi :- 48 x 48 <br/>
drawable-xxhdpi :- 72 x 72 <br/>
drawable-xxxhdpi :- 96 x 96 <br/>

### Go to tools->android and click on sync project with gradle files

### Run the application

### To support deeplink in application
Add these peice of code in AndroidManifest.xml file in each activity in which you want Deep-Link.

```xml
    <intent-filter>
    <action android:name = "android.intent.action.VIEW" />
    <category android:name = "android.intent.category.DEFAULT" />
    <category android:name = "android.intent.category.BROWSABLE" />
    <data android:scheme = "<scheme name>" android :host= "<hostname>" />
    </intent-filter>
Ex.
    <intent-filter>
    <action android:name= "android.intent.action.VIEW" />
    <category android:name= "android.intent.category.DEFAULT" />
    <category android:name= "android.intent.category.BROWSABLE" />
    <data android:scheme = "smartech" android :host= "products" />
    </intent-filter>
```


