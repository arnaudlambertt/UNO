/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uno.project;

/**
 *
 * @author Utilisateur
 */
public class ReverseCard extends Card
{
    public ReverseCard(int id, char color)
    {
        super(id, 'r', color);
    }
    
    @Override
    public void play(Game g)
    {
        super.play(g);
        
        if(g.getActivePlayerCount() == 2)
            g.playerIndexIncrementation();
        else    
            g.reverse();
    }
}
