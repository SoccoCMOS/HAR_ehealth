# HAR_ehealth

This repository comprises the code source of the Owl Med App that uses Privacy-Aware Human Activity Recognition for the Remote Monitoring of Arrythmias. 
The java source code is organized as follow: 

- A BLL (Business Logic Layer) package that contains the following processing Intent Services:
   - Communication service: it handles acquisition of data from an external MQTT Broker (using Paho Client Services)
   - Processing Service: it handles humain activity recognition processings as well as the remote API calls.
   - Decision Support Service: it handles the application of decision rules to ecg data and activity recognition results.

- A DAL (Data Access Layer) pacage that contains Model classes as well as DatabaseHelper classes, synchronization code and an Intent Service
S_DataAccess that exposes all the previous methods. 

- A UI (User Interface) package that comprises Android Activities code, UI Controllers. 

Layouts, themes and resources are contained in the res folder under src/main.

There is a Unit_Tests package that contains a set of tests for the various functionalities of tha app individually. 


Target: API Level 19+, Android SDK 23.
Dependencies: Android Paho MQTT Client, Android Volley library, MPAndroidChart by PhilJay

Contact: cs_si-moussi@esi.dz or bw_benchaib@esi.dz for further documentation.
