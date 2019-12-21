package first;

import java.awt.*;
import java.io.IOException;
import java.text.DecimalFormat;
import javax.swing.*;
import javax.swing.border.Border;

public class DataPrinter extends JPanel {
    private JEditorPane textArea;
    private JEditorPane textArea1;
    private DataCollector data;
    private DataCollector data1;
    int preferredPixelSize = 13;
    private GUI gui;

    public DataPrinter(DataCollector data, DataCollector data1, GUI gui) throws IOException {
        this.gui = gui;
        this.data = data;
        this.data1=data1;
        this.setLayout(new BorderLayout(0,0));
        this.textArea = new JEditorPane("text/html", "");
        this.textArea.setEditable(false);
        this.textArea.putClientProperty(JEditorPane.HONOR_DISPLAY_PROPERTIES, Boolean.TRUE);
        this.textArea.setFont(new Font("Euclid", Font.PLAIN, 16));

        this.textArea1 = new JEditorPane("text/html", "");
        this.textArea1.setEditable(false);
        this.textArea1.putClientProperty(JEditorPane.HONOR_DISPLAY_PROPERTIES, Boolean.TRUE);
        this.textArea1.setFont(new Font("Euclid", Font.PLAIN, 16));

        this.add(this.textArea, BorderLayout.NORTH);
        this.add(this.textArea1, BorderLayout.CENTER);
        this.Update();
    }
    public void Update(){
        UpdateData(textArea, textArea1);
    }
   public void UpdateData(JEditorPane textArea, JEditorPane textArea1){
        SwingUtilities.invokeLater(new Runnable(){
            public void run(){
                //The first map data...
                String info = "<b>Mapa 1:</b><br>";
                info += someData(data);
                textArea.setText(info);
                //...and the second map data
                info = "<br><br><b>Mapa 2:</b><br>";
                info += someData(data1);
                textArea1.setText(info);

                // textArea1.setText(info+"<br><br>Delay = "+gui.delay);
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
