package first;

public class SpiedAnimal {
    public Animals animal;
    private int initialChildrenCount;
    public int deathDay = -1;

    public SpiedAnimal(Animals animal){
        this.animal = animal;
        this.initialChildrenCount = animal.childrenCount;
    }

    public int recentChildrenCount(){
        return this.animal.childrenCount - this.initialChildrenCount;
    }
}
