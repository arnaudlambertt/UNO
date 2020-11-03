/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uno.project;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.util.ArrayList;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import static javax.swing.BorderFactory.createEmptyBorder;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
/**
 *
 * @author Utilisateur
 */
public class Game
{
    private ArrayList<BufferedImage[]> cardImages;
    private ArrayList<Player> player;
    private HiddenDeck hiddenDeck;
    private RevealedDeck revealedDeck;
    private JFrame window;
    private JPanel [] userPanels;
    private JPanel deckPanel;
    
    public Game()
    {
        loadWindow();
        loadImages();
    }
    
    public void play()
    {
        for(int i=0; i<76; i++)
        {            
            Card lastCard = hiddenDeck.revealLastCard();
            System.out.println(lastCard.getSymbol() +" "+ lastCard.getColor());
        }
    }
}
