package first;

/**
 * The interface responsible for keeping what various similar but not same objects in one "place"
 * Assumes that Vector2d is defined
 *
 */
public interface IMapElement{
    /**
     * Return position of the element
     *
     * @return position of the element as Vector2d
     */
    public Vector2d getPosition();
    /**
     * Return string form of the object
     *
     * @return appropriate string depending on the object
     */
    public String toString();
}
