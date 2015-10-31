
package rushhour;

import java.awt.Color;
import java.util.HashMap;

public class Vehicle
{
    public Vehicle(String type, String color, int x, int y, String orientation)
        throws VehicleConstructorError
    {
        this.type = type;
        this.length = (this.type.equals("car")) ? 2 : 3;
        this.x = x;
        this.y = y;
        this.orientation = orientation;
        this.colorString = color;

        this.colorMap = new HashMap<>();

        this.colorMap.put("red", Color.red);
        this.colorMap.put("lime", Color.decode("0x66FF66"));
        this.colorMap.put("purple", Color.decode("0x660198"));
        this.colorMap.put("orange", Color.decode("0xFF6600"));
        this.colorMap.put("blue", Color.decode("0x003EFF"));
        this.colorMap.put("yellow", Color.yellow);
        this.colorMap.put("lightblue", Color.decode("0x66CCFF"));
        this.colorMap.put("aqua", Color.decode("0x66CCCC"));
        this.colorMap.put("violet", Color.decode("0xBF5FFF"));
        this.colorMap.put("pink", Color.decode("0xFF00FF"));
        this.colorMap.put("black", Color.black);
        this.colorMap.put("camo", Color.decode("0x996633"));
        this.colorMap.put("green", Color.decode("0x009933"));

        this.color = this.colorMap.get(color);

        if (this.color == null)
        {
            throw new VehicleConstructorError(color + " is not a valid color!");
        }
    }

    final private String type;
    final private Color color;
    final private String colorString;
    final private HashMap<String, Color> colorMap;
    final private int length;
    final private String orientation;
    private int x;
    private int y;

    public String type()
    {
        return this.type;
    }

    public Color color()
    {
        return this.color;
    }

    public String colorString()
    {
        return this.colorString;
    }

    public int length()
    {
        return this.length;
    }

    public int x()
    {
        return this.x;
    }

    public int y()
    {
        return this.y;
    }
    
    public String orientation()
    {
        return this.orientation;
    }
    
    public String dynamicInfo()
    {
        String answer = "";
            
        if (this.orientation.equals("h"))
        {
            answer += this.x;
        }
        else
        {
            answer += this.y;
        }
        
        return answer;
    }

    /**
     * Move this vehicle to the left one space.
     * @return whether the vehicle was successfully moved
     * @throws InvalidMovementException thrown when a vertical vehicle is moved
     * left
     */
    public boolean left()
        throws InvalidMovementException
    {
        if (orientation.equals("h"))
        {
            if (x > 0)
            {
                x--;
                return true;
            }
            else
            {
                return false;
            }
        }
        else
        {
            String msg = "This vehicle is not oriented that way!";
            throw new InvalidMovementException(msg);
        }
    }

    /**
     * Moves the vehicle a number of spaces to the left.
     * @param spaces number of spaces to move
     * @return whether or not the vehicle was able to be moved the full 
     * distance.
     * @throws InvalidMovementException thrown when a vertical vehicle is moved
     * left
     */
    public boolean left(int spaces)
        throws InvalidMovementException
    {
        for (int i = 0; i < spaces; i++)
        {
            if (!left())
            {
                return false;
            }
        }

        return true;
    }

    /**
     * Moves this vehicle to the right.
     * @return whether or not the operation succeeded
     * @throws InvalidMovementException thrown when a vertical vehicle is moved
     * right
     */
    public boolean right()
        throws InvalidMovementException
    {
        if (orientation.equals("h"))
        {
            if (x + length < 6)
            {
                x++;
                return true;
            }
            else
            {
                return false;
            }
        }
        else
        {
            String msg = "This vehicle is not oriented that way!";
            throw new InvalidMovementException(msg);
        }
    }

    /**
     * Moves this vehicle a number of spaces to the right.
     * @param spaces number of spaces to move
     * @return whether the operation succeeded fully or not
     * @throws InvalidMovementException thrown when a vertical vehicle is moved
     * right
     */
    public boolean right(int spaces)
        throws InvalidMovementException
    {
        for (int i = 0; i < spaces; i++)
        {
            if (!right())
            {
                return false;
            }
        }

        return true;
    }

    public boolean up()
        throws InvalidMovementException
    {
        if (orientation.equals("v"))
        {
            if (y > 0)
            {
                y--;
                return true;
            }
            else
            {
                return false;
            }
        }
        else
        {
            String msg = "This vehicle is not oriented that way!";
            throw new InvalidMovementException(msg);
        }
    }

    public boolean up(int spaces)
        throws InvalidMovementException
    {
        for (int i = 0; i < spaces; i++)
        {
            if (!up())
            {
                return false;
            }
        }

        return true;
    }

    public boolean down()
        throws InvalidMovementException
    {
        if (orientation.equals("v"))
        {
            if (y + length < 6)
            {
                y++;
                return true;
            }
            else
            {
                return false;
            }
        }
        else
        {
            String msg = "This vehicle is not oriented that way!";
            throw new InvalidMovementException(msg);
        }
    }

    public boolean down(int spaces)
        throws InvalidMovementException
    {
        for (int i = 0; i < spaces; i++)
        {
            if (!down())
            {
                return false;
            }
        }

        return true;
    }
}
