package first;

import java.util.Arrays;

public class Genotype {
    //TODO test Genotype() and turnAnimal() [random functions] and then (???)delete(???) exceptions
    public int[] values;
    public Genotype(){
        int[] present = new int[8];
        for(int i=0; i<8; i++)
            present[i]=0;
        this.values=new int[32];
        for(int i=0; i<32; i++){
            this.values[i]= (int) (Math.random()*8);
            present[this.values[i]]++;
        }
        // Here is checking if there is at least one of each gene in the genotype
        for(int i=0;i<8;i++){
            if(present[i] == 0){
                int a;
                do{
                    a = (int)(Math.random()*32);
                }while(present[values[a]] < 2);
                present[values[a]]--;
                values[a] = i;
                present[i]++;
            }
        }
        // And sorting the array
        Arrays.sort(values);
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

        // Here is checking if there is at least one of each gene in the genotype
        int[] present = new int[8];
        for(i=0; i<8; i++)
            present[i] = 0;
        for(i=0; i<32; i++){
            present[values[i]]++;
        }

        for(i=0;i<8;i++){
            if(present[i] == 0){
                int a;
                do{
                    a = (int)(Math.random()*32);
                }while(present[values[a]] < 2);
                present[values[a]]--;
                values[a] = i;
                present[i]++;
            }
        }
        // And sorting the array
        Arrays.sort(values);
    }

    public int turnAnimal(){
        int i=(int) (Math.random()*32);
        return this.values[i];
    }
}
