/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uno.project;

/**
 *
 * @author Arnaud
 */
public class WildDrawCard extends WildCard
{

    public WildDrawCard()
    {
        super();
    }
    
    // get the file name for the sprite
    @Override
    public String getFileName()
    {
        return "sprites/wildDrawCard.png";
    }
    
    @Override
    public void play(Game g)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
