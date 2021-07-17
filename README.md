# The Command Center

## Description
The Command Center is a project that works on the principle of Internet of Things. It uses network programming i.e sockets in java & c++ at micro-controller end.
TCC is having 3 essentials entities - BoardSim (Board Simulator) , IOTController (Command Center) & IOTServer (The Server End). Board Simulator can be used
to register boards with corrosponding electronic units , IOTController - the command center can be used to display all the available boards which were registered earlier
by the user , through CC a user can feed a list of commands mentioned in documentation & can perform several operations related to on/off for respective electronic
units on boards. IOT Server is giving a server enviroment which is working as a central authority to take care of operations & boards registered by user. On the
other end , the Micro-Controller i.e Arudino Nano is having a pull-based mechanism where on each cycle the controller asks for commands to the server & takes
the necessary actions.

### Features
* Customised Commands
* Boards Listing Feature
* Turn on / Turn off electronic equipments
* Register n number of boards
* Wireless enviroment


## Commands 

* BR (Board Register) - To be given from board simulator

```
java BoardSIM BLUE FAN COOLER AC
```
Here , BLUE is the Board ID & the space separated strings are electronic units

* BC (Board Commands) - Board is asking for commands (To be given from micro-controller end to server)

* LS (Lists Available Bords) - To be feeded by user

```
java IOTController
iot-controller> ls (Enter)
```

* Turn on / Turn off - To be feeded by user

```
iot-controller> Turn on Fan connected to echo (Enter)
iot-controller> Turn off Fan connected to echo (Enter)
```
Here , Fan is the electronic unit ID & echo is the Board ID


* Turn on all / Turn off all - To be feeded by user

```
iot-controller> Turn on all connected to echo (Enter)
iot-controller> Turn off all connected to echo (Enter)
```
Here , echo is the board ID

