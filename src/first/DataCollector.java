package first;

public class DataCollector {
    public long summaryDeathAge=0;
    public int deadAnimalsCount=0;
    private RectangularMap map;
    private int[] genes;
    public DataCollector(RectangularMap map){
        this.genes = new int[8];
        for(int i=0; i<8; i++){
            this.genes[i] = 0;
        }
        this.map = map;
    }


    public void updateAnimalsGeneStats(Animals animal){
        Genotype genotype = animal.getGenotype();
        for(int i=0; i<32; i++){
            genes[genotype.values[i]]++;
        }
    }
    public int getAnimalsCount(){
        return this.map.animals.size();
    }
    public int getGrassCount(){
        return this.map.grass.size();
    }
    public int getDominatingGene(){
        int max = 0;
        int maxCount = this.genes[0];
        for(int i=1; i<8; i++){
            if(maxCount < this.genes[i]){
                max = i;
                maxCount = this.genes[i];
            }
        }
        return max;
    }
    public float getAverageEnergy(){
        long sum = 0;
        for(Animals animal: this.map.animals){
            sum += animal.energy;
        }
        return (sum/(float)this.map.animals.size());
    }
    public float getAverageDeathAge(){
        return (summaryDeathAge/(float)deadAnimalsCount);
    }
    public float getAverageChildrenCount(){
        int sum = 0;
        for(Animals animal: this.map.animals){
            sum += animal.childrenCounter;
        }
        return (sum/(float)this.map.animals.size());
    }



    public void printAnimalsAge(){
        System.out.println("Still living animals' ages:");
        for(Animals animal : this.map.animals){
            if(animal!=this.map.animals.get(0))
                System.out.print(", "+animal.age);
            else
                System.out.print(animal.age);
        }
        System.out.println(" ");
    }
}
