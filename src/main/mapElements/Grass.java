package mapElements;

import features.Vector2d;

public class Grass implements IMapElement {
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

