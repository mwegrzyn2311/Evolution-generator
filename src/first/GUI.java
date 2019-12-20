package first;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.io.IOException;

public class GUI extends JFrame{
    private RectangularMap map;
    private RectangularMap map1;
    public GUI(RectangularMap map, RectangularMap map1) throws InterruptedException, IOException {
        super("Generator ewolucyjny");
        this.map = map;
        this.map1 = map1;
        //Create the frame
        this.setLayout(new BorderLayout(4,4));
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Creating map panels
        GUIMap showedMap = new GUIMap(this.map);
        GUIMap showedMap1 = new GUIMap(this.map1);
        DataPrinter dataPanel = new DataPrinter(map.data, map1.data, map.height){
            @Override
            public Dimension getPreferredSize() {
                return new Dimension(230, Math.max(map.height * preferredPixelSize, 300));
            }

        };
        Empty empty = new Empty();
        this.add(empty, BorderLayout.NORTH);
        this.add(showedMap, BorderLayout.WEST);
        this.add(showedMap1, BorderLayout.CENTER);
        this.add(dataPanel, BorderLayout.EAST);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        for(int i=0; i < 10000; i++){
            map.runXDays(1);
            map1.runXDays(1);
            Thread.sleep(100);

            showedMap = new GUIMap(this.map);
            showedMap1 = new GUIMap(this.map1);
            dataPanel.Update();
            this.add(showedMap, BorderLayout.WEST);
            this.add(showedMap1, BorderLayout.CENTER);
            this.setVisible(true);
        }

    }
    /*
    public GUI(RectangularMap map, RectangularMap map1) throws InterruptedException {
        this.map = map;
        this.map1 = map1;
        //Create the frame
        frame = new JFrame("Generator ewolucyjny");
        frame.setLayout(new BorderLayout(5,5));
        //frame.setSize(650, 500);
        frame.setResizable(false);
        //Creating button for starting and stopping the simulation
        GUIMap showedMap = new GUIMap(map);
        GUIMap showedMap1 = new GUIMap(map1);
        frame.add(showedMap, BorderLayout.WEST);
        frame.add(showedMap1, BorderLayout.CENTER);
        //addSimulationButtons();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        for(int i=0; i < 10000; i++){
            map.runXDays(1);
            map1.runXDays(1);
            Thread.sleep(100);

            //showedMap = new GUIMap(map);
            //showedMap1 = new GUIMap(map1);
            //frame.add(showedMap);
            //frame.add(showedMap1);

            showedMap = new GUIMap(map);
            showedMap1 = new GUIMap(map1);
            frame.add(showedMap, BorderLayout.WEST);
            frame.add(showedMap1, BorderLayout.CENTER);
            frame.setVisible(true);
        }

    }
    */
    public void addSimulationButtons(){
        JPanel panel = new JPanel();
        JButton start = new JButton(">");
        JButton pause = new JButton("||");
        panel.add(start);
        panel.add(pause);
        getContentPane().add(BorderLayout.SOUTH, panel);
    }
}
