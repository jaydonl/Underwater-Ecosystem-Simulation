import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Black rectangle that will cover the screen when it is night time. Completely transparent when spawned.
 * 
 * @author Terrence Hung
 * @version March 19, 2014
 */
public class Night extends Actor
{
    //declare variables
    private GreenfootImage myImage;
    private int transparency;
    
    /**
     * Constructs black screen, with it being completely transparent.
     */
    public Night()
    {
        myImage = this.getImage(); //get the image of the actor
        transparency = 0; //make transparency 0
        myImage.setTransparency(transparency); //set transparency to 0
    }

    /**
     * Changes the actor's transparency and updates it.
     * 
     * @param change How much the transparency will change by.
     */
    public void changeTransparency(int change)
    {
        transparency += change; //change the transparency according to parameter
        myImage.setTransparency(transparency); //set the new transparency level
    }
    
    /**
     * Gets the transparency level.
     * 
     * @return int The current transparency level of the actor.
     */
    public int getTransparency()
    {
        return transparency;
    }
}
