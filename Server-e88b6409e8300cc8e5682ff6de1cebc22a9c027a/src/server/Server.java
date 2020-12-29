
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
    public ServerProcess serverProcess;
    private Boolean shutdown = false;
    private Thread thread;

 
    public void start() { 
 
        thread = new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    //nombre y contraseña

                    serverSocket = new ServerSocket(9000);

                    while (!shutdown) {
                        Socket socket = serverSocket.accept();
                        //We use Threads (hilos) to snd the client to serverProcess. The server is free to listen to another client in the same port.
                        serverProcess = new ServerProcess(socket);
                        serverProcess.start();
                      
                    }
                } catch (IOException ex) { }
                finally {
                    releaseResourcesServer();
                }
            }
        });
        thread.start();
    }
    
    public void shutdownServer () {
        try {
            shutdown = true;
            if(!serverSocket.isClosed()){
                    serverSocket.close();
            }
            thread.join();
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    private void releaseResourcesServer() {
        try {
            serverSocket.close();
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}