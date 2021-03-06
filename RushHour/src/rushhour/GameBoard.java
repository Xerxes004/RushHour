/**
 * This class is the GameBoard for the Rush Hour game.
 *
 * @author Wesley Kelly
 * @version 1.0
 *
 * File: GameBoard.java Created: 27 October 2015
 *
 * Copyright Cedarville University, its Computer Science faculty, and the
 * authors. All rights reserved.
 *
 * Description: this class is the GameBoard GUI for the Rush Hour Game. The
 * RushHour class manipulates this GUI to update it with vehicles and a proper
 * solution array. A user can walk through this solution to see exactly how the
 * problem was solved, step-by-step.
 */

package rushhour;

import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JLabel;

public class GameBoard
    extends javax.swing.JFrame
{
    /**
     * Creates new form GameBoard
     */
    public GameBoard()
    {
        // netbeans code
        initComponents();

        // Wes code
        grids = new HashMap<>();
        vehicles = new ArrayList<>();
        moves = new ArrayList<>();
        this.movePosition = 0;

        // All components in GridPanel are JLabels.
        // We store the default color so that the board can be redrawn.
        defaultColor = GridPanel.getComponent(0).getBackground();

        // Clear the board and get it ready for use.
        this.clearBoard();

        // Populate the ArrayList of grids which we use when drawing the board
        // and moving vehicles.
        for (Component g : GridPanel.getComponents())
        {
            JLabel grid = (JLabel) g;

            if (grid != null)
            {
                if (g.getName() != null)
                {
                    grid.setOpaque(true);
                    grids.put(g.getName(), g);
                }
            }
        }
    }

    private final Color defaultColor;
    final private HashMap<String, Component> grids;
    final private ArrayList<Vehicle> vehicles;
    private ArrayList<Move> moves;
    private int movePosition;

    /**
     * Sets the ArrayList of moves that should contain the best solution to the
     * puzzle.
     *
     * @param moves the ArrayList of moves containing a solution
     */
    public void setMoves(ArrayList<Move> moves)
    {
        this.moves = moves;
        totalMovesLabel.setText("Total moves: " + (this.moves.size() + 1));
    }

    /**
     * Adds a vehicle to the board and draws it.
     *
     * @param vehicle the vehicle to be added to the board.
     *
     * NOTE: The board is dumb and assumes you know where to place cars. They
     * can be placed on top of one another if you don't do it right.
     */
    public void addVehicle(Vehicle vehicle)
    {
        vehicles.add(vehicle);
        this.drawAllVehicles();
    }

    /**
     * Finds the vehicle that was added to the board with the given color.
     *
     * @param color the color of the vehicle to be found
     * @return the vehicle with the specified color, or null if not found
     */
    private Vehicle findVehicleByColor(String color)
    {
        for (Vehicle v : vehicles)
        {
            if (v.color().equals(color))
            {
                return v;
            }
        }
        return null;
    }

    /**
     * Clears the board.
     */
    private void clearBoard()
    {
        for (Component comp : GridPanel.getComponents())
        {
            JLabel grid = (JLabel) comp;
            grid.setBackground(defaultColor);
            grid.setText("");
        }
    }

    /**
     * Draws all vehicles stored by the board.
     */
    private void drawAllVehicles()
    {
        clearBoard();
        for (Vehicle v : vehicles)
        {
            drawVehicle(v);
        }
    }

    /**
     * Moves the vehicle associated with the given Move object.
     *
     * @param move the move object containing the move to be taken
     * @return true if the move succeeded, false otherwise
     */
    public boolean moveVehicle(Move move)
    {
        return moveVehicle(move.color(), move.spaces(), move.direction());
    }

    /**
     * Moves the vehicle associated with the color given.
     *
     * @param color the color of the vehicle to move
     * @param moves the amount of spaces to move
     * @param direction the direction of movement
     * @return true if the move succeeded, false otherwise
     */
    public boolean moveVehicle(String color, int moves, String direction)
    {
        for (int i = 0; i < moves; i++)
        {
            if (!moveVehicle(color, direction))
            {
                return false;
            }
        }
        return true;
    }

    /**
     * Moves the vehicle with the given color a single space.
     *
     * @param color the color of the vehicle being moved
     * @param direction the direction of movement
     * @return
     */
    private boolean moveVehicle(String color, String direction)
    {
        try
        {
            boolean succeeded = false;

            switch (direction)
            {
                case "u":
                case "U":
                    succeeded = findVehicleByColor(color).up();
                    break;

                case "d":
                case "D":
                    succeeded = findVehicleByColor(color).down();
                    break;

                case "l":
                case "L":
                    succeeded = findVehicleByColor(color).left();
                    break;

                case "r":
                case "R":
                    succeeded = findVehicleByColor(color).right();
                    break;

                default:
                    return false;
            }

            drawAllVehicles();

            return succeeded;
        }
        catch (InvalidMovementException ex)
        {
            return false;
        }
    }

    /**
     * Draws a single vehicle on the board.
     * 
     * @param vehicle the vehicle to draw
     * 
     * Useful for seeing where a single vehicle is positioned.
     */
    public void drawOneVehicle(Vehicle vehicle)
    {
        clearBoard();
        drawVehicle(vehicle);
    }

    /**
     * Draws a vehicle on the board, but does not clear the board.
     * 
     * @param vehicle the vehicle to draw
     * 
     * Used to populate the board with vehicles.
     */
    private void drawVehicle(Vehicle vehicle)
    {
        String front = "Grid" + vehicle.x() + vehicle.y();

        String type = vehicle.type();
        String orientation = vehicle.orientation();

        int offset = (type.equals("car") ? 1 : 2);

        String back = "";

        if (orientation.equals("h"))
        {
            back = "Grid" + (vehicle.x() + offset) + vehicle.y();
        }
        else
        {
            back = "Grid" + vehicle.x() + (vehicle.y() + offset);
        }

        JLabel frontGrid = (JLabel) grids.get(front);
        frontGrid.setBackground(vehicle.colorValue());

        JLabel backGrid = (JLabel) grids.get(back);
        backGrid.setBackground(vehicle.colorValue());

        if (type.equals("truck"))
        {
            String middle = "";
            if (orientation.equals("h"))
            {
                middle = "Grid" + (vehicle.x() + 1) + vehicle.y();
            }
            else
            {
                middle = "Grid" + vehicle.x() + (vehicle.y() + 1);
            }

            JLabel middleGrid = (JLabel) grids.get(middle);
            middleGrid.setBackground(vehicle.colorValue());
        }
    }
    
    /**
     * Gets the component with the specified name.
     *
     * @param name name of component to find
     * @return the component with the specified name, null if not found
     */
    public Component getComponentWithName(String name)
    {
        return grids.get(name);
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {
        
        GridPanel = new javax.swing.JPanel();
        Grid02 = new javax.swing.JLabel();
        Grid14 = new javax.swing.JLabel();
        Grid00 = new javax.swing.JLabel();
        Grid30 = new javax.swing.JLabel();
        Grid31 = new javax.swing.JLabel();
        Grid05 = new javax.swing.JLabel();
        Grid20 = new javax.swing.JLabel();
        Grid42 = new javax.swing.JLabel();
        Grid43 = new javax.swing.JLabel();
        Grid11 = new javax.swing.JLabel();
        Grid35 = new javax.swing.JLabel();
        Grid41 = new javax.swing.JLabel();
        Grid03 = new javax.swing.JLabel();
        Grid10 = new javax.swing.JLabel();
        Grid23 = new javax.swing.JLabel();
        Grid40 = new javax.swing.JLabel();
        Grid33 = new javax.swing.JLabel();
        Grid25 = new javax.swing.JLabel();
        Grid01 = new javax.swing.JLabel();
        Grid32 = new javax.swing.JLabel();
        Grid04 = new javax.swing.JLabel();
        Grid45 = new javax.swing.JLabel();
        Grid12 = new javax.swing.JLabel();
        Grid24 = new javax.swing.JLabel();
        Grid34 = new javax.swing.JLabel();
        Grid21 = new javax.swing.JLabel();
        Grid15 = new javax.swing.JLabel();
        Grid22 = new javax.swing.JLabel();
        Grid44 = new javax.swing.JLabel();
        Grid13 = new javax.swing.JLabel();
        Grid54 = new javax.swing.JLabel();
        Grid52 = new javax.swing.JLabel();
        Grid55 = new javax.swing.JLabel();
        Grid53 = new javax.swing.JLabel();
        Grid50 = new javax.swing.JLabel();
        Grid51 = new javax.swing.JLabel();
        ExitLabel = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        BackButton = new javax.swing.JButton();
        ForwardButton = new javax.swing.JButton();
        moveCounterLabel = new javax.swing.JLabel();
        totalMovesLabel = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        
        GridPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Rush Hour!", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Avenir Next", 0, 14))); // NOI18N
        GridPanel.setName("GridPanel"); // NOI18N
        
        Grid02.setBackground(new java.awt.Color(255, 255, 255));
        Grid02.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        Grid02.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Grid02.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        Grid02.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        Grid02.setName("Grid25"); // NOI18N
        Grid02.setOpaque(true);
        Grid02.setPreferredSize(new java.awt.Dimension(80, 80));
        
        Grid14.setBackground(new java.awt.Color(255, 255, 255));
        Grid14.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        Grid14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Grid14.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        Grid14.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        Grid14.setName("Grid44"); // NOI18N
        Grid14.setOpaque(true);
        Grid14.setPreferredSize(new java.awt.Dimension(80, 80));
        
        Grid00.setBackground(new java.awt.Color(255, 255, 255));
        Grid00.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        Grid00.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Grid00.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        Grid00.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        Grid00.setName("Grid05"); // NOI18N
        Grid00.setOpaque(true);
        Grid00.setPreferredSize(new java.awt.Dimension(80, 80));
        
        Grid30.setBackground(new java.awt.Color(255, 255, 255));
        Grid30.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        Grid30.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Grid30.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        Grid30.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        Grid30.setName("Grid02"); // NOI18N
        Grid30.setOpaque(true);
        Grid30.setPreferredSize(new java.awt.Dimension(80, 80));
        
        Grid31.setBackground(new java.awt.Color(255, 255, 255));
        Grid31.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        Grid31.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Grid31.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        Grid31.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        Grid31.setName("Grid12"); // NOI18N
        Grid31.setOpaque(true);
        Grid31.setPreferredSize(new java.awt.Dimension(80, 80));
        
        Grid05.setBackground(new java.awt.Color(255, 255, 255));
        Grid05.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        Grid05.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Grid05.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        Grid05.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        Grid05.setName("Grid55"); // NOI18N
        Grid05.setOpaque(true);
        Grid05.setPreferredSize(new java.awt.Dimension(80, 80));
        
        Grid20.setBackground(new java.awt.Color(255, 255, 255));
        Grid20.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        Grid20.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Grid20.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        Grid20.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        Grid20.setName("Grid03"); // NOI18N
        Grid20.setOpaque(true);
        Grid20.setPreferredSize(new java.awt.Dimension(80, 80));
        
        Grid42.setBackground(new java.awt.Color(255, 255, 255));
        Grid42.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        Grid42.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Grid42.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        Grid42.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        Grid42.setName("Grid21"); // NOI18N
        Grid42.setOpaque(true);
        Grid42.setPreferredSize(new java.awt.Dimension(80, 80));
        
        Grid43.setBackground(new java.awt.Color(255, 255, 255));
        Grid43.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        Grid43.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Grid43.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        Grid43.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        Grid43.setName("Grid31"); // NOI18N
        Grid43.setOpaque(true);
        Grid43.setPreferredSize(new java.awt.Dimension(80, 80));
        
        Grid11.setBackground(new java.awt.Color(255, 255, 255));
        Grid11.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        Grid11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Grid11.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        Grid11.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        Grid11.setName("Grid14"); // NOI18N
        Grid11.setOpaque(true);
        Grid11.setPreferredSize(new java.awt.Dimension(80, 80));
        
        Grid35.setBackground(new java.awt.Color(255, 255, 255));
        Grid35.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        Grid35.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Grid35.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        Grid35.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        Grid35.setName("Grid52"); // NOI18N
        Grid35.setOpaque(true);
        Grid35.setPreferredSize(new java.awt.Dimension(80, 80));
        
        Grid41.setBackground(new java.awt.Color(255, 255, 255));
        Grid41.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        Grid41.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Grid41.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        Grid41.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        Grid41.setName("Grid11"); // NOI18N
        Grid41.setOpaque(true);
        Grid41.setPreferredSize(new java.awt.Dimension(80, 80));
        
        Grid03.setBackground(new java.awt.Color(255, 255, 255));
        Grid03.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        Grid03.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Grid03.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        Grid03.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        Grid03.setName("Grid35"); // NOI18N
        Grid03.setOpaque(true);
        Grid03.setPreferredSize(new java.awt.Dimension(80, 80));
        
        Grid10.setBackground(new java.awt.Color(255, 255, 255));
        Grid10.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        Grid10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Grid10.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        Grid10.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        Grid10.setName("Grid04"); // NOI18N
        Grid10.setOpaque(true);
        Grid10.setPreferredSize(new java.awt.Dimension(80, 80));
        
        Grid23.setBackground(new java.awt.Color(255, 255, 255));
        Grid23.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        Grid23.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Grid23.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        Grid23.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        Grid23.setName("Grid33"); // NOI18N
        Grid23.setOpaque(true);
        Grid23.setPreferredSize(new java.awt.Dimension(80, 80));
        
        Grid40.setBackground(new java.awt.Color(255, 255, 255));
        Grid40.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        Grid40.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Grid40.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        Grid40.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        Grid40.setName("Grid01"); // NOI18N
        Grid40.setOpaque(true);
        Grid40.setPreferredSize(new java.awt.Dimension(80, 80));
        
        Grid33.setBackground(new java.awt.Color(255, 255, 255));
        Grid33.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        Grid33.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Grid33.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        Grid33.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        Grid33.setName("Grid32"); // NOI18N
        Grid33.setOpaque(true);
        Grid33.setPreferredSize(new java.awt.Dimension(80, 80));
        
        Grid25.setBackground(new java.awt.Color(255, 255, 255));
        Grid25.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        Grid25.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Grid25.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        Grid25.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        Grid25.setName("Grid53"); // NOI18N
        Grid25.setOpaque(true);
        Grid25.setPreferredSize(new java.awt.Dimension(80, 80));
        
        Grid01.setBackground(new java.awt.Color(255, 255, 255));
        Grid01.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        Grid01.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Grid01.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        Grid01.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        Grid01.setName("Grid15"); // NOI18N
        Grid01.setOpaque(true);
        Grid01.setPreferredSize(new java.awt.Dimension(80, 80));
        
        Grid32.setBackground(new java.awt.Color(255, 255, 255));
        Grid32.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        Grid32.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Grid32.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        Grid32.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        Grid32.setName("Grid22"); // NOI18N
        Grid32.setOpaque(true);
        Grid32.setPreferredSize(new java.awt.Dimension(80, 80));
        
        Grid04.setBackground(new java.awt.Color(255, 255, 255));
        Grid04.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        Grid04.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Grid04.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        Grid04.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        Grid04.setName("Grid45"); // NOI18N
        Grid04.setOpaque(true);
        Grid04.setPreferredSize(new java.awt.Dimension(80, 80));
        
        Grid45.setBackground(new java.awt.Color(255, 255, 255));
        Grid45.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        Grid45.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Grid45.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        Grid45.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        Grid45.setName("Grid51"); // NOI18N
        Grid45.setOpaque(true);
        Grid45.setPreferredSize(new java.awt.Dimension(80, 80));
        
        Grid12.setBackground(new java.awt.Color(255, 255, 255));
        Grid12.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        Grid12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Grid12.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        Grid12.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        Grid12.setName("Grid24"); // NOI18N
        Grid12.setOpaque(true);
        Grid12.setPreferredSize(new java.awt.Dimension(80, 80));
        
        Grid24.setBackground(new java.awt.Color(255, 255, 255));
        Grid24.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        Grid24.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Grid24.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        Grid24.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        Grid24.setName("Grid43"); // NOI18N
        Grid24.setOpaque(true);
        Grid24.setPreferredSize(new java.awt.Dimension(80, 80));
        
        Grid34.setBackground(new java.awt.Color(255, 255, 255));
        Grid34.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        Grid34.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Grid34.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        Grid34.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        Grid34.setName("Grid42"); // NOI18N
        Grid34.setOpaque(true);
        Grid34.setPreferredSize(new java.awt.Dimension(80, 80));
        
        Grid21.setBackground(new java.awt.Color(255, 255, 255));
        Grid21.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        Grid21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Grid21.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        Grid21.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        Grid21.setName("Grid13"); // NOI18N
        Grid21.setOpaque(true);
        Grid21.setPreferredSize(new java.awt.Dimension(80, 80));
        
        Grid15.setBackground(new java.awt.Color(255, 255, 255));
        Grid15.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        Grid15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Grid15.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        Grid15.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        Grid15.setName("Grid54"); // NOI18N
        Grid15.setOpaque(true);
        Grid15.setPreferredSize(new java.awt.Dimension(80, 80));
        
        Grid22.setBackground(new java.awt.Color(255, 255, 255));
        Grid22.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        Grid22.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Grid22.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        Grid22.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        Grid22.setName("Grid23"); // NOI18N
        Grid22.setOpaque(true);
        Grid22.setPreferredSize(new java.awt.Dimension(80, 80));
        
        Grid44.setBackground(new java.awt.Color(255, 255, 255));
        Grid44.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        Grid44.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Grid44.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        Grid44.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        Grid44.setName("Grid41"); // NOI18N
        Grid44.setOpaque(true);
        Grid44.setPreferredSize(new java.awt.Dimension(80, 80));
        
        Grid13.setBackground(new java.awt.Color(255, 255, 255));
        Grid13.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        Grid13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Grid13.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        Grid13.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        Grid13.setName("Grid34"); // NOI18N
        Grid13.setOpaque(true);
        Grid13.setPreferredSize(new java.awt.Dimension(80, 80));
        
        Grid54.setBackground(new java.awt.Color(255, 255, 255));
        Grid54.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        Grid54.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Grid54.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        Grid54.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        Grid54.setName("Grid40"); // NOI18N
        Grid54.setOpaque(true);
        Grid54.setPreferredSize(new java.awt.Dimension(80, 80));
        
        Grid52.setBackground(new java.awt.Color(255, 255, 255));
        Grid52.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        Grid52.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Grid52.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        Grid52.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        Grid52.setName("Grid20"); // NOI18N
        Grid52.setOpaque(true);
        Grid52.setPreferredSize(new java.awt.Dimension(80, 80));
        
        Grid55.setBackground(new java.awt.Color(255, 255, 255));
        Grid55.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        Grid55.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Grid55.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        Grid55.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        Grid55.setName("Grid50"); // NOI18N
        Grid55.setOpaque(true);
        Grid55.setPreferredSize(new java.awt.Dimension(80, 80));
        
        Grid53.setBackground(new java.awt.Color(255, 255, 255));
        Grid53.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        Grid53.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Grid53.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        Grid53.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        Grid53.setName("Grid30"); // NOI18N
        Grid53.setOpaque(true);
        Grid53.setPreferredSize(new java.awt.Dimension(80, 80));
        
        Grid50.setBackground(new java.awt.Color(255, 255, 255));
        Grid50.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        Grid50.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Grid50.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        Grid50.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        Grid50.setName("Grid00"); // NOI18N
        Grid50.setOpaque(true);
        Grid50.setPreferredSize(new java.awt.Dimension(80, 80));
        
        Grid51.setBackground(new java.awt.Color(255, 255, 255));
        Grid51.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        Grid51.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Grid51.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        Grid51.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        Grid51.setName("Grid10"); // NOI18N
        Grid51.setOpaque(true);
        Grid51.setPreferredSize(new java.awt.Dimension(80, 80));
        
        javax.swing.GroupLayout GridPanelLayout = new javax.swing.GroupLayout(GridPanel);
        GridPanel.setLayout(GridPanelLayout);
        GridPanelLayout.setHorizontalGroup(
                                           GridPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                           .addGroup(GridPanelLayout.createSequentialGroup()
                                                     .addGroup(GridPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                               .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, GridPanelLayout.createSequentialGroup()
                                                                         .addGap(0, 10, Short.MAX_VALUE)
                                                                         .addComponent(Grid00, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                         .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                         .addComponent(Grid01, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                         .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                         .addComponent(Grid02, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                         .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                         .addComponent(Grid03, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                         .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                         .addComponent(Grid04, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                         .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                         .addComponent(Grid05, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                               .addGroup(GridPanelLayout.createSequentialGroup()
                                                                         .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                         .addGroup(GridPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                                   .addGroup(javax.swing.GroupLayout.Alignment.CENTER, GridPanelLayout.createSequentialGroup()
                                                                                             .addComponent(Grid30, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                             .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                             .addComponent(Grid31, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                             .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                             .addComponent(Grid32, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                             .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                             .addComponent(Grid33, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                             .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                             .addComponent(Grid34, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                             .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                             .addComponent(Grid35, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                                   .addGroup(javax.swing.GroupLayout.Alignment.CENTER, GridPanelLayout.createSequentialGroup()
                                                                                             .addComponent(Grid40, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                             .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                             .addComponent(Grid41, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                             .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                             .addComponent(Grid42, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                             .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                             .addComponent(Grid43, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                             .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                             .addComponent(Grid44, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                             .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                             .addComponent(Grid45, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                                   .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, GridPanelLayout.createSequentialGroup()
                                                                                             .addComponent(Grid50, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                             .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                             .addComponent(Grid51, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                             .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                             .addComponent(Grid52, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                             .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                             .addComponent(Grid53, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                             .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                             .addComponent(Grid54, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                             .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                             .addComponent(Grid55, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                                   .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, GridPanelLayout.createSequentialGroup()
                                                                                             .addComponent(Grid20, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                             .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                             .addComponent(Grid21, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                             .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                             .addComponent(Grid22, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                             .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                             .addComponent(Grid23, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                             .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                             .addComponent(Grid24, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                             .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                             .addComponent(Grid25, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                                   .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, GridPanelLayout.createSequentialGroup()
                                                                                             .addComponent(Grid10, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                             .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                             .addComponent(Grid11, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                             .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                             .addComponent(Grid12, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                             .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                             .addComponent(Grid13, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                             .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                             .addComponent(Grid14, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                             .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                             .addComponent(Grid15, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                                                     .addContainerGap())
                                           );
        GridPanelLayout.setVerticalGroup(
                                         GridPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                         .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, GridPanelLayout.createSequentialGroup()
                                                   .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                   .addGroup(GridPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                             .addComponent(Grid50, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                             .addComponent(Grid51, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                             .addComponent(Grid52, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                             .addComponent(Grid53, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                             .addComponent(Grid54, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                             .addComponent(Grid55, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                   .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                   .addGroup(GridPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                             .addComponent(Grid40, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                             .addComponent(Grid41, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                             .addComponent(Grid42, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                             .addComponent(Grid43, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                             .addComponent(Grid44, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                             .addComponent(Grid45, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                   .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                   .addGroup(GridPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                             .addComponent(Grid30, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                             .addComponent(Grid31, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                             .addComponent(Grid32, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                             .addComponent(Grid33, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                             .addComponent(Grid34, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                             .addComponent(Grid35, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                   .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                   .addGroup(GridPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                             .addComponent(Grid20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                             .addComponent(Grid21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                             .addComponent(Grid22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                             .addComponent(Grid23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                             .addComponent(Grid24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                             .addComponent(Grid25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                   .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                   .addGroup(GridPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                             .addComponent(Grid10, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                             .addComponent(Grid11, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                             .addComponent(Grid12, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                             .addComponent(Grid13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                             .addComponent(Grid14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                             .addComponent(Grid15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                   .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                   .addGroup(GridPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                             .addComponent(Grid00, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                             .addComponent(Grid01, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                             .addComponent(Grid02, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                             .addComponent(Grid03, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                             .addComponent(Grid04, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                             .addComponent(Grid05, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                   .addContainerGap())
                                         );
        
        ExitLabel.setFont(new java.awt.Font("Avenir Next", 0, 24)); // NOI18N
        ExitLabel.setForeground(new java.awt.Color(255, 0, 51));
        ExitLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ExitLabel.setText("EXIT");
        ExitLabel.setFocusable(false);
        ExitLabel.setName(""); // NOI18N
        ExitLabel.setOpaque(true);
        ExitLabel.setPreferredSize(new java.awt.Dimension(80, 80));
        
        jLabel2.setText("Step through solution");
        
        BackButton.setText("Prev");
        BackButton.setPreferredSize(new java.awt.Dimension(94, 29));
        BackButton.addActionListener(new java.awt.event.ActionListener()
                                     {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                BackButtonActionPerformed(evt);
            }
        });
        
        ForwardButton.setText("Next");
        ForwardButton.addActionListener(new java.awt.event.ActionListener()
                                        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                ForwardButtonActionPerformed(evt);
            }
        });
        
        moveCounterLabel.setText("Move: 0");
        
        totalMovesLabel.setText("jLabel3");
        
        jLabel1.setForeground(new java.awt.Color(153, 153, 153));
        jLabel1.setText("Wesley Kelly and James Von Eiff, Copyright Cedarville University 2015.");
        
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                                  layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                  .addGroup(layout.createSequentialGroup()
                                            .addContainerGap()
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                      .addGroup(layout.createSequentialGroup()
                                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                          .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                                                                                    .addGroup(layout.createSequentialGroup()
                                                                                              .addComponent(BackButton, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                              .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                              .addComponent(ForwardButton, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                                    .addComponent(jLabel2))
                                                                          .addComponent(totalMovesLabel)
                                                                          .addComponent(moveCounterLabel))
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                .addComponent(GridPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(ExitLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addContainerGap())
                                                      .addGroup(layout.createSequentialGroup()
                                                                .addGap(5, 5, 5)
                                                                .addComponent(jLabel1)
                                                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                                  );
        layout.setVerticalGroup(
                                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                          .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addGroup(layout.createSequentialGroup()
                                                              .addGap(35, 35, 35)
                                                              .addComponent(totalMovesLabel)
                                                              .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                              .addComponent(moveCounterLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                              .addGap(18, 18, 18)
                                                              .addComponent(jLabel2)
                                                              .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                              .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                                        .addComponent(BackButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addComponent(ForwardButton, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                    .addComponent(GridPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addGroup(layout.createSequentialGroup()
                                                              .addGap(205, 205, 205)
                                                              .addComponent(ExitLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                          .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                          .addComponent(jLabel1)
                                          .addContainerGap())
                                );
        
        GridPanel.getAccessibleContext().setAccessibleName("GridPanel");
        
        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    private void BackButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_BackButtonActionPerformed
    {//GEN-HEADEREND:event_BackButtonActionPerformed
        if (this.movePosition == this.moves.size() + 1)
        {
            Grid34.setBackground(Color.red);
            this.movePosition = this.moves.size();
            moveCounterLabel.setText("Move: " + this.movePosition);
        }
        else if (this.movePosition > 0)
        {
            this.movePosition--;
            this.moveVehicle(moves.get(this.movePosition).invert());
            moveCounterLabel.setText("Move: " + this.movePosition);
        }
    }//GEN-LAST:event_BackButtonActionPerformed
    
    private void ForwardButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_ForwardButtonActionPerformed
    {//GEN-HEADEREND:event_ForwardButtonActionPerformed
        if (this.movePosition < moves.size())
        {
            this.moveVehicle(moves.get(this.movePosition));
            this.movePosition++;
            moveCounterLabel.setText("Move: " + this.movePosition);
        }
        else
        {
            this.movePosition = this.moves.size() + 1;
            Grid34.setBackground(defaultColor);
            moveCounterLabel.setText("Move: " + this.movePosition);
        }
    }//GEN-LAST:event_ForwardButtonActionPerformed
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[])
    {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try
        {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels())
            {
                if ("Nimbus".equals(info.getName()))
                {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        }
        catch (ClassNotFoundException ex)
        {
            java.util.logging.Logger.getLogger(GameBoard.class.getName()).log(
                                                                              java.util.logging.Level.SEVERE, null, ex);
        }
        catch (InstantiationException ex)
        {
            java.util.logging.Logger.getLogger(GameBoard.class.getName()).log(
                                                                              java.util.logging.Level.SEVERE, null, ex);
        }
        catch (IllegalAccessException ex)
        {
            java.util.logging.Logger.getLogger(GameBoard.class.getName()).log(
                                                                              java.util.logging.Level.SEVERE, null, ex);
        }
        catch (javax.swing.UnsupportedLookAndFeelException ex)
        {
            java.util.logging.Logger.getLogger(GameBoard.class.getName()).log(
                                                                              java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable()
                                        {
            public void run()
            {
                new GameBoard().setVisible(true);
            }
        });
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BackButton;
    private javax.swing.JLabel ExitLabel;
    private javax.swing.JButton ForwardButton;
    private javax.swing.JLabel Grid00;
    private javax.swing.JLabel Grid01;
    private javax.swing.JLabel Grid02;
    private javax.swing.JLabel Grid03;
    private javax.swing.JLabel Grid04;
    private javax.swing.JLabel Grid05;
    private javax.swing.JLabel Grid10;
    private javax.swing.JLabel Grid11;
    private javax.swing.JLabel Grid12;
    private javax.swing.JLabel Grid13;
    private javax.swing.JLabel Grid14;
    private javax.swing.JLabel Grid15;
    private javax.swing.JLabel Grid20;
    private javax.swing.JLabel Grid21;
    private javax.swing.JLabel Grid22;
    private javax.swing.JLabel Grid23;
    private javax.swing.JLabel Grid24;
    private javax.swing.JLabel Grid25;
    private javax.swing.JLabel Grid30;
    private javax.swing.JLabel Grid31;
    private javax.swing.JLabel Grid32;
    private javax.swing.JLabel Grid33;
    private javax.swing.JLabel Grid34;
    private javax.swing.JLabel Grid35;
    private javax.swing.JLabel Grid40;
    private javax.swing.JLabel Grid41;
    private javax.swing.JLabel Grid42;
    private javax.swing.JLabel Grid43;
    private javax.swing.JLabel Grid44;
    private javax.swing.JLabel Grid45;
    private javax.swing.JLabel Grid50;
    private javax.swing.JLabel Grid51;
    private javax.swing.JLabel Grid52;
    private javax.swing.JLabel Grid53;
    private javax.swing.JLabel Grid54;
    private javax.swing.JLabel Grid55;
    private javax.swing.JPanel GridPanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel moveCounterLabel;
    private javax.swing.JLabel totalMovesLabel;
    // End of variables declaration//GEN-END:variables
}
