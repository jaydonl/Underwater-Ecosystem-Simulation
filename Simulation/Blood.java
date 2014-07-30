import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Blood that fades away over time.
 * 
 * @author Jason Fok and Terrence Hung
 * @version March 23, 2014
 */
public class Blood extends Actor
{
    //declare variables
    private int counter;
    private GreenfootImage myImage;
    
    /**
     * Constructs blood.
     */
    public Blood()
    {
        counter = 255; //set counter to 255
        myImage = this.getImage(); //set myImage to the image of the blood
    }

    /**
     * Makes the transparency of the blood depreciate after each act and removes 
     * the blood from the world after 255 acts
     */
    public void act()
    {
        myImage.setTransparency(counter); //set transparency according to counter
        counter--; //subract one from counter
        if (counter == 0) //if counter is 0, remove blood from world
        {
            getWorld().removeObject(this);
        }
    }
}
