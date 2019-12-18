package first;

import java.util.ArrayList;
import java.util.List;

public class Animals extends abstractMapElement{
    private MapDirection dir;
    public int energy;
    public int startEnergy;
    public int breedEnergyRequired;
    public int age=0;//age tells us how many days has animal survived
    private Genotype genotype;
    private long ID;

    Animals(RectangularMap map, Vector2d initialPosition, int startingEnergy) {
        super(map, initialPosition);
        this.genotype = new Genotype();
        this.dir=MapDirection.getRandDir();
        this.startEnergy=startingEnergy;
        this.breedEnergyRequired=startingEnergy/2;
        this.energy=startingEnergy;
    }
    Animals(RectangularMap map, Vector2d initialPosition, Genotype genotype, int energy, int startingEnergy) {
        super(map, initialPosition);
        this.genotype=genotype;
        this.dir=MapDirection.getRandDir();
        this.startEnergy=startingEnergy;
        this.breedEnergyRequired=startingEnergy/2;
        this.energy=energy;
    }

    public void move(){
        int turn=genotype.turnAnimal();
        for(int j=0; j < turn; j++){
            this.dir=this.dir.next();
        }
        Vector2d move=this.dir.toOneMoveVector();
        this.position=this.position.add(move);
        this.position=this.map.backIntoMap(this.position);
    }

    public MapDirection getDir(){return this.dir;}
    public Genotype getGenotype(){return this.genotype;}

    public boolean equals(Object other){
        if (this == other)
            return true;
        if (!(other instanceof Animals))
            return false;
        Animals that = (Animals) other;
        return(this.position.equals(that.position) && this.dir==that.dir);
    }

    public String toString(){
        //String pos="Zwierzę jest we współrzędnych (";
        //pos+=Integer.toString(coordinates.x)+","+Integer.toString(coordinates.y)+"), kierując się na "+dir.toString();
        //return pos;
        // return dir.toShortString();
        return "A";
    }

    public void setID(long ID){
        this.ID = ID;
    }
    public long getID(){
        return this.ID;
    }

}