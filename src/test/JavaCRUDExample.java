package test;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class JavaCRUDExample {
    static Map<Integer, String> dataStore = new HashMap<>();
    static int idCounter = 1;
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
	boolean isRunning = true;
	while (isRunning) {
	    System.out.println("1. Create\t2. Read\t3. Update\t4. Delete\t5. Exit");
	    System.out.print("Enter your choice: ");
	    int choice = scanner.nextInt();
	    scanner.nextLine(); // Consume newline character

	    switch (choice) {
	    case 1:
		createRecord();
		break;
	    case 2:
		readRecord();
		break;
	    case 3:
		updateRecord();
		break;
	    case 4:
		deleteRecord();
		break;
	    case 5:
		isRunning = false;
		break;
	    default:
		System.out.println("Invalid choice.");
	    }
	}
	System.out.println("Exited.");
    }

    static void createRecord() {
	System.out.print("Enter data: ");
	String data = scanner.nextLine();
	dataStore.put(idCounter, data);
	System.out.println("Record created with ID: " + idCounter++);
    }

    static void readRecord() {
	System.out.print("Enter ID to read: ");
	int id = scanner.nextInt();
	String data = dataStore.get(id);
	if (data != null) {
	    System.out.println("Data: " + data);
	} else {
	    System.out.println("Record not found for ID: " + id);
	}
    }

    static void updateRecord() {
	System.out.print("Enter ID to update: ");
	int id = scanner.nextInt();
	scanner.nextLine(); // Consume newline character
	String data = dataStore.get(id);
	if (data != null) {
	    System.out.print("Enter new data: ");
	    String newData = scanner.nextLine();
	    dataStore.put(id, newData);
	    System.out.println("Record updated for ID: " + id);
	} else {
	    System.out.println("Record not found for ID: " + id);
	}
    }

    static void deleteRecord() {
	System.out.print("Enter ID to delete: ");
	int id = scanner.nextInt();
	String data = dataStore.get(id);
	if (data != null) {
	    dataStore.remove(id);
	    System.out.println("Record deleted for ID: " + id);
	} else {
	    System.out.println("Record not found for ID: " + id);
	}
    }
}
