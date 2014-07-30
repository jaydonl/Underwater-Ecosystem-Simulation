import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.awt.Color;
/**
 * Fishing line for the fisherman and it reels in and reels out
 * 
 * @author Jason Fok 
 * @version March 2014
 */
public class FishingLine extends Actor
{
    private GreenfootImage line;   
    private int currLength;
    /**
     * Constructor for a FishingLine
     */
    public FishingLine()
    {
        line = new GreenfootImage (1, 6);
        line.setColor(Color.BLACK);
        line.fill();
        setImage(line);
    }

    /**
     * Checks to see if the line should be removed
     */
    public void act()
    {
        checkAndRemove();
    }

    /**
     * Update the length of the fishing line when it is being reeled out
     * @param currLength Current length of the FishingLine
     */
    public void updateOut (int currLength)
    {
        this.currLength = currLength;
        line.clear();
        line = new GreenfootImage (1, currLength);
        if (currLength <= 300)
        {
            this.setLocation (getX(),getY()+ 3);
        }
        line.fill();
        setImage(line);
    }

    /**
     * Update the length of the fishing line when it is being reeled in
     * @param currLength Current length of the FishingLine
     */
    public void updateIn (int currLength)
    {
        this.currLength = currLength;
        line.clear();
        line = new GreenfootImage (1, currLength);
        if (currLength >= 0)
        {
            this.setLocation (getX(),getY()- 3);
        }
        line.fill();
        setImage(line);
    }

    /**
     * Checks to see if the FishingLine should be removed
     */
    private void checkAndRemove()
    {
        if (getX() < -100 || getX() >= 959)
        {
            getWorld().removeObject(this);
        }
    }	
}
