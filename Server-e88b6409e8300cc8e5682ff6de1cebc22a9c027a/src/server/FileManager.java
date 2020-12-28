
package server;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;
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
    final String BASE_DIRECTORY;
    ReentrantLock lock = new ReentrantLock();
    
    private String USER_FILE = "users.txt";
    private String FIXED_FILE = "fixed.txt";

    public FileManager() {
        BASE_DIRECTORY = System.getProperty("user.dir");// //Folder used for all the files   
    }

    //GUARDAAAR 
    public boolean saveUserPassword(String user, String password) {
        
        String saveUser = BASE_DIRECTORY + File.separator + USER_FILE;
        System.out.println(saveUser);
        lock.lock();
        dir = new File(saveUser);
        if (!dir.exists()) {
            try {
                dir.createNewFile(); //Create a new file
            } catch (IOException ex) {
                Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
                lock.unlock();
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
            lock.unlock();
            return false;
        }
        lock.unlock();
        return true;
    }

    public boolean saveFixedVariables(String name, String gender, int age, double weight, double height) {
        lock.lock();
        String saveFixedVar = BASE_DIRECTORY + File.separator + FIXED_FILE;
        System.out.println(saveFixedVar);
        dir = new File(saveFixedVar);
        if (!dir.exists()) {
            try {
                dir.createNewFile(); //Create a new file
            } catch (IOException ex) {
                Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
                lock.unlock();
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
            lock.unlock();
            return false;
        }
        lock.unlock();
        return true;
    }

    public boolean saveChangingVariables(String userName,double flex_ang, double turn_ang, List<Integer> bitalino) {
        // TODO Crear directorio con nombre del paciente
        lock.lock();
        String saveChangingVar = BASE_DIRECTORY + File.separator + userName + ".txt";
        System.out.println(saveChangingVar);
        dir = new File(saveChangingVar);
        if (!dir.exists()) {
            try {
                dir.createNewFile(); //Create a new file
            } catch (IOException ex) {
                Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
                lock.unlock();
                return false;
            }
        }
        try {
            pw = new PrintWriter(new FileWriter(dir, true));

            pw.append(todaysDate() + "\n");
            pw.append(flex_ang + "\n");
            pw.append(turn_ang + "\n");
            pw.append(bitalino.size()+ "\n");
            
            for(Integer data: bitalino) {
                pw.append(data + "\n");
            }
            
            pw.append("\n");
            pw.close();

        } catch (Exception ex) {
            Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
            lock.unlock();
            return false;
        }
        lock.unlock();
        return true;
    }

    //CARGAAR
    public List[] getUserPassword() {
        try {
            lock.lock();
            String user = BASE_DIRECTORY + File.separator + USER_FILE;
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
            lock.unlock();
            return new List[]{userNames, passwords};
        } catch (IOException e) {
            System.out.println("Could not read users or passwords!");
            e.printStackTrace(); //Helps to trace the exception
            lock.unlock();
            return null;
        }
    }

    private String todaysDate() {        
        Date date = new Date();
        Format formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String formattedDate = formatter.format(date);
        return formattedDate;
    }
    
}
