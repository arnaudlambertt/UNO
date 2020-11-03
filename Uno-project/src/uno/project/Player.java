/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uno.project;

import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import java.util.ArrayList;

/**
 *
 * @author Utilisateur
 */
public class Player
{
    private final String name;
    private final ArrayListWithPanel<CardButton> cards;
    
    public Player(String name, JPanel panel, int panelId, ArrayList<BufferedImage[]> cardImages)
    {
        this.name = name;
        this.cards = new ArrayListWithPanel<>(panel,panelId,cardImages);
    }
    
     /////////////////////TEMPO//////////////////////////////
    public void test(ArrayList<BufferedImage[]> cardImages)
    {
        for (int i = 0; i < 10; ++i)
        {
            cards.add(new NumberCard(0,'0','b'));
        }
    }
    
    public void turn(Game g)
    {
        
    }
}
