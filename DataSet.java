package search;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class DataSet {

    private final ArrayList <Person> peopleList = new ArrayList<>();
    private final Scanner scanner = new Scanner(System.in);
    private static int counter = 0;
    private final Map<String, ArrayList <Integer>> invertedIndex = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);



   public void addPerson(Person person) {
        for (String s : person.getPersonArray()) {
            if (!invertedIndex.containsKey(s)) {
                ArrayList <Integer> arrayList = new ArrayList<>();
                arrayList.add(counter);
                invertedIndex.put(s,arrayList);
            } else {
                invertedIndex.get(s).add(counter);
            }
        }
        counter++;
        peopleList.add(person);
    }



    public DataSet(File file) throws FileNotFoundException {
        try (Scanner scannerTwo = new Scanner(file)) {
            while (scannerTwo.hasNext()) {
                addPerson(new Person(scannerTwo.nextLine().split(" ")));
            }
        }
    }

    public void showMenu() {
        boolean exit = true;
        while (exit) {
            System.out.println("=== Menu ===");
            System.out.print("1. Find a person\n" +
                    "2. Print all people\n" +
                    "0. Exit\n");
            int choice = Integer.parseInt(scanner.nextLine());
            System.out.println();
            switch (choice) {
                case 1: {
                    System.out.println("Select a matching strategy: ALL, ANY, NONE");
                    String algorithm = scanner.nextLine();
                    System.out.println("\nEnter a name or email to search all suitable people.");
                    String string = scanner.nextLine();
                    System.out.println("\n");
                    executeStrategy(algorithm,string);
                    break;
                }
                case 2: {
                    printAll();
                    break;
                }
                case 0: {
                    exit = false;
                    System.out.println("\nBye!");
                    break;
                }
                default : {
                    System.out.println("Incorrect option! Try again.");
                    break;
                }
            }
        }
    }

    public void findPerson() {
        System.out.println("Enter a name or email to search all suitable people.");
        String string = scanner.nextLine();
        ArrayList<Integer> personIndex = invertedIndex.get(string);
        if(invertedIndex.get(string) == null) {
            System.out.println("No matching people found.\n");
        } else {
            System.out.printf("%d persons found\n", personIndex.size());
            for (Integer i : personIndex) {
                System.out.println(peopleList.get(i).getFullText());
            }
            System.out.println("\n");
        }
    }
    public void printAll() {
        System.out.println("=== List of people ===");
        for(Person p : peopleList) {
            System.out.println(p.getFullText());
        }
        System.out.print("\n");
    }
    public void executeStrategy(String algo, String stringToBeArrayed){
       switch(algo) {
           case "ALL" : {
               findPersonAll(stringToBeArrayed);
               break;
           }
           case "ANY": {
                findPersonAny(stringToBeArrayed);
               break;
           }
           case "NONE" : {
                findPersonNone(stringToBeArrayed);
               break;
           }
       }
    }

    public void findPersonAll(String string) {

       String[] query = string.split(" ");
       ArrayList <Integer> firstContainer = invertedIndex.get(query[0]);
       List <Integer> temp = new ArrayList<>();
        for (String s : query) {
            temp = invertedIndex.get(s);
            if (temp != null) {
                firstContainer.addAll(temp);
            }
        }
       if(firstContainer == null) {
           System.out.println("No matching people found.\n");
       } else {
           System.out.printf("%d persons found:\n",firstContainer.size());
           for(Integer i : firstContainer) {
               System.out.println(peopleList.get(i).getFullText());
           }
       }
        System.out.println("\n");
    }

    public void findPersonAny (String string) {
        String[] query = string.split(" ");
        List <Integer> temp = new ArrayList<>();
        Set <Integer> set = new LinkedHashSet<>();
        for (String s : query) {
            temp = invertedIndex.get(s);
            if (temp != null) {
                set.addAll(temp);
            }
        }
        ArrayList <Integer> firstContainer = new ArrayList<>(set);
        if(firstContainer.size() != 0) {
            System.out.printf("%d persons found:\n",firstContainer.size());
            for(Integer i : firstContainer) {
                System.out.println(peopleList.get(i).getFullText());
            }
        } else {
            System.out.println("No matching people found.\n");
        }
        System.out.println("\n");
    }

    public void findPersonNone(String string) {
        String[] query = string.split(" ");
        Set <Integer> set = new LinkedHashSet<>();
        for (String s : query) {
            ArrayList<Integer> temp = invertedIndex.get(s);
            if (temp != null) {
                set.addAll(temp);
            }
        }
        ArrayList <Integer> firstContainer = new ArrayList<>(set);
        int temp1 = peopleList.size() - firstContainer.size();
        if(temp1 == 0) {
            System.out.println("No matching people found.\n");
            return;
        }
        for(int i = 0; i < peopleList.size(); i++) {
            if(!firstContainer.contains(i)) {
                System.out.println(peopleList.get(i).getFullText());
            }
        }
        System.out.println("\n");

    }

}
