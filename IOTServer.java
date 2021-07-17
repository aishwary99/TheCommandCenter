/*
Board is registering: BR,Blue,FAN,Cooler,AC#
Board is asking for commands: BC,Blue#
Command center is asking for list CC,ls
Command center is issuing a command: CC,CMD,Turn on <device-name> connected to <board-name>
			          CC,CMD,JOKER,Tablet Pc,1
			Response -> 0 1 2 3
			0 : if board name is incorrect and both device names are incorrect
			1 : device name is incorrect
			2 : all is done (command accepted)
*/
import java.net.*;
import java.util.*;
import java.io.*;
class Board
{
public String id;
public List<String> electronicUnits;
} 
class IOTServer
{
public HashMap<String,LinkedList<String>> commands;
private List<Board>boards;
private ServerSocket serverSocket;
private InputStream inputStream;
private InputStreamReader inputStreamReader;
private String request,response;
private OutputStream outputStream;
private OutputStreamWriter outputStreamWriter;
private Socket socket;
IOTServer()
{
boards=new LinkedList<>();
commands=new HashMap<>();
}
public void start()
{
try
{
serverSocket=new ServerSocket(5050);
int i;
StringBuffer stringBuffer;
String splits[];
String origin;
String command;
String boardID;
String commandText;
String electronicUnit;
String boardName;
String deviceName;
Board board;
boolean boardNameFound=false;
boolean deviceNameFound=false;
LinkedList<String> electronicUnits;
LinkedList<String> commandsList;
int x;
while(true)
{
System.out.println("Server is ready to accept request on port: 5050");
socket=serverSocket.accept();
stringBuffer=new StringBuffer();
inputStream=socket.getInputStream();
inputStreamReader=new InputStreamReader(inputStream);
while(true)
{
i=inputStreamReader.read();
if(i=='#' || i==-1) break;
stringBuffer.append((char) i);
}
request=stringBuffer.toString();
System.out.println("Request arrived -> "+request);
splits=request.split(",");
origin=splits[0];
if(origin.equals("BR"))	//board is registering itself.
{
boardID=splits[1];
i=2;
electronicUnits=new LinkedList<>();
while(i<splits.length)		//adding electronic components in separate list.
{
electronicUnit=splits[i];
electronicUnits.add(electronicUnit);
i++;
}
board=new Board();
board.id=boardID;
board.electronicUnits=electronicUnits;
boards.add(board);
response="GOT_IT#";
outputStream=socket.getOutputStream();
outputStreamWriter=new OutputStreamWriter(outputStream);
outputStreamWriter.write(response);
outputStreamWriter.flush();
socket.close();
}
else if(origin.equals("BC")) 	//board is asking for commands.
{
boardName=splits[1];
response="";
if(commands.containsKey(boardName))
{
commandsList=commands.get(boardName);
if(commandsList.size()>0)
{
for(String cmd:commandsList)
{
if(response.length()>0) response+=",";
response+=cmd;
}
commandsList.clear();
}
}
System.out.println("Response sending to board simulator : "+response);
response+="#";
outputStream=socket.getOutputStream();
outputStreamWriter=new OutputStreamWriter(outputStream);
outputStreamWriter.write(response);
outputStreamWriter.flush();
System.out.println("Response: "+response);
socket.close();
continue;
}
else if(origin.equals("CC")) 	//something arrived from command center
{
//CC,ls 
//CC,CMD,Turn on the fan connected to blue/echo.
command=splits[1];
if(command.equals("CMD"))
{
//CC,CMD,JOKER,TABLET PC,1
boardName=splits[2];
deviceName=splits[3];
System.out.println("Board name: "+boardName+" , Device name: "+deviceName);
boardNameFound=false;
deviceNameFound=false;
for(x=0;x<boards.size();x++)
{
board=boards.get(x);
if(board.id.equalsIgnoreCase(boardName))
{
boardNameFound=true;
for(i=0;i<board.electronicUnits.size();i++)
{
if(deviceName.equalsIgnoreCase(board.electronicUnits.get(i))) 
{
deviceNameFound=true;
break;
}
}
break;
}
}
if(boardNameFound && deviceNameFound)
{
command=splits[3]+","+splits[4];
commandsList=commands.get(boardName);
if(commandsList==null)
{
commandsList=new LinkedList<>();
commands.put(boardName,commandsList);
}
commandsList.add(command);
response="2#";
}
else if(boardNameFound && !deviceNameFound && deviceName.equalsIgnoreCase("ALL"))
{
//ALL
board=boards.get(x);
for(i=0;i<board.electronicUnits.size();i++)
{
//FAN,COOLER,AC
electronicUnit=board.electronicUnits.get(i);
command=electronicUnit+","+splits[4];
System.out.println("Command: "+command);
commandsList=commands.get(boardName);
if(commandsList==null)
{
commandsList=new LinkedList<>();
commands.put(boardName,commandsList);
}
commandsList.add(command);
}
response="2#";
}
else if(boardNameFound==false && deviceNameFound==false)
{
response="0#";
}
else if(boardNameFound && deviceNameFound==false)
{
response="1#";
}
outputStream=socket.getOutputStream();
outputStreamWriter=new OutputStreamWriter(outputStream);
outputStreamWriter.write(response);
outputStreamWriter.flush();
socket.close();
continue;
}
else if(command.equals("ls") || command.equals("LS"))
{
response="";
i=0;
for(Board b:boards)
{
response+=b.id;
i++;
for(String eu:b.electronicUnits)
{
response+=","+eu;
}
if(i<boards.size())
{
response+="!";
}
}
response+="#";
System.out.println("Response: "+response);
outputStream=socket.getOutputStream();
outputStreamWriter=new OutputStreamWriter(outputStream);
outputStreamWriter.write(response);
outputStreamWriter.flush();
socket.close();
}
}
}
}catch(Exception e)
{
System.out.println(e);
}
}
public static void main(String gg[])
{
IOTServer iotServer=new IOTServer();
iotServer.start();
}
}