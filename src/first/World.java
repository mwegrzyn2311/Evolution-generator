package first;
import org.json.*;

import javax.swing.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class World {
    public static void main(String[] args) {
        try {
            String path="src/first/parameters.json";

            String parameters=new String(Files.readAllBytes(Paths.get(path)));
            JSONObject arguments = new JSONObject(parameters);
            int width = arguments.getInt("width");
            int height= arguments.getInt("height");
            int startEnergy = arguments.getInt("startEnergy");
            int moveEnergy = arguments.getInt("moveEnergy");
            int plantEnergy = arguments.getInt("plantEnergy");
            double jungleRatio = arguments.getDouble("jungleRatio");

            if(width == 0 || height == 0 || startEnergy == 0 || moveEnergy == 0 || plantEnergy == 0 || jungleRatio == 0.0){
                throw new IllegalArgumentException("None of the parameters is allowed to be equal to zero");
            }
            if(width < 0){
                width *= (-1);
                System.out.println("Changed width to positive value");
            }
            if(height < 0){
                height *= (-1);
                System.out.println("Changed height to positive value");
            }
            if(startEnergy < 0){
                startEnergy *= (-1);
                System.out.println("Changed startEnergy to positive value");
            }
            if(moveEnergy < 0){
                moveEnergy *= (-1);
                System.out.println("Changed moveEnergy to positive value");
            }
            if(plantEnergy < 0) {
                plantEnergy *= (-1);
                System.out.println("Changed plantEnergy to positive value");
            }
            if(jungleRatio < 0){
                jungleRatio *= (-1);
            }
            if(jungleRatio > 1.0){
                System.out.println("Changed jungleRatio to 1.0 as jungleRatio should be a number between 0.0 and 1.0");
            }
            if(jungleRatio > 0.7){
                System.out.println("Is it even a jungle now when it takes over 49% of the world, though?");
            }

            int animalsCount = 20;
            RectangularMap map = new RectangularMap(width, height, startEnergy, moveEnergy, plantEnergy, jungleRatio);
            RectangularMap map1 = new RectangularMap(width, height, startEnergy, moveEnergy, plantEnergy, jungleRatio);
            spawnXRandomAnimals(map, map1, animalsCount, startEnergy, width, height);

            GUI gui = new GUI(map, map1);

            /*
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    try {
                        GUI gui = new GUI(map, map1);
                    } catch (InterruptedException | IOException e) {
                        e.printStackTrace();
                    }
                }
            });
             */
        } catch(IllegalArgumentException | IOException | InterruptedException ex){
            System.out.println(ex);
        }
    }

    private static void spawnXRandomAnimals(RectangularMap map, RectangularMap map1,  int animalsCounter, int startEnergy, int width, int height){
        for(int i=0; i<animalsCounter; i++){
            int x, y;
            do{
                x=(int)(Math.random()*width);
                y=(int)(Math.random()*height);
            }while(map.containsSomething(new Vector2d(x, y)));
            Animals animal = new Animals(map, new Vector2d(x, y), startEnergy);
            Animals animal1 = new Animals(map1, new Vector2d(x, y), startEnergy);
            map.placeAnimal(animal);
            map1.placeAnimal(animal1);
        }
    }

}
