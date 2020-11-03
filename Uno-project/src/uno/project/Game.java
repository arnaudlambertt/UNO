/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uno.project;
import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.util.ArrayList;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Stack;
import javax.imageio.ImageIO;
import static javax.swing.BorderFactory.createEmptyBorder;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
/**
 *
 * @author Utilisateur
 */
public final class Game
{
    private static ArrayList<BufferedImage[]> cardImages;
    private static ArrayList<Player> players;
    private static HiddenDeck hiddenDeck;
    private static RevealedDeck revealedDeck;
    private static JFrame window;
    
    private Game()
    {
        
    }
    
    public static void init()
    {
        loadImages();
        createWindow();
        JPanel[] panels = createPanels();
        createHiddenDeck(panels);
        createUsers(panels);
    }
    
    public static void loadImages()
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
    
    public static BufferedImage[] loadImage(String source)
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

    public static void createWindow()
    {
        window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setTitle("Uno!");
        window.setSize(1600, 900);
        window.setExtendedState(JFrame.MAXIMIZED_BOTH);
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }
    
    public static void repaint()
    {
        window.revalidate();
        window.repaint();
    }
    
    public static JPanel[] createPanels()
    {
        JPanel[] panels = { new JPanel( new GridBagLayout()),
                                new JPanel( new GridBagLayout()), 
                                new JPanel( new GridBagLayout()), 
                                new JPanel( new GridBagLayout()),
                                new JPanel( new GridBagLayout())
        };
  
        JPanel centralPanel = new JPanel();
     

        JScrollPane [] scrollPanes = {
            new JScrollPane (panels[0],  ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER),
            new JScrollPane (panels[1],  ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER),
            new JScrollPane (panels[2],  ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER),
            new JScrollPane (panels[3],  ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER)
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
        
        centralPanel.add(scrollPanes[2]);
        centralPanel.add(panels[4]);
        centralPanel.add(scrollPanes[0]);
        
        window.add(scrollPanes[1], BorderLayout.WEST);
        window.add(scrollPanes[3], BorderLayout.EAST);
        window.add(centralPanel, BorderLayout.CENTER);
        repaint();
        
        return panels;
    }
    
    public static void createHiddenDeck(JPanel[] panels)
    {
        int id = 0;
        CardButton h = new CardButton(cardImages, -1,new NumberCard(0,'0','b'));  
        hiddenDeck = new HiddenDeck(h);
               
        CardButton r = new CardButton(cardImages, -1,hiddenDeck.getTopCard());
        r.setVisible(false);
        revealedDeck = new RevealedDeck(r);
        
        panels[4].add(h);
        panels[4].add(r);
        
        repaint();
    }
    
    public static void createUsers(JPanel[] panels)//4 deck
    {
        
        int playerCount = Integer.max(2, Integer.min(4, Integer.parseInt(JOptionPane.showInputDialog("Enter player count (2 to 4) :"))));
        players = new ArrayList<>();
        
        for(int i = 0; i < playerCount; ++i)
        {
            String name = JOptionPane.showInputDialog("Enter name for player " + (i+1) +" :");
            int panelId = playerCount == 2 && i == 1 ? 2 : i;
            players.add(new Player(name, panels[panelId],panelId,cardImages));   
            
            /////TEMPO/////////////////////////////////////////////////////////////
            players.get(i).test(cardImages);
            ////////////////////////////////////////////////////////////////////////
        }
        repaint();
       
    }
    
}
