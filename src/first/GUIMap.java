package first;

import javax.swing.*;
import java.awt.*;

public class GUIMap extends JPanel
{
    private RectangularMap map;
    public final int NUM_ROWS;
    public final int NUM_COLS;
    private int startEne;
    private final Color[][] terrainGrid;

    public static final int PREFERRED_GRID_SIZE_PIXELS=13;

    public GUIMap(RectangularMap map){
        this.map = map;
        this.NUM_COLS = map.width;
        this.NUM_ROWS = map.height;
        this.startEne = map.startEnergy;
        terrainGrid = new Color[NUM_COLS][NUM_ROWS];
        for(int i=0; i  < NUM_COLS; i++){
            for(int j=0; j < NUM_ROWS; j++){
                this.terrainGrid[i][j] = getColorByTile(new Vector2d(i, NUM_ROWS-j-1));
            }
        }
        int preferredWidth = NUM_COLS * PREFERRED_GRID_SIZE_PIXELS;
        int preferredHeight = NUM_ROWS * PREFERRED_GRID_SIZE_PIXELS;
        setPreferredSize(new Dimension(preferredWidth, preferredHeight));
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
            return new Color(229, 1, 164);
        if(this.map.containsAnimal(position))
            return getColorByEnergy(map.getMaxEneFromTile(position));
        return new Color(255, 255, 255);
    }
    public Color getColorByEnergy(int energy){
        if(energy < startEne/4){
            return new Color(0, 0, 0);
        }else if(energy < startEne/2){
            return new Color(156, 0, 8);
        }else if(energy < startEne*0.75){
            return new Color(203, 192, 2);
        }else if(energy < startEne *1.5){
            return new Color(64, 214, 41);
        }else
            return new Color(39, 123, 21);
    }
}
