package map;

import features.Vector2d;
import mapElements.Animal;

public interface IPositionChangeObserver {
    void changePosition(Vector2d oldPosition, Vector2d newPosition, Animal animal);
}
