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
    private JButton showDominant;
    private GUI gui;
    private AtomicBoolean paused;
    private Thread threadObject;
    public boolean showingDominant = false;
    private JEditorPane genotypeText;
    private RectangularMap spiedMap;

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
        pause.setPreferredSize(new Dimension(64,42));

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
        speed.setPreferredSize(new Dimension(64,42));

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
        slow.setPreferredSize(new Dimension(64,42));

        showDominant = new JButton();
        showDominant.addActionListener(new ButtonListener(this.gui){
            @Override
            public void actionPerformed(ActionEvent e){
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        if(paused.get()) {
                            if(!showingDominant) {
                                gui.remove(gui.showedMap);
                                gui.remove(gui.showedMap1);
                                GUIMap showedMap = new DominantGUIMap(gui.map, paused, gui);
                                GUIMap showedMap1 = new DominantGUIMap(gui.map1, paused, gui);
                                gui.add(showedMap, BorderLayout.WEST);
                                gui.add(showedMap1, BorderLayout.CENTER);
                                gui.setVisible(true);
                                showingDominant = !showingDominant;
                            }
                            else{
                                gui.remove(gui.showedMap);
                                gui.remove(gui.showedMap1);
                                GUIMap showedMap = new GUIMap(gui.map, paused, gui);
                                GUIMap showedMap1 = new GUIMap(gui.map1, paused, gui);
                                gui.add(showedMap, BorderLayout.WEST);
                                gui.add(showedMap1, BorderLayout.CENTER);
                                gui.setVisible(true);
                                showingDominant = !showingDominant;
                            }
                        }
                    }
                });
            }
        });
        showDominant.setPreferredSize(new Dimension(64,42));
        showDominant.setText("Mark D");
        showDominant.setMargin(new Insets(0,0,0,0));

        genotypeText = new JEditorPane("text/html", "");
        genotypeText.setEditable(false);
        genotypeText.setVisible(false);
        updateGenotypeText();

        this.add(slow);
        this.add(pause);
        this.add(speed);
        this.add(showDominant);
        this.add(genotypeText);
    }

    public void updateGenotypeText(){
        spiedMap = gui.map;
        if(gui.map.spiedAnimal == null) {
            spiedMap = gui.map1;
            if (gui.map1.spiedAnimal == null)
                return;
        }
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                int[] values = spiedMap.spiedAnimal.animal.getGenotype().values;
                String text ="";
                text += "<table><tr>";
                for(int i = 0; i < 32; i++){
                    text += "<th>" + values[i] + "</th>";
                }
                text += "</tr></table>";
                genotypeText.setText(text);
                genotypeText.setVisible(true);
            }
        });
    }

}
