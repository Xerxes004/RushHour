/**
 * This is the main class for the Rush Hour game.
 *
 * @author Wesley Kelly, James Von Eiff
 * @version 1.0
 *
 * File: RushHour.java Created: 27 October 2015
 *
 * Copyright Cedarville University, its Computer Science faculty, and the
 * authors. All rights reserved.
 *
 * Description: This class contains all of the methods to solve the Rush Hour
 * game for a given input file, and then pass the solution to a GameBoard GUI.
 *
 * On the command line, run as java -jar RushHour.jar [filename] to solve the
 * file and open the solution in a GUI.
 * 
 * Algorithm overview:
 * 1. make a queue and make the initial value be the first dynamic value of the
 *    board
 * 2. while the queue is not empty, pop the front
 * 3. if the dequeued dynamic value has not been visited, find all adjacent
 *    moves and push them onto the back of the queue
 * 4. repeat step 2 - 4 until the red car escapes
 */
package rushhour;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class RushHour
{
    public RushHour(GameBoard board)
    {
        this.numCars = 0;
        this.vehicles = new ArrayList<>();
        this.lastMove = new ArrayList<>();
        this.board = board;
    }

    private int numCars;
    final private ArrayList<Vehicle> vehicles;
    final private ArrayList<Move> lastMove;
    final private GameBoard board;

    /**
     * This method solves the Rush Hour game with the given input file name.
     *
     * @param inputFileName the name of the file containing the Rush Hour game
     * parameters
     * @return the length of the solution, including the last move of the red
     * car off the board
     * @throws FileNotFoundException thrown if the input file is not found
     */
    public int solve(String inputFileName)
        throws FileNotFoundException
    {
        // Create and display the GameBoard
        java.awt.EventQueue.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                board.setVisible(true);
            }
        });

        // populate the internal variables with game information
        this.parseInput(inputFileName);

        // add all vehicles to the GUI board
        for (Vehicle v : this.vehicles)
        {
            board.addVehicle(v);
        }

        // Keys are dynamic values created from the current board state.
        // Values contain the previous dynamic value and the Move subsequently
        // taken.
        HashMap<String, DynamicMove> moves = new HashMap();

        // First move's parent is null
        String dynamicParentString = null;

        ArrayList<Vehicle> dynamicVehicles = this.getVehicles();

        //Put the dynamic info inot a string
        String dynamicInfo = "";

        //This is to keep track of the red car
        Integer redIndex = 0;

        for (int i = 0; i < this.numCars; i++)
        {
            dynamicInfo += dynamicVehicles.get(i).dynamicValue();
            if (dynamicVehicles.get(i).color().equals("red"))
            {
                redIndex = i;
            }
        }

        //Put the first node in the hashmap
        moves.put(dynamicInfo, new DynamicMove(dynamicParentString, null));

        // This is the queue we will use to do the breadth first search
        Queue<String> dynamicMoveValuesQueue = new LinkedList();

        //Put the first node on the queue
        dynamicMoveValuesQueue.add(dynamicInfo);

        //Tell if the game is over
        boolean solved = false;

        //String to hold the last move
        String winningDynamicString = "";

        // This is an array representation of the board with the first index 
        // being  the number of the row and the second being the number of the 
        // column
        String[][] abstractBoard = new String[6][6];

        //This loop will do the search
        while ((!dynamicMoveValuesQueue.isEmpty()) && (!solved))
        {
            //Get node
            String queueFront = dynamicMoveValuesQueue.poll();

            //Get array representation of the board
            abstractBoard = this.getAbstractBoardRepresentation(queueFront);

            //Loop through all the cars to get all the possible moves
            for (int i = 0; i < this.numCars; i++)
            {

                Vehicle currentCar = dynamicVehicles.get(i);
                String orient = currentCar.orientation();
                int x;
                int y;

                //If the car is horizontal
                if (orient.equals("h"))
                {
                    //Get the dynamic variable from the dynamic value string
                    x = Integer.parseInt(queueFront.substring(i, i + 1));
                    y = currentCar.y();

                    //If it is possible to move the car to the left
                    if (x != 0)
                    {
                        if (abstractBoard[y][x - 1].equals("_"))
                        {
                            //Make dynamic string to match the move
                            String leftMove = queueFront.substring(0, i)
                                + Integer.toString(x - 1)
                                + queueFront.substring(i + 1, this.numCars);

                            //See if this move has been done before
                            if (!moves.containsKey(leftMove))
                            {
                                //parent is the previous move
                                dynamicParentString = queueFront;

                                //Put it in the hashtable
                                moves.put(leftMove, new DynamicMove(
                                    dynamicParentString, new Move(
                                        currentCar.color(), 1, "L")));

                                //Add node to the queue
                                dynamicMoveValuesQueue.add(leftMove);
                            }
                        }
                    }

                    //See if the car can move right
                    if ((x + currentCar.length() - 1 < 5)
                        && 
                        (abstractBoard[y][x + currentCar.length()].equals("_")))
                    {
                        //Make dynamic string to match the move
                        String rightMove = queueFront.substring(0, i)
                            + Integer.toString(x + 1)
                            + queueFront.substring(i + 1, this.numCars);

                        //See if this move has been done before
                        if (!moves.containsKey(rightMove))
                        {
                            //Its parent is the previous move
                            dynamicParentString = queueFront;

                            //Put it in the hashtable
                            moves.put(rightMove,
                                new DynamicMove(dynamicParentString,
                                    new Move(currentCar.color(), 1, "R")));

                            //Add node to the queue
                            dynamicMoveValuesQueue.add(rightMove);
                        }
                    }
                }
                //If the car is vertical
                else
                {
                    x = currentCar.x();

                    //Get the dynamic variable from the string
                    y = Integer.parseInt(queueFront.substring(i, i + 1));

                    //See of the car can move up
                    if (y != 0)
                    {
                        if (abstractBoard[y - 1][x].equals("_"))
                        {
                            //Make dynamic string to match the move
                            String upMove = queueFront.substring(0, i)
                                + Integer.toString(y - 1)
                                + queueFront.substring(i + 1, this.numCars);

                            //See if this move has been done before
                            if (!moves.containsKey(upMove))
                            {
                                //Its parent is the previous move
                                dynamicParentString = queueFront;

                                //Put it in the hashtable
                                moves.put(upMove, new DynamicMove(
                                    dynamicParentString,
                                    new Move(
                                        currentCar.color(), 1, "U")));

                                //Add to the queue
                                dynamicMoveValuesQueue.add(upMove);
                            }
                        }
                    }

                    //See if a move down is possible
                    if ((y + currentCar.length() - 1 < 5)
                        && 
                        (abstractBoard[y + currentCar.length()][x].equals("_")))
                    {
                        //Make a dynamic string to match the move
                        String downMove = queueFront.substring(0, i)
                            + Integer.toString(y + 1)
                            + queueFront.substring(i + 1, this.numCars);

                        //See if the move has been done before
                        if (!moves.containsKey(downMove))
                        {
                            //Its parent is the previous move
                            dynamicParentString = queueFront;

                            //Put it in the hash table
                            moves.put(downMove,
                                new DynamicMove(dynamicParentString,
                                    new Move(currentCar.color(), 1, "D")));

                            //Add to the queue
                            dynamicMoveValuesQueue.add(downMove);
                        }
                    }
                }

                //This will stop the loop is the red car gets to the exit point
                if (queueFront.substring(redIndex, redIndex + 1).equals("4"))
                {
                    solved = true;
                    winningDynamicString = queueFront;
                    break;
                }
            }
        }

        if (solved)
        {
            String currentMove = winningDynamicString;

            while (moves.get(currentMove).parent() != null)
            {
                this.pushLastMove(moves.get(currentMove).move());
                currentMove = moves.get(currentMove).parent();
            }

            board.setMoves(this.moves());
        }
        else
        {
            System.out.println("Solve failed! Check input file.");
        }

        // returns one bigger because the last move is moving off the board, 
        // which is not allowed by the Vehicle class.
        return lastMove.size() + 1;
    }

    /**
     * Prints the moves taken to find the optimal solution.
     */
    public void printMoves()
    {
        System.out.println("(last move is red car off the board)");

        int i = 1;

        for (Move move : this.lastMove)
        {
            System.out.println(
                i + ": "
                + move.color() + " "
                + move.spaces() + " "
                + move.direction()
            );
            i++;
        }
    }

    /**
     * Pushes the last move taken to the front of the lastMove <code>ArrayList
     * </code> which is used to reconstruct the best move.
     *
     * @param move the last move taken
     *
     * Walk through the solution from the last move taken to the first move
     * taken for desired results.
     */
    public void pushLastMove(Move move)
    {
        this.lastMove.add(0, move);
    }

    /**
     * Gets the ArrayList of last moves.
     *
     * @return the ArrayList of last moves
     *
     * This array will be empty unless you use pushLastMove.
     */
    public ArrayList<Move> moves()
    {
        return this.lastMove;
    }

    /**
     * Gets the ArrayList of vehicles created from the input file.
     *
     * @return the ArrayList of vehicles from the input file
     *
     * The list will be empty unless parseInput is called on a file.
     */
    public ArrayList<Vehicle> getVehicles()
    {
        return this.vehicles;
    }

    /**
     * Parses the file to find vehicles for this particular Rush Hour scenario.
     *
     * @param fileName the name of the file containing the Rush Hour game
     * vehicles and positions.
     * @throws FileNotFoundException thrown if the specified file is not found
     */
    public void parseInput(String fileName)
        throws FileNotFoundException
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
            try
            {
                this.vehicles.add(new Vehicle(type, color, x, y, orientation));
            }
            catch (VehicleConstructorError ex)
            {
                String msg = "Error during vehicle construction while parsing ";
                msg += "the input file:" + ex.getMessage();
                System.out.println(msg);
            }
        }
    }

    /**
     * Creates a virtual game board using the "dynamic value" from the current
     * board state, where each underscore represents an open space and the first
     * letter of the car's color takes up non-empty spaces.
     *
     * @param dynamicValue the dynamic value of the current board state
     * @return a 6x6 array of Strings that contain the current board state
     *
     * This method allows for very easy debugging when attempting to see how the
     * queue is operating during a solve.
     */
    public String[][] getAbstractBoardRepresentation(String dynamicValue)
    {

        String[][] gameBoard = new String[6][6];
        for (int i = 0; i < 6; i++)
        {
            for (int j = 0; j < 6; j++)
            {
                gameBoard[i][j] = "_";
            }
        }

        for (int i = 0; i < this.numCars; i++)
        {
            Vehicle car = this.vehicles.get(i);

            int y;
            int x;

            if (car.orientation().equals("h"))
            {
                y = car.y();
                x = Integer.parseInt(dynamicValue.substring(i, i + 1));
            }
            else
            {
                y = Integer.parseInt(dynamicValue.substring(i, i + 1));
                x = car.x();
            }

            gameBoard[y][x] = car.color().substring(0, 1);

            if (car.orientation().equals("h"))
            {
                gameBoard[y][x + 1] = car.color().substring(0, 1);

                if (car.length() == 3)
                {
                    gameBoard[y][x + 2] = car.color().substring(0, 1);
                }
            }
            else
            {
                gameBoard[y + 1][x] = car.color().substring(0, 1);
                if (car.length() == 3)
                {
                    gameBoard[y + 2][x] = car.color().substring(0, 1);
                }
            }

        }

        return gameBoard;
    }

    /**
     * Prints the 6x6 array returned by the getAbstractBoardRepresentation 
     * method.
     *
     * @param board the 6x6 board array of Strings
     */
    private static void printBoard(String[][] board)
    {
        System.out.println("-----------");
        for (String s[] : board)
        {
            for (String string : s)
            {
                System.out.print(string);
            }
            System.out.println("");
        }
    }
    
    /**
     * Runs the RushHour game and solution for the specified file named in the
     * first command line argument.
     *
     * @param args the command line arguments. If an argument is present, the
     * program tries to use it as the filename for the game to solve.
     * @throws java.io.FileNotFoundException thrown when the specified input
     * file is not found
     */
    public static void main(String[] args)
        throws FileNotFoundException
    {
        GameBoard board = new GameBoard();
        RushHour game = new RushHour(board);
        try
        {
            int solutionLength = 0;

            if (args.length > 0)
            {
                solutionLength = game.solve(args[0]);
            }
            else
            {
                solutionLength = game.solve("game1.dat");
            }

            System.out.println("Minimum number of moves: " + solutionLength);
            game.printMoves();
        }
        catch (FileNotFoundException ex)
        {
            System.out.println("File not found: " + ex.getMessage());
        }
    }

}
