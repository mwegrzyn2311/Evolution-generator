package first;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

public class UserPanel extends JPanel {
    private JButton pause;
    private JButton speed;
    private JButton slow;
    private GUI gui;
    private AtomicBoolean paused;
    private Thread threadObject;

    public UserPanel(GUI gui, AtomicBoolean paused, Thread threadObject) throws IOException {
        this.paused = paused;
        this.threadObject = threadObject;
        this.gui = gui;

        pause = new JButton();
        pause.addActionListener(new ButtonListener(this.gui) {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(paused.get()){
                    paused.set(false);
                    synchronized (threadObject) {
                        threadObject.notify();
                    }
                }else {
                    paused.set(true);

                }
            }
        });
        pause.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/images/pause.png"))));

        speed = new JButton();
        speed.addActionListener(new ButtonListener(this.gui) {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(new Runnable(){
                    public void run(){
                        if(gui.delay > 30) {
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
                            if(gui.delay < 400)
                             gui.delay *= 2;
                            else if(gui.delay < 800)
                                gui.delay += 200;
                            else if(gui.delay < 1200)
                                gui.delay += 100;
                            else
                                gui.delay += 50;
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
