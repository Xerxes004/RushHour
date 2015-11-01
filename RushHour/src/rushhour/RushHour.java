
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
    private ArrayList<Move> lastMove;
    
    public RushHour()
    {
        this.vehicles = new ArrayList<>();
        this.numCars = 0;
        this.lastMove = new ArrayList<>();
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
        
        game.pushLastMove(new Move("lightblue", 3, "l"));
        game.pushLastMove(new Move("yellow", 3, "d"));
        game.pushLastMove(new Move("lime", 1, "r"));
        game.pushLastMove(new Move("purple", 1, "u"));
        game.pushLastMove(new Move("orange", 1, "u"));
        game.pushLastMove(new Move("aqua", 2, "L"));
        game.pushLastMove(new Move("blue", 2, "D"));
        game.pushLastMove(new Move("red", 4, "R"));
        
        for (Move move : game.moves())
        {
            board.moveVehicle(move);
        }
        
        //This hashtable will use the dynamic information, in the form of a
        //string, as the key and has a string array the first item is the color 
        // of the node and the second is the parent node
        Hashtable <String, String> nodes = new Hashtable();
        //First moves parent is null
        String parent = null;

        
        //This is for easy of access
        ArrayList<Vehicle> dynamicVehicles = game.getVehicles();
        //This is an array representation of the board with the first index being 
        //the number of the row and the second being the number of the column
        Character[][] boardArray = new Character[6][6];
        
        //Put the dynamic info inot a string
        String dynamicInfo = "";
        //This is to keep track of the red car
        Integer redNum = 0;
        for(int i = 0; i < game.numCars; i++){
            dynamicInfo += dynamicVehicles.get(i).dynamicInfo();
            if(dynamicVehicles.get(i).colorString().equals("red")) {
                redNum = i;
            }
        }
        //Put the first node in the hashtable
        nodes.put(dynamicInfo, parent);
 
        // This is the queue we will use to do the breath first search
        Queue<Character[][]> nodesToSearch = new LinkedList();
        //Put the first node on the queue
        nodesToSearch.add(boardArray);
        //Tell if the game is over
        boolean solved = false;
        //String to hold the last move
        String finalMove = "";
        //This loop will do the search
        while((!nodesToSearch.isEmpty())||(solved)) {
            //Get node
            boardArray = nodesToSearch.poll();
            //Get the dynamic string for array
            String newDynam = game.changeArrayToString(boardArray);
            //Loop through all the cars to get all the possible moves
            for(int i = 0; i < game.numCars; i++){
                Vehicle currentCar = dynamicVehicles.get(i);
                String orient = currentCar.orientation();
                Integer x;
                Integer y;
                //If the car is horizontal
                if(orient.equals("h") ) {
                    //Get the dynamic variable from the srting
                    x = Integer.valueOf(dynamicInfo.substring(i, i + 1));
                    y = currentCar.y();
                    //If it is possible to move the car to the left
                    if((x != 0)&&(boardArray[y][x-1].equals('_'))){
                        //Make dynamic string to match the move
                        String check = dynamicInfo.substring(0, i) + 
                                Integer.toString(x-1) + 
                                dynamicInfo.substring(i+1, game.numCars);
                        //See if this move has been done before
                        if(!nodes.containsKey(check)) {
                            //parent is the previous move
                            parent = dynamicInfo;

                            //Put it in the hashtable
                            nodes.put(check, parent);
                            //New array for the queue
                            Character[][] changedArray = new Character[6][6];
                            changedArray = boardArray;
                            //Perform the move in the new array
                            Character color = changedArray[y][x];
                            changedArray[y][x+currentCar.length()-1] = '_';
                            changedArray[y][x-1] = color;
                            //Add array to the queue
                            nodesToSearch.add(changedArray);
                        }
                    } 
                    //See if the car can move right
                    if ((x + currentCar.length()-1 < 5)&&
                        (boardArray[y][x + currentCar.length()-1].equals('_'))){
                        //Make dynamic string to match the move
                        String check = dynamicInfo.substring(0, i) + 
                                Integer.toString(x+1) + 
                                dynamicInfo.substring(i+1, game.numCars);
                        //See if this move has been done before
                        if(!nodes.containsKey(check)) {
                            //Its parent is the previous move
                            parent = dynamicInfo;
                            //Put it in the hashtable
                            nodes.put(check, parent);
                            //New array for the queue
                            Character[][] changedArray = new Character[6][6];
                            changedArray = boardArray;
                            //Perform the move in the new array
                            Character color = changedArray[y][x];
                            changedArray[y][x+currentCar.length()+1] = color;
                            changedArray[y][x] = '_';
                            //Add array to the queue
                            nodesToSearch.add(changedArray);
                        }
                    }
                //If the car is vertical
                } else {
                    x = currentCar.x();
                    //Get the dynamic variable from the string
                    y = Integer.valueOf(dynamicInfo.substring(i, i + 1));
                    //See of the car can move up
                    if((y != 0)&&(boardArray[y-1][x].equals('_'))){
                        //Make dynamic string to match the move
                        String check = dynamicInfo.substring(0, i) + 
                                Integer.toString(y-1) + 
                                dynamicInfo.substring(i+1, game.numCars);
                        //See if this move has been done before
                        if(!nodes.containsKey(check)) {
                            //Its parent is the previous move
                            parent = dynamicInfo;
                            //Put it in the hashtable
                            nodes.put(check, parent);
                            //New array for the queue
                            Character[][] changedArray = new Character[6][6];
                            changedArray = boardArray;
                            //Perform the move in the new array
                            Character color = changedArray[y][x];
                            changedArray[y+currentCar.length()-1][x] = '_';
                            changedArray[y-1][x] = color;
                            //Add to the queue
                            nodesToSearch.add(changedArray);
                        }
                    } 
                    //See if a move down is possible
                    if ((y + currentCar.length()-1 < 5)&&
                        (boardArray[y + currentCar.length()-1][x].equals('_'))){
                        //Make a dynamic string to match the move
                        String check = dynamicInfo.substring(0, i) + 
                                Integer.toString(y+1) + 
                                dynamicInfo.substring(i+1, game.numCars);
                        //See if the move has been done before
                        if(!nodes.containsKey(check)) {
                            //Its parent is the previous move
                            parent = dynamicInfo;
                            //Put it in the hash table
                            nodes.put(check, parent);
                            //New array for the queue
                            Character[][] changedArray = new Character[6][6];
                            changedArray = boardArray;
                            //Perfom the move in the new array
                            Character color = changedArray[y][x];
                            changedArray[y+currentCar.length()+1][x] = color;
                            changedArray[y][x] = '_';
                            //Add to the queue
                            nodesToSearch.add(changedArray);
                        }
                    }
                }
                //This will stop the loop is the red car gets to the exit point
                if(dynamicInfo.substring(redNum, redNum+1) == "4") {
                    solved = true;
                    finalMove = newDynam;
                }
            }
            
        }
        if(solved) {
            
        } else {
            
        }
    }
    
    public void pushLastMove(Move move)
    {
        this.lastMove.add(0, move);
    }
    
    public ArrayList<Move> moves()
    {
        return this.lastMove;
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
