package Management_Exc;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class PersonTest {

    List<Person> persons;
    @BeforeEach
    void setUp() {
        persons = new ArrayList<>();
    }

    @AfterEach
    void tearDown() {
        persons.clear();
    }

    @Test
    void testAgeValid() {
        assertDoesNotThrow(()-> {
            persons.add(new Developer("Jea", 25, 47500));
            persons.add(new Manager("Vince", 28, 32700));
            persons.add(new Employee("Jeshiel", 21, 35000));
            persons.add(new Manager("Mary", 24, 50200));
        });
        assertEquals(24, persons.get(3).getAge());
    }

    @Test
    void testAgeInvalid() {
        persons.add(new Manager("Vince", 28, 32700));
        assertThrows(IllegalArgumentException.class, () -> persons.add(new Manager("Jea", -25, 47500)), "Age cannot be negative");
        try {
            persons.add(new Manager("Jea", -25, 47500));
            assert false : "Should be an exception";
        } catch (IllegalArgumentException e) {
            assertEquals("Age must be non-negative.", e.getMessage());
        }
        assertEquals(1, persons.size());
    }

    @Test
    void testSalaryInvalid() {
        persons.add(new Manager("Vince", 28, 32700));
        assertThrows(IllegalArgumentException.class, () -> persons.add(new Manager("Jea", -25, 47500)), "Age cannot be negative");
        try {
            persons.add(new Manager("Jea", 25, 27500));
            assert false : "Should be an exception";
        } catch (IllegalArgumentException e) {
            assertEquals("Salary must be greater than or equal to 30000", e.getMessage());
        }
        assertEquals(1, persons.size());
    }

    @Test
    void testAssignPMValid() {
        persons.add(new Developer("Jea", 25, 47500));
        persons.add(new Developer("Vince", 28, 32700));
        persons.add(new Employee("Jeshiel", 21, 35000));
        persons.add(new Manager("Mary", 24, 50200));
        persons.add(new Manager("Claire", 27, 52600));
        assertDoesNotThrow(()-> {
            Main.assignPM(persons, "Vince", "Mary");
            Main.assignPM(persons, "Jea", "Claire");
        });
        Developer vince = (Developer) persons.get(1);
        Developer jea = (Developer) persons.get(0);
        assertEquals(vince.getProjectManager(), persons.get(3));
        assertEquals(jea.getProjectManager(), persons.get(4));
    }

    @Test
    void testAssignPMNotManager() {
        persons.add(new Developer("Jea", 25, 47500));
        persons.add(new Developer("Vince", 28, 32700));
        persons.add(new Employee("Jeshiel", 21, 35000));
        persons.add(new Manager("Mary", 24, 50200));
        persons.add(new Manager("Claire", 27, 52600));
        assertThrows(ClassCastException.class, ()-> Main.assignPM(persons, "Vince", "Jeshiel"));
        try {
            Main.assignPM(persons, "Vince", "Jeshiel");
            assert false : "Should be an exception";
        } catch (ClassCastException e) {
            assertEquals("Jeshiel is not a manager", e.getMessage());
        }
    }

    @Test
    void testAssignPMNotListed() {
        persons.add(new Developer("Jea", 25, 47500));
        persons.add(new Developer("Vince", 28, 32700));
        persons.add(new Employee("Jeshiel", 21, 35000));
        persons.add(new Manager("Mary", 24, 50200));
        persons.add(new Manager("Claire", 27, 52600));
        assertThrows(NoSuchElementException.class, ()-> Main.assignPM(persons, "Vince", "Scott"));
        try {
            Main.assignPM(persons, "Vince", "Scott");
            assert false : "Should be an exception";
        } catch (NoSuchElementException e) {
            assertEquals("Scott does not exist", e.getMessage());
        }
    }

    @Test
    void testAssignPMAlreadyHas() {
        persons.add(new Developer("Jea", 25, 47500));
        persons.add(new Developer("Vince", 28, 32700));
        persons.add(new Employee("Jeshiel", 21, 35000));
        persons.add(new Manager("Mary", 24, 50200));
        persons.add(new Manager("Claire", 27, 52600));
        assertDoesNotThrow(()-> {
            Main.assignPM(persons, "Vince", "Mary");
            Main.assignPM(persons, "Jea", "Mary");
        });
        assertThrows(IllegalStateException.class, ()-> Main.assignPM(persons, "Vince", "Claire"));
        try {
            Main.assignPM(persons, "Vince", "Claire");
            assert false : "Should be an exception";
        } catch (IllegalStateException e) {
            assertEquals("Vince already has a manager: Mary", e.getMessage());
        }
    }

    @Test
    void testGiveRaiseValid() {
        persons.add(new Developer("Jea", 25, 47500));
        persons.add(new Developer("Vince", 28, 32700));
        persons.add(new Employee("Jeshiel", 21, 35000));
        persons.add(new Manager("Mary", 24, 50200));
        persons.add(new Manager("Claire", 27, 52600));
        assertDoesNotThrow(()-> {
            Main.assignPM(persons, "Vince", "Mary");
            Main.assignPM(persons, "Jea", "Mary");
            Main.giveRaise(persons, "Mary", "Vince", 140.30);
        });
        Employee vince = (Employee) persons.get(1);
        Employee mary = (Employee) persons.get(3);
        assertEquals(32840.30, vince.getSalary());
        assertEquals(50270.15, mary.getSalary());
    }
    @Test
    void testGiveRaiseInvalid1() {
        persons.add(new Developer("Jea", 25, 47500));
        persons.add(new Developer("Vince", 28, 32700));
        persons.add(new Employee("Jeshiel", 21, 35000));
        persons.add(new Manager("Mary", 24, 50200));
        persons.add(new Manager("Claire", 27, 52600));
        persons.add(new Customer("Felix", 52));
        assertDoesNotThrow(()-> {
            Main.assignPM(persons, "Vince", "Mary");
            Main.assignPM(persons, "Jea", "Mary");
            Main.giveRaise(persons, "Mary", "Vince", 140.30);
        });
        assertThrows(ClassCastException.class, ()->Main.giveRaise(persons, "Felix", "Jeshiel", 2500));
        try {
            Main.giveRaise(persons, "Felix", "Jeshiel", 2500);
            assert false : "Should be an exception";
        } catch (ClassCastException e) {
            assertEquals("Felix is not a manager", e.getMessage());
        }
        Employee jeshiel = (Employee) persons.get(2);
        assertEquals(35000, jeshiel.getSalary());
    }
    @Test
    void testGiveRaiseInvalid2() {
        persons.add(new Developer("Jea", 25, 47500));
        persons.add(new Developer("Vince", 28, 32700));
        persons.add(new Employee("Jeshiel", 21, 35000));
        persons.add(new Manager("Mary", 24, 50200));
        persons.add(new Manager("Claire", 27, 52600));
        persons.add(new Customer("Felix", 52));
        assertDoesNotThrow(()-> {
            Main.assignPM(persons, "Vince", "Mary");
            Main.assignPM(persons, "Jea", "Mary");
            Main.giveRaise(persons, "Mary", "Vince", 140.30);
        });
        assertThrows(ClassCastException.class, ()->Main.giveRaise(persons, "Felix", "Jeshiel", 2500));
        try {
            Main.giveRaise(persons, "Claire", "Felix", 2500);
            assert false : "Should be an exception";
        } catch (ClassCastException e) {
            assertEquals("Felix is not an employee", e.getMessage());
        }
        Employee claire = (Employee) persons.get(4);
        assertEquals(52600, claire.getSalary());
    }
    @Test
    void testGiveRaiseInvalid3() {
        persons.add(new Developer("Jea", 25, 47500));
        persons.add(new Developer("Vince", 28, 32700));
        persons.add(new Employee("Jeshiel", 21, 35000));
        persons.add(new Manager("Mary", 24, 50200));
        persons.add(new Manager("Claire", 27, 52600));
        persons.add(new Customer("Felix", 52));
        assertDoesNotThrow(()-> {
            Main.assignPM(persons, "Vince", "Mary");
            Main.assignPM(persons, "Jea", "Mary");
            Main.giveRaise(persons, "Claire", "Vince", 140.30);
        });
        assertThrows(IllegalArgumentException.class, ()->Main.giveRaise(persons, "Mary", "Jeshiel", -2500));
        try {
            Main.giveRaise(persons, "Mary", "Jeshiel", -2500);
            assert false : "Should be an exception";
        } catch (IllegalArgumentException e) {
            assertEquals("Raise must be non-negative", e.getMessage());
        }
        Employee mary = (Employee) persons.get(3);
        Employee jeshiel = (Employee) persons.get(2);
        assertEquals(50200, mary.getSalary());
        assertEquals(35000, jeshiel.getSalary());
    }
    @Test
    void testGiveRaiseInvalid4() {
        persons.add(new Developer("Jea", 25, 47500));
        persons.add(new Developer("Vince", 28, 32700));
        persons.add(new Employee("Jeshiel", 21, 35000));
        persons.add(new Manager("Mary", 24, 50200));
        persons.add(new Manager("Claire", 27, 52600));
        persons.add(new Customer("Felix", 52));
        assertDoesNotThrow(()-> {
            Main.assignPM(persons, "Vince", "Mary");
            Main.assignPM(persons, "Jea", "Mary");
            Main.giveRaise(persons, "Mary", "Vince", 140.30);
        });
        assertThrows(NoSuchElementException.class, ()->Main.giveRaise(persons, "Alaera", "Jeshiel", 2500));
        try {
            Main.giveRaise(persons, "Alaera", "Jeshiel", 2500);
            assert false : "Should be an exception";
        } catch (NoSuchElementException e) {
            assertEquals("Alaera does not exist", e.getMessage());
        }
        try {
            Main.giveRaise(persons, "Claire", "Frieren", 2500);
            assert false : "Should be an exception";
        } catch (NoSuchElementException e) {
            assertEquals("Frieren does not exist", e.getMessage());
        }
    }

    @Test
    void testCustomerSpeakValid() {
        persons.add(new Developer("Jea", 25, 47500));
        persons.add(new Developer("Vince", 28, 32700));
        persons.add(new Employee("Jeshiel", 21, 35000));
        persons.add(new Customer("Jewel", 38));
        persons.add(new Manager("Mary", 24, 50200));
        persons.add(new Manager("Claire", 27, 52600));
        persons.add(new Customer("Felix", 52));
        assertDoesNotThrow(()->{
           Main.customerSpeak(persons, "Felix", "Jeshiel");
           Main.customerSpeak(persons, "Jewel", "Jea");
        });

        assertEquals("Oh, hello, Jeshiel. Can you assist me?", Main.customerSpeak(persons, "Felix", "Jeshiel"));
        assertEquals("Oh, hello, Jea. Can you assist me?", Main.customerSpeak(persons, "Jewel", "Jea"));
        Main.assignPM(persons, "Jea", "Mary");

        assertEquals("Can I see your manager Mary?", Main.customerSpeak(persons, "Felix", "Jea"));
    }

    @Test
    void testCustomerSpeakInvalid() {
        persons.add(new Developer("Jea", 25, 47500));
        persons.add(new Developer("Vince", 28, 32700));
        persons.add(new Employee("Jeshiel", 21, 35000));
        persons.add(new Customer("Jewel", 38));
        persons.add(new Manager("Mary", 24, 50200));
        persons.add(new Manager("Claire", 27, 52600));
        persons.add(new Customer("Felix", 52));
        assertThrows(ClassCastException.class, ()->{
            Main.customerSpeak(persons, "Mary", "Jeshiel");
            Main.customerSpeak(persons, "Jewel", "Felix");
        });
        try {
            String ignore = Main.customerSpeak(persons, "Mary", "Jeshiel");
            assert false : "Should be an exception";
        } catch (ClassCastException e) {
            assertEquals("Mary is not a customer", e.getMessage());
        }
        try {
            String ignore = Main.customerSpeak(persons, "Jewel", "Felix");
            assert false : "Should be an exception";
        } catch (ClassCastException e) {
            assertEquals("Felix is not an employee", e.getMessage());
        }
    }

    @Test
    void testCustomerSpeakInvalid2() {
        persons.add(new Developer("Jea", 25, 47500));
        persons.add(new Developer("Vince", 28, 32700));
        persons.add(new Employee("Jeshiel", 21, 35000));
        persons.add(new Customer("Jewel", 38));
        persons.add(new Manager("Mary", 24, 50200));
        persons.add(new Manager("Claire", 27, 52600));
        persons.add(new Customer("Felix", 52));
        assertThrows(NoSuchElementException.class, ()->{
            Main.customerSpeak(persons, "Travis", "Jeshiel");
            Main.customerSpeak(persons, "Jewel", "Travis");
        });
        try {
            String ignore = Main.customerSpeak(persons, "Jewel", "Travis");
            assert false : "Should be an exception";
        } catch (Exception e) {
            assertEquals("Travis does not exist", e.getMessage());
        }
    }

    @Test
    void testStoreToFile() {
        assertDoesNotThrow(()-> {
            persons.add(new Developer("Jea", 25, 47500));
            persons.add(new Manager("Vince", 28, 32700));
            persons.add(new Employee("Jeshiel", 21, 35000));
            persons.add(new Customer("Mae", 37));
            persons.add(new Manager("Mary", 24, 50200));
            Main.storeToFile(persons);
            BufferedReader br = new BufferedReader(new FileReader("people.txt"));
            assertEquals("Developer,Jea,25,47500.0", br.readLine());
            assertEquals("Manager,Vince,28,32700.0", br.readLine());
            assertEquals("Employee,Jeshiel,21,35000.0", br.readLine());
            assertEquals("Customer,Mae,37,0.0", br.readLine());
            assertEquals("Manager,Mary,24,50200.0", br.readLine());
            assertNull(br.readLine());
        });
    }

    @Test
    void testStoreToFile2() {
        assertDoesNotThrow(()-> {
            persons.add(new Customer("Mae", 37));
            persons.add(new Employee("Jeshiel", 21, 35000));
            persons.add(new Manager("Mary", 24, 50200));
            persons.add(new Developer("Jea", 25, 47500));
            persons.add(new Manager("Vince", 28, 32700));
            Main.storeToFile(persons);
            BufferedReader br = new BufferedReader(new FileReader("people.txt"));
            assertEquals("Customer,Mae,37,0.0", br.readLine());
            assertEquals("Employee,Jeshiel,21,35000.0", br.readLine());
            assertEquals("Manager,Mary,24,50200.0", br.readLine());
            assertEquals("Developer,Jea,25,47500.0", br.readLine());
            assertEquals("Manager,Vince,28,32700.0", br.readLine());
            assertNull(br.readLine());
        });
    }

    @Test
    void testRetrieveFromFile() {
        assertDoesNotThrow(()-> {
            persons.add(new Developer("Jea", 25, 47500));
            persons.add(new Manager("Vince", 28, 32700));
            persons.add(new Employee("Jeshiel", 21, 35000));
            persons.add(new Customer("Mae", 37));
            persons.add(new Manager("Mary", 24, 50200));
            Main.storeToFile(persons);
            persons.clear();
            Main.retrieveFromFile(persons);
            assertEquals(5, persons.size());
            assertEquals("Jea", persons.get(0).getName());
            assertEquals(28, persons.get(1).getAge());
            assertEquals(35000, ((Employee) persons.get(2)).getSalary());
            assertEquals("Mae", persons.get(3).getName());
            assertEquals("Mary", ((Manager) persons.get(4)).getName());
            assertInstanceOf(Manager.class, persons.get(1));
        });
    }

    @Test
    void testRetrieveFromFile2() {
        assertDoesNotThrow(() -> {
            persons.add(new Manager("Mary", 24, 50200));
            persons.add(new Customer("Mae", 37));
            persons.add(new Manager("Vince", 28, 32700));
            persons.add(new Employee("Jeshiel", 21, 35000));
            persons.add(new Developer("Jea", 25, 47500));
            Main.storeToFile(persons);
            persons.clear();
            Main.retrieveFromFile(persons);
            assertEquals(5, persons.size());
            assertEquals("Jea", persons.get(4).getName());
            assertEquals(28, persons.get(2).getAge());
            assertEquals(35000, ((Employee) persons.get(3)).getSalary());
            assertEquals("Mae", persons.get(1).getName());
            assertEquals("Mary", persons.get(0).getName());
            assertInstanceOf(Manager.class, persons.get(0));
        });
    }
}