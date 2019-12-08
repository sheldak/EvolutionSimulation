package world;

import java.util.*;

public class WorldMap implements IWorldMap, IPositionChangeObserver {
    public static int moveEnergy;

    private final int width;
    private final int height;

    private final int jungleWidth;
    private final int jungleHeight;
    private final Vector2d jungleStart;

    private final int plantEnergy;

    private List<Animal> animals = new ArrayList<>();
    private Map<Vector2d, List<Object>> elementsMap = new HashMap<>();

    WorldMap(int width, int height, double jungleRatio, int plantEnergy, int moveEnergy) {
        this.width = width;
        this.height = height;

        this.jungleWidth = (int) ((double) width * jungleRatio);
        this.jungleHeight = (int) ((double) height * jungleRatio);
        this.jungleStart = new Vector2d((this.width - this.jungleWidth)/2, (this.height - this.jungleHeight)/2);

        this.plantEnergy = plantEnergy;
        WorldMap.moveEnergy = moveEnergy;

        for (int i=0; i<width; i++) {
            for (int j=0; j<height; j++) {
                List<Object> newList = new ArrayList<>();
                elementsMap.put(new Vector2d(i, j), newList);
            }
        }
    }

    @Override
    public String toString() {
        return this.draw();
    }

    @Override
    public void run() {
        List <Vector2d> toCheck = new ArrayList<>();
        for (Animal animal : this.animals) {
            toCheck.add(animal.move());
        }

        this.checkGrassConsumption(toCheck);
        this.checkReproduction(toCheck);

    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return !this.objectsAt(position).isEmpty();
    }

    @Override
    public List<Object> objectsAt(Vector2d position) {
        return this.elementsMap.get(position);
    }

    @Override
    public void changePosition(Vector2d oldPosition, Vector2d newPosition, Animal animal) {
        elementsMap.get(oldPosition).remove(animal);
        elementsMap.get(newPosition).add(animal);
    }

    Vector2d correctDestination(Vector2d destination){
        return new Vector2d((destination.getX() + this.width) % this.width,
                (destination.getY() + this.height) % this.height);
    }

    void nextDay(){
        this.getRidOfDeadAnimals();

        this.run();

        this.placeGrassInJungle();
        this.placeGrassInSavannah();
    }

    void placeInitialAnimals(int number, int startEnergy) {
        for (int i=0; i<number; i++) {
            int animalX = new Random().nextInt(this.width);
            int animalY = new Random().nextInt(this.height);

            for (int j = 0; j < this.width * this.height; j++) {
                if (!isOccupied(new Vector2d(animalX, animalY))) { // when there is no other animal on this place
                    Animal newAnimal = new Animal(this, animalX, animalY, MapDirection.getRandomDirection(),
                            Animal.getRandomGenome(), startEnergy, startEnergy);

                    newAnimal.addObserver(this);

                    this.animals.add(newAnimal);
                    elementsMap.get(new Vector2d(animalX, animalY)).add(newAnimal);

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

    Vector2d getBabyPosition(Vector2d parentsPosition) {
        MapDirection babyPosition = MapDirection.getRandomDirection();
        for (int i=0; i<8; i++) {
            if (!this.isOccupied(parentsPosition.add(babyPosition.toUnitVector()))) {
                return parentsPosition.add(babyPosition.toUnitVector());
            }
            babyPosition.change(1);
        }
        return parentsPosition;
    }

    private void checkGrassConsumption(List <Vector2d> positions) {
        for (Vector2d position : positions) {
            Grass grass = null;

            for (Object element : elementsMap.get(position)) {
                if (element instanceof Grass) {
                    grass = (Grass) element;
                    break;
                }
            }

            if (grass != null) {
                Animal eater = null;

                for (Object element : elementsMap.get(position)) {
                    if (element instanceof Animal &&
                            (eater == null || ((Animal) element).getEnergy() > eater.getEnergy()))
                        eater = (Animal) element;
                }

                if (eater != null)
                    eater.consumeGrass(this.plantEnergy);

                this.elementsMap.get(position).remove(grass);
            }
        }
    }

    private void checkReproduction(List <Vector2d> positions) {
        for (Vector2d position : positions) {
            if (elementsMap.get(position).size() >=2) {  // there are at least 2 animals to reproduce
                Animal firstAnimal = null;
                Animal secondAnimal = null;

                for (Object element : elementsMap.get(position)) {  // finding a pair with the highest level of energy
                    Animal currAnimal = (Animal) element;
                    if (firstAnimal == null || currAnimal.getEnergy() > firstAnimal.getEnergy()) {
                        secondAnimal = firstAnimal;
                        firstAnimal = currAnimal;
                    }
                    else if (secondAnimal == null || currAnimal.getEnergy() > secondAnimal.getEnergy())
                        secondAnimal = currAnimal;
                }

                if (firstAnimal != null && secondAnimal != null &&
                        firstAnimal.hasMinimumReproductionEnergy() && secondAnimal.hasMinimumReproductionEnergy()) {
                    Animal babyAnimal = firstAnimal.reproduce(secondAnimal);

                    this.animals.add(babyAnimal);
                    this.elementsMap.get(babyAnimal.getPosition()).add(babyAnimal);
                }
            }
        }
    }

    private void getRidOfDeadAnimals() {
        List<Animal> animalsToRemove = new ArrayList<>();
        for (Animal animal : this.animals) {
            if (animal.isDead()) {
                animalsToRemove.add(animal);
            }
        }

        for (Animal animal : animalsToRemove) {
            this.elementsMap.get(animal.getPosition()).remove(animal);
            this.animals.remove(animal);
        }
    }

    private void placeGrassInJungle() {
        int grassX = this.jungleStart.getX() + new Random().nextInt(this.jungleWidth);
        int grassY = this.jungleStart.getY() + new Random().nextInt(this.jungleHeight);

        for (int i = 0; i < this.jungleWidth * this.jungleHeight; i++){
            if (!isOccupied(new Vector2d(grassX, grassY))){ // when there is no other object on this place
                Grass newGrass = new Grass(grassX, grassY);
                this.elementsMap.get(new Vector2d(grassX, grassY)).add(newGrass);
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
                this.elementsMap.get(new Vector2d(grassX, grassY)).add(newGrass);
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
