# SCM-FPS-STATUS
Designed and developed an Android Mobile App for FCI-AP, Food Corporation of India- Government of Andhra Pradesh to equip the app users with commodity information. - Internship Project

##  Target User Audience  
The intended users of this app are: the Supply Chain Management representatives, exporters and distributors in the districts of Andhra Pradesh State.

# Functionality

The user needs to enter a valid Fair Price Shop number and choose the correct district name from the spinner and press the submit button in the main screen.
<br/>
<img src="https://github.com/pa1-teja/SCM-FPS-STATUS/blob/master/app/src/main/res/drawable/main_screen.png" width="200" height = "350">
<img src="https://github.com/pa1-teja/SCM-FPS-STATUS/blob/master/app/src/main/res/drawable/ms_spinner.png" width="200" height = "350">
<br/>

If the entered credentials are valid, then the app will move to the SCM dashboard where a list of reports related to the project are displayed to the user.
<br/>
<img src="https://github.com/pa1-teja/SCM-FPS-STATUS/blob/master/app/src/main/res/drawable/scm_dashboard.PNG" width="200" height = "350">
<br/>
Based on the report selected in the dashboard screen, the user will navigated to the appropriate report details screen.
<br/>
<img src="https://github.com/pa1-teja/SCM-FPS-STATUS/blob/master/app/src/main/res/drawable/alloc_screen.png" width="200" height = "350">
<br/>

## Project Details
The app makes an API call when the user enter's his/her credentials and allows the user to access only the reports that they're authorized to access.

### Dependencies used in the project

 Below are the dependencies used in the project: 
`com.android.support:support-v4:21.0.3`<br/>
`com.android.support:appcompat-v7:21.0.3`<br/>
`libs/ksoap2-android-assembly-3.4.0-jar-with-dependencies.jar` <br/>

### Version Compatibility

The App supports all the Android devices and Tablets which runs Android FROYO and above versions. i.e API 8 and above.
