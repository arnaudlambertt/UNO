/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uno.project;

import javax.swing.JOptionPane;

/**
 *
 * @author Arnaud
 */
public class WildDrawCard extends WildCard
{

    public WildDrawCard(int id)
    {
        super(id);
    }
    
    @Override
    public void play(Game g)
    {
        super.play(g);      
        g.playerIndexIncrementation(true);
        g.playerDraw(4);
    }
    
    @Override
    public void botPlay(Game g, ArrayListWithPanel<CardButton> cards)
    {
        String selectedColor = autoSelectColor(cards);
        
        g.playCard(this);
        g.playerIndexIncrementation(true);
        g.playerDraw(4);
        
        JOptionPane.showMessageDialog(null, selectedColor +" selected");
    }
    
}
