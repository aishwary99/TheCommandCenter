import java.io.*;
import java.net.*;
import java.util.*;
class IOTController
{
public static void main(String gg[])
{
try
{
InputStream inputStream;
OutputStream outputStream; 
InputStreamReader inputStreamReader;
OutputStreamWriter outputStreamWriter;
String command;
String request,response;
String splits1[],splits2[];
StringBuffer stringBuffer;
String device,board;
int x,i;
Socket socket;
InputStreamReader keyboardInputStreamReader;
keyboardInputStreamReader=new InputStreamReader(System.in);
BufferedReader bufferedReader=new BufferedReader(keyboardInputStreamReader);
while(true)
{
System.out.print("iot-controller>");
command=bufferedReader.readLine();
command=command.trim();
while(command.indexOf("  ")!=-1) command=command.replaceAll("  "," ");
if(command.equalsIgnoreCase("quit")) break;
if(command.equalsIgnoreCase("help")) 
{
System.out.println("ls : to get the list of connected devices");
System.out.println("Turn on <electronic-device> connected to <device-id>");
System.out.println("quit : to exit from the controller software");
continue;
}
if(command.equalsIgnoreCase("ls"))
{
request="CC,ls#";
socket=new Socket("localhost",5050);
outputStream=socket.getOutputStream();
outputStreamWriter=new OutputStreamWriter(outputStream);
outputStreamWriter.write(request);
outputStreamWriter.flush();
inputStream=socket.getInputStream();
inputStreamReader=new InputStreamReader(inputStream);
stringBuffer=new StringBuffer();
while(true)
{
x=inputStreamReader.read();
if(x=='#' || x==-1) break;
stringBuffer.append((char) x);
}
response=stringBuffer.toString();
System.out.println("Response arrived -> "+response);
socket.close();
if(response==null || response.length()==0)
{
System.out.println("No boards connected to server");
continue;
}
splits1=response.split("!");
for(x=0;x<splits1.length;x++)
{
splits2=splits1[x].split(",");
System.out.println("----------------------------------------------");
System.out.println("Board : "+splits2[0]);
System.out.println("----------------------------------------------");
for(i=1;i<splits2.length;i++)
{
System.out.println(i+") "+splits2[i]);
}
}
continue;
}
String str=command.toUpperCase();
if(str.startsWith("TURN ON ") || str.startsWith("TURN OFF "))
{
x=str.indexOf(" CONNECTED TO ");
if(x==-1)
{
System.out.println("Invalid command, type help for list of commands");
continue;
}
//8 Turn on <something> connected to <something> 
//9 Turn off <something> connected to <something> 
if(str.startsWith("TURN ON ")) i=8; else i=9;
device=command.substring(i,x);
board=command.substring(x+14);
//CC,CMD,JOKER,TABLET PC,1 (Any case)
request="CC,CMD,"+board+","+device+",";
if(i==8) request+="1#";
else if(i==9) request+="0#";
socket=new Socket("localhost",5050);
outputStream=socket.getOutputStream();
outputStreamWriter=new OutputStreamWriter(outputStream);
outputStreamWriter.write(request);
outputStreamWriter.flush();
inputStream=socket.getInputStream();
inputStreamReader=new InputStreamReader(inputStream);
stringBuffer=new StringBuffer();
while(true)
{
x=inputStreamReader.read();
if(x=='#' || x==-1) break;
stringBuffer.append((char) x);
}
response=stringBuffer.toString();
System.out.println("Response arrived -> "+response);
socket.close();
if(response.equals("0"))
{
//both device names are incorrect
System.out.println("Board name: "+board+" and device name: "+device+" is incorrect");
}
else if(response.equals("1"))
{
//board name is incorrect
System.out.println("Board name: "+board+" is incorrect");
}
else 
{
//command accepted
System.out.println("Request accepted");
}
continue;
}
if(str.startsWith("TURN ON ALL ") || str.startsWith("TURN OFF ALL "))
{
x=str.indexOf(" CONNECTED TO ");
if(x==-1)
{
System.out.println("Invalid command, type help for list of commands");
continue;
}
//12 Turn on ALL <something> connected to <something> 
//13 Turn off ALL <something> connected to <something> 
if(str.startsWith("TURN ON ")) i=12; else i=13;
device=command.substring(i,x);
board=command.substring(x+14);
//CC,CMD,JOKER,TABLET PC,1 (Any case)
request="CC,CMD,"+board+","+device+",";
if(i==12) request+="1#";
else if(i==13) request+="0#";
socket=new Socket("localhost",5050);
outputStream=socket.getOutputStream();
outputStreamWriter=new OutputStreamWriter(outputStream);
outputStreamWriter.write(request);
outputStreamWriter.flush();
inputStream=socket.getInputStream();
inputStreamReader=new InputStreamReader(inputStream);
stringBuffer=new StringBuffer();
while(true)
{
x=inputStreamReader.read();
if(x=='#' || x==-1) break;
stringBuffer.append((char) x);
}
response=stringBuffer.toString();
System.out.println("Response arrived -> "+response);
socket.close();
if(response.equals("0"))
{
//both device names are incorrect
System.out.println("Board name: "+board+" and device name: "+device+" is incorrect");
}
else if(response.equals("1"))
{
//board name is incorrect
System.out.println("Board name: "+board+" is incorrect");
}
else 
{
//command accepted
System.out.println("Request accepted");
}
continue;
}
System.out.println("Invalid command , type help for list of commands...");
}
System.out.println("Thankyou for using command center software...");
}catch(Exception e)
{
System.out.println(e);
}
}
}