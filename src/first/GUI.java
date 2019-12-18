package first;

import javax.swing.*;
import java.awt.*;

public class GUI {
    JFrame frame;
    RectangularMap map;
    public GUI(RectangularMap map) {
        this.map = map;
        //Create the frame
        frame = new JFrame("Generator ewolucyjny");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(650, 500);
        frame.setLocationRelativeTo(null);

        //Creating button for starting and stopping the simulation
        showMap();
        addSimulationButtons();

        frame.setVisible(true);
    }

    public void showMap(){
        for(int i = 0; i < map.width; i++){
            JPanel row = new JPanel();
            for(int j = 0; j< map.width; j++){
                JButton button = new JButton(i+","+j);
                row.add(button);
            }
            frame.getContentPane().add(BorderLayout.CENTER, row);
        }
    }

    public void addSimulationButtons(){
        JPanel panel = new JPanel();
        JButton start = new JButton(">");
        JButton pause = new JButton("||");
        panel.add(start);
        panel.add(pause);
        frame.getContentPane().add(BorderLayout.SOUTH, panel);
    }
}
