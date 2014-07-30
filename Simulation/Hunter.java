import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Throws spears
 * 
 * @author Jason Fok 
 * @version March 2014
 */
public class Hunter extends Person
{
    private int speed;
    /**
     * Constructor for a Hunter
     * @param speed Speed of the Hunter
     */
    public Hunter (int speed)
    {
        this.speed = speed;
    }

    /**
     * Move the hunter
     */
    public void act() 
    {
        move (speed);
        checkAndRemove();
    }    

    /**
     * Set the speed of theHhunter
     * @param speed Speed of the Hunter
     */
    public void setSpeed(int speed)
    {
        this.speed = speed;
    }

    /**
     * Checks and removes the hunter when it's at the edge of the world
     */
    protected void checkAndRemove()
    {        
        if (getX() <= 0 || getX() >= 895)
        {
            getWorld().removeObject(this);
        }
    }	
}
