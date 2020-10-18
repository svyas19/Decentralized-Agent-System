/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pingpong;

import java.io.*;
import java.net.*;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class PingAgent extends Agent {
//Extending Agent clas to connect with the agent security to get permission 

    public void createPingAgent() throws IOException {
        //System.out.println(mainAdd);
        Socket sc = new Socket(mainAdd, 5000);//Connecting with AgentSecurity
        //Waiting for Permission
        System.out.println("Please wait getting permission from system.....");
        //Creating streams to talk with AgentSecurity
        DataInputStream dis1 = new DataInputStream(sc.getInputStream());
        DataOutputStream dos1 = new DataOutputStream(sc.getOutputStream());
        //Reading the response from AgentSecurity
        String ansc = dis1.readUTF();
        if (ansc.equals("Yes")) {
            //If "Yes" Permission Granted
            dos1.writeUTF("Ping");
            sc.close();
            //Closing the Interface with AgentSecurity
            try {
                Scanner scn = new Scanner(System.in);
                System.out.println("Permission Granted \n");

                //Creating Interface to connect with Pong Agents
                Socket s;
                Scanner sf1 = new Scanner(System.in);
                //Declarations
                int check;
                String userIp;
                int userPort;
                //Allowing ping the flexibility to connect automatically with first available pong or connect with some specific pong according to its wish
                System.out.println("Press 1 to connect automatically with available Pong Agent \nPress 2 to connect with specific Pong Agent");
                check = sf1.nextInt();
                if (check == 1) {
                    s = new Socket("localhost", 6000);
                    //Replace "localhost" with the IP of system where you will be running pong
                    //OR else use SendIP Method of GetIP Class and iterate over

                } else {
                    //If user selects some different pong ,  get details for that pong
                    System.out.println("Enter the Ip address on which Pong Agent is running");
                    userIp = scn.nextLine();
                    System.out.println("Enter the port number on which Pomg Agent is running");
                    userPort = scn.nextInt();
                    s = new Socket(userIp, userPort);

                }
                //Creating Streams to talk with Pong
                DataInputStream dis = new DataInputStream(s.getInputStream());
                DataOutputStream dos = new DataOutputStream(s.getOutputStream());

                while (true) {
                    //Continuos Communication
                    //Reads the welcome message from Pong Agent
                    String msg = dis.readUTF();
                    //Prints the received message
                    System.out.println(msg);
                    // if(msg.equals(1))
                    //Checks if the received messgae is some flag or not
                    if (msg.equals("2")) {

                        //If Received "2" Reply back with the user system details
                        String os = System.getProperties().toString();//System.getProperty("os.name")-other way to send some specific information;
                        //Sending message to pong agent with unique id
                        dos.writeUTF("Ping Agent[id :" + this.hashCode() + "]" + os);
                    } else if (msg.equals("File1")) {
                        //All the conditions below are same as pong and shows behavior of ping based on flag received
                        TimeUnit.SECONDS.sleep(10);
                        Socket sock1 = new Socket("127.0.0.1", 1000);
                        //Use s.getRemoteSocket Address to get Pong IP
                        byte[] mybytearray1 = new byte[1024];
                        InputStream is1 = sock1.getInputStream();
                        FileOutputStream fos1 = new FileOutputStream("H:\\testTransfer\\Receive\\r.txt");
                        BufferedOutputStream bos1 = new BufferedOutputStream(fos1);
                        int bytesRead = is1.read(mybytearray1, 0, mybytearray1.length);
                        bos1.write(mybytearray1, 0, bytesRead);
                        System.out.println("File Received");
                        bos1.close();
                        sock1.close();
                        Scanner sf = new Scanner(System.in);
                        System.out.println("Enter Message for Pong Agent");
                        msg = sf.nextLine();
                        dos.writeUTF("Ping Agent[id :" + this.hashCode() + "]" + msg);

                    } else {
                        System.out.println("Enter Message for Pong Agent or Press 1 to Transfer file");

                        String tosend = scn.nextLine();
                        if (tosend.equals("1")) {
                            dos.writeUTF("File");
                            ServerSocket servsock = new ServerSocket(2000);
                            File myFile = new File("H:\\testTransfer\\Send\\s.txt");

                            Socket sock = servsock.accept();
                            byte[] mybytearray = new byte[(int) myFile.length()];
                            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(myFile));
                            bis.read(mybytearray, 0, mybytearray.length);
                            OutputStream os = sock.getOutputStream();
                            os.write(mybytearray, 0, mybytearray.length);
                            System.out.println("File Sent");
                            os.flush();
                            sock.close();
                            servsock.close();
                            //String m  = dis.readUTF();
                            //   System.out.println("Pong Agent [id : "+ s.hashCode() +"] :" + m);
                            // System.out.println();

                        } else {
                            dos.writeUTF("PingAgent[ Id: " + this.hashCode() + "]: " + tosend);

                            if (tosend.equals("Exit")) {
                                System.out.println("Closing this connection : " + s);
                                s.close();
                                System.out.println("Connection closed");
                                break;
                            }

                        }
                    }

                    // closing resources 
                    //scn.close(); 
                    //dis.close(); 
                    //dos.close(); 
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            System.out.println("Agent System Denied");
        }
    }
}
