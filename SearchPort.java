/*

This Class helps to find and assign ports to agents
 */
package pingpong;

import java.io.IOException;
import java.net.ServerSocket;

import java.net.Socket;
import java.net.ServerSocket;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 *
 * @author Shiv
 */
public class SearchPort {

    public int searchAvailablePort(InetAddress host, String agent) {
        //Declarations
        boolean result;
        ServerSocket s;
        int backlog = 0;

        //As a security constraint fixing the ports on which Pong Agents can run
        for (int port1 = 6000; port1 < 7001; port1++) //Iterarting and checking avaiblity int permissible range
        {
            try {
                //Creating a temporary socket to check avaiblity of port
                s = new ServerSocket(port1, backlog, host);
                //If created No error will be thrown and this also shows that this port is free to use
                //Closing the temporary socket
                s.close();
                //Returning result
                result = true;
                //Printing the available
                System.out.println("Port Available " + port1);
                if (agent.equals("Pong")) {
                    return port1;
                }
                //break;
            } catch (IOException ex) {
                //Shows that port is not available
                result = false;

            }

        }

        //Returns 0 if some error arises in the running the system
        return 0;    

    }

}
