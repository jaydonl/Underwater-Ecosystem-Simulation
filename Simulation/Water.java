import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Water is a Greenfoot actor that covers the water portion of the world.
 * Water is set to be transparent, giving the world a more authentic underwater look.
 * 
 * @author Jaydon Lau, Terrence Hung
 * @version March 2014
 */
public class Water extends Actor
{
    private GreenfootImage myImage;

    /**
     * Creates a Water object that is slightly transparent. 
     */
    public Water() {
        myImage = this.getImage();
        myImage.setTransparency (75);
    }

        /**
     * Water will move across the world, and will reset the location once it has reached the edge of the world.
     */
    public void act() 
    {
        //move background right 7 pixels at a time
        setLocation (getX()-1, getY());
        // if background has moved all the way right, then reset X position to 0 so it can move right again
        if (getX() <= 0) {
            setLocation (960,getY());
        }
    }    
}
