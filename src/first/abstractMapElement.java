package first;

abstract class abstractMapElement implements IMapElement{
    protected Vector2d position;
    protected RectangularMap map;
    abstractMapElement(RectangularMap map, Vector2d initialPosition){
        this.map=map;
        this.position=initialPosition;
    }
    public Vector2d getPosition(){
        return this.position;
    }
}
