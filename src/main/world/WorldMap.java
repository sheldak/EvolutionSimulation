package world;

import java.util.*;

public class WorldMap implements IWorldMap, IPositionChangeObserver {
    private final int width;
    private final int height;

    private final int jungleWidth;
    private final int jungleHeight;
    private final Vector2d jungleStart;

    private final int plantEnergy;

    private List<Animal> animals = new ArrayList<>();
    private Map<Vector2d, IMapElement> elementsMap = new HashMap<>();
    private List<Grass> grasses = new ArrayList<>();

    public WorldMap(int width, int height, double jungleRatio, int plantEnergy) {
        this.width = width;
        this.height = height;

        this.jungleWidth = (int) ((double) width * jungleRatio);
        this.jungleHeight = (int) ((double) height * jungleRatio);
        this.jungleStart = new Vector2d((this.width - this.jungleWidth)/2, (this.height - this.jungleHeight)/2);

        this.plantEnergy = plantEnergy;
    }

    @Override
    public String toString() {
        return this.draw();
    }

    @Override
    public boolean canMoveTo(Vector2d position) { // TODO delete it
        return true;
    }

    @Override
    public void run() {
        for (Animal animal : this.animals) {
            animal.move();
        }
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return this.objectAt(position) != null;
    }

    @Override
    public Object objectAt(Vector2d position) {
        return this.elementsMap.get(position);
    }

    @Override
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition) {
        Animal animal = (Animal) this.elementsMap.get(oldPosition);
        this.elementsMap.remove(oldPosition);

        this.elementsMap.put(newPosition, animal);
    }

    public Vector2d correctDestination(Vector2d destination){
        return new Vector2d((destination.getX() + this.width) % this.width,
                (destination.getY() + this.height) % this.height);
    }

    public void nextDay(){
        this.getRidOfDeadAnimals();

        this.run();

        this.placeGrassInJungle();
        this.placeGrassInSavannah();
    }

    public void placeInitialAnimals(int number, int startEnergy, int moveEnergy) {
        for (int i=0; i<number; i++) {
            int animalX = new Random().nextInt(this.width);
            int animalY = new Random().nextInt(this.height);

            for (int j = 0; j < this.width * this.height; j++) {
                if (!isOccupied(new Vector2d(animalX, animalY))) { // when there is other animal on this place
                    Animal newAnimal = new Animal(this, animalX, animalY, MapDirection.getRandomDirection(),
                            Animal.getRandomGenome(), startEnergy, startEnergy, moveEnergy);

                    newAnimal.addObserver(this);

                    this.animals.add(newAnimal);
                    this.elementsMap.put(new Vector2d(animalX, animalY), newAnimal);

                    break;
                }
                else {
                    animalX = (animalX + 1) % this.width;

                    if (animalX % this.width == 0) {
                        animalY = (animalY + 1) % this.height;
                    }
                }
            }
        }
    }

//    public void consumeGrass(Vector2d position) {
//        Object object = this.objectAt(position);
//
//        if (object instanceof Grass) {
//            this.placeGrass(1);
//
//            grasses.remove(object);
//            mapBoundary.deleteObject(((Grass) object).getPosition());
//
//            this.elementsMap.remove(((Grass) object).getPosition());
//        }
//    }

    private void getRidOfDeadAnimals() {
        for (Animal animal : animals) {
            if (animal.isDead()) {
                this.elementsMap.remove(animal);
                this.animals.remove(animal);
            }
        }
    }

    private void placeGrassInJungle() {
        int grassX = this.jungleStart.getX() + new Random().nextInt(this.jungleWidth);
        int grassY = this.jungleStart.getY() + new Random().nextInt(this.jungleHeight);

        for (int i = 0; i < this.jungleWidth * this.jungleHeight; i++){
            if (!isOccupied(new Vector2d(grassX, grassY))){ // when there is other object on this place
                Grass newGrass = new Grass(grassX, grassY);
                this.elementsMap.put(new Vector2d(grassX, grassY), newGrass);
                this.grasses.add(newGrass);
                break;
            }
            else{ // changing position by 1
                grassX = (grassX + 1 - this.jungleStart.getX()) % this.jungleWidth + this.jungleStart.getX();

                if (grassX == this.jungleStart.getX())
                    grassY = (grassY + 1 - this.jungleStart.getY()) % this.jungleHeight + this.jungleStart.getY();
            }
        }
    }

    private void placeGrassInSavannah() {
        /*      areas and their names
        *        *******************
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

        int positionFromBeginning = new Random().
                nextInt(this.width * this.height - this.jungleWidth * this.jungleHeight);

        int AreaASize = this.width * this.jungleStart.getY();
        int AreaBSize = this.jungleHeight * this.jungleStart.getX();
        int AreaCSize = this.jungleHeight * (this.width - this.jungleWidth - this.jungleStart.getX());

        if (positionFromBeginning < AreaASize) { // new grass in area A
            grassX = positionFromBeginning % this.width;
            grassY = positionFromBeginning / this.width;
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
            grassX = positionInAreaD % this.width;
            grassY = this.jungleStart.getY() + this.jungleHeight + positionInAreaD / this.width;
        }

        for (int i = 0; i < this.width * this.height - this.jungleWidth * this.jungleHeight; i++) {
            if (!isOccupied(new Vector2d(grassX, grassY))){ // when there is other object on this place
                Grass newGrass = new Grass(grassX, grassY);
                this.elementsMap.put(new Vector2d(grassX, grassY), newGrass);
                this.grasses.add(newGrass);
                break;
            }
            else{ // changing position by 1
                grassX = (grassX + 1) % this.width;

                if (grassX == 0)
                    grassY = (grassY + 1) % this.height;

                if (grassY >= jungleStart.getY() && grassY < jungleStart.getY() + jungleHeight
                        && grassX == jungleStart.getX()) { // to not place grass in the jungle (jump to area C)
                    grassX += jungleWidth;
                }
            }
        }
    }

    private String draw(){
        MapVisualizer mapVisualizer = new MapVisualizer(this);

        Vector2d leftDown = new Vector2d(0,0);
        Vector2d rightUp = new Vector2d(this.width-1, this.height-1);

        return mapVisualizer.draw(leftDown, rightUp);
    }
}
