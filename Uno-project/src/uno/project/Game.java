/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uno.project;

import java.awt.BorderLayout;
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
    private ArrayList<BufferedImage[]> cardImages;
    private ArrayList<Player> players;
    private HiddenDeck hiddenDeck;
    private RevealedDeck revealedDeck;
    private JFrame window;
    private int playerIndex;
    private int currentTurnIndex;
    private int playerIterator;
    private int playerCount;

    public Game()
    {
        init();
    }
    
    public void init()
    {
        loadImages();
        createWindow();
        JPanel[] panels = createPanels();
        createUsers(panels);
        createDecks(panels);
        play();
    }

    public void loadImages()
    {
        cardImages = new ArrayList<>();

        cardImages.add(loadImage("sprites/deck.png"));
        cardImages.add(loadImage("sprites/wildCard.png"));
        cardImages.add(loadImage("sprites/wildDrawCard.png"));

        char[] letters =
        {
            'r', 'g', 'b', 'y'
        };

        for (char i : letters)
        {
            for (int j = 0; j < 10; ++j)
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

    public void createWindow()
    {
        window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setTitle("Uno!");
        window.setSize(1600, 900);
        window.setExtendedState(JFrame.MAXIMIZED_BOTH);
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }

    public void repaint()
    {
        window.revalidate();
        window.repaint();
    }

    public JPanel[] createPanels()
    {
        JPanel[] panels =
        {
            new JPanel(new GridBagLayout()),
            new JPanel(new GridBagLayout()),
            new JPanel(new GridBagLayout()),
            new JPanel(new GridBagLayout()),
            new JPanel(new GridBagLayout())
        };

        JPanel centralPanel = new JPanel();

        JScrollPane[] scrollPanes =
        {
            new JScrollPane(panels[0], ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER),
            new JScrollPane(panels[1], ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER),
            new JScrollPane(panels[2], ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER),
            new JScrollPane(panels[3], ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER)
        };

        scrollPanes[0].setHorizontalScrollBar(new JScrollBar(JScrollBar.HORIZONTAL)
        {
            @Override
            public boolean isVisible()
            {
                return true;
            }
        });
        scrollPanes[1].setVerticalScrollBar(new JScrollBar(JScrollBar.VERTICAL)
        {
            @Override
            public boolean isVisible()
            {
                return true;
            }
        });
        scrollPanes[2].setHorizontalScrollBar(new JScrollBar(JScrollBar.HORIZONTAL)
        {
            @Override
            public boolean isVisible()
            {
                return true;
            }
        });
        scrollPanes[3].setVerticalScrollBar(new JScrollBar(JScrollBar.VERTICAL)
        {
            @Override
            public boolean isVisible()
            {
                return true;
            }
        });

        for (JScrollPane i : scrollPanes)
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

    public void createDecks(JPanel[] panels)
    {
        int id = 0;
        CardButton h = new CardButton(cardImages, -1, new NumberCard(0, '0', 'b')); //carte tempo
        hiddenDeck = new HiddenDeck(h);

        CardButton r = new CardButton(cardImages, -1, new NumberCard(0, '0', 'b')); //carte tempo
        revealedDeck = new RevealedDeck(r);

        panels[4].add(h);
        panels[4].add(r);

        repaint();
    }

    public void createUsers(JPanel[] panels) //4 deck
    {
        playerCount = Integer.max(2, Integer.min(4, Integer.parseInt(JOptionPane.showInputDialog("Enter player count (2 to 4) :"))));
        players = new ArrayList<>();

        for (int i = 0; i < playerCount; ++i)
        {
            String name = JOptionPane.showInputDialog("Enter name for player " + (i + 1) + " :");
            int panelId = (playerCount == 2 && i == 1 ? 2 : i);
            players.add(new Player(name, panels[panelId], panelId, cardImages));
        }
    }

    public void play()
    {
        distribution();
        
        while(players.size() > 1)
        {
            this.currentTurnIndex = playerIndex;
            players.get(playerIndex).turn(this);
            playerIndexIncrementation();
            
            if(hiddenDeck.isEmpty() && !revealedDeck.isEmpty())
                hiddenDeck.setDeckShuffle(revealedDeck.getDeck());
            
            repaint();
        }
        
        end();
    }

    public void distribution()
    {
        this.playerIndex = getActivePlayerCount()-1;
        this.playerIterator = 1;

        for(int i = 0; i < 7; ++i)
            for(int j = 0; j < players.size(); ++j)
                players.get(j).draw(hiddenDeck.getTopCard());
        
        repaint();

        firstCard();
        
        repaint();
    }
    
    public void playerIndexIncrementation()
    {
        playerIndex += playerIterator;
        
        if(playerIndex >= players.size())
            playerIndex = 0;
        
        else if(playerIndex < 0)
            playerIndex = players.size()-1;
    }
    
    public void reverse()
    {
        playerIterator = -playerIterator;
    }

    public Card getHiddenDeckTop()
    {
        if(hiddenDeck.isEmpty())
        {
            if(!revealedDeck.isEmpty())
                hiddenDeck.setDeckShuffle(revealedDeck.getDeck());
            else
                return null;
        }
            
        return hiddenDeck.getTopCard();
    }
    
    public Card getRevealedDeckTop()
    {
        return revealedDeck.getTopCard();
    }
    
    public void playerDraw(int amount)
    {
        for(int i = 0; i < amount; ++i)
        {
            players.get(playerIndex).draw(getHiddenDeckTop());
        }
    }
    
    public boolean hiddenDeckClicked()
    {
        return hiddenDeck.isClicked();
    }
    
    public void hiddenDeckDisable()
    {
        hiddenDeck.disable();
    } 
    public void playCard(Card c)
    {
        revealedDeck.addCard(c);
    }
    
    public void removePlayer()
    {
        repaint();
        if(playerCount == getActivePlayerCount())
            JOptionPane.showMessageDialog(null, players.get(currentTurnIndex).getName() + " won !");
        
        players.remove(currentTurnIndex);
        reverse();
        playerIndexIncrementation();
        reverse();
    }
    
    public void firstCard()
    {
        hiddenDeck.firstCard(this);
        playerIndexIncrementation();
    }
    
    public void end()
    {
        players.get(0).setRevealed(true);
        JOptionPane.showMessageDialog(null, "Unfortunately, " + players.get(0).getName() + " lost.\nThank you for playing Uno!");
        window.dispose();
    }

    public int getActivePlayerCount()
    {
        return players.size();
    }
}
