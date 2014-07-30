import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Person is an abstract Greenfoor actor that is the parent class of three types of people: Diver, Hunter, and Fisherman.
 * This class contains the variables and methods that each of the subclasses share.
 * <p>
 * People are essential to the ecosystem, as they all act as shark food, and the fisherman fishes the small fish out of the water.
 * The hunters also kill any fish with their spears.
 * 
 * @author Terrence Hung and Jason Fok
 * @version March 23, 2014
 */
public abstract class Person extends Actor
{
    //declare variables
    protected int speed;
    protected GreenfootSound scream = new GreenfootSound("scream.wav");
    //declare abstract methods
    protected abstract void checkAndRemove();
}
