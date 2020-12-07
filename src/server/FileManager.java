
package server;

import java.io.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ClaraU
 */
public class FileManager {

    File dir;
    BufferedReader bf;
    PrintWriter pw;
    String string;
    
    private String USER_FILE = "\\users.txt";

    public FileManager() {
        string = "C:\\Users\\ClaraU\\Documents";//System.getProperty("user.dir"); //Folder used for all the files
    }

    //GUARDAAAR 
    public boolean saveUserPassword(String user, String password) {
        String saveUser = "C:\\Users\\ClaraU\\Documents" + USER_FILE;
        System.out.println(saveUser);
        dir = new File(saveUser);
        if (!dir.exists()) {
            try {
                dir.createNewFile(); //Create a new file
            } catch (IOException ex) {
                Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
        }
        try {
            pw = new PrintWriter(new FileWriter(dir, true));
            pw.append(user + "\n");
            pw.append(password + "\n");
            pw.append("\n");
            pw.close();

        } catch (Exception ex) {
            Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }

    public boolean saveFixedVariables(String name, String gender, int age, double weight, double height) {
        String saveFixedVar = "C:\\Users\\ClaraU\\Documents" + "\\fixed.txt";
        System.out.println(saveFixedVar);
        dir = new File(saveFixedVar);
        if (!dir.exists()) {
            try {
                dir.createNewFile(); //Create a new file
            } catch (IOException ex) {
                Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
        }
        try {
            pw = new PrintWriter(new FileWriter(dir, true));
            pw.append(name + "\n");
            pw.append(gender + "\n");
            pw.append(age + "\n");
            pw.append(weight + "\n");
            pw.append(height + "\n");
            pw.append("\n");
            pw.close();

        } catch (Exception ex) {
            Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }

    public boolean saveChangingVariables(String userName,String flex_ang, String turn_ang, List<String> bitalino) {
        // TODO Crear directorio con nombre del paciente
        String saveChangingVar = "C:\\Users\\ClaraU\\Documents" + "\\" + userName + ".txt";
        System.out.println(saveChangingVar);
        dir = new File(saveChangingVar);
        if (!dir.exists()) {
            try {
                dir.createNewFile(); //Create a new file
            } catch (IOException ex) {
                Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
        }
        try {
            pw = new PrintWriter(new FileWriter(dir, true));

            pw.append(todaysDate() + "\n");
            pw.append(flex_ang + "\n");
            pw.append(turn_ang + "\n");
            pw.append(bitalino.size()+ "\n");
            
            for(String data: bitalino) {
                pw.append(data + "\n");
            }
            
            pw.append("\n");
            pw.close();

        } catch (Exception ex) {
            Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }

    //CARGAAR
    public List[] getUserPassword() {
        try {
            String user = "C:\\Users\\ClaraU\\Documents" + USER_FILE;
            //Read from the user and password file
            bf = new BufferedReader(new InputStreamReader(new FileInputStream(user)));
            List<String> userNames = new ArrayList<String>();
            List<String> passwords = new ArrayList<String>();
            String read; //Class represents character constant strings. Values cannot be changed after create them.
            int counter = 0;
            //While I have info, I continue reading 
            while ((read = bf.readLine()) != null) {
                if (read.equals("")) {
                    continue;
                }
                //As we start with position 0, even numbers are the user names
                if (counter % 2 == 0) {
                    userNames.add((String) read);
                } else {
                    //Odd numbers are the passwords
                    passwords.add((String) read);
                }
                counter++;
            }
            return new List[]{userNames, passwords};
        } catch (Exception e) {
            System.out.println("Could not read users or passwords!");
            e.printStackTrace(); //Helps to trace the exception
            return null;
        }
    }

    private String todaysDate() {        
        Date date = new Date();
        Format formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String formattedDate = formatter.format(date);
        return formattedDate;
    }
    
    /*
    public List[] getFixedVariables() {
        try {
            String saveFixedVar = "C:\\Users\\ClaraU\\Documents" + "\\saveFixed.txt";
            //Read from the user and password file
            bf = new BufferedReader(new InputStreamReader(new FileInputStream(saveFixedVar)));
            List<String> userID = new ArrayList<String>();
            List<String> userNames = new ArrayList<String>();
            List<String> passwords = new ArrayList<String>();
            List<String> age = new ArrayList<String>();
            List<String> gender = new ArrayList<String>();
            List<String> weight = new ArrayList<String>();
            List<String> height = new ArrayList<String>();
            java.lang.String read; //Class represents character constant strings. Values cannot be changed after create them.
            
            //While I have info, I continue reading 
            while ((read = bf.readLine()) != null) {
                if (read.equals("")) {
                    continue;
                }
                userID.add(read);
                userNames.add(read);
                passwords.add(read);
                age.add(read);
                gender.add(read);
                weight.add(read);
                height.add(read);
            }
            return new List[]{userID, userNames, passwords, age, gender, weight, height};
        } catch (Exception e) {
            System.out.println("Could not read the profiles of the users!");
            e.printStackTrace(); //Helps to trace the exception
            return null;
        }
    }
    
    public List[] getChangingVariables() {
        try {
            String saveChangingVar = "C:\\Users\\ClaraU\\Documents" + "\\saveFixed.txt";
            //Read from the user and password file
            bf = new BufferedReader(new InputStreamReader(new FileInputStream(saveChangingVar)));
            List<String> flex_ang = new ArrayList<String>();
            List<String> turn_ang = new ArrayList<String>();
            List<String> bitalino = new ArrayList<String>();
            java.lang.String read; //Class represents character constant strings. Values cannot be changed after create them.
            int counter = 0;
            //While I have info, I continue reading 
            while ((read = bf.readLine()) != null) {
                if (read.equals("")) {
                    continue;
                }
                flex_ang.add(read);
                turn_ang.add(read);
                bitalino.add(read);
            }
            return new List[]{flex_ang, turn_ang, bitalino};
        } catch (Exception e) {
            System.out.println("Could not read the profiles of the users!");
            e.printStackTrace(); //Helps to trace the exception
            return null;
        }
    }
*/
}
