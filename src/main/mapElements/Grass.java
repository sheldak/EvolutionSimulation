package mapElements;

import features.Vector2d;

public class Grass implements IMapElement {
    // class with features of grass (so just position)
    private Vector2d position;

    public Grass(int x, int y){
        this.position = new Vector2d(x, y);
    }

    public String toString(){
        return " * ";
    }

    public Vector2d getPosition(){
        return this.position;
    }
}

