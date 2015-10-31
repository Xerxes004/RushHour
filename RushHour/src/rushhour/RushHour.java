
/**
 * Title : Project 3, CS3410 Description: A breadth-first-search solution to the
 * Rush Hour game.
 *
 * Copyright : Copyright (c) 2015 Wesley Kelly, James Von Eiff, Cedarville
 * University
 *
 * @author : Wesley Kelly, James Von Eiff
 * @version 1.0
 */

package rushhour;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class RushHour
{
    private int numCars;
    private ArrayList<Vehicle> vehicles;
    
    public RushHour()
    {
        this.vehicles = new ArrayList<>();
        this.numCars = 0;
    }
    
    /**
     * @param args the command line arguments
     * @throws rushhour.InvalidMovementException
     */
    public static void main(String[] args)
        throws InvalidMovementException, VehicleConstructorError, FileNotFoundException
    {
        final GameBoard board = new GameBoard();
        final RushHour game = new RushHour();

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {   
                board.setVisible(true);
            }
        });

        game.parseInput("game2.dat");

        for (Vehicle v : game.getVehicles())
        {
            board.addVehicle(v);
        }
        
        //This hashtable will use the board, in the form of a 36 cahracter
        //string, as the key and has a string array the first item is the color 
        // of the node and the second is the parent node
        Hashtable <String, String[]> nodes = new Hashtable();
        String[] items = new String[2];
        items[0] = null;
        items[1] = "gray";
        Character[][] boardArray = new Character[6][6];
        String boardInString = "";
        
        nodes.put(boardInString, items);
        
        Queue<Character[][]> nodesToSearch = new LinkedList();
        nodesToSearch.add(boardArray);
    }
    
    public ArrayList<Vehicle> getVehicles()
    {
        return this.vehicles;
    }

    public void parseInput(String fileName)
        throws VehicleConstructorError, FileNotFoundException
    {
        Scanner scan = new Scanner(new File(fileName));
        if (scan.hasNextInt())
        {
            this.numCars = scan.nextInt();
        }
        for (int i = 0; i < numCars; i++)
        {
            String type = "";

            if (scan.hasNext())
            {
                type = scan.next();
            }

            String color = "";

            if (scan.hasNext())
            {
                color = scan.next();
            }

                String orientation = "";

            if (scan.hasNext())
            {
                orientation = scan.next();
            }

            int y = 0;

            if (scan.hasNextInt())
            {
                y = scan.nextInt() - 1;
            }

            int x = 0;

            if (scan.hasNextInt())
            {
                x = scan.nextInt() - 1;
            }

            this.vehicles.add(new Vehicle(type, color, x, y, orientation));
        }
    }
    
    public String changeArrayToString(Character[][] board) {
        
        String stringBoard = "";
        for(int i = 0; i < 6; i++){
            for(int j = 0; j < 6; j++){
                stringBoard += board[i][j];
            }
        }
        
        return stringBoard;
    }
}
