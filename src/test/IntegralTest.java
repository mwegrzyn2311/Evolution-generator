
import first.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class IntegralTest {
    /*
        @Test
        public void unboundedMapTest(){

            String[] args={"r", "l", "r", "f", "l", "f", "f", "f","f"};
            MoveDirection[] directions = new OptionsParser().parse(args);
            List<HayStack> hayStacks=new ArrayList<HayStack>();
            hayStacks.add(new HayStack(new Vector2d(-4,-4)));
            hayStacks.add(new HayStack(new Vector2d(7,7)));
            hayStacks.add(new HayStack(new Vector2d(3,6)));
            hayStacks.add(new HayStack(new Vector2d(2,0)));

            IWorldMap map = new UnboundedMap(hayStacks);
            map.place(new Animals(map,new Vector2d(0,0)));
            map.place(new Animals(map,new Vector2d(2,1)));
            map.place(new Animals(map,new Vector2d(2,6)));


            IWorldMap map2 = new UnboundedMap(hayStacks);
            map2.place(new Animals(map,new Vector2d(1,0)));
            map2.place(new Animals(map,new Vector2d(2,1)));
            map2.place(new Animals(map,new Vector2d(2,6)));
            String[] args2={"r", "l", "r", "f", "l"};
            MoveDirection[] directions2 = new OptionsParser().parse(args2);
            map2.run(directions2);
            map.run(directions);
            Assert.assertEquals(map2.toString(), map.toString());
        }

     */

       /*
    @Test
    public void rectangularMapTest(){
        String[] args={"f", "r", "b", "l", "l", "l", "b", "b", "b", "r", "b", "b", "b", "r", "l", "b", "l", "l", "f", "b", "f", "r", "f", "b", "l", "l", "f", "b", "f", "r", "f", "b", "l", "l", "f", "b", "f", "r", "f", "b", "l", "l", "f", "b", "r", "r", "f", "b", "f", "l","f","l"};
        MoveDirection[] directions = new OptionsParser().parse(args);
        IWorldMap map = new RectangularMap(10, 10);
        map.place(new Animals(map,new Vector2d(0,0)));
        map.place(new Animals(map,new Vector2d(2,1)));
        map.place(new Animals(map,new Vector2d(9,9)));
        map.place(new Animals(map,new Vector2d(2,6)));

        Animals[] animals={new Animals(map,new Vector2d(2,0)),
                           new Animals(map,new Vector2d(2,1)),
                           new Animals(map,new Vector2d(7,6)),
                           new Animals(map,new Vector2d(6,6))};
        animals[1].move(MoveDirection.RIGHT);
        animals[2].move(MoveDirection.LEFT);
        animals[3].move(MoveDirection.RIGHT);
        animals[3].move(MoveDirection.RIGHT);
        //System.out.println(map.toString());
        map.run(directions);
        //System.out.println(map.toString());
        for(int i=0;i<4;i++){
            Assert.assertEquals(((RectangularMap) map).animals.get(i),animals[i]);
        }
    }
*/

    /*
    @Test
    public void animalsTests(){
        Animals animal=new Animals();
        Assert.assertEquals(animal.dir, MapDirection.NORTH);
        String[] leaves ={"b","b","b","b","l","f","f","f","kl","afsafa","f","r","f","f","f","f","f","f","f","f","f","l","l","l","f","f","f","f","f","f"};//(2,2)->(2,0)->tries to leave, turns left->(0,0)->tries to leave->turns right->(4,4)
        MoveDirection[] moves= OptionsParser.parse(leaves);
        Vector2d pos1 = new Vector2d(0,0);
        Vector2d pos2 = new Vector2d(4,4);
        for(MoveDirection move : moves){
            animal.move(move);
            Assert.assertTrue(pos1.precedes(animal.coordinates));
            Assert.assertTrue(pos2.follows(animal.coordinates));
        }


        Animals animal2=new Animals();
        String[] interprets ={"f","kl","forward","alamakota","aolamapsa","b","backward","k","j","r","right","l","qwerty","asdfgh","left","hlakbr"};
        MoveDirection[] expectedMoveDir={MoveDirection.FORWARD,MoveDirection.FORWARD,MoveDirection.BACKWARD,MoveDirection.BACKWARD,MoveDirection.RIGHT,MoveDirection.RIGHT,MoveDirection.LEFT,MoveDirection.LEFT};
        MapDirection[] expectedMapDir={MapDirection.NORTH,MapDirection.NORTH,MapDirection.NORTH,MapDirection.NORTH,MapDirection.EAST,MapDirection.SOUTH,MapDirection.EAST,MapDirection.NORTH};
        Vector2d[] expectedVector={new Vector2d(2,3), new Vector2d(2,4), new Vector2d(2,3), new Vector2d(2,2), new Vector2d(2,2), new Vector2d(2,2), new Vector2d(2,2), new Vector2d(2,2)};
        MoveDirection[] parsed=OptionsParser.parse(interprets);
        Assert.assertEquals(parsed.length, 8);
        int i=0;
        for(MoveDirection move:parsed){
            animal2.move(move);
            Assert.assertEquals(move,expectedMoveDir[i]);
            Assert.assertEquals(animal2.dir,expectedMapDir[i]);
            Assert.assertEquals(animal2.coordinates,expectedVector[i++]);
        }



    }
    */
}

