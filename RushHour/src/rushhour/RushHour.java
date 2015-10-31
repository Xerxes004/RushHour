
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
import java.util.Objects;
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

        game.parseInput("game1.dat");

        for (Vehicle v : game.getVehicles())
        {
            board.addVehicle(v);
        }
        
        board.moveVehicle("lightblue", 3, "l");
        board.moveVehicle("yellow", 3, "d");
        board.moveVehicle("lime", 1, "r");
        board.moveVehicle("purple", 1, "u");
        board.moveVehicle("orange", 1, "u");
        board.moveVehicle("aqua", 2, "L");
        board.moveVehicle("blue", 2, "D");
        board.moveVehicle("red", 4, "R");
        
        //This hashtable will use the dynamic information, in the form of a
        //string, as the key and has a string array the first item is the color 
        // of the node and the second is the parent node
        Hashtable <String, String[]> nodes = new Hashtable();
        String[] items = new String[2];
        //Set the first node's parent to null
        items[0] = null;
        //Set its color to gray
        items[1] = "gray";
        
        //This is for easy of access
        ArrayList<Vehicle> dynamicVehicles = game.getVehicles();
        
        Character[][] boardArray = new Character[6][6];
        
        //Put the dynamic info inot a string
        String dynamicInfo = "";
        for(int i = 0; i < game.numCars; i++){
            dynamicInfo += dynamicVehicles.get(i).dynamicInfo();
        }
        //Put the first node in the hashtable
        nodes.put(dynamicInfo, items);
 
        // This is the queue we will use to do the breath first search
        Queue<Character[][]> nodesToSearch = new LinkedList();
        //Put the first node on the queue
        nodesToSearch.add(boardArray);
        
        //This loop will do the search
        while(!nodesToSearch.isEmpty()) {
            boardArray = nodesToSearch.poll();
            
            for(int i = 0; i < game.numCars; i++){
                Vehicle currentCar = dynamicVehicles.get(i);
                String orient = currentCar.orientation();
                Integer x;
                Integer y;
                if(orient.equals("h") ) {
                    x = Integer.valueOf(dynamicInfo.substring(i, i + 1));
                    y = currentCar.y();
                    if((x != 0)&&(boardArray[y][x-1].equals('_'))){
                        String check = dynamicInfo.substring(0, i) + 
                                Integer.toString(x) + 
                                dynamicInfo.substring(i+1, game.numCars);
                        if(!nodes.containsKey(check)) {
                            String[] data = new String[2];
                            data[1] = dynamicInfo;
                            data[2] = "gray";
                            nodes.put(check, data);
                            Character[][] changedArray = new Character[6][6];
                            changedArray = boardArray;
                            Character color = changedArray[y][x];
                            changedArray[y][x+currentCar.length()-1] = '_';
                            changedArray[y][x-1] = color;
                            nodesToSearch.add(changedArray);
                        }
                    }
                }
            }
        }
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
