import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Boat that follows the Fisherman and Hunter as a means of transportation 
 * 
 * @author Jason Fok
 * @version March 2014
 */
public class FishingBoat extends Actor
{
    private int speed;  
    private boolean boatSinked;
    
    /**
     * Constructor for a FishingBoat
     * @param speed Speed of the boat
     */    
    public FishingBoat(int speed)
    {
        this.speed = speed;
    }

    /**
     * Controls the boat's movements
     */
    public void act() 
    {
        //Move the boat forward
        move (speed);
        //When the boat sinks, stop the boat and rotate it 180 degrees and make it move down 
        //gradually
        if (boatSinked)
        {
            speed = 0;
            setRotation (180);
            if (getY() < 630) 
            {
                setLocation (getX(), getY() + 2);
            }
            else
            {
                getWorld().removeObject(this); 
            }
        }
    }

    /**
     * Mutator that changes the speed of the Boat
     * @param int Speed of the boat
     */
    public void setSpeed(int speed)
    {
        this.speed = speed;
    }	

    /**
     * Checks if the boat is sinked
     * @Return boolean True if boat is sinked
     */
    public boolean checkIfBoatSank()
    {
        return boatSinked;
    }

    /**
     * Mutator to make the boat sink
     */
    public void sinkBoat()
    {
        boatSinked = true;
    }
}
