# Smart Log

SmartLog is a simple mobile application that allows a user to record thoughts, ideas, reminders,
or anything they choose. Each entry includes a title and some text, as well as an optional image
or drawing. The user can collect these entries and convert them into a document that shows a
history or story.

--------------
The development process begins with the MVVM pattern in which user interaction happens in the UI (View) and I have used a single activity 
and only a single instance exists throughout the app and every page is a fragment. 

The Database or the backend process is entirely done using ROOM arhitecture which can handle DB queries and it is 
convenient and user friendly and much faster and provides compile time errors

The ViewModel which handles most of the interaction between UI and Database and process of the logic happens here. So, a single 
View Model is created for the entire app which handles communication.


1. User can add a LOGBOOK with unique name and every logbook contains LOG ENTRIES 

2. One can fill in a log entry with mandatory title and optional description and images

3. Every log entry is stored in accordance with the date of creation

4. All log entries can be edited 

5. One can move log from one logbook to another

6. Delete of logbook and log entries are allowed. Once a logbook is deleted all its log entries are deleted.

7. A log book can be converted to pdf, this option is provided in each row of the logbook 
and pdf conversion is done using iText library

8. A log entry can contain images. To pick images a third party libraray is used ie "MediaPickerLib"

RecyclerView is used to list LogBooks and on selection of every logBook again a list of Log Entries are shown with title and 
some description and date of creation.

All the icons used are android default icons found in [https://material.io/tools/icons/?style=baseline](https://material.io/tools/icons/?style=baseline)

There is a help page which provides information about the app

The database contains 3 tables ie LogBook, Log entry and Log attachments.

Database architecture
[https://gitlab.com/arvinkumar/smart-log/blob/master/db_uml.png](https://gitlab.com/arvinkumar/smart-log/blob/master/db_uml.png)

---------

If more time is available, 

1. Removal and addition of more images can be done using recycler view with horizontal orientation 
and which can provide easy navigation of images and download option too.

2. Clip drawings created by user can be implemented

3. Exported pdf contains only text, but images can be added

4. Only one attachment image can be shown in each row of the log entry

5. Above all, the cloud storage can be provided using Google's Firebase cloud storage which requires some initial setup 
and implementation

----------

Android automation test case is created for basic functionalities to check for operations
like add, view, delete and edit log entry using espresso. (MainActivityTest2.kt)







