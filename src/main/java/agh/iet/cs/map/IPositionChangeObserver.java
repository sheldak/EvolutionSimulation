package agh.iet.cs.map;

import agh.iet.cs.features.Vector2d;
import agh.iet.cs.mapElements.Animal;

public interface IPositionChangeObserver {
    void changePosition(Vector2d oldPosition, Vector2d newPosition, Animal animal);
}
