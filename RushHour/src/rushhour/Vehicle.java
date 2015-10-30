
package rushhour;

import java.awt.Color;
import java.util.HashMap;

public class Vehicle
{

    public Vehicle(Type type, String color, int x, int y, Orientation orientation)
    {
        this.type = type;
        this.x = x;
        this.y = y;
        this.orientation = orientation;
        this.length = (type == Type.car) ? 2 : 3;
        
        this.colorString = color;
        
        this.colorMap = new HashMap<>();
        
        this.colorMap.put("red", Color.red);
        this.colorMap.put("lime", Color.decode("0x66FF33"));
        this.colorMap.put("purple", Color.decode("0xCC00CC"));
        this.colorMap.put("orange", Color.orange);
        this.colorMap.put("blue", Color.blue);
        this.colorMap.put("yellow", Color.yellow);
        this.colorMap.put("lightblue", Color.decode("0x66CCFF"));
        this.colorMap.put("aqua", Color.cyan);
        
        this.color = this.colorMap.get(color);
    }
    
    public enum Type
    {
        car,
        truck
    }
    public enum Orientation
    {
        horizontal,
        vertical
    }

    final private Type type;
    final private Color color;
    final private String colorString;
    final private HashMap<String, Color> colorMap;
    final private int length;
    final private Orientation orientation;
    private int x;
    private int y;

    public String typeString()
    {
        return (this.type == Type.car) ? "car" : "truck";
    }

    public Type type()
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

    public Orientation orientation()
    {
        return this.orientation;
    }

    public boolean left()
        throws InvalidMovementException
    {
        if (orientation == Orientation.horizontal)
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
    
    public boolean left(int spaces) throws InvalidMovementException
    {
        for (int i = 0; i < spaces; i++)
        {
            if(!left())
            {
                return false;
            }
        }
        
        return true;
    }

    public boolean right()
        throws InvalidMovementException
    {
        if (orientation == Orientation.horizontal)
        {
            if (x + length < 5)
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
    
    public boolean right(int spaces) throws InvalidMovementException
    {
        for (int i = 0; i < spaces; i++)
        {
            if(!right())
            {
                return false;
            }
        }
        
        return true;
    }

    public boolean up()
        throws InvalidMovementException
    {
        if (orientation == Orientation.vertical)
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
    
    public boolean up(int spaces) throws InvalidMovementException
    {
        for (int i = 0; i < spaces; i++)
        {
            if(!up())
            {
                return false;
            }
        }
        
        return true;
    }

    public boolean down()
        throws InvalidMovementException
    {
        if (orientation == Orientation.vertical)
        {
            if (y + length < 5)
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
    
    public boolean down(int spaces) throws InvalidMovementException
    {
        for (int i = 0; i < spaces; i++)
        {
            if(!down())
            {
                return false;
            }
        }
        
        return true;
    }
}
