package first;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RectangularMap{
    private ArrayList<Animals> animals = new ArrayList<>();
    private Map<Vector2d, ArrayList<Animals>> animalz=new HashMap<>();
    private Map<Vector2d, Grass> grass=new HashMap<>();
    private MapVisualizer map=new MapVisualizer(this);
    private int plantEnergy;
    private int moveEnergy;
    private double jungleRatio;//TODO: Check if needed
    public int height;
    public int width;
    private int jungleHeight;
    private int jungleWidth;
    private Vector2d jungleLowerLeft;
    private Vector2d jungleUpperRight;

    public RectangularMap(int width, int height, int moveEnergy, int plantEnergy, double jungleRatio){
        if(jungleRatio > 1.0)
            throw new IllegalArgumentException("Jungle larger than the whole world? What do you mean?");

        this.width=width;
        this.height=height;
        this.plantEnergy=plantEnergy;
        this.moveEnergy=moveEnergy;
        this.jungleRatio=jungleRatio;
        this.jungleWidth=(int)(width*jungleRatio);
        this.jungleHeight=(int)(height*jungleRatio);
        this.jungleLowerLeft=new Vector2d( (width-jungleWidth)/2, (height-jungleHeight)/2);
        this.jungleUpperRight=this.jungleLowerLeft.add(new Vector2d(jungleWidth-1, jungleHeight-1));
    }

    public boolean isInJungle(Vector2d position){
        return (position.precedes(jungleUpperRight) && position.follows(jungleLowerLeft));
    }

    public boolean isInMap(Vector2d position){
        return (position.x >= 0 && position.y >=0 && position.x < width && position.y < height);
    }

    public void addAnimalToMap(Animals animal, Vector2d position){
        if(animalz.get(position)==null){
            ArrayList<Animals> animalsList = new ArrayList<>();
            animalsList.add(animal);
            animalz.put(position, animalsList);
        }else{
            animalz.get(position).add(animal);
        }
    }

    public void placeAnimal(Animals animal){
        Vector2d position=animal.getPosition();
        this.animals.add(animal);
        addAnimalToMap(animal, position);
    }

    private boolean removeTheDead(){
        //returns false if every animal is dead
        if(animals.size()==0){
            return false;
        }
        for(int i=animals.size()-1;i>=0;i--){
            if(animals.get(i).energy <= 0) {
                Vector2d pos=animals.get(i).getPosition();
                List<Animals> animalsList=animalz.get(pos);
                animalsList.remove(animals.get(i));
                animals.remove(i);
            }
        }
        return true;
    }

    private boolean outsideJungleFull(){
        for(int i = 0; i < this.width; i++){
            for(int j = 0; j < this.height; j++){
                Vector2d position = new Vector2d(i, j);
                if(!isInJungle(position) && !containsSomething(position))
                    return false;
            }
        }
        return true;
    }


    private boolean jungleFull(){
        for(int i=0;i<jungleWidth;i++){
            for(int j=0;j<jungleHeight;j++){
                if(!containsSomething(jungleLowerLeft.add(new Vector2d(i,j))))
                    return false;
            }
        }
        return true;
    }

    private void spawnGrass(){
        //For making things go faster, may stop growing grass(or looking for the first grass-empty tile in the jungle),
        //when grass takes over 80-90% of the jungle(instead of only if is full)

        //Firstly spawn grass outside the jungle
        if(!outsideJungleFull()) {
            while (true) {
                Vector2d position = new Vector2d((int) (Math.random() * width), (int) (Math.random() * height));
                if (!isInJungle(position) && !containsSomething(position)) {
                    grass.put(position, new Grass(this, position));
                    break;
                }
            }
        }

        //Secondly spawn grass in the jungle
        if(!jungleFull()){
            while(true) {
                Vector2d position = jungleLowerLeft.add(new Vector2d((int) (Math.random() * jungleWidth), (int) (Math.random() * jungleHeight)));
                if (!containsSomething(position)) {
                    grass.put(position, new Grass(this, position));
                    break;
                }
            }
        }

    }

    public Vector2d backIntoMap(Vector2d position){
        if(position.x < 0){
            position.x += this.width;
        }
        if(position.y < 0){
            position.y += this.height;
        }
        if(position.x > this.width){
            position.x -= this.width;
        }
        if(position.y > this.height){
            position.y -= this.height;
        }
        return position;
    }

    private Vector2d findEmptyPosition(Vector2d position){
        Vector2d res;
        for(int i = -1; i <= 1; i++){
            for(int j = -1; j <=1; j++){
                res = backIntoMap(position.add(new Vector2d(i,j)));
                if(!containsSomething(res))
                    return res;
            }
        }
        return position;
    }

    private void breedAll(){
        ArrayList<Vector2d> haveBeenAt=new ArrayList<>();
        for(int i=animals.size()-1; i>=0; i--){
            Animals animal=animals.get(i);
            Vector2d position = animal.getPosition();
            if(!haveBeenAt.contains(position) && animalz.get(position).size() >= 2){
                haveBeenAt.add(position);
                ArrayList<Animals> animalsList=animalz.get(position);
                Animals father=animalsList.get(0);
                Animals mother=animalsList.get(1);
                if(father.energy < mother.energy){
                    Animals swap=father;
                    father=mother;
                    mother=swap;
                }
                for(i=2; i<animalsList.size(); i++){
                    int currEnergy = animalsList.get(i).energy;
                    if(currEnergy >= father.breedEnergyRequired){
                        if(currEnergy > father.energy){
                            mother=father;
                            father=animalsList.get(i);
                        }
                        else if(currEnergy > mother.energy){
                            mother=animalsList.get(i);
                        }
                    }
                }
                if(mother.energy > mother.breedEnergyRequired){
                    breed(mother, father);
                }
            }
        }
    }

    private void breed(Animals mother, Animals father){
        System.out.print("Animals breeding in "+ mother.getPosition());
        int babyEnergy = mother.energy/4;
        babyEnergy += father.energy/4;
        mother.energy -= mother.energy/4;
        father.energy -= father.energy/4;
        Genotype genotype = new Genotype(mother.getGenotype(), father.getGenotype());
        Vector2d position = findEmptyPosition(mother.getPosition());
        this.placeAnimal(new Animals(this, position, genotype, babyEnergy, mother.startEnergy));
        System.out.println(". Baby has spawned in "+position);
    }

    private void run(){
        for(int i=animals.size()-1; i >= 0; i--) {
            Animals animal = animals.get(i);
            Vector2d pre = animal.getPosition();
            List<Animals> animalsList = animalz.get(pre);
            for (Animals tmp : animalsList) {
                if (tmp == animal) {
                    animalsList.remove(animal);
                    break;
                }
            }
            animal.energy -= moveEnergy;
            animal.move();
            animal.age++;
            Vector2d post = animal.getPosition();
            addAnimalToMap(animal, post);
        }
    }

    private void eat(){
        for(Animals animal:animals){
            Vector2d position = animal.getPosition();
            if(containsGrass(position)){
                Animals eater=new Animals(this, new Vector2d(-1, -1), -1);
                ArrayList<Animals> animalsList=animalz.get(position);
                int eatersCounter=1;
                for(Animals tmp:animalsList){
                    if(tmp.energy > eater.energy) {
                        eater = tmp;
                        eatersCounter=1;
                    }
                    else if(tmp.energy == eater.energy){
                        eatersCounter++;
                    }
                }
                if(eatersCounter==1)
                    eater.energy += plantEnergy;
                else{
                    int actualEnergy = plantEnergy/eatersCounter;
                    for(Animals tmp:animalsList)
                        if(tmp.energy==eater.energy)
                            tmp.energy+=actualEnergy;
                }
                grass.remove(position);
            }
        }
    }

    public void runXDays(int days){
        for(int i=0;i<days;i++){
            this.dayCycle();
        }
    }

    private void dayCycle(){
        if(!removeTheDead())
            return;
        //checkLists();
        spawnGrass();
        run();
        eat();
        breedAll();
        //System.out.println(this.toString());
    }
    public String toString(){
        return map.draw(new Vector2d(0,0), new Vector2d(this.width-1,this.height-1));
    }

    boolean containsSomething(Vector2d position){
        return ((animalz.get(position)!=null && animalz.get(position).size() != 0)|| grass.containsKey(position));
    }
    private boolean containsGrass(Vector2d position){
        return (grass.containsKey(position));
    }
    private boolean containsAnimal(Vector2d position){
        return (animalz.get(position)!=null && animalz.get(position).size()!=0);
    }

    boolean objectIsInMap(IMapElement object){
        Vector2d pos=object.getPosition();
        return (pos.x < width && pos.y < height && pos.x >=0 && pos.y >= 0);
    }

    Object objectAt(Vector2d position){
        //returns grass or just one of animals in the tile (for MapVisualizer to work)
        if(containsGrass(position))
            return grass.get(position);
        else if(containsAnimal(position)) {
            return animalz.get(position).get(0);
        }
        else return null;
    }

    public void checkLists(){
        System.out.println("Animals alive: "+animals.size());
        for(Animals animal: animals){
            Vector2d position=animal.getPosition();
            Animals tmp=animalz.get(position).get(0);
            System.out.println(tmp+ " at position "+animal.getPosition()+" having "+animal.energy+" energy");
        }
    }

    public void printAnimalsAge(){
        System.out.println("Still living animals' ages:");
        for(Animals animal : animals){
            if(animal!=animals.get(0))
                System.out.print(", "+animal.age);
            else
                System.out.print(animal.age);
        }
        System.out.println("");
    }
}
