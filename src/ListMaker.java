import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;

public class ListMaker {
    private static ArrayList<String> itemList = new ArrayList<>();
    private static Scanner inputScanner = new Scanner(System.in);
    private static boolean needsToBeSaved = false;

    public static void main(String[] args) {
        while (true) {
            displayMenu();

            String choice = SafeInput.getRegExString(inputScanner, "Enter your choice: [AaDdVvOoSsCcQqNn]", "[AaDdVvOoSsCcQqNn]");
            switch (choice.toUpperCase()) {
                case "A":
                    addItem();
                    break;
                case "D":
                    deleteItem();
                    break;
                case "V":
                    printList();
                    break;
                case "O":
                    // Call the method to open a list from disk
                    openList();
                    break;
                case "S":
                    // Call the method to save the current list to disk
                    saveList();
                    break;
                case "C":
                    // Call the method to clear the list
                    clearList();
                    break;
                case "Q":
                    // Check if the user wants to quit and return if confirmed
                    if (checkUnsavedChanges()) {
                        return;
                    }
                    break;
                case "N":
                    // Check for unsaved changes before creating a new list
                    if (checkUnsavedChanges()) {
                        createNewList();
                    }
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private static void addItem() {
        System.out.println("Add Items to the List (Type 'Q' to stop adding items)");

        while (true) {
            String item = SafeInput.getNonZeroLenString(inputScanner, "Enter an item (or 'Q' to stop adding):");
            if (item.equalsIgnoreCase("Q")) {
                break;
            }
            itemList.add(item);
            needsToBeSaved = true;
        }

        System.out.println("Items on the list.");
    }

    private static void displayMenu() {
        System.out.println("Menu:");
        System.out.println("A - Add item to the list");
        System.out.println("D - Delete item from the list");
        System.out.println("V - View the list");
        System.out.println("O - Open a list file from disk");
        System.out.println("S - Save the current list file to disk");
        System.out.println("C - Clear the list");
        System.out.println("Q - Quit/Stop");

        if (!itemList.isEmpty()) {
            System.out.println("\nCurrent List of Items:");
            for (int i = 0; i < itemList.size(); i++) {
                System.out.println((i + 1) + ". " + itemList.get(i));
            }
        }
    }

    private static void deleteItem() {
        if (itemList.isEmpty()) {
            System.out.println("list is empty.");
            return;
        }

        printNumberedItems();

        int itemNumber = SafeInput.getRangedInt(inputScanner, "Enter the number of the item you would like to delete", 1, itemList.size());
        String deletedItem = itemList.remove(itemNumber - 1);
        System.out.println("Item deleted: " + deletedItem);
        needsToBeSaved = true;
    }

    private static void printList() {
        if (itemList.isEmpty()) {
            System.out.println("list is empty.");
        } else {
            System.out.println("List items:");
            for (String item : itemList) {
                System.out.println(item);
            }
        }
    }

    private static void printNumberedItems() {
        System.out.println("List items (numbered):");
        for (int i = 0; i < itemList.size(); i++) {
            System.out.println((i + 1) + ". " + itemList.get(i));
        }
    }

    private static void openList() {
        // Check for unsaved changes before opening a new list
        if (checkUnsavedChanges()) {
            // Implement the logic to open a list from disk
            // You can use FileReader to read the contents of the file
            // and populate the itemList with the read data
            System.out.println("Open list logic goes here.");
        }
    }

    private static void saveList() {
        if (needsToBeSaved) {
            String fileName = SafeInput.getNonZeroLenString(inputScanner, "Enter the basename for the new list file (without extension):");
            fileName = fileName.trim() + ".txt"; // Append the .txt extension

            try (FileWriter writer = new FileWriter(fileName)) {
                for (String item : itemList) {
                    writer.write(item + System.lineSeparator());
                }
                System.out.println("List saved to disk with filename: " + fileName);
                needsToBeSaved = false; // Reset the flag as the list has been saved
            } catch (IOException e) {
                System.out.println("Error saving the list to disk: " + e.getMessage());
            }
        } else {
            System.out.println("No changes to save.");
        }
    }

    private static void clearList() {
        // Check for unsaved changes before clearing the list
        if (checkUnsavedChanges()) {
            itemList.clear(); // Clear the existing list
            System.out.println("List cleared.");
            needsToBeSaved = true;
        }
    }

    private static void createNewList() {
        // Check for unsaved changes before creating a new list
        if (checkUnsavedChanges()) {
            itemList.clear(); // Clear the existing list
            System.out.println("New list created.");
            needsToBeSaved = true;
        }
    }

    private static boolean checkUnsavedChanges() {
        if (needsToBeSaved) {
            return SafeInput.getYNConfirm(inputScanner, "You have unsaved changes. Do you want to save the list before continuing? [Y/N]");
        }
        return true;
    }
}




