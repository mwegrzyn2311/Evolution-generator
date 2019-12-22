package first;

import java.util.ArrayList;
import java.util.List;

public class Animals extends abstractMapElement{
    private MapDirection dir;
    public int energy;
    public int startEnergy;
    public int breedEnergyRequired;
    public int age = 0;//age tells us how many days has animal survived
    private Genotype genotype;
    private long ID;
    public int childrenCount = 0;

    Animals(RectangularMap map, Vector2d initialPosition, int startingEnergy) {
        super(map, initialPosition);
        this.genotype = new Genotype();
        this.dir = MapDirection.getRandDir();
        this.startEnergy = startingEnergy;
        this.breedEnergyRequired = startingEnergy/2;
        this.energy = startingEnergy;
    }
    Animals(RectangularMap map, Vector2d initialPosition, Genotype genotype, int energy, int startingEnergy) {
        super(map, initialPosition);
        this.genotype = genotype;
        this.dir = MapDirection.getRandDir();
        this.startEnergy = startingEnergy;
        this.breedEnergyRequired = startingEnergy/2;
        this.energy = energy;
    }

    public void move(){
        int turn = genotype.turnAnimal();
        for(int j = 0; j < turn; j++){
            this.dir = this.dir.next();
        }
        Vector2d move = this.dir.toOneMoveVector();
        this.position = this.position.add(move);
        this.position = this.map.backIntoMap(this.position);
    }

    public MapDirection getDir(){return this.dir;}
    public Genotype getGenotype(){return this.genotype;}

    public boolean equals(Object other){
        if (this == other)
            return true;
        if (!(other instanceof Animals))
            return false;
        Animals that = (Animals) other;
        return(this.position.equals(that.position) && this.dir == that.dir);
    }

    public int getDominantGene(){
        int max = -1;
        int maxGene = -1;
        int[] valuesCount = new int [8];
        for(int i = 0; i < 8; i++){
            valuesCount[i] = 0;
        }
        int[] values = this.genotype.values;
        for(int i = 0; i<32; i++){
            valuesCount[values[i]]++;
        }
        for(int i = 0; i < 8; i++){
            if(valuesCount[i] > max){
                max = valuesCount[i];
                maxGene = i;
            }
        }
        return maxGene;
    }

    public String toString(){
        // return dir.toShortString();
        // return "A";
        return Long.toString(ID);
    }

    public void setID(long ID){
        this.ID = ID;
    }
    public long getID(){
        return this.ID;
    }

}