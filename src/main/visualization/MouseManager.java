package visualization;

import features.Vector2d;
import map.WorldMap;
import mapElements.Animal;

public class MouseManager {
    private WorldMap mapA;
    private WorldMap mapB;

    private MenuPanel menuPanel;

    public MouseManager(WorldMap mapA, WorldMap mapB, MenuPanel menuPanel) {
        this.mapA = mapA;
        this.mapB = mapB;

        this.menuPanel = menuPanel;
    }

    public Animal getAnimalFromClick(double posX, double posY, WorldMap map) {
        if (menuPanel.getMenuState() == MenuState.FOLLOWING) {
            int xOnMap = (int) (posX / (400.0 / (double) map.getWidth()));
            int yOnMap = (int) ((400.0 - posY) / (400.0 / (double) map.getHeight())) + 1;

            Vector2d position = new Vector2d(xOnMap, yOnMap);

            if (map.isOccupied(position)) {
                Animal mostEnergeticAnimal = null;
                for(Object object : map.objectsAt(position)) {
                    if (object instanceof Animal && (mostEnergeticAnimal == null ||
                            ((Animal) object).getEnergy() > mostEnergeticAnimal.getEnergy())) {
                        mostEnergeticAnimal = (Animal) object;
                    }
                }
                return mostEnergeticAnimal;
            }
        }
        return null;
    }
}