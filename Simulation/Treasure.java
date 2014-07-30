import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Creates a treasure chest that gets spawned in the world. It will follow the diver if they are touching.
 * 
 * @author Terrence Hung
 * @version March 23, 2014
 */
public class Treasure extends Actor
{
    //declare variables
    private static boolean inWorld;
    private static int xCoordinate;
    private static int yCoordinate;
    private static boolean followingDiver;
    private static boolean moveLeft;
    private GreenfootSound chaching = new GreenfootSound("cha ching.mp3");
    /**
     * Constructs a treasure chest that will be spawned in the world.
     */
    public Treasure()
    {
        inWorld = false; //set inWorld to false
        followingDiver = false; //set followingDiver to false
    }

    /**
     * Make the treasure chest follow the diver if they are in contact, and remove it from the world and play a sound when it reaches the edge.
     */
    public void act()
    {
        //update coordinates to static variables
        xCoordinate = this.getX();
        yCoordinate = this.getY();
        //if it is following diver then update coordinates according to diver's position
        if (followingDiver)
        {
            if (moveLeft) //if moving left, set position to left and below diver
                setLocation(Diver.getXCoordinate() - 45, Diver.getYCoordinate() + 22);
            else if (!(moveLeft)) //if moving right, set position to right and below diver
                setLocation(Diver.getXCoordinate() + 45, Diver.getYCoordinate() + 22);
        }
        checkEdgeOfWorld(); //check if treasure is at the edge of the world
        //if treasure chest is not following diver anymore and is not at the bottom, make it sink
        //in case treasure will appear to be below world, move treasure to bottom of world
        if (inWorld && !(followingDiver) && getY() > 616)
            setLocation(getX(), 616);
        else if (inWorld && !(followingDiver) && getY() != 616) //go down if not at bottom
            setLocation(getX(), getY() + 1);   
        //if inWorld is false, remove treasure from world
        if (!(inWorld))
        {   
            ((EcoWorld)getWorld()).changeTreasuresCaught(1); //add 1 to treasuresCaught in world
            chaching.play(); //play chaching sound effect
            getWorld().removeObject(this); //remove object from world
        }
    }

    /**
     * Changes the value of the boolean inWorld.
     * 
     * @param trueOrFalse Value that boolean inWorld will be changed to.
     */
    public static void setInWorld(boolean trueOrFalse)
    {
        inWorld = trueOrFalse;
    }

    /**
     * Gets the value of the boolean inWorld.
     * 
     * @return boolean Return true if object is still in world, false if not.
     */
    public static boolean getInWorld()
    {
        return inWorld;
    }

    /**
     * Sets the coordinates of the treasure chest.
     * 
     * @param xCoord x-coordinate of the chest.
     * @param yCoord x-coordinate of the chest.
     */
    public void setCoordinates(int xCoord, int yCoord)
    {
        xCoordinate = xCoord;
        yCoordinate = yCoord;
    }

    /**
     * Returns x-coordinate of the treasure chest.
     * 
     * @return int x-coordinate of the treasure chest.
     */
    public static int getXCoordinate()
    {
        return xCoordinate;
    }

    /**
     * Returns y-coordinate of the treasure chest.
     * 
     * @return int y-coordinate of the treasure chest.
     */
    public static int getYCoordinate()
    {
        return yCoordinate;
    }

    /**
     * Changes value of boolean followingDiver.
     * 
     * @param trueOrFalse Set true if it is touching the diver, false if not.
     */
    public static void setFollow(boolean trueOrFalse)
    {
        followingDiver = trueOrFalse;
    }

    /**
     * Sets inWorld to false if treasure chest is at the edge of the world.
     */
    private void checkEdgeOfWorld()
    {
        //check if it is touching the sides of the world
        if (getX() <= 0 || getX() >= 959)
        {
            inWorld = false;
        }
    }   

    /**
     * Makes the treasure chest move to the left or to the right of the diver.
     * 
     * @param trueOrFalse Set true if chest is to the left of diver, false if to the right.
     */
    public static void setDirection(boolean trueOrFalse)
    {
        moveLeft = trueOrFalse;
    }
}
