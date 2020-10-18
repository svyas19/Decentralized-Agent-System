/*/**
  This Class is used to create Ping and Pong Agents
  We should create .exe of this classes and give user, so that It allows us to maintain privacy and User Comfort.
 ***/
package pingpong;

import static com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type.Int;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import pingpong.PingAgent;
import pingpong.PongAgent;
import java.net.InetAddress;

/**
 *
 * @author Shiv
 */
/**
 *
 * @author Shiv
 */
public class AgentSystem {

    public static void main(String args[]) throws IOException {
        //This provides user the information they might want to share with other users for customise connection
        System.out.println("Your System: " + InetAddress.getLocalHost());
        //Asking user for the choice of agent they want to create
        System.out.println(" Enter 1 to create Pong Agent");
        System.out.println(" Enter 2 to create Ping Agent");
        //As a privacy & security constraint the can know if there are any other agents running on the same machine
        System.out.println(" Enter 3 search all the agents runnig on your machine");
        Scanner sf = new Scanner(System.in);
        int ans = sf.nextInt();
        if (ans == 1) {
            //Creating a Pong Agent
            PongAgent p1;
            p1 = new PongAgent();
            //Creating object of Pong Agent and calling the method to create the Pong Agent
            p1.createPongAgent();
        } else if (ans == 2) {

            PingAgent p2 = new PingAgent();
            p2.createPingAgent();
            //Creating object of Pong Agent and calling the method to create the Pong Agent

        } else if (ans == 3) {
            //Calling the constructor of Search Agent Class
            SearchAgent sa = new SearchAgent();

        } else {
            System.out.println("Enter proper option");
        }
    }
}
