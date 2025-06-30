package Management_Exc;

import java.util.List;
import java.util.NoSuchElementException;

public class Main {

    /**
     * TODO this implementation
     * Stores the given list of persons to the text "people.csv" in project directory
     * The first column should be its classification, whether Customer, Developer, Employee, or Manager
     * The next columns are the following in order -- name, age, salary (if Customer, 0.0)
     * @param persons the list of persons
     */
    public static void storeToFile(List<Person> persons) {

    }

    /**
     * TODO this implementation
     * Loads the saved details of people from "people.txt" to the list of persons
     * The first column is its classification, whether Customer, Developer, Employee, or Manager
     * The next columns are the following in order -- name, age, salary (if Customer, 0.0)
     * @param persons the list of persons
     */
    public static void retrieveFromFile(List<Person> persons) {

    }

    /**
     * TODO this implementation
     * @param persons the list of persons
     * @param manager the manager to give the salary
     * @param employee the employee to receive the raise
     * @param salary the salary increase to be given
     * @throws ClassCastException when manager or employee is not a manager or employee
     * @throws IllegalArgumentException when salary is invalid
     * @throws NoSuchElementException when given manager or employee does not exist in the list of persons
     */
    public static void giveRaise(List<Person> persons, String manager, String employee, double salary)  {

    }

    /**
     * TODO this implementation
     * @param persons the list of persons
     * @param developer the developer to be assigned
     * @param manager the manager assigned to the dev
     * @throws ClassCastException when manager or developer is not a manager or employee
     * @throws NoSuchElementException when given manager or developer does not exist in the list of persons
     * @throws IllegalStateException when developer already has a manager
     */
    public static void assignPM(List<Person> persons, String developer, String manager) {

    }

    /**
     * TODO this implementation
     * @param persons the list of persons
     * @param customer the customer to speak to the employee
     * @param employee the employee to be spoken to
     * @return the dialogue of the customer to the employee
     * @throws ClassCastException when given customer or employee is not what they are
     * @throws NoSuchElementException when given customer or employee is not in the list of persons
     */
    public static String customerSpeak(List<Person> persons, String customer, String employee) {
        return null;
    }
}
