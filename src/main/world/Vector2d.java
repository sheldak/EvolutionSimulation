package world;

public class Vector2d {
    private final int x;
    private final int y;

    public Vector2d(int x, int y){
        this.x = x;
        this.y = y;
    }

    public String toString(){
       return "(" + this.x + "," + this.y + ")";
    }

    public int hashCode() {
        int hash = 13;
        hash += this.x * 31;
        hash += this.y * 17;
        return hash;
    }

    boolean equals(Vector2d other) {
        return this.hashCode() == other.hashCode();
    }

    boolean precedes(Vector2d other){
        return (this.x <= other.x && this.y <= other.y);
    }

    boolean follows(Vector2d other){
        return (this.x >= other.x && this.y >= other.y);
    }

    Vector2d upperRight(Vector2d other){
        int up;
        int right;

        up = Math.max(this.y, other.y);
        right = Math.max(this.x, other.x);

        return new Vector2d(right, up);
    }

    Vector2d lowerLeft(Vector2d other){
        int down;
        int left;

        down = Math.min(this.y, other.y);
        left = Math.min(this.x, other.x);

        return new Vector2d(left, down);
    }

    Vector2d add(Vector2d other){
        return new Vector2d(this.x + other.x, this.y + other.y);
    }

    Vector2d subtract(Vector2d other){
        return new Vector2d(this.x - other.x, this.y - other.y);
    }

    public boolean equals(Object other){
        if(this == other)
            return true;
        if(!(other instanceof Vector2d))
            return false;

        Vector2d that = (Vector2d) other;
        return (this.x == that.x && this.y == that.y);
    }

    Vector2d opposite(){
        return new Vector2d(-this.x, -this.y);
    }

    int getX(){
        return this.x;
    }

    int getY(){
        return this.y;
    }
}
