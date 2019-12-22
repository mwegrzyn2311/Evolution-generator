package first;

import java.awt.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class DominantGUIMap extends GUIMap{


    public DominantGUIMap(RectangularMap map, AtomicBoolean paused, GUI gui){
        super(map, paused, gui);
    }
    @Override
    public Color getColorByTile(Vector2d position){
        if(this.map.containsGrass(position))
            return new Color(93, 247, 65);
        if(this.map.containsAnimal(position)) {
            if(map.containsDominantAnimal(position))
                return new Color(0,0,0);
            return getColorByEnergy(this.map.getMaxEneFromTile(position));
        }
        return new Color(255, 255, 255);
    }
}
