
package server;

import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ifndz
 */
public class Server {
    
    public ServerSocket serverSocket;
    
    public static void main(String args[]) {
        Server server = new Server();
        try {
            server.start();
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
   
    public void start() throws IOException { //nombre y contraseña

        serverSocket = new ServerSocket(9000);
        
        while(true) {
            Socket socket = serverSocket.accept();
            //We use Threads (hilos) to snd the client to serverProcess. The server is free to listen to another client in the same port.
            ServerProcess serverProcess = new ServerProcess(socket);
            serverProcess.start();
        }
        
        //TODO Salir del servidor releaseResourcesServer();

    }

    private void releaseResourcesServer() {
        try {
            serverSocket.close();
        } catch (IOException ex) {
            Logger.getLogger(Server.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }
}