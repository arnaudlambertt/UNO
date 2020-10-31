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
    public SkipCard(char color)
    {
        super('s', color);
    }
    
    @Override
    public void play(Game g)
    {
    }
}
