# LocationSupportTouristTeam-AndroidApp

Android application support team traveling in the form of long-distance travel (backpacking, hiking, climbing, adventure Travel...)

You need to clone and run this [backend project](https://github.com/tungtsdev96/LocationSupportTouristTeam) and run it before install to your device.

## Architecture

- The architecture of project follows the [MVP Architecture](https://github.com/android/architecture-samples/tree/todo-mvp)

![Architecture](https://github.com/tungtsdev96/LocationSupportTouristTeam-AndroidApp/blob/master/demo/Ph%E1%BB%A5%20thu%E1%BB%99c%20pkg.png?raw=true)


- Each package in UI written follow MVP

![MVP](https://github.com/tungtsdev96/LocationSupportTouristTeam-AndroidApp/blob/master/demo/MVP.png?raw=true)

## Function

1, The UI of app is designed by Material Design include these functions below:

 - Login/logout
 - Search any place on Map, explore places around current location of user by type.
 - See all information of place (data from GoogleMapAPI).
 - Saving place that you like, display direction from current location to any place.
 - Create room location for travel team.
 - Finding users and invite them to join team.
 - Support all member of group travel to get current location of the each other members.
 - Share any point from map to team.
 - Notify to team whenever meet a problem or an acident.
 - Support for searching place on map by name or place nearby current location by place type.

2, Other functions, but they haven't implemented yet.

- Chat in room.
- Manage the cost of team in the trip.
- Create and manage the schedule for the trip.

## Demo for application

- See all video from [play list](https://www.youtube.com/watch?v=3LLOPOsq-DE&list=PLBtA78ApBjj5-2MGXdL39WvE95mhj9JLa) 

- Function reated to Map.

<div align="center">
   <a href="https://www.youtube.com/embed/ZXDPSU-KlAc">
      <img 
        src="https://img.youtube.com/vi/ZXDPSU-KlAc/0.jpg" 
        alt="Function reated to Map." 
        style="width:100%;">
   </a>
</div>

- Create room and join room.

<div align="center">
   <a href="https://www.youtube.com/embed/2kKToDvZlNA">
      <img 
        src="https://img.youtube.com/vi/2kKToDvZlNA/0.jpg" 
        alt="Create room and join room." 
        style="width:100%;">
   </a>
</div>

- Share Location to room

<div align="center">
   <a href="https://www.youtube.com/embed/3LLOPOsq-DE">
      <img 
        src="https://img.youtube.com/vi/3LLOPOsq-DE/0.jpg" 
        alt="Share Location to room" 
        style="width:100%;">
   </a>
</div>

- Notify to team whenever meet a problem or an acident.

<div align="center">
   <a href="https://www.youtube.com/embed/GbtZWpEJhQs">
      <img 
        src="https://img.youtube.com/vi/GbtZWpEJhQs/0.jpg" 
        alt="Notify to team whenever meet a problem or an acident." 
        style="width:100%;">
   </a>
</div>

- See all notifications

<div align="center">
   <a href="https://www.youtube.com/embed/1EfWl4sSy9g">
      <img 
        src="https://img.youtube.com/vi/1EfWl4sSy9g/0.jpg" 
        alt="See all notifications." 
        style="width:100%;">
   </a>
</div>

## Language, libraries and tools
- [Java](https://developer.android.com/guide) the project is 100% written in Java.
- [Retrofit](https://square.github.io/retrofit/) A type-safe HTTP client for Android.
- [Glide](https://github.com/bumptech/glide) An image loading and caching library for Android focused on smooth scrolling.
- [EventBus](https://github.com/greenrobot/EventBus) simplifies communication between Activities, Fragments, Threads, Services, etc. Less code, better quality.
- [uCrop](https://github.com/Yalantis/uCrop) Image Cropping Library for Android
- [Dexter](https://github.com/Karumi/Dexter) Android library that simplifies the process of requesting permissions at runtime.

## Platform integrated
- [GoogleMapAPI](https://developers.google.com/maps/documentation/android-sdk/start): In order to embed map in app.
- **Firebase**: Used to update current location in real time with [Realtime Database](https://firebase.google.com/docs/database/admin/start) and simply authenticate user by [Firebase Authentication](https://firebase.google.com/docs/auth). 
- [OneSignal](https://onesignal.com/) receive notification that be sent from server in realtime.

## Getting Started

### Adding Google map API key
**Step 1:** See the [quick guide getting an API key](https://developers.google.com/maps/documentation/android-sdk/get-api-key) to get your API key.

**Step 2:** Add your API key to the file debug/values/google_maps_api.xml. It's pulled from there into your app's AndroidManifest.xml file.

### [Register App](https://firebase.google.com/docs/android/setup#register-app) with Firebase

**Step 1:** Go to the [Firebase console](https://console.firebase.google.com/)

**Step 2:** In the center of the project overview page, click the Android icon (plat_android) to launch the setup workflow.

If you've already added an app to your Firebase project, click Add app to display the platform options.

**Step 3:** Enter your app's package name in the Android package name field.

**Step 4:** (Optional) Enter other app information: **App nickname** and **Debug signing certificate SHA-1.**

 - **App nickname:** An internal, convenience identifier that is only visible to you in the Firebase console

 - **Debug signing certificate SHA-1:** A [SHA-1](https://developers.google.com/android/guides/client-auth) hash is required by Firebase Authentication (when using [Google Sign In](https://firebase.google.com/docs/auth/android/google-signin) or [phone number sign in](https://firebase.google.com/docs/auth/android/phone-auth)) and [Firebase Dynamic Links](https://firebase.google.com/docs/dynamic-links).


### Set up mobile Push notification with [OneSignal](https://documentation.onesignal.com/docs/android-sdk-setup)

**Step 1:** Add the following in your android > defaultConfig section.
Update "PUT_YOUR_ONESIGNAL_APP_ID_HERE" with your OneSignal App id

>

    android {
         defaultConfig {
             manifestPlaceholders = [
                 onesignal_app_id: 'PUT_YOUR_ONESIGNAL_APP_ID_HERE',
                 // Project number pulled from dashboard, local value is ignored.
                 onesignal_google_project_number: 'REMOTE'
            ]
         }
    }

- Now you can build your project to your device.

### Contributing to this project
Just make pull request. You are in!