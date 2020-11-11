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
public class DrawCard extends SkipCard
{

    public DrawCard(int id, char color)
    {
        super(id, 'd', color);
    }

    @Override
    public void play(Game g)
    {
        super.play(g);
        g.playerDraw(2);
    }
}
