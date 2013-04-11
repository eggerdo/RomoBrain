# RomoBrain

RomoBrain is an Android application using ZMQ to control a [Romo 1.0](http://romotive.com/) and display the camera of the android device on a client.

The app requires a server to receive and send ZMQ command and video messages (see [Robot Server](https://github.com/eggerdo/robot_server.node)). 

The client can be either a web browser (e.g. [here](https://github.com/eggerdo/robot_ctrl.node)) or another android device (e.g. [here](https://github.com/eggerdo/RoboTalk-User))

Note: We have successfully run the Romo with the Nexus 4 and the LG Optimus 2x. Beside putting the media volume to maximum, on the Nexus 4 we used the SpeakerBoost App and put the boost to maximum. On the LG Optimus 2x we are running the CyanogenMod and had to put the 'Attenuation of set volume' to 0 dB in the CyanogenMod Sound settings.