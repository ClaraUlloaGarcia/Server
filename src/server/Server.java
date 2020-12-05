/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ifndz
 */
public class Server {

    public byte[] buffer;
    public byte[] data;

    public Socket socket;
    public ServerSocket serverSocket;
    public InputStream inputStream;
    public OutputStream outputStream;
    public String user = null;

    public static void main(String args[]) {
        Server server = new Server();
        try {
            server.start();
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /*        
        DatagramSocket socket = null;

        try {

            socket = new DatagramSocket(9000);
            buffer = new byte[1024];

            while (true) {
                DatagramPacket datagram = new DatagramPacket(buffer, buffer.length);
                socket.receive(datagram);
                InetAddress originHost = datagram.getAddress();
                int destinationPort = datagram.getPort();
                byte[] data = datagram.getData();
                String cadena = new String(data, 0, datagram.getLength());
                System.out.println("Welcome to the server. Sent from"
                        + originHost + " through the port "
                        + destinationPort + " the message: " + cadena);

                System.out.println("Socket Local IP: " + socket.getLocalAddress());
                System.out.println("Socket Local Port: " + socket.getLocalPort());
                System.out.println("Socket Remote IP: " + originHost);
                System.out.println("Socket Remote Port: " + destinationPort);
                
                //RESPUESTA SERVER -> CLIENT
                String string = "Recibido";
                byte[] message = string.getBytes();
                DatagramPacket response = new DatagramPacket(message, message.length, originHost, destinationPort);
                socket.send(response);

            }
        } catch (SocketException ex) {
            Logger.getLogger(Myserver.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Myserver.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (socket != null) {
                socket.close();
            }
        }
     */
    public void loggingUser() throws IOException {

    }
    
    private String readLine(InputStream inputStream) {
        int s;
        String line = "";
        try {
            s = inputStream.read();
            while (s != '\n') {
                if(s == '\r'){
                     s = inputStream.read();
                     continue;
                 }
                line += new Character((char)s) ;
                //Va letra por letra
                s = inputStream.read();
            }
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
        return line;
    }
    
    private void processLogin(InputStream inputStream, OutputStream outputStream){
        try {
            String userName = readLine(inputStream);
            String password = readLine(inputStream);
            boolean loggedIn = login(userName, password);
            if(loggedIn) {
                user = userName;
                outputStream.write(65); //Ha ido bien
            } else {
                outputStream.write(66); // Error
            }
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private boolean login (String userName, String password) {
        // TEMA DEL FICHRO
        return "eduardo".equals(userName) && "1234".equals(password);
    }
    
    private void processRegister (InputStream inputStream, OutputStream outputStream) {
        try {
            String userName = readLine(inputStream);
            String password = readLine(inputStream);
            String gender = readLine(inputStream);
            String age = readLine(inputStream);
            String weight = readLine(inputStream);
            String height = readLine(inputStream);
            
            boolean register = register(userName, password, gender, age, weight, height);
            if(register) {
                user = userName;
                outputStream.write(65); //Ha ido bien
            } else {
                outputStream.write(66); // Error
            }
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private boolean register (String userName, String password, String gender, String age, String weight, String height) {
        FileManager fileManager = new FileManager ();
        // TODO Validacion
        return fileManager.saveUserPassword(userName, password) && fileManager.saveFixedVariables(userName, gender, Integer.parseInt(age), 
                Double.parseDouble(weight), Double.parseDouble(height));     
    }

    private void process(Socket socket) {

        try {
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();
            while (true) {
                
                int s;
                s = inputStream.read();

                if (s == 'L') {
                    processLogin(inputStream, outputStream);
                } else if (s == 'R') {
                    processRegister(inputStream, outputStream);
                } else if (s == 'D') {
                    System.out.println("Variable Data");
                } else {
                    System.out.println("Error");
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    public void start() throws IOException { //nombre y contraseña

        serverSocket = new ServerSocket(9000);
        socket = serverSocket.accept();
        process(socket);

    }

    public static void receiveParameter() throws IOException {
    }

    public static void storeData() throws IOException {
    }

    /*
        private static void releaseResourcesClient(InputStream inputStream, Socket socket) {

        try {
            inputStream.close();
        } catch (IOException ex) {
            Logger.getLogger(Myserver.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(Myserver.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        // try {
        //     socketServidor.close();
        // } catch (IOException ex) {
        //     Logger.getLogger(ServerReceiveCharactersViaNetwork.class.getName()).log(Level.SEVERE, null, ex);
        // }
    }

     */
    private static void releaseResourcesServer(InputStream inputStream, Socket socket, ServerSocket socketServidor) {

        try {
            inputStream.close();

        } catch (IOException ex) {
            Logger.getLogger(Server.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

        try {
            socket.close();

        } catch (IOException ex) {
            Logger.getLogger(Server.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        try {
            socketServidor.close();

        } catch (IOException ex) {
            Logger.getLogger(Server.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

}
