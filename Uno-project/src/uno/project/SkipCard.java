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
public class SkipCard extends Card
{

    public SkipCard(int id, char symbol, char color)
    {
        super(id, symbol, color);
    }

    @Override
    public void play(Game g)
    {
        super.play(g);
        g.playerIndexIncrementation(true);
    }
}
