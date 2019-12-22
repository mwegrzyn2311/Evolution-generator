package first;

import java.awt.*;
import java.io.IOException;
import java.text.DecimalFormat;
import javax.swing.*;

public class DataPanel extends JPanel {
    private JEditorPane textArea;
    private JEditorPane textArea1;
    private JEditorPane spiedAnimalText;
    private DataCollector data;
    private DataCollector data1;
    int preferredPixelSize = 13;
    private GUI gui;

    public DataPanel(DataCollector data, DataCollector data1, GUI gui) throws IOException {
        this.gui = gui;
        this.data = data;
        this.data1=data1;
        // this.setLayout(new BorderLayout(0,0));
        // this.setLayout(new GridLayout(3,1));
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.textArea = new JEditorPane("text/html", "");
        this.textArea.setEditable(false);
        this.textArea.putClientProperty(JEditorPane.HONOR_DISPLAY_PROPERTIES, Boolean.TRUE);
        this.textArea.setFont(new Font("Euclid", Font.PLAIN, 16));
        this.textArea.setPreferredSize(new Dimension(230, 120));

        this.textArea1 = new JEditorPane("text/html", "");
        this.textArea1.setEditable(false);
        this.textArea1.putClientProperty(JEditorPane.HONOR_DISPLAY_PROPERTIES, Boolean.TRUE);
        this.textArea1.setFont(new Font("Euclid", Font.PLAIN, 16));
        this.textArea1.setPreferredSize(new Dimension(230, 120));

        this.spiedAnimalText = new JEditorPane("text/html", "");
        this.spiedAnimalText.setEditable(false);
        this.spiedAnimalText.putClientProperty(JEditorPane.HONOR_DISPLAY_PROPERTIES, Boolean.TRUE);
        this.spiedAnimalText.setFont(new Font("Euclid", Font.PLAIN, 16));
        this.spiedAnimalText.setPreferredSize(new Dimension(230, 50));

        this.add(this.textArea);
        this.add(this.textArea1);
        this.add(this.spiedAnimalText);
        this.update();
    }

    public void update(){
        updateData(textArea, textArea1);
    }
    private void updateData(JEditorPane textArea, JEditorPane textArea1){
        SwingUtilities.invokeLater(new Runnable(){
            public void run(){
                //The first map data...
                String info = "<b>Mapa 1:</b><br>";
                info += someData(data);
                textArea.setText(info);
                //...and the second map data...
                info = "<b>Mapa 2:</b><br>";
                info += someData(data1);
                textArea1.setText(info);
                //...and finally spiedAnimal data
                SpiedAnimal spiedAnimal = gui.map.spiedAnimal;
                info ="";
                spiedAnimalText.setText(info);
                if(spiedAnimal == null){
                    spiedAnimal = gui.map1.spiedAnimal;
                    if(spiedAnimal == null) {
                        return;
                    }
                }
                info = "<b>Wybrany:</b><br>&nbsp Dzieci wybranego: " + spiedAnimal.recentChildrenCount();
                if(spiedAnimal.deathDay == -1){
                    info += "<br>&nbsp Wybrany nadal żyje";
                }
                else{
                    info += "<br>&nbsp Wybrany umarł dnia " + spiedAnimal.deathDay;
                }
                spiedAnimalText.setText(info);
            }
        });

    }
    private String someData(DataCollector data){
        String info="";
        info += "&nbsp Żyje zwierząt: ";
        info += data.getAnimalsCount();
        info += "<br>&nbsp W sumie jest roślin: ";
        info += data.getGrassCount();
        info += "<br>&nbsp Dominuje genom: ";
        info += data.getDominatingGene();
        info += "<br>&nbsp Średnia energia: ";
        info += new DecimalFormat("##.##").format(data.getAverageEnergy());
        info += "<br>&nbsp Średnia długość życia: ";
        info += new DecimalFormat("##.##").format(data.getAverageDeathAge());
        info += "<br>&nbsp Średnia ilość dzieci: ";
        info += new DecimalFormat("##.##").format(data.getAverageChildrenCount());
        return info;
    }
}
