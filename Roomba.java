/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.BufferedReader;
import java.io.FileReader;

/**
 *
 * @author Harjit
 */
public class Roomba {

    boolean[][] room;
    int lineno, currX, currY, cleaned;

    public void clean(String fileName) {
        cleaned = 0;
        lineno = 1;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String line = null;
            while ((line = reader.readLine()) != null) {
                interpretLine(line);
            }

            System.out.println(currX + " " + currY);
            System.out.println(cleaned);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Could not find the file: " + fileName);
        }
    }

    void interpretLine(String line) {
        String[] coordinates = line.split(" ");

        //if there is a space in the line, it will specify a coordinate pair,
        //otherwise, it is the line that gives directions to the Roomba
        if (coordinates.length > 1) {
            int x = Integer.parseInt(coordinates[0]);
            int y = Integer.parseInt(coordinates[1]);

            //if it is the first line, set room dimensions
            if (lineno == 1) {
                room = new boolean[x][y];
            } //if it is the second line, set rooma location
            else if (lineno == 2) {
                if (x < room.length && y < room[0].length) {
                    currX = x;
                    currY = y;
                } else {
                    System.out.println("Error in file: initial location outside of room. Check file and run again");
                    System.exit(1);
                }

            } //else it is the location of dirt
            else if (x < room.length && x >= 0 && y < room[0].length && y >= 0) {
                room[x][y] = true;
            }
            
            lineno++;

        } //the line is the directions for the roomba
        else {
            for (int i = 0; i < line.length(); i++) {
                char movement = line.charAt(i);
                move(movement);
            }
        }

    }

    void move(char movement) {
        if (movement == 'N' || movement == 'n') {
            if (currY + 1 < room[0].length) {
                currY++;
            }
        } else if (movement == 'S' || movement == 's') {
            if (currY - 1 >= 0) {
                currY--;
            }
        } else if (movement == 'W' || movement == 'w') {
            if (currX - 1 >= 0) {
                currX--;
            }
        } else if (movement == 'E' || movement == 'e') {
            if (currX + 1 < room.length) {
                currX++;
            }
        }

        if (room[currX][currY]) {
            room[currX][currY] = false;
            cleaned++;
        }
    }

    public static void main(String[] args) {
        Roomba test = new Roomba();
        test.clean("input.txt");
    }
}
