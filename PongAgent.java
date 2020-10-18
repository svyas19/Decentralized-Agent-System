/*
This class will help to create Pong Agents
According to the task one agent should be started first
So, Pong Agent should be started first.

 */
package pingpong;

import java.io.*;
import java.text.*;
import java.util.*;
import java.net.*;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
//import static sun.security.krb5.Confounder.bytes;

//It Extends the Agent Class
//Agent Class contains the IP Address of Agent Security
public class PongAgent extends Agent {

    public void createPongAgent() throws IOException {
        //      System.out.println(mainAdd);
        //AgentSecurity as = new AgentSecurity();
        Socket sc = new Socket(mainAdd, 5000);
        //Creating a temporary socket to get permission from AgentSecurity
        //Getting permission from Agent security
        System.out.println("Please Wait, Getting Permission From Agent System.....");
        System.out.println("\n");
        //Creating Input and Output Streams to talk with AgentSecurity
        DataInputStream dis1 = new DataInputStream(sc.getInputStream());
        DataOutputStream dos1 = new DataOutputStream(sc.getOutputStream());
        //Reading the answer from AgentSecurity - Either allowed or Denied Agent Creation
        String ansc = dis1.readUTF();
        if (ansc.equals("Yes")) {
            //Permission Granted
            //Informing Agent Security that it is a pong Agent
            dos1.writeUTF("Pong");
            //Closing the temporary socket
            sc.close();
            //Searching for the permissable and available port to run pong agent
            SearchPort sp = new SearchPort();
            //Calling Method from SearchPort to get Port
            int port = sp.searchAvailablePort(InetAddress.getLocalHost(), "Pong");
            ServerSocket ss = new ServerSocket(port);
            System.out.println("Permission Granted");
            //Pong Agent Running, Waiting for Ping Agents
            System.out.println("Pong Agent started \nWaiting for Ping Agent");

            while (true) {
                Socket s = null;
                //Interface to connect to Ping Agent

                try {
                    //Connecting with Ping Agents by accepting its request
                    s = ss.accept();
                    //Shows Ping Agent connected
                    //Shows where ping agent is running  
                    System.out.println("A new ping is connected : " + s);

                    //Creating Streams to talk with Ping Agent
                    DataInputStream dis = new DataInputStream(s.getInputStream());
                    DataOutputStream dos = new DataOutputStream(s.getOutputStream());

                    //Alllowing Multiple Pings to connect with one Pong
                    //Each Ping connected will be provided a thread of Pong
                    Thread t = new PingHandler(s, dis, dos);

                    //Startting the thread
                    t.start();

                } catch (Exception e) {
                    //If Some Problem Close Connection with Ping
                    s.close();
                    e.printStackTrace();
                }
            }
        } else {
            //Will not create agent if AgentSecurity Denied
            System.out.println("Agent System Denied");
        }
    }
}

class PingHandler extends Thread {

    final DataInputStream dis;
    final DataOutputStream dos;
    final Socket s;

    public PingHandler(Socket s, DataInputStream dis, DataOutputStream dos) {
        this.s = s;
        this.dis = dis;
        this.dos = dos;
    }

    @Override

