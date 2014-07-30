import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Button is an abstract Greenfoot Actor that is the parent class of two different types of buttons: a Plus 
 * button and a Minus button. Both the Plus and the Minus button is responsible for doing the same action, 
 * (changing the value of the plant spawn rate), so the code for doing this action is in the button superclass.
 * 
 * @author Douglas Wong
 * @version March 2014
 */
public class Button extends Actor
{
    //Protected variable for the specific buttons
    protected int differenceSpawnRate;
    /**
     * Updates the plant's spawn rate if the button is pushed
     */
    protected void updatePlantSpawnRate() {
        //Checks whether or not the button is pushed
        if (Greenfoot.mouseClicked(this)){ 
            //Calls the method changePlantSpawnRate to change the plant spawn rate in the world
            ((EcoWorld)getWorld()).changePlantSpawnRate (differenceSpawnRate);
        }
    }
}
