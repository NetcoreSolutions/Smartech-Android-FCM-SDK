## [![Netcore Logo](https://netcore.in/wp-content/themes/netcore/img/Netcore-new-Logo.png)](http:www.netcore.in) SDK For Native Android Application Using FCM

##### (for cordova android Application follow above cordova PDF )

### Prerequisites

##### 1. Get google-services.json file and FCM Server Key from Firebase Developer console

##### 2. Get App ID from Smartech Panel

### For FCM  Setup in Application:

##### 1. Add google-services.json file in app directory of project

##### 2. Add below code in dependencies of buildscript in project build.gradle
```java
 classpath 'com.google.gms:google-services:3.1.0'
 classpath​ ​'com.android.tools.build:gradle:3.1.3'
```

##### 3. Add below code at last line in app build.gradle
```java
apply plugin: 'com.google.gms.google-services'
```

### For Integrate SDK:    
###### (supported android minsdkversion 16 or more (i.e. jelly bean and above))

##### Add below code in dependencies of build.gradle file of app: (not in child dependencies)
```java
    implementation 'in.netcore.smartechfcm:smartech-fcm:1.1.3'
    implementation 'com.google.firebase:firebase-messaging:11.6.2'
    implementation​​ 'com.google.code.gson:gson:2.8.0'
    implementation​​ 'com.firebase:firebase-jobdispatcher:0.8.5'
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

### To fetch delivered push notifications
##### Add below code in activity:
```java
JSONArray jsonNotification = NetcoreSDK.getNotifications(context);
```
Note: The method will return a JSONArray of push notifications delievered

### To use push notifications along with custom Firebase Messaging Class
#### Add below code in Firebase Messaging Class:

```java
boolean pushFromSmartech = NetcoreSDK.handleNotification(context, remoteMessage);
```
Note:  The behaviour of the method mentioned above is as follows:

- It returns true, if the push notification is received from the Smartech panel. In this case, the SDK will do the needful itself without any extra efforts. 

-  It returns false, in case if the push notification is not received from the Smartech panel. In this case, one can handle the push notification as per their custom implementation of Firebase Messaging Class or pass on the instructions to some other SDK as per the choice.

### If user wants to opt out from being tracked
#### Add below code

```java
NetcoreSDK.optOut(context, <boolean_flag>);
```
Note:  The method mentioned above accepts a compulsory boolean value (true/false).

- If an end user wants to opt out, the flag should be passed as **true**. Once the user opts out, Netcore SDK will not be able to track that particular user further and no communications will be received by that user. </br>
**e.g. NetcoreSDK.optOut(context, true);**

- If an end user wants to opt in, the flag should be passed as **false**. Once the user opts in, Netcore SDK will be able to track that particular user further and next communications will be received by that user.</br>
**e.g NetcoreSDK.optOut(context, false);**

### To track location of the user
#### Add below code

```java
NetcoreSDK.setUserLocation(context, <double_latitude>, <double_longitude>);
```
Note:  The method mentioned above accepts 3 parameters including context, latitude & longitude. Data type of ‘latitude’ & ‘longitude’ should compulsorily be ​'Double​​'.

- If an end user wants to opt out, the flag should be passed as **true**. Once the user opts out, Netcore SDK will not be able to track that particular user further and no communications will be received by that user. </br>
**e.g. NetcoreSDK.setUserLocation(context, 18.9431, 72.8272);**

- In case if any one of the parameters is ​null​​, the SDK will not be able to persist user location.</br>

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
