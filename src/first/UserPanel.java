package first;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class UserPanel extends JPanel {
    private JButton pause;
    private JButton speed;
    private JButton slow;
    private GUI gui;

    public UserPanel(GUI gui) throws IOException {
        this.gui = gui;

        pause = new JButton();
        pause.addActionListener(new ButtonListener(this.gui) {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        pause.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/images/pause.png"))));

        speed = new JButton();
        speed.addActionListener(new ButtonListener(this.gui) {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(new Runnable(){
                    public void run(){
                        if(gui.delay > 50) {
                            if (gui.delay > 1200) {
                                gui.delay -= 200;
                            } else if (gui.delay > 800) {
                                gui.delay -= 300;
                            } else
                                gui.delay /= 2;
                        }
                    }
                });
            }
        });
        speed.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/images/speedup1.png"))));

        slow = new JButton();
        slow.addActionListener(new ButtonListener(this.gui) {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(new Runnable(){
                    public void run(){
                        if(gui.delay < 1600){
                            if(gui.delay < 800)
                             gui.delay *= 2;
                            else if(gui.delay < 1200)
                                gui.delay += 300;
                            else
                                gui.delay += 200;
                        }
                    }
                });
            }
        });
        slow.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/images/slowdown.png"))));

        this.add(slow);
        this.add(pause);
        this.add(speed);
    }
}
