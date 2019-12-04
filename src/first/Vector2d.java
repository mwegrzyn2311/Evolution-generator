package first;

public class Vector2d {
    int x;
    int y;
    public Vector2d(int x, int y){
        this.x=x;
        this.y=y;
    }
    public String toString(){
        String res="(";
        res=res+Integer.toString(x)+","+Integer.toString(y)+")";
        return res;
    }
    public boolean precedes(Vector2d other){
        return(this.x <= other.x &&  this.y <= other.y );
    }
    public boolean follows(Vector2d other){
        return(this.x >= other.x &&  this.y >= other.y);
    }
    public Vector2d upperRight(Vector2d other){
        if(this.precedes(other))
            return other;
        else if(this.follows(other))
            return this;
        else{
            int x;
            int y;
            if(this.x >= other.x) {
                x = this.x;
                y = other.y;
            }
            else {
                x = other.x;
                y = this.y;
            }
            return new Vector2d(x,y);
        }
    }
    public Vector2d lowerLeft(Vector2d other){
        if(this.precedes(other))
            return this;
        else if(this.follows(other))
            return other;
        else{
            int x;
            int y;
            if(this.x <= other.x) {
                x = this.x;
                y = other.y;
            }
            else {
                x = other.x;
                y = this.y;
            }
            return new Vector2d(x,y);
        }
    }
    public Vector2d add(Vector2d other){
        return new Vector2d(this.x+other.x,this.y+other.y);
    }
    public Vector2d subtract(Vector2d other){
        return new Vector2d(this.x-other.x,this.y-other.y);
    }
    public boolean equals(Object other){
        if (this == other)
            return true;
        if (!(other instanceof Vector2d))
            return false;
        Vector2d that = (Vector2d) other;
        return(this.x==that.x && this.y==that.y);
    }
    public Vector2d opposite(){
        return new Vector2d(-this.x,-this.y);
    }

    @Override
    public int hashCode(){
        int hash=13;
        hash+=this.x*17;
        hash+=this.y*31;
        return hash;
    }
}