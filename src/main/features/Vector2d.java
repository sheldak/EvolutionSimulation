package features;

public class Vector2d {
    // class containing position of elements at the map
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

    public Vector2d add(Vector2d other){
        return new Vector2d(this.x + other.x, this.y + other.y);
    }

    public boolean equals(Object other){
        if(this == other)
            return true;
        if(!(other instanceof Vector2d))
            return false;

        Vector2d that = (Vector2d) other;
        return (this.x == that.x && this.y == that.y);
    }

    public Vector2d opposite(){
        return new Vector2d(-this.x, -this.y);
    }

    public int getX(){
        return this.x;
    }

    public int getY(){
        return this.y;
    }
}
