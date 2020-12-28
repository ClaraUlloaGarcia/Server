/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author ClaraU
 */
public class WorkoutManager {
    
    private final int TOTAL_WORKOUT = 3;
    private String workoutList [] = new String [] {
        "Hide your good arm and let the other free. You are going to bend and strech progresively.",
        "Open and close you hemiplegic hand. If youh feel confident try to tight as much as possible.", 
        "Interlace your hands and do circular movements. The good rm is going to guide your injured one.",
        "Put up your elbow at the hight of your face and extend your hand as high as possible.", 
        "Situate your elbow on the table and flex your arm. Try to reach 90º.",
        "Straight your arm and try to turn in the same height backwards until it makes an horizontal line with your body.",
        "Stand up and strench your arms up. Mantain this position during 20 seconds.",
        "Try to grab different 'light' weights from the floor like a book, a bottle full of water, a ball...",
        "Practice fine motor skills (precise small movements with your arm).",
        "Finger walk",
        "Snap your fingers",
        "Clap your hands"
    };
    
    public String[] nextWorkout () {
        Random random = new Random();
        List<Integer> positions = new ArrayList();
        while(positions.size() < TOTAL_WORKOUT){
            int position = random.nextInt(workoutList.length);
            if(positions.indexOf(position) < 0){
                positions.add(position);
            }
        }
        String output [] = new String[TOTAL_WORKOUT];
        for(int i = 0; i < TOTAL_WORKOUT; i++){
            int position = positions.get(i);
            output[i] = workoutList[position]; 
        }
        return output;
    }
    
}