    public void run() {
        //This method is mandatory to write when we use thread
        //This method declares the behavior of Pong Agent
        //Declaration String to receive and send data to Ping Agent
        String received;
        String toreturn;

        while (true) {//Run Continuosly 
            try {
                //Sending a welcome message to ping along with ID of Pong Agent
                //".HashCode" is method which provides object code and it is unique for each and every obeject
                dos.writeUTF("PongAgent[id :" + this.hashCode() + "]: " + "Welcome Ping Agent");

                //Waiting for reply from Ping
                received = dis.readUTF();

                Scanner sf = new Scanner(System.in);

                while (!received.equals("Exit")) {
                    //Continue Communication with Ping untill it says exit
                    if (received.equals("File")) {
                        //"File" acts a flag that Ping is Sending a file
                        try {
                            //Giving some time to ping to make the file ready to send
                            TimeUnit.SECONDS.sleep(10);
                            //File Transfer will be done through internal socket which will be secured
                            //This helps to keep communication interface safe if there is some error in file

                            Socket sock = new Socket("127.0.01.1", 2000);
                            //Use s.getRemoteSocket Address to get Ping IP
                            //Creating Byte array to read file
                            byte[] mybytearray = new byte[1024];
                            //Declaring Streams for filer transfer
                            InputStream is = sock.getInputStream();
                            //Location of file where you want to save the received
                            //Use the Scanner to take runtime input
                            FileOutputStream fos = new FileOutputStream("H:\\testTransfer\\Receive\\r.txt");
                            BufferedOutputStream bos = new BufferedOutputStream(fos);
                            int bytesRead = is.read(mybytearray, 0, mybytearray.length);
                            bos.write(mybytearray, 0, bytesRead);//Writing the file contents in the file location we want to save the file
                            System.out.println("File Received");
                            //Notify Filer received
                            //Closing Internal Streams and Sockets
                            bos.close();
                            sock.close();
                            //Reply Ping Agent
                            System.out.println("Enter Message for Ping Agent or Press 1 to transfer file");
                            String m = sf.nextLine();
                            if (m.equals("1")) {
                                //If Input 1 means Pong wants to send file to ping
                                dos.writeUTF("File1");
                                //Flag sent to Ping that Pong is sending file
                                //Settting up Internal socket to send file and create new to keep other sockets safe
                                ServerSocket servsock1 = new ServerSocket(1000);
                                //Give the location and file name which you want to transfer
                                //Use Scanner class to get runtime input
                                File myFile1 = new File("H:\\testTransfer\\Send\\s.txt");
                                //Connecting to the internal socket of ping
                                Socket sock1 = servsock1.accept();
                                //Creating streams to write file
                                byte[] mybytearray1 = new byte[(int) myFile1.length()];
                                BufferedInputStream bis1 = new BufferedInputStream(new FileInputStream(myFile1));
                                bis1.read(mybytearray1, 0, mybytearray1.length);//Reading file
                                OutputStream os1 = sock.getOutputStream();//writing file to ping agent
                                os1.write(mybytearray1, 0, mybytearray1.length);
                                System.out.println("File Sent");//Notify file sent
                                os1.flush();
                                sock1.close();
                                servsock1.close();
                                //close all the internal streams and sockets
                                received = dis.readUTF();//Waiting for the reply from Ping
                                //String m  = dis.readUTF();
                                //   System.out.println("Pong Agent [id : "+ s.hashCode() +"] :" + m);
                                // System.out.println();

                            } else {
                                //If not file transfer then Pong writes some message
                                dos.writeUTF("PongAgent[ Id : " + this.hashCode() + "]: " + m);
                                received = dis.readUTF();
                            }

                        } catch (Exception e) {
                            System.out.println(e);
                        }
                    } else {
                        //Printing the message from ping
                        System.out.println(received);
                        //Asking Pong what does he want to send again
                        System.out.println("Enter Message for Ping Agent");
                        System.out.println("Enter 2 to get Agent-User details");
                        System.out.println("Enter 1 to send files");
                        String msg = sf.nextLine();
                        if (msg.equals("1")) {
                            //Everything is same as above file sending case but this is a scenario that might arise during communicating back and fro
                            dos.writeUTF("File1");
                            try {
                                // dos.writeUTF("File");
                                ServerSocket servsock2 = new ServerSocket(1000);
                                File myFile2 = new File("H:\\testTransfer\\Send\\s.txt");

                                Socket sock2 = servsock2.accept();
                                byte[] mybytearray2 = new byte[(int) myFile2.length()];
                                BufferedInputStream bis2 = new BufferedInputStream(new FileInputStream(myFile2));
                                bis2.read(mybytearray2, 0, mybytearray2.length);
                                OutputStream os2 = sock2.getOutputStream();
                                os2.write(mybytearray2, 0, mybytearray2.length);
                                System.out.println("File Sent");
                                os2.flush();
                                sock2.close();
                                servsock2.close();
                                received = dis.readUTF();

                            } catch (Exception e) {
                                System.out.println(e);
                            }
                        } // String msg = sf.nextLine();
                        else if (msg.equals("2")) {
                            //If "2" it means Pong want to see the system details on which Pong is runnig
                            dos.writeUTF(msg);
                            //Sending Flag Ping to send details of system
                            System.out.println("Getting Agent details");
                            String os = dis.readUTF();//Reading the details in string form
                            String[] Lines = new String[100000];

                            int i = 0;
                            //Spliting the received string into more readable format i.e. reading Line by Line
                            Lines = os.split(",");
                            while (i < Lines.length) {
                                System.out.println(Lines[i]);
                                i++;
                            }

                        } else {
                            //Same as above if not file transfer then transfer some message but this is also some different scenario that might arise
                            // System.out.println(msg);
                            dos.writeUTF("PongAgent[ Id : " + this.hashCode() + "]: " + msg);
                            received = dis.readUTF();
                        }
                    }

                }
            } catch (IOException ex) {
                Logger.getLogger(PingHandler.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }
}
