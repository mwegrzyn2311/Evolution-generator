package first;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class World {
    public static void main(String[] args) {
        try {
            String arguments=new String(Files.readAllBytes(Paths.get("parameters.json")));
            Gson
            int startEnergy=40;
            int moveEnergy=1;
            int plantEnergy=40;
            double jungleRatio=1.0/3;
            int width=70;
            int height=30;
            int animalsCount = 200;
            RectangularMap map = new RectangularMap(width, height, moveEnergy, plantEnergy, jungleRatio);
            //spawnHardCodedAnimals(map);
            spawnXRandomAnimals(map, animalsCount, startEnergy, width, height);
            System.out.println(map.toString());
            map.printAnimalsAge();
            map.runXDays(10000);
            System.out.println(map.toString());
            map.printAnimalsAge();
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
