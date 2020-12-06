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

    private int CODE_ERROR = 66;
    private int CODE_OK = 65;
    
    public static void main(String args[]) {
        Server server = new Server();
        try {
            server.start();
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
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
                outputStream.write(CODE_OK); //Ha ido bien
            } else {
                outputStream.write(CODE_ERROR); // Error
            }
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private boolean login (String userName, String password) {
        FileManager fileManager= new FileManager();
        List[] credentials= fileManager.getUserPassword();
        if (credentials == null){
            return false;
        }
        if (credentials.length!=2){
            return false;
        }
        List<String> users = credentials[0];
        List<String> passwords = credentials[1];
        
        if(users.size()!=passwords.size()){
            System.out.println("La cantidad de usuarios no coincide con las contraseñas");
            return false;
        }
        for(int i=0; i<users.size(); i++){
            if(users.get(i).equals(userName)&& passwords.get(i).equals(password)){
                return true;
            }
        }
        return false;
    }
    
    private void processRegister (InputStream inputStream, OutputStream outputStream) {
        try {
            String userName = readLine(inputStream);
            System.out.println("Usuario leido");
            String password = readLine(inputStream);
            System.out.println("password leido");
            String gender = readLine(inputStream);
            System.out.println("gender leido");
            String age = readLine(inputStream);
            System.out.println("age leido");
            String weight = readLine(inputStream);
            System.out.println("weight leido");
            String height = readLine(inputStream);
            System.out.println("height leido");
            
            boolean register = register(userName, password, gender, age, weight, height);
            if(register) {
                user = userName;
                outputStream.write(CODE_OK); //Ha ido bien
                System.out.println("Enviado ok");
            } else {
                outputStream.write(CODE_ERROR); // Error
                System.out.println("Enviado error");
            }
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private boolean register (String userName, String password, String gender, String age, String weight, String height) {
        FileManager fileManager = new FileManager ();
        // TODO Validacion
        return  fileManager.saveFixedVariables(userName, gender, Integer.parseInt(age), 
                Double.parseDouble(weight), Double.parseDouble(height)) && fileManager.saveUserPassword(userName, password);     
    }
    private void processData(InputStream inputStream, OutputStream outputStream){
        if (user== null){
            try {
                outputStream.write(CODE_ERROR); // Error
            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
             return;
        }
        String sizeString= readLine(inputStream);
        int size = Integer.parseInt(sizeString);
        List<String> bitalinoData= new ArrayList(); //Lista de strings
        for(int i=0; i<size; i++){
            String line= readLine(inputStream); 
            bitalinoData.add(line);//lee linea y añade a la lista
        }
        String flex_ang= readLine(inputStream);
        String turn_ang= readLine(inputStream);
        
        FileManager fileManager= new FileManager();
        boolean saved= fileManager.saveChangingVariables(user, flex_ang, turn_ang, bitalinoData);
        try {
            if (saved) {
                outputStream.write(CODE_OK); //Ha ido bien
            } else {
                outputStream.write(CODE_ERROR); // Error
            }
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
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
                    System.out.println("Registro");
                    processRegister(inputStream, outputStream);
                } else if (s == 'D') {
                    processData(inputStream, outputStream);
                } else {
                    outputStream.write(CODE_ERROR); // Error
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
