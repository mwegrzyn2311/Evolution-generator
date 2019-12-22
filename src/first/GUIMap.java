package first;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.concurrent.atomic.AtomicBoolean;

public class GUIMap extends JPanel
{
    public RectangularMap map;
    public final int NUM_ROWS;
    public final int NUM_COLS;
    private int startEne;
    private GUI gui;
    private final Color[][] terrainGrid;

    public static final int PREFERRED_GRID_SIZE_PIXELS=13;

    public GUIMap(RectangularMap map, AtomicBoolean paused, GUI gui){
        this.map = map;
        this.gui = gui;
        this.NUM_COLS = map.width;
        this.NUM_ROWS = map.height;
        this.startEne = map.startEnergy;
        terrainGrid = new Color[NUM_COLS][NUM_ROWS];
        int preferredWidth = NUM_COLS * PREFERRED_GRID_SIZE_PIXELS;
        int preferredHeight = NUM_ROWS * PREFERRED_GRID_SIZE_PIXELS;
        setPreferredSize(new Dimension(preferredWidth, preferredHeight));
        updateMap();
        GUIMap thisMap =this;
        addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e){
                if(paused.get()) {
                    if(gui.map.spiedAnimal != null)
                        gui.map.spiedAnimal = null;
                    else if(gui.map1.spiedAnimal != null)
                        gui.map1.spiedAnimal = null;

                    Point clickedPoint = MouseInfo.getPointerInfo().getLocation();
                    Point mapPoint = thisMap.getLocationOnScreen();
                    clickedPoint.translate(- mapPoint.x, - mapPoint.y);
                    Vector2d location = new Vector2d(clickedPoint.x/PREFERRED_GRID_SIZE_PIXELS, NUM_ROWS-clickedPoint.y/PREFERRED_GRID_SIZE_PIXELS-1);
                    // System.out.println("Vector of clicked "+location);
                    // for(Animals animal: map.animals)
                        // System.out.println(animal.getPosition());
                    if(map.containsAnimal(location)){
                        // System.out.println(map.getAnimal(location));
                        map.spiedAnimal = new SpiedAnimal(map.getAnimal(location));
                        gui.userPanel.updateGenotypeText();
                        gui.dataPanel.update();
                        gui.setVisible(true);
                    }
                }
            }
        });
    }

    public void updateMap(){
        for(int i=0; i  < NUM_COLS; i++){
            for(int j=0; j < NUM_ROWS; j++){
                this.terrainGrid[i][j] = getColorByTile(new Vector2d(i, NUM_ROWS-j-1));
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        // Important to call super class method
        super.paintComponent(g);
        // Clear the board
        g.clearRect(0, 0, getWidth(), getHeight());
        // Draw the grid
        int rectWidth = getWidth() / NUM_COLS;
        int rectHeight = getHeight() / NUM_ROWS;

        for (int i = 0; i < NUM_COLS; i++) {
            for (int j = 0; j < NUM_ROWS; j++) {
                // Upper left corner of this terrain rect
                int x = i * rectWidth;
                int y = j * rectHeight;
                Color terrainColor = terrainGrid[i][j];
                g.setColor(terrainColor);
                g.fillRect(x, y, rectWidth, rectHeight);
            }
        }
    }

    public Color getColorByTile(Vector2d position){
        if(this.map.containsGrass(position))
            return new Color(93, 247, 65);
        if(this.map.containsAnimal(position))
            return getColorByEnergy(map.getMaxEneFromTile(position));
        return new Color(255, 255, 255);
    }
    public Color getColorByEnergy(int energy){
        if(energy < startEne * 0.15)
            return new Color(6, 0, 159);
        else if(energy < startEne * 0.3)
            return new Color(52, 108, 189);
        else if(energy < startEne*0.5)
            return new Color(153, 176, 203);
        else if(energy < startEne *0.75)
            return new Color(255, 244, 91);
        else if(energy < startEne * 1.25)
            return new Color(255, 227, 16);
        else if(energy < startEne * 1.75)
            return new Color (255, 165,0);
        else if(energy < startEne * 2.15)
            return new Color(255, 118, 12);
        else
            return new Color(243,0,0);
    }
}
