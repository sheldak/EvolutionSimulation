package world;

public class Grass implements IMapElement {
    private Vector2d position;

    Grass(int x, int y){
        this.position = new Vector2d(x, y);
    }

    public String toString(){
        return " * ";
    }

    public Vector2d getPosition(){
        return this.position;
    }
}

