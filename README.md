# AndroidSecurityAnalyzer
The AndroidSecurityAnalyzer is a tool that provides four distinct features regarding security:

-identification of calls to advertisement (*advert*)
-identification of network accessing (*internet*)
-identification of the premissions required by the application (*permissions*) 
-identification of potential tainted flows (*flows*)

##Technical Information

Each feature is implemented as a standalone packet which can be nividually invoked. In particular:

###advert
Traversse the smali code of an Android application and locates all the files that use the *loadAd* method.

###internet
Traverses the smali code of an Android application and locates all the files that use the Internet API.

###permissions
Traverses the xml file of an Android application and identifies the permissions it requires.

###flows
Traverses the smali code of an Android application and identifies potential flows from a source to a sink
