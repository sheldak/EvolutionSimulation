package world;

public interface IPositionChangeObserver {
    void changePosition(Vector2d oldPosition, Vector2d newPosition, Animal animal);
}
