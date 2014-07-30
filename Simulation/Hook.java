import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Hook for the fishingline and reels in small fish
 * 
 * @author Jason Fok
 * @version March 2014
 */
public class Hook extends Actor
{
    /**
     * Checks if the Hook is touching a SmallFish
     * @return boolean True if the hook is touching a fish
     */
    public boolean checkFish()
    {
        SmallFish sf = (SmallFish)getOneIntersectingObject(SmallFish.class);
        if (sf != null)
        {
            return true;            
        }
        else
        {
            return false;
        }
    }

    /**
     * Returns the reference of the fish or nothing
     * @return Actor Returns reference of the fish if a fish is touching the hook
     */
    public Actor returnFish() 
    {
        if (checkFish() == true) 
        {
            SmallFish sf = (SmallFish)getOneIntersectingObject(SmallFish.class);
            return sf;
        }
        else 
        {
            return null;
        }
    }
}
