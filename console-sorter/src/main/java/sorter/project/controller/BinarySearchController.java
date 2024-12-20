package sorter.project.controller;

import sorter.project.entity.Animal;
import sorter.project.entity.Barrel;
import sorter.project.entity.Human;
import sorter.project.entity.WorkingCollection;
import sorter.project.utils.BinarySearch;
import sorter.project.utils.QuickSort;
import sorter.project.utils.Validation;

import java.util.Comparator;
import java.util.Scanner;

public class BinarySearchController {
    public static void binarySearch() {
        if (!WorkingCollection.isSorted()) {
            System.out.println("Коллекция не отсортирована");
            return;
        }
        Scanner scanner = new Scanner(System.in);
        System.out.println("Выберите тип объектов для бинарного поиска:");
        System.out.println("1. Human");
        System.out.println("2. Animal");
        System.out.println("3. Barrel");
        System.out.println("4. Выход");
        int choice = scanner.nextInt();
        if (choice == 4) {
            return;
        }

        int attempts = 0;
        int maxAttempts = 5;
        String detected = scanner.nextLine();

        do {
            System.out.println("Введите искомый объект");
            detected = scanner.nextLine();

            if (Validation.generalValidation(detected) &&
                    (Validation.barrelValidation(detected) ||
                            Validation.animalValidation(detected) ||
                            Validation.manValidation(detected))) {
                break;
            } else {
                System.out.println("не валидно");
                attempts++;
            }
            if (attempts == maxAttempts) {
                System.out.println("превышено максимальное количество попыток");
                return;
            }

        } while (true);
        performBinarySearch(choice, detected);
        if ((WorkingCollection.getDetectedObject() == -1)) {
            System.out.println("Объект не найден");
        } else {
            System.out.println("Объект находится на " + WorkingCollection.getDetectedObject() + " строчке");
        }
    }

    private static void performBinarySearch(int typeChoice, String searchValue) {
        Comparator comparator;
        int first = 0;
        Object target;
        String[] value = searchValue.split(" ");
        switch (typeChoice) {
            case 1:
                for (int i = 0; i < WorkingCollection.getCollection().size(); i++) {
                    if (WorkingCollection.getCollection().get(i) instanceof Human) {
                        first = i;
                        break;
                    }
                }
                QuickSort<Human> quickSortHuman = new QuickSort<>();
                BinarySearch<Human> binarySearchHuman = new BinarySearch<>();
                String lastName = value[0];
                int age = 0;
                String gender = null;
                if (value.length >= 2) {
                    age = Integer.parseInt(value[1]);
                    if (value.length >= 3) {
                        gender = value[2];
                    }
                }
                target = new Human.HumanBuilder(lastName)
                        .setAge(age)
                        .setSex(gender)
                        .build();
                comparator = Comparator.comparing(o -> ((Human) o).getSurname());
                WorkingCollection.setDetectedObject(binarySearchHuman.search(quickSortHuman.sort(
                        SortTheCollectionController.humanCollection(WorkingCollection.getCollection()),
                        comparator), target, comparator) + first);
                break;
            case 2:
                for (int i = 0; i < WorkingCollection.getCollection().size(); i++) {
                    if (WorkingCollection.getCollection().get(i) instanceof Animal) {
                        first = i;
                        break;
                    }
                }
                QuickSort<Animal> quickSortAnimal = new QuickSort<>();
                BinarySearch<Animal> binarySearchAnimal = new BinarySearch<>();
                String types = value[0];
                boolean wool = false;
                String eyeColor = null;
                if (value.length >= 2) {
                    wool = Boolean.parseBoolean(value[1]);
                    if (value.length >= 3) {
                        eyeColor = value[2];
                    }
                }
                target = new Animal.AnimalBuilder(types)
                        .setEyeColor(eyeColor)
                        .setWool(wool)
                        .build();
                comparator = Comparator.comparing(o -> ((Animal) o).getType());
                WorkingCollection.setDetectedObject(binarySearchAnimal.search(quickSortAnimal.sort(
                        SortTheCollectionController.animalCollection(WorkingCollection.getCollection()),
                        comparator), target, comparator) + first);
                break;
            case 3:
                for (int i = 0; i < WorkingCollection.getCollection().size(); i++) {
                    if (WorkingCollection.getCollection().get(i) instanceof Barrel) {
                        first = i;
                        break;
                    }
                }
                QuickSort<Barrel> quickSortBarrel = new QuickSort<>();
                BinarySearch<Barrel> binarySearchBarrel = new BinarySearch<>();
                float volume = Float.parseFloat(value[0]);
                String material = null;
                String storedMaterial = null;
                if (value.length >= 2) {
                    material = value[1];
                    if (value.length >= 3) {
                        storedMaterial = value[2];
                    }
                }
                target = new Barrel.BarrelBuilder(volume)
                        .setMaterial(material)
                        .setStoredMaterial(storedMaterial)
                        .build();
                comparator = Comparator.comparingDouble(o -> ((Barrel) o).getVolume());
                WorkingCollection.setDetectedObject(binarySearchBarrel.search(quickSortBarrel.sort
                        (SortTheCollectionController.barrelCollection(WorkingCollection.getCollection()),
                        comparator), target, comparator) + first);
                break;
            default:
                System.out.println("Неверный выбор!");
        }
    }
}
