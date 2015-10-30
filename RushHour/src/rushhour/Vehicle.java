package rushhour;

import java.awt.Color;

public class Vehicle
{
    public Vehicle(Type type, Color color, int x, int y, Orientation orientation)
    {
        this.type = type;
        this.color = color;
        this.position = new Position(x, y);
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
    private Position position;
    private Orientation orientation;
    
    public enum Orientation
    {
        horizontal,
        vertical
    }
    
    public String getTypeString()
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
    
    public Type getType()
    {
        return this.type;
    }
    
    public Color color()
    {
        return this.color;
    }
    
    public int getLength()
    {
        return this.length;
    }
    
    public int x()
    {
        return this.position.x();
    }
    public int y()
    {
        return this.position.y();
    }
    public Orientation orientation()
    {
        return this.orientation;
    }
    
    
    public boolean left() throws InvalidMovementException
    {
        if (orientation == Orientation.horizontal)
        {
            if (position.x() > 0)
            {
                position.x(position.x() - 1);
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
            if (position.x() + length < 5)
            {
                position.x(position.x() + 1);
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
            if (position.y() < 5)
            {
                position.y(position.y() + 1);
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
            if (position.y() + length < 5)
            {
                position.y(position.y() - 1);
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
