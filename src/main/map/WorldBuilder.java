package map;

import features.MapDirection;
import features.Vector2d;
import mapElements.Animal;
import features.Genome;
import mapElements.Grass;

import java.util.Random;

public class WorldBuilder {
    // it builds world map by finding appropriate coordinates to place animals and grass
    private WorldMap map;

    private int mapWidth;
    private int mapHeight;

    private int jungleWidth;
    private int jungleHeight;
    private Vector2d jungleStart;

    public WorldBuilder(WorldMap map) {
        this.map = map;
    }

    void passMapSize(int width, int height) {
        this.mapWidth = width;
        this.mapHeight = height;
    }

    void placeJungle(double jungleRatio){ // initializing variables connected with size of the jungle
        this.jungleWidth = (int) ((double) this.mapWidth * jungleRatio);
        this.jungleHeight = (int) ((double) this.mapHeight * jungleRatio);
        this.jungleStart = new Vector2d(
                (this.mapWidth - this.jungleWidth)/2, (this.mapHeight - this.jungleHeight)/2);
    }

    void placeInitialAnimals(int number) { // finding space for animals added at the beginning of the world
        for (int i=0; i<number; i++) {
            int animalX = new Random().nextInt(this.mapWidth);
            int animalY = new Random().nextInt(this.mapHeight);

            for (int j = 0; j < this.mapWidth * this.mapHeight; j++) {
                // when there is no other animal on this place
                if (!this.map.isOccupied(new Vector2d(animalX, animalY))) {
                    Animal newAnimal = new Animal(this.map, animalX, animalY, MapDirection.getRandomDirection(),
                            new Genome(), Animal.startEnergy);

                    this.map.addAnimal(newAnimal);

                    break;
                }
                else { // changing position by 1 until finding free place
                    animalX = (animalX + 1) % mapWidth;

                    if (animalX % mapWidth == 0) {
                        animalY = (animalY + 1) % mapHeight;
                    }
                }
            }
        }
    }

    Vector2d getBabyPosition(Vector2d parentsPosition) { // looking for position for the new animal
        MapDirection babyPosition = MapDirection.getRandomDirection();
        for (int i=0; i<8; i++) {
            if (!this.map.isOccupied(map.correctDestination(parentsPosition.add(babyPosition.toUnitVector())))) { // TODO correct destination to world builder
                return map.correctDestination(parentsPosition.add(babyPosition.toUnitVector()));
            }
            babyPosition.change(1);
        }
        return parentsPosition;
    }

    void placeGrassInJungle() {
        int grassX = this.jungleStart.getX() + new Random().nextInt(this.jungleWidth);
        int grassY = this.jungleStart.getY() + new Random().nextInt(this.jungleHeight);

        for (int i = 0; i < this.jungleWidth * this.jungleHeight; i++){
            if (!this.map.isOccupied(new Vector2d(grassX, grassY))){ // when there is no other object on this place
                Grass newGrass = new Grass(grassX, grassY);
                this.map.addGrass(newGrass);
                break;
            }
            else{ // changing position by 1 until finding free place
                grassX = (grassX + 1 - this.jungleStart.getX()) % this.jungleWidth + this.jungleStart.getX();

                if (grassX == this.jungleStart.getX())
                    grassY = (grassY + 1 - this.jungleStart.getY()) % this.jungleHeight + this.jungleStart.getY();
            }
        }
    }

    void placeGrassInSavannah() {
        /*       savannah is divided on 4 areas, there is equal probability for every place to get grass,
                 for every grass position is randomized just once (then changed by 1 until finding free place)
                 *******************
                 *********D*********
                 *******************
                 *******JJJJJ*******
                 ***B***JJJJJ***C***
                 *******JJJJJ*******
                 *******************
                 *********A*********
                 *******************
         **/
        int grassX;
        int grassY;

        // every number represents one place in the savannah
        int positionFromBeginning = new Random().
                nextInt(this.mapWidth * this.mapHeight - this.jungleWidth * this.jungleHeight);

        int AreaASize = this.mapWidth * this.jungleStart.getY();
        int AreaBSize = this.jungleHeight * this.jungleStart.getX();
        int AreaCSize = this.jungleHeight * (this.mapWidth - this.jungleWidth - this.jungleStart.getX());

        if (positionFromBeginning < AreaASize) { // new grass in area A
            grassX = positionFromBeginning % this.mapWidth;
            grassY = positionFromBeginning / this.mapWidth;
        }
        else if (positionFromBeginning < AreaASize + AreaBSize) { // new grass in area B
            int positionInAreaB = positionFromBeginning - AreaASize;
            grassX = positionInAreaB % (AreaBSize / this.jungleHeight);
            grassY = this.jungleStart.getY() + positionInAreaB / (AreaBSize / this.jungleHeight);
        }
        else if (positionFromBeginning < AreaASize + AreaBSize + AreaCSize) { // new grass in area C
            int positionInAreaC = positionFromBeginning - AreaASize - AreaBSize;
            grassX = this.jungleStart.getX() + this.jungleWidth + (positionInAreaC % (AreaCSize / this.jungleHeight));
            grassY = this.jungleStart.getY() + positionInAreaC / (AreaCSize / this.jungleHeight);
        }
        else { // new grass in area D
            int positionInAreaD = positionFromBeginning - AreaASize - AreaBSize - AreaCSize;
            grassX = positionInAreaD % this.mapWidth;
            grassY = this.jungleStart.getY() + this.jungleHeight + positionInAreaD / this.mapWidth;
        }

        // when found place is occupied
        for (int i = 0; i < this.mapWidth * this.mapHeight - this.jungleWidth * this.jungleHeight; i++) {
            if (!this.map.isOccupied(new Vector2d(grassX, grassY))){ // when there is no other object on this place
                Grass newGrass = new Grass(grassX, grassY);
                this.map.addGrass(newGrass);
                break;
            }
            else{ // changing position by 1 until finding free place
                grassX = (grassX + 1) % this.mapWidth;

                if (grassX == 0)
                    grassY = (grassY + 1) % this.mapHeight;

                if (grassY >= jungleStart.getY() && grassY < jungleStart.getY() + jungleHeight
                        && grassX == jungleStart.getX()) { // to not place grass in the jungle (jump to area C)
                    grassX += jungleWidth;
                }
            }
        }
    }
}
