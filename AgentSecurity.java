/*
This is the Agent Security classs.
This is the class which should be ran first.
This Class provides Agent System the capablity to allow or deny agents being created from any system
 */
package pingpong;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Shiv
 */
public class AgentSecurity {
    //I was advised to create a databaseless system, This array will be used to store information of all the agents
    //As a privacy constraint this data will be available to Agent System only 

    public static String[] PingList = new String[100];
    public static String[] PongList = new String[100];
    static int pi = 0;
    static int po = 0;

    public static void main(String[] args) throws IOException {
        ServerSocket ss = new ServerSocket(5000);
        //This Socket will be permanently running on which before creating any agent the user system will ask for permission on this socket
        System.out.println("Agent System Security Activated \nWaiting for Agents");
        // System.out.println(PongList[0]);
        //       System.out.println(PingList[0]);

        while (true) {
            Socket s = null;
            //Socket to accept connection for authorization
            try {
                s = ss.accept();
                //Accepting User request for permission to create a new agent

                System.out.println("A new Agent want join agent system : " + s.getRemoteSocketAddress());
                System.out.println("Do you want to allow this agent? \nYes to Allow \nNo to Deny ");

                //Creating streams to write and read from user
                DataInputStream dis = new DataInputStream(s.getInputStream());
                DataOutputStream dos = new DataOutputStream(s.getOutputStream());
                Scanner sf = new Scanner(System.in);

                String ans = sf.nextLine();

                if (ans.equals("Yes")) {
                    //If "Yes" grant Permission
                    //Notifying User 
                    dos.writeUTF("Yes");

                } else {
                    //Denying the User
                    //Notify user
                    dos.writeUTF("No");
                }
                //Receive the type of agent created
                String name = dis.readUTF();
                if (name.equals("Pong")) {
                    //If Pong Agent created., adding it to PongList
                    PongList[po] = s.getRemoteSocketAddress().toString();
                    po++;
                }
                if (name.equals("Ping")) {
                    //If Ping Agent created., adding it to PingList
                    PingList[pi] = s.getRemoteSocketAddress().toString();
                    pi++;
                }
                //Asking to view the agents running
                System.out.println("Do you want to see the agents running? \n Press Yes to see or No to Continue");
                String c = sf.nextLine();
                if (c.equals("Yes")) {
                    int i = 0;
                    System.out.println("The ping agents are:");

                    while (PingList[i] != null) {
                        //Printing the Ping Agents
                        System.out.println(PingList[i]);
                        i++;
                    }
                    int p = 0;
                    System.out.println("The pong agents are:");

                    while (PongList[p] != null) {
                        //Printing the Ping Agents
                        System.out.println(PongList[p]);
                        p++;

                    }

                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }
}
