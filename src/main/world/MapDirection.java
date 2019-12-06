package world;

import java.util.Random;

enum MapDirection {
    NORTH,
    NORTHEAST,
    EAST,
    SOUTHEAST,
    SOUTH,
    SOUTHWEST,
    WEST,
    NORTHWEST;

    public static MapDirection getRandomDirection(){
        int index = new Random().nextInt(8);
        return MapDirection.values()[index % MapDirection.values().length];
    }

    public String toString(){
        switch(this){
            case NORTH:
                return "North";
            case NORTHEAST:
                return "Northeast";
            case EAST:
                return "East";
            case SOUTHEAST:
                return "Southeast";
            case SOUTH:
                return "South";
            case SOUTHWEST:
                return "Southwest";
            case WEST:
                return "West";
            case NORTHWEST:
                return "Northwest";
            default:
                return "Error";
        }
    }

    public MapDirection change(int value) {
        return MapDirection.values()[(this.ordinal() + value) % MapDirection.values().length];
    }

    public Vector2d toUnitVector() {
        switch(this){
            case NORTH:
                return new Vector2d(0, 1);
            case NORTHEAST:
                return new Vector2d(1, 1);
            case EAST:
                return new Vector2d(1, 0);
            case SOUTHEAST:
                return new Vector2d(1,-1);
            case SOUTH:
                return new Vector2d(0,-1);
            case SOUTHWEST:
                return new Vector2d(-1, -1);
            case WEST:
                return new Vector2d(-1, 0);
            case NORTHWEST:
                return new Vector2d(-1, 1);
            default:
                return new Vector2d(0,0);
        }
    }
}
