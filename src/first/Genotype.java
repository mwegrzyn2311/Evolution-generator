package first;

public class Genotype {
    //TODO test Genotype() and turnAnimal() [random functions] and then (???)delete(???) exceptions
    public int[] values;
    public Genotype(){
        this.values=new int[32];
        for(int i=0; i<32; i++){
            this.values[i]= (int) (Math.random()*8);
            if(this.values[i] < 0 || this.values[i] > 7){
                throw new IllegalArgumentException("Genotype generator needs to be fixed");
            }
        }
    }

    public Genotype(Genotype first, Genotype second){
        //Randomly chooses exactly 16 genes from first parent and the same ammount from the second one
        this.values=new int[32];
        int x=(int)(Math.random() * 30)+1;//{1,2,...,30}
        int y;
        do{
            y=(int)(Math.random()*30)+1;
        }while(y==x);
        //We want y to be greater than x
        if(x>y){
            int tmp=y;
            y=x;
            x=tmp;
        }
        int i;
        for(i = 0; i < x; i++){
            this.values[i]=first.values[i];
        }
        for(i=x; i<y; i++){
            this.values[i]=second.values[i];
        }
        for(i=y;i<32;i++){
            this.values[i]=first.values[i];
        }

        int randomGeneChance = 5;
        int random = (int)(Math.random()*100);
        if(random < randomGeneChance){
            int gene = (int)(Math.random()*32);
            this.values[gene] = (int)(Math.random()*8);
        }
    }

    public int turnAnimal(){
        int i=(int) (Math.random()*32);
        return this.values[i];
    }
}
