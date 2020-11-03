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
        loadImages();
        loadWindow();
    }
    
    final public void loadImages()
    {
        cardImages = new ArrayList<>();

        cardImages.add(loadImage("sprites/deck.png"));
        cardImages.add(loadImage("sprites/wildCard.png"));
        cardImages.add(loadImage("sprites/wildDrawCard.png"));
        
        char[] letters = {'r','g','b','y'};
        
        for(char i : letters )
        {
            for(int j = 0; j < 10; ++j)
                cardImages.add(loadImage("sprites/" + i + j + ".png"));
            
            cardImages.add(loadImage("sprites/" + i + 'd' + ".png"));
            cardImages.add(loadImage("sprites/" + i + 'r' + ".png"));
            cardImages.add(loadImage("sprites/" + i + 's' + ".png"));
        }
    }
    
    public BufferedImage[] loadImage(String source)
    {
        BufferedImage bi[] = new BufferedImage[2];
        try
        {
            Image image = ImageIO.read(new File(source));
            bi[0] = new BufferedImage(90, 138, BufferedImage.TYPE_INT_RGB);
            bi[0].getGraphics().drawImage(image.getScaledInstance(90, 138, java.awt.Image.SCALE_SMOOTH), 0, 0, null);

            bi[1] = new BufferedImage(180, 276, BufferedImage.TYPE_INT_RGB);
            bi[1].getGraphics().drawImage(image.getScaledInstance(180, 276, java.awt.Image.SCALE_SMOOTH), 0, 0, null);
        } catch (IOException e)
        {
            System.out.println("Cannot load " + source);
        }
        return bi;
    }

    private void loadWindow()
    {
        window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setTitle("Uno!");
        window.setSize(1600, 900);
        window.setExtendedState(JFrame.MAXIMIZED_BOTH);
        window.setLocationRelativeTo(null);
        
        userPanels = new JPanel[4];
        
        for(int i = 0; i < 4; ++i)
            userPanels[i] = new JPanel(new GridBagLayout());
  
        JPanel centralPanel = new JPanel();
        deckPanel = new JPanel();

        JScrollPane [] scrollPanes = {
            new JScrollPane (userPanels[0],  ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER),
            new JScrollPane (userPanels[1],  ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER),
            new JScrollPane (userPanels[2],  ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER),
            new JScrollPane (userPanels[3],  ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER)
        };
  
        scrollPanes[0].setHorizontalScrollBar(new JScrollBar(JScrollBar.HORIZONTAL){ @Override public boolean isVisible(){return true;}});
        scrollPanes[1].setVerticalScrollBar(new JScrollBar(JScrollBar.VERTICAL){ @Override public boolean isVisible(){return true;}});
        scrollPanes[2].setHorizontalScrollBar(new JScrollBar(JScrollBar.HORIZONTAL){ @Override public boolean isVisible(){return true;}});
        scrollPanes[3].setVerticalScrollBar(new JScrollBar(JScrollBar.VERTICAL){ @Override public boolean isVisible(){return true;}});

        for(JScrollPane i : scrollPanes)
        {
            i.getHorizontalScrollBar().setUnitIncrement(30);
            i.getVerticalScrollBar().setUnitIncrement(30);
            i.setBorder(createEmptyBorder());
        }           

        centralPanel.setLayout(new BoxLayout(centralPanel, BoxLayout.Y_AXIS));
        deckPanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.NONE;

        centralPanel.add(scrollPanes[2]);
        centralPanel.add(deckPanel);
        centralPanel.add(scrollPanes[0]);
        
        window.setVisible(true);
    }
}
