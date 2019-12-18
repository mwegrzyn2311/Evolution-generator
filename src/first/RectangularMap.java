package first;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RectangularMap{
    public ArrayList<Animals> animals = new ArrayList<>();
    private Map<Vector2d, ArrayList<Animals>> animalz=new HashMap<>();
    public Map<Vector2d, Grass> grass=new HashMap<>();
    private MapVisualizer map=new MapVisualizer(this);
    private int plantEnergy;
    private int moveEnergy;
    public int height;
    public int width;
    private int jungleHeight;
    private int jungleWidth;
    private Vector2d jungleLowerLeft;
    private Vector2d jungleUpperRight;
    private int jungleArea;
    private int outsideJungleArea;
    private int day=0;
    private DataCollector data;

    public RectangularMap(int width, int height, int moveEnergy, int plantEnergy, double jungleRatio){
        data = new DataCollector(this);
        this.width = width;
        this.height = height;
        this.plantEnergy = plantEnergy;
        this.moveEnergy = moveEnergy;
        this.jungleWidth = (int)(width*jungleRatio);
        this.jungleHeight = (int)(height*jungleRatio);
        this.jungleLowerLeft = new Vector2d( (width-jungleWidth)/2, (height-jungleHeight)/2);
        this.jungleUpperRight = this.jungleLowerLeft.add(new Vector2d(jungleWidth-1, jungleHeight-1));
        this.jungleArea = jungleHeight*jungleWidth;
        this.outsideJungleArea = width*height-this.jungleArea;
    }

    public boolean isInJungle(Vector2d position){
        return (position.precedes(jungleUpperRight) && position.follows(jungleLowerLeft));
    }

    public boolean isInMap(Vector2d position){
        return (position.x >= 0 && position.y >=0 && position.x < width && position.y < height);
    }

    public void addAnimalToMap(Animals animal, Vector2d position){
        if(animalz.get(position)==null) {
            ArrayList<Animals> animalsList = new ArrayList<>();
            animalz.put(position, animalsList);
        }
        animalz.get(position).add(animal);
    }

    public void placeAnimal(Animals animal){
        animal.setID(this.animals.size());
        Vector2d position=animal.getPosition();
        this.animals.add(animal);
        addAnimalToMap(animal, position);
        data.updateAnimalsGeneStats(animal);
    }

    private void removeTheDead(){
        //returns false if every animal is dead
        if(animals.size()==0){
            return;
        }
        for(int i=animals.size()-1;i>=0;i--){
            if(animals.get(i).energy <= 0) {
                Animals deadAnimal = animals.get(i);
                Vector2d pos=deadAnimal.getPosition();
                animalz.get(pos).remove(deadAnimal);
                animals.remove(deadAnimal);
                this.data.deadAnimalsCount++;
                this.data.summaryDeathAge += deadAnimal.age;
            }
        }
    }

    private boolean outsideJungleFull(){
        if(jungleArea == outsideJungleArea)
            return true;

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
        if(jungleArea == 0)
            return true;

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
        int counter;
        if(!outsideJungleFull()) {
            for(counter = 0; counter < outsideJungleArea; counter++) {
                Vector2d position = new Vector2d((int) (Math.random() * width), (int) (Math.random() * height));
                if (!isInJungle(position) && !containsSomething(position)) {
                    grass.put(position, new Grass(this, position));
                    break;
                }
            }
            if(counter == outsideJungleArea){
                boolean spawned;
                if(width > height){
                    do{
                        int row = (int) (Math.random() * height);
                        spawned = false;
                        for(int i = 0; i < width; i++){
                            Vector2d position = new Vector2d(i, row);
                            if(!isInJungle(position) && !containsSomething(position)){
                                grass.put(position, new Grass(this, position));
                                spawned = true;
                                break;
                            }
                        }
                    }while(!spawned);

                } else{
                    do{
                        int column = (int) (Math.random() * width);
                        spawned = false;
                        for(int j = 0; j < height; j++){
                            Vector2d position = new Vector2d(column, j);
                            if(!isInJungle(position) && !containsSomething(position)){
                                grass.put(position, new Grass(this, position));
                                spawned = true;
                                break;
                            }
                        }
                    }while(!spawned);
                }
            }
        }

        //Secondly spawn grass in the jungle
        if(!jungleFull()){
            for(counter = 0; counter < jungleArea; counter++) {
                Vector2d position = jungleLowerLeft.add(new Vector2d((int) (Math.random() * jungleWidth), (int) (Math.random() * jungleHeight)));
                if (!containsSomething(position)) {
                    grass.put(position, new Grass(this, position));
                    break;
                }
            }
            if(counter == jungleArea){
                boolean spawned;
                if(width > height){
                    do{
                        int row = jungleLowerLeft.y + (int) (Math.random() * jungleHeight);
                        spawned = false;
                        for(int i = jungleLowerLeft.x; i <= jungleUpperRight.x; i++){
                            Vector2d position = new Vector2d(i, row);
                            if(!containsSomething(position)){
                                grass.put(position, new Grass(this, position));
                                spawned = true;
                                break;
                            }
                        }
                    }while(!spawned);

                } else{
                    do{
                        int column = jungleLowerLeft.x + (int) (Math.random() * jungleWidth);
                        spawned = false;
                        for(int j = jungleLowerLeft.y; j <= jungleUpperRight.y; j++){
                            Vector2d position = new Vector2d(column, j);
                            if(!containsSomething(position)){
                                grass.put(position, new Grass(this, position));
                                spawned = true;
                                break;
                            }
                        }
                    }while(!spawned);
                }
            }
        }

    }

    public Vector2d backIntoMap(Vector2d position){
        Vector2d res = new Vector2d(position.x, position.y);
        if(res.x < 0){
            res.x += this.width;
        }
        if(res.y < 0){
            res.y += this.height;
        }
        if(res.x >= this.width){
            res.x -= this.width;
        }
        if(res.y >= this.height){
            res.y -= this.height;
        }
        return res;
    }

    private Vector2d findEmptyPosition(Vector2d position){
        Vector2d res;
        for(int i = -1; i <= 1; i++){
            for(int j = -1; j <=1; j++){
                res = backIntoMap(position.add(new Vector2d(i, j)));
                if (!containsSomething(res))
                    return res;
            }
        }
        int x,y;
        do {
            x = (int) (Math.random() * 3) - 1;
            y = (int) (Math.random() * 3) - 1;
        }while(x != 0 || y != 0);
        res = backIntoMap(position.add(new Vector2d(x,y)));
        return res;
    }

    private void breed(Animals mother, Animals father){

        int babyEnergy = mother.energy/4;
        babyEnergy += father.energy/4;
        mother.energy -= mother.energy/4;
        father.energy -= father.energy/4;
        Genotype genotype = new Genotype(mother.getGenotype(), father.getGenotype());
        Vector2d position = findEmptyPosition(mother.getPosition());
        this.placeAnimal(new Animals(this, position, genotype, babyEnergy, mother.startEnergy));
        mother.childrenCounter++;
        father.childrenCounter++;
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
                for(int j = 2; j < animalsList.size(); j++){
                    int currEnergy = animalsList.get(j).energy;
                    if(currEnergy >= father.breedEnergyRequired){
                        if(currEnergy > father.energy){
                            mother=father;
                            father=animalsList.get(j);
                        }
                        else if(currEnergy > mother.energy){
                            mother=animalsList.get(j);
                        }
                    }
                }
                if(mother.energy > mother.breedEnergyRequired){
                    breed(mother, father);
                }
            }
        }
    }

    private void run(){
        for(Animals animal: animals) {
            Vector2d pre = animal.getPosition();
            animalz.get(pre).remove(animal);
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
                    int maxEne = eater.energy;
                    for(Animals tmp:animalsList)
                        if(tmp.energy == maxEne)
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
        this.day++;
        spawnGrass();
        run();
        eat();
        breedAll();
        removeTheDead();
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

    Object objectAt(Vector2d position){
        //returns grass or just one of animals in the tile (for MapVisualizer to work)
        if(containsAnimal(position))
            return animalz.get(position).get(0);
        else if(containsGrass(position)) {
            return grass.get(position);
        }
        else return null;
    }

}
