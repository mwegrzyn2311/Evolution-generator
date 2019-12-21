package first;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.io.IOException;

public class GUI extends JFrame{
    private RectangularMap map;
    private RectangularMap map1;
    public int delay = 200;

    public GUI(RectangularMap map, RectangularMap map1) throws InterruptedException, IOException {
        super("Generator ewolucyjny");
        this.map = map;
        this.map1 = map1;
        //Create the frame
        this.setLayout(new BorderLayout(4,4));
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setIconImage((new ImageIcon(this.getClass().getResource("/images/icon.png"))).getImage());
        //Creating map panels
        GUIMap showedMap = new GUIMap(this.map);
        GUIMap showedMap1 = new GUIMap(this.map1);
        this.add(showedMap, BorderLayout.WEST);
        this.add(showedMap1, BorderLayout.CENTER);
        //Creating statistics panel
        DataPrinter dataPanel = new DataPrinter(map.data, map1.data, this){
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
        UserPanel userPanel = new UserPanel(this);
        this.add(userPanel, BorderLayout.SOUTH);
        // Now everything is set, we can finally show the GUI to user
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        while(true){
            map.runXDays(1);
            map1.runXDays(1);
            Thread.sleep(delay);
            /*
            showedMap.Update();
            showedMap1.Update();
            */

            showedMap = new GUIMap(this.map);
            showedMap1 = new GUIMap(this.map1);
            dataPanel.Update();
            this.add(showedMap, BorderLayout.WEST);
            this.add(showedMap1, BorderLayout.CENTER);
            this.setVisible(true);
        }
    }
    public void addSimulationButtons(){
        JPanel panel = new JPanel();
        JButton start = new JButton(">");
        JButton pause = new JButton("||");
        panel.add(start);
        panel.add(pause);
        getContentPane().add(BorderLayout.SOUTH, panel);
    }
}
