package org.example.mehrana;

import org.example.mehrana.entity.dto.PersonnelDto;
import org.example.mehrana.exception.DuplicateNationalCodeException;
import org.example.mehrana.exception.SaveRecordException;
import org.example.mehrana.srvice.PersonnelService;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        PersonnelService personnelService = new PersonnelService();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("=== Personnel Management System ===");
            System.out.println("1. Add Personnel");
            System.out.println("2. Delete Personnel");
            System.out.println("3. Search Personnel");
            System.out.println("4. Update Personnel");
            System.out.println("5. Exit");
            System.out.print("Please select an option (1-5): ");
            int choice = scanner.nextInt();
            scanner.nextLine();  // consume the leftover newline character

            switch (choice) {
                case 1:
                    // Add Personnel
                    System.out.println("Enter Personnel Details:");

                    System.out.print("Name: ");
                    String name = scanner.nextLine();

                    System.out.print("Password: ");
                    String password = scanner.nextLine();

                    System.out.print("National Code: ");
                    long nationalCode = scanner.nextLong();
                    scanner.nextLine();  // consume the leftover newline character

                    PersonnelDto personnelDto = new PersonnelDto();
                    personnelDto.setUsername(name);
                    personnelDto.setPassword(password);
                    personnelDto.setNationalCode(nationalCode);

                    try {
                        personnelService.create(personnelDto);
                        System.out.println("Personnel saved successfully!");
                    } catch (DuplicateNationalCodeException e) {
                        System.out.println("Duplicate national code detected. Please try again.");
                    } catch (SaveRecordException e) {
                        System.out.println("Error saving record. Please try again.");
                    }
                    break;

                case 2:
                    // Delete Personnel
                    System.out.print("Enter the National Code of the Personnel to Delete: ");
                    long deleteNationalCode = scanner.nextLong();
                    scanner.nextLine();

                    try {
                        personnelService.delete(deleteNationalCode);
                        System.out.println("Personnel deleted successfully!");
                    } catch (Exception e) {
                        System.out.println("Error deleting personnel. Please try again.");
                    }
                    break;

                case 3:
                    // Search Personnel
                    System.out.print("Enter the National Code of the Personnel to Search: ");
                    long searchNationalCode = scanner.nextLong();
                    scanner.nextLine();

                    PersonnelDto searchedPersonnel = personnelService.getByNationalCode(searchNationalCode);

                    if (searchedPersonnel != null) {
                        System.out.println("Personnel Details:");
                        System.out.println("Name: " + searchedPersonnel.getUsername());
                        System.out.println("Password: " + searchedPersonnel.getPassword());
                        System.out.println("National Code: " + searchedPersonnel.getNationalCode());
                    } else {
                        System.out.println("Personnel not found with the given National Code.");
                    }
                    break;

                case 4:
                    // Update Personnel
                    System.out.println("Enter Personnel Details to Update:");

                    System.out.print("National Code: ");
                    long updateNationalCode = scanner.nextLong();
                    scanner.nextLine();  // consume newline

                    System.out.print("New Name: ");
                    String newName = scanner.nextLine();

                    System.out.print("New Password: ");
                    String newPassword = scanner.nextLine();

                    PersonnelDto updatePersonnelDto = new PersonnelDto();
                    updatePersonnelDto.setNationalCode(updateNationalCode);
                    updatePersonnelDto.setUsername(newName);
                    updatePersonnelDto.setPassword(newPassword);

                    try {
                        personnelService.update(updatePersonnelDto);
                    } catch (Exception e) {
                        System.out.println("Error updating personnel. Please try again.");
                    }
                    break;

                case 5:
                    // Exit
                    System.out.println("Program terminated.");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid option. Please try again.");
            }

            System.out.println();
        }
    }
}
