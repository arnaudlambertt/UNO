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
    public void botPlay(Game g, char priorityColor)
    {
        super.botPlay(g, priorityColor);
        g.playerIndexIncrementation(true);
        g.playerDraw(4);
    }

}
