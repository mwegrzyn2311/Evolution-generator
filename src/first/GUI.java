package first;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

public class GUI extends JFrame{
    private Thread threadObject;
    private RectangularMap map;
    private RectangularMap map1;
    private GUIMap showedMap;
    private GUIMap showedMap1;
    private DataPrinter dataPanel;
    private AtomicBoolean paused;

    public int delay = 200;

    public GUI(RectangularMap map, RectangularMap map1) throws InterruptedException, IOException {
        super("Generator ewolucyjny");
        this.paused = new AtomicBoolean(true);
        this.map = map;
        this.map1 = map1;
        //Create the frame
        this.setLayout(new BorderLayout(4,4));
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setIconImage((new ImageIcon(this.getClass().getResource("/images/icon.png"))).getImage());
        //Creating map panels
        this.showedMap = new GUIMap(this.map);
        this.showedMap1 = new GUIMap(this.map1);
        this.add(this.showedMap, BorderLayout.WEST);
        this.add(this.showedMap1, BorderLayout.CENTER);
        //Creating statistics panel
        dataPanel = new DataPrinter(map.data, map1.data, this){
            @Override
            public Dimension getPreferredSize() {
                return new Dimension(230, Math.max(map.height * preferredPixelSize, 300));
            }

        };
        this.add(dataPanel, BorderLayout.EAST);
        //Panel for north border
        Empty empty = new Empty();
        this.add(empty, BorderLayout.NORTH);
        //Panel with buttons and basically all functionality
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                while(true) {
                    if(paused.get()) {
                        synchronized(threadObject) {
                            // Pause
                            try {
                                threadObject.wait();
                            }
                            catch (InterruptedException e) {
                            }
                        }
                    }
                    while(true) {
                        try {
                            update();
                            if(paused.get())
                                break;
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        };
        threadObject = new Thread(runnable);

        UserPanel userPanel = new UserPanel(this, this.paused, threadObject);
        this.add(userPanel, BorderLayout.SOUTH);
        // Now everything is set, we can finally show the GUI to user
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        /* This part of code does not work but why? I had to try a different attempt
        while(true){
            SwingUtilities.invokeLater(new Runnable(){
                public void run(){
                    try {
                        update();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
         */


        threadObject.start();
    }

    public void update() throws InterruptedException {
        map.runXDays(1);
        map1.runXDays(1);
        Thread.sleep(delay);
        this.remove(showedMap);
        this.remove(showedMap1);

        showedMap = new GUIMap(this.map);
        showedMap1 = new GUIMap(this.map1);
            /* Why does this part not work correctly???
        showedMap.validate();
        showedMap.repaint();
        showedMap1.validate();
        showedMap1.repaint();
             */
            dataPanel.Update();
        this.add(showedMap, BorderLayout.WEST);
        this.add(showedMap1, BorderLayout.CENTER);
        this.setVisible(true);
    }
}
