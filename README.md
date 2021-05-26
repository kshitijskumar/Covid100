# Covid100
An app that can be used for A-Z stuff about covid-19. 

## Idea:
A Covid resources app that can be used for all sorts of covid related stuff, like:
- sharing resources such as hospital beds, oxygen etc
- asking for help for your loved ones who might need oxygen cylinders or hospital beds.
- looking for covid stats in overall India as well as state wise data for confirmed/active/recovered/deaths.
- all the Covid-19 related news from worldwide can be read here.

The app can be open sourced for people to upload verified resources and ask for help.

## Architecture pattern:
- Single Activity MVVM Architecture.

## Libraries and frameworks used:
- kotlin coroutines - for asynchronous programming
- kotlin flows - to bind repository to viewmodel
- retrofit - for network calls
- navigation component - for fragment navigation
- glide - for image loading

## Backend:
- Firebase Firestore is used for storing Resources and Help Needed data.
## Screenshots: 
### Splach Screen
<img src="https://github.com/kshitijskumar/Covid100/blob/main/screenshots/splash.jpg" height=400> &nbsp;&nbsp;
### All Resources
<img src="https://github.com/kshitijskumar/Covid100/blob/main/screenshots/all%20res.jpg" height=400> &nbsp;&nbsp;
### Help Needed
<img src="https://github.com/kshitijskumar/Covid100/blob/main/screenshots/help.jpg" height=400> &nbsp;&nbsp;
### Upload form
<img src="https://github.com/kshitijskumar/Covid100/blob/main/screenshots/upload.jpg" height=400> &nbsp;&nbsp;
### Details
<img src="https://github.com/kshitijskumar/Covid100/blob/main/screenshots/details.jpg" height=400> &nbsp;&nbsp;
### Covid Stats
<img src="https://github.com/kshitijskumar/Covid100/blob/main/screenshots/stats.jpg" height=400> &nbsp;&nbsp;
### Covid News
<img src="https://github.com/kshitijskumar/Covid100/blob/main/screenshots/news.jpg" height=400> &nbsp;&nbsp;

## Future addition:
- Custom backend can be made, which can make searching by name or keyword easy.
- Vaccine slots finder as well as notifier can also be added




