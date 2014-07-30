import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Spear that can kill organisms and thrown by hunter
 * 
 * @author Jason Fok 
 * @version March 2014
 */
public class Spear extends Actor
{
    private Fisherman fisherman;
    private boolean remove;

    /**
     * Constructor for a Spear
     * @param fisherman Reference to the fisherman who shares the same boat as the Hunter
     */
    public Spear (Fisherman fisherman)
    {
        this.fisherman = fisherman;
    }

    /**
     * Controls the Spears movements, kills organism if hit and check to see when it should remove
     * itself
     */
    public void act() 
    {
        move (2);
        checkHitOrganism (); 
        checkToRemove();
        if (remove)
        {
            //Tells the fisherman that the spear is removed so that the hunter
            //should throw another spear
            fisherman.spearIsRemoved();
            getWorld().removeObject(this);
        }
    }

    /**
     * Checks if the spear is in contact with an organims and kills it
     */
    public void checkHitOrganism ()
    {
        // Check collision for a organism one pixel ahead
        // of the Spear and made sure the fish is not hooked before killing it and the diver
        //is still alive before killing it
        Fish f = (Fish)getOneObjectAtOffset((this.getImage().getWidth() / 2) + 1, 0, Fish.class);
        if (f != null)
        {
            if ((f instanceof SmallFish))
            {   
                if (!((SmallFish)f).getHooked())
                {
                    f.setFishDead(); 
                }
            }
            else 
            {
                f.setFishDead(); 
            }
        }
        Diver d = (Diver)getOneObjectAtOffset((this.getImage().getWidth() / 2) + 1, 0, Diver.class);
        if (d != null && d.getAlive())
        {
            d.makeDiverSink();            
        }
    }

    /**
     * Mutator to set the boolean remove true
     */
    public void remove()
    {
        remove = true;
    }

    /**
     * Checks if the spear should be removed
     */
    public void checkToRemove()
    {       
        if (getX() <= 0 || getX() >= 958 || getY() >= 638)
        {
            remove = true;
        }
    }
}
