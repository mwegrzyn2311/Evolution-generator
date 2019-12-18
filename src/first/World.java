package first;
import org.json.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import java.util.Scanner;

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


            int animalsCount = 12;
            RectangularMap map = new RectangularMap(width, height, moveEnergy, plantEnergy, jungleRatio);
            //spawnHardCodedAnimals(map);
            spawnXRandomAnimals(map, animalsCount, startEnergy, width, height);
            System.out.println(map.toString());
            //map.printAnimalsAge();

            /*
            map.runXDays(10000);
            System.out.println(map.toString());
            map.printAnimalsAge();
            */

            for(int i=0; i < 20; i++){
                Scanner input = new Scanner(System.in);
                int number = input.nextInt();
                //System.out.println(number);
                map.runXDays(40000);
                System.out.println(map.toString());
                //map.printAnimalsAge();
                //map.checkLists();
            }
        } catch(IllegalArgumentException | IOException ex){
            System.out.println(ex);
        }
    }
    private static void spawnXRandomAnimals(RectangularMap map, int animalsCounter, int startEnergy, int width, int height){
        for(int i=0; i<animalsCounter; i++){
            int x, y;
            do{
                x=(int)(Math.random()*width);
                y=(int)(Math.random()*height);
            }while(map.containsSomething(new Vector2d(x, y)));
            map.placeAnimal(new Animals(map, new Vector2d(x, y), startEnergy));
        }
    }

    private static void spawnHardCodedAnimals(RectangularMap map){
        //Animals born inside or near the jungle
        /*
        map.placeAnimal(new Animals(map, new Vector2d(50,15)));
        map.placeAnimal(new Animals(map, new Vector2d(48,17)));
        map.placeAnimal(new Animals(map, new Vector2d(52,13)));
        map.placeAnimal(new Animals(map, new Vector2d(49,13)));
        map.placeAnimal(new Animals(map, new Vector2d(53,14)));
        map.placeAnimal(new Animals(map, new Vector2d(45,10)));
        map.placeAnimal(new Animals(map, new Vector2d(43,17)));
        //Animals born far from jungle
        map.placeAnimal(new Animals(map, new Vector2d(10,5)));
        map.placeAnimal(new Animals(map, new Vector2d(20,15)));
        map.placeAnimal(new Animals(map, new Vector2d(10,25)));
        map.placeAnimal(new Animals(map, new Vector2d(90,2)));
        map.placeAnimal(new Animals(map, new Vector2d(95, 25)));
        map.placeAnimal(new Animals(map, new Vector2d(70, 10)));
        map.placeAnimal(new Animals(map, new Vector2d(30, 28)));
        map.placeAnimal(new Animals(map, new Vector2d(47,12)));
        map.placeAnimal(new Animals(map, new Vector2d(12,5)));
        map.placeAnimal(new Animals(map, new Vector2d(66,17)));
        map.placeAnimal(new Animals(map, new Vector2d(37,16)));
        map.placeAnimal(new Animals(map, new Vector2d(14,21)));
        map.placeAnimal(new Animals(map, new Vector2d(87,4)));
        map.placeAnimal(new Animals(map, new Vector2d(96, 23)));
        map.placeAnimal(new Animals(map, new Vector2d(71, 6)));
        map.placeAnimal(new Animals(map, new Vector2d(34, 22)));
         */
    }
}
