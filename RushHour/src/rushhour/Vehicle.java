package rushhour;

import java.awt.Color;

public class Vehicle
{
    public Vehicle(Type type, Color color, int x, int y, Orientation orientation)
    {
        this.type = type;
        this.color = color;
        this.x = x;
        this.y = y;
        this.orientation = orientation;
        
        if (type == Type.car)
        {
            this.length = 2;
        }
        else
        {
            this.length = 3;
        }
    }
    
    public enum Type {
        car,
        truck
    }

    public enum Direction {
        up,
        down,
        left,
        right
    }
    
    final private Type type;
    final private Color color;
    final private int length;
    private Orientation orientation;
    private int x;
    private int y;
    
    public enum Orientation
    {
        horizontal,
        vertical
    }
    
    public String typeString()
    {
        if (this.type == Type.car)
        {
            return "car";
        }
        else
        {
            return "truck";
        }
    }
    
    public Type type()
    {
        return this.type;
    }
    
    public Color color()
    {
        return this.color;
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
    
    
    public boolean left() throws InvalidMovementException
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
    
    public boolean right() throws InvalidMovementException
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
    
    public boolean up() throws InvalidMovementException
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
    
    public boolean down() throws InvalidMovementException
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
}
