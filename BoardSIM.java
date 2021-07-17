import java.net.*;
import java.io.*;
/*
we will accept command line argument as 
BLUE FAN Cooler
*/
class BoardSIM
{
public static void main(String gg[])
{
try
{
if(gg.length<2)
{
System.out.println("You need to pass board id and list of electronic units");
return;
}
String request="BR";
int x;
for(x=0;x<gg.length;x++)
{
request=request+","+gg[x];
}
request=request+"#";	
Socket socket=new Socket("localhost",5050);
InputStream inputStream;
OutputStream outputStream;
InputStreamReader inputStreamReader;
OutputStreamWriter outputStreamWriter;
StringBuffer stringBuffer;
int i;
String response;
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
if(x=='#') break;
stringBuffer.append((char) x);
}
response=stringBuffer.toString();
System.out.println(response);
socket.close();
//gg[0] contains name of board/board id
//gg[1] contains name of device it controls
//gg[2] ......
String deviceName;
String command;
String splits[];
while(true)
{
try
{
Thread.sleep(5000);
}catch(Exception e)
{
}
socket=new Socket("localhost",5050);
request="BC,"+gg[0]+"#";
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
if(x=='#') break;
stringBuffer.append((char)x);
}
socket.close();
response=stringBuffer.toString();
System.out.println("Response -> "+response);
if(response.length()==0)
{
System.out.println("Nothing to do");
continue;
}
splits=response.split(",");
x=0;
while(x<splits.length)
{
deviceName=splits[x];
x++;
command=splits[x];
x++;
if(command.equals("0"))
{
System.out.println("Turning off : "+deviceName);
}
if(command.equals("1"))
{
System.out.println("Turning on : "+deviceName);
}
}
}
}catch(Exception e)
{
System.out.println(e);
}
}
}