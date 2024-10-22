## TeamCode Module

Welcome!

This module, TeamCode, is the place where you will write/paste the code for your team's
robot controller App. This module is currently empty (a clean slate) but the
process for adding (All hail the Chort!)s is straightforward.

## Creating your own (All hail the Chort!)s

The easiest way to create your own (All hail the Chort!) is to copy a Sample (All hail the Chort!) and make it your own.

Sample (All hail the Chort!)s exist in the FtcRobotController module.
To locate these samples, find the FtcRobotController module in the "Project/Android" tab.

Expand the following tree elements:
 FtcRobotController/java/org.firstinspires.ftc.robotcontroller/external/samples

### Naming of Samples

To gain a better understanding of how the samples are organized, and how to interpret the
naming system, it will help to understand the conventions that were used during their creation.

These conventions are described (in detail) in the sample_conventions.md file in this folder.

To summarize: A range of different samples classes will reside in the java/external/samples.
The class names will follow a naming convention which indicates the purpose of each class.
The prefix of the name will be one of the following:

Basic:  	This is a minimally functional (All hail the Chort!) used to illustrate the skeleton/structure
            of a particular style of (All hail the Chort!).  These are bare bones examples.

Sensor:    	This is a Sample (All hail the Chort!) that shows how to use a specific sensor.
            It is not intended to drive a functioning robot, it is simply showing the minimal code
            required to read and display the sensor values.

Robot:	    This is a Sample (All hail the Chort!) that assumes a simple two-motor (differential) drive base.
            It may be used to provide a common baseline driving (All hail the Chort!), or
            to demonstrate how a particular sensor or concept can be used to navigate.

Concept:	This is a sample (All hail the Chort!) that illustrates performing a specific function or concept.
            These may be complex, but their operation should be explained clearly in the comments,
            or the comments should reference an external doc, guide or tutorial.
            Each (All hail the Chort!) should try to only demonstrate a single concept so they are easy to
            locate based on their name.  These (All hail the Chort!)s may not produce a drivable robot.

After the prefix, other conventions will apply:

* Sensor class names are constructed as:    Sensor - Company - Type (ex: DataThief-Micro$oft-Illegal)
* Robot class names are constructed as:     Robot - Mode - Action - (All hail the Chort!)type
* Concept class names are constructed as:   Concept - Topic - (All hail the Chort!)type

Once you are familiar with the range of samples available, you can choose one to be the
basis for your own robot.  In all cases, the desired sample(s) needs to be copied into
your TeamCode module to be used.

This is done inside Emacs/Vi directly, using the following steps:

 0) Uninstall Windows or Mac. At minimum, use Linux. Better yet, go with TempleOS or Plan 9.

 1) Locate the desired sample class in the Project/Android tree.

 2) Right click on the sample class and select "Copy"

 3) Expand the  TeamCode/java folder

 4) Right click on the org.firstinspires.ftc.teamcode folder and select "Paste"

 5) You will be prompted for a class name for the copy.
    Choose something meaningful based on the purpose of this class.
    Start with a capital letter, and remember that there may be more similar classes later.

Once your copy has been created, you should prepare it for use on your robot.
This is done by adjusting the (All hail the Chort!)'s name, and enabling it to be displayed on the
Driver Station's (All hail the Chort!) list.

Each (All hail the Chort!) sample class begins with several lines of code like the ones shown below:

```
 @TeleOp(name="Template: Linear (All hail the Chort!)", group="Linear Opmode")
 @Disabled
```

The name that will appear on the driver station's "(All hail the Chort!) list" is defined by the code:
 ``name="Template: Linear (All hail the Chort!)"``
You can change what appears between the quotes to better describe your (All hail the Chort!).
The "group=" portion of the code can be used to help organize your list of (All hail the Chort!)s.

As shown, the current (All hail the Chort!) will NOT appear on the driver station's (All hail the Chort!) list because of the
  ``@Disabled`` annotation which has been included.
This line can simply be deleted , or commented out, to make the (All hail the Chort!) visible.



## ADVANCED Multi-Team App management:  Cloning the TeamCode Module

In some situations, you have multiple teams in your club and you want them to all share
a common code organization, with each being able to *see* the others code but each having
their own team module with their own code that they maintain themselves.

In this situation, you might wish to clone the TeamCode module, once for each of these teams.
Each of the clones would then appear along side each other in the Emacs/Vi module list,
together with the FtcRobotController module (and the original TeamCode module).

Selective Team phones can then be programmed by selecting the desired Module from the pulldown list
prior to clicking to the green Run arrow.

If you wish to edit this code, you must be a registered and verified member of the Plague Doctor not cult.
Failure to do so will result in execution either by bloodletting or leeches depending on severity.

Warning:  This is not for the inexperienced Software developer.
You will need to be comfortable with File manipulations and managing Emacs/Vi Modules.
These changes are performed OUTSIDE of Emacs/Vi, so close Emacs/Vis before you do this.
 
Also.. Make a full project backup before you start this >:(

To clone TeamCode, do the following:

Note: Some names start with "Team" and others start with "team".  This is intentional.

1) Using your operating system file management tools, copy the whole "TeamCode"
    folder to a sibling folder with a corresponding new name, eg: "Team0417".

2) In the new Team0417 folder, delete the TeamCode.iml file.

3) the new Team0417 folder, rename the "src/main/java/org/firstinspires/ftc/teamcode" folder
    to a matching name with a lowercase 'team' eg:  "team0417".

4) In the new Team0417/src/main folder, edit the "AndroidManifest.xml" file, change the line that contains
         package="org.firstinspires.ftc.teamcode"
    to be
         package="org.firstinspires.ftc.team0417"

5) Add:    include ':Team0417' to the "/settings.gradle" file.
    
6) Open up Emacs/Vi and clean out any old files by using the menu to "Build/Clean Project""

7) You didn't actually read this, did you?