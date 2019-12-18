package first;

import java.util.Map;

public enum MapDirection{
    //TODO: Check if after finishing we need to keep previous() function
    NORTH,
    EAST,
    SOUTH,
    WEST,
    NORTH_EAST,
    SOUTH_EAST,
    SOUTH_WEST,
    NORTH_WEST;

    public String toString(){
        switch(this){
            case NORTH: return "Północ";
            case EAST: return "Wschód";
            case SOUTH: return "Południe";
            case WEST: return "Zachód";
            case NORTH_EAST: return "Północny Wschód";
            case SOUTH_EAST: return "Południowy Wschód";
            case SOUTH_WEST: return "Południowy Zachód";
            case NORTH_WEST: return "Północny Zachód";
            default: return null;
        }
    }

    public String toShortString(){
        //If it is not understood, look at numeric keyboard
        switch(this){
            case NORTH: return "8";
            case EAST: return "6";
            case SOUTH: return "2";
            case WEST: return "4";
            case NORTH_EAST: return "9";
            case SOUTH_EAST: return "3";
            case SOUTH_WEST: return "1";
            case NORTH_WEST: return "7";
            default: return null;
        }
    }

    public MapDirection next(){
        switch(this){
            case NORTH: return NORTH_EAST;
            case NORTH_EAST: return EAST;
            case EAST: return SOUTH_EAST;
            case SOUTH_EAST: return SOUTH;
            case SOUTH: return SOUTH_WEST;
            case SOUTH_WEST: return WEST;
            case WEST: return NORTH_WEST;
            case NORTH_WEST: return NORTH;
            default: return null;
        }
    }
    public MapDirection previous(){
        switch(this){
            case NORTH: return NORTH_WEST;
            case NORTH_EAST: return NORTH;
            case EAST: return NORTH_EAST;
            case SOUTH_EAST: return EAST;
            case SOUTH: return SOUTH_EAST;
            case SOUTH_WEST: return SOUTH;
            case WEST: return SOUTH_WEST;
            case NORTH_WEST: return WEST;
            default: return null;
        }
    }

    public static MapDirection getRandDir(){
        MapDirection res=NORTH;
        int turns=(int)(Math.random()*8);
        for(int i=0;i<turns;i++){
            res=res.next();
        }
        return res;
    }

    public Vector2d toOneMoveVector(){
        switch(this){
            case NORTH: return new Vector2d(0,1);
            case NORTH_EAST: return new Vector2d(1,1);
            case EAST: return new Vector2d(1,0);
            case SOUTH_EAST: return new Vector2d(1,-1);
            case SOUTH: return new Vector2d(0,-1);
            case SOUTH_WEST: return new Vector2d(-1,-1);
            case WEST: return new Vector2d(-1,0);
            case NORTH_WEST: return new Vector2d(-1,1);
            default: return null;
        }
    }
}