package org.example.mehrana;

import org.example.mehrana.entity.Leave;
import org.example.mehrana.entity.Personnel;
import org.example.mehrana.entity.dto.LeaveDto;
import org.example.mehrana.entity.dto.PersonnelDto;
import org.example.mehrana.exception.*;
import org.example.mehrana.srvice.LeaveService;
import org.example.mehrana.srvice.PersonnelService;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
//open,close,high,low

public class Main {
    public static void main(String[] args) throws UpdateException, NotFoundException, DeleteException, SaveRecordException, DuplicateNationalCodeException, PersonnelNotFoundException, SQLException {

        PersonnelService personnelService = new PersonnelService();
        LeaveService leaveService = new LeaveService();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            try{
            System.out.println("=== Management System ===");
            System.out.println("1. Add Personnel"); //*
            System.out.println("2. Delete Personnel"); //*
            System.out.println("3. Search Personnel"); //*
            System.out.println("4. Update Personnel"); //*
            System.out.println("5. Add Leave"); //*
            System.out.println("6. All personnel's"); //*
            System.out.println("7. approve/reject Leave");
            System.out.println("8. view ListLeave By username"); //*
            System.out.println("9. Update Leave");
            System.out.println("10. Delete Leave ");
            System.out.println("0. Exit"); //*
            System.out.print("Please select an option (1-10): ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    // Add Personnel
                    System.out.println("Enter Personnel Details:");

                    System.out.print("Username: ");
                    String name = scanner.nextLine();

                    System.out.print("Password: ");
                    String password = scanner.nextLine();

                    System.out.print("National Code: ");
                    long nationalCode = scanner.nextLong();
                    scanner.nextLine();

                    PersonnelDto personnelDto = new PersonnelDto();
                    personnelDto.setUsername(name);
                    personnelDto.setPassword(password);
                    personnelDto.setNationalCode(nationalCode);
                    try {
                        personnelService.create(personnelDto);
                        System.out.println("Personnel saved successfully!");
                    } catch (DuplicateNationalCodeException e) {
                        throw new DuplicateNationalCodeException("Duplicate national code detected. Please try again.");
                    } catch (SaveRecordException e) {
                        throw new SaveRecordException();
                    }
                    break;

                case 2:
                    // Delete Personnel
                    System.out.println("Do you want to delete By:");
                    System.out.println("1. nationalCode ");
                    System.out.println("2. ID ");
                    long choiceDelete = scanner.nextLong();
                    scanner.nextLine();
                    try {
                        if (choiceDelete == 1) {
                            System.out.print("Enter the National Code of the Personnel to Delete: ");
                            long deleteNationalCode = scanner.nextLong();
                            scanner.nextLine();

                            personnelService.deleteByNationalCode(deleteNationalCode);
                            System.out.println("Personnel deleted successfully!");
                        } else if (choiceDelete == 2) {
                            System.out.print("Enter the ID of the Personnel to Delete: ");
                            Long deleteId = scanner.nextLong();
                            scanner.nextLine();

                            personnelService.deleteById(deleteId);
                            System.out.println("Personnel deleted successfully!");
                        }
                    } catch (Exception e) {
                        throw new DeleteException();
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
                        System.out.println("Username: " + searchedPersonnel.getUsername());
                        System.out.println("Password: " + searchedPersonnel.getPassword());
                        System.out.println("National Code: " + searchedPersonnel.getNationalCode());
                    } else {
                        throw new NotFoundException("Personnel not found with the given National Code");
                    }
                    break;

                case 4:
                    // Update Personnel
                    System.out.println("Enter Personnel Details to Update:");

                    System.out.print("National Code: ");
                    long updateNationalCode = scanner.nextLong();
                    scanner.nextLine();

                    System.out.print("New Username: ");
                    String newName = scanner.nextLine();

                    System.out.print("New Password: ");
                    String newPassword = scanner.nextLine();

                    PersonnelDto updatePersonnelDto = new PersonnelDto();
                    updatePersonnelDto.setNationalCode(updateNationalCode);
                    updatePersonnelDto.setUsername(newName);
                    updatePersonnelDto.setPassword(newPassword);

                    try {
                        personnelService.updatePersonnel(updatePersonnelDto);
                    } catch (Exception e) {
                        throw new UpdateException();
                    }
                    break;

                case 5:
                    System.out.print("Enter your NationalCode: ");
                    long nationalCodePersonnel = scanner.nextLong();
                    scanner.nextLine();

                    PersonnelDto personnel = personnelService.getByNationalCode(nationalCodePersonnel);
                    if (personnel != null) {
                        System.out.println("National Code: " + nationalCodePersonnel);
                        System.out.println("Enter Leave Details:");

                        System.out.print("Enter start date (YYYY-MM-DD): ");
                        String startDate = scanner.nextLine();

                        System.out.print("Enter end date (YYYY-MM-DD): ");
                        String endDate = scanner.nextLine();

                        System.out.print("Enter description: ");
                        String description = scanner.nextLine();

                        LeaveDto leaveDto = new LeaveDto();
                        leaveDto.setLeaveDate(LocalDateTime.now());
                        leaveDto.setStartDate(LocalDate.parse(startDate));
                        leaveDto.setEndDate(LocalDate.parse(endDate));
                        leaveDto.setDescription(description);
                        leaveDto.setPersonnelId(personnel.getId());
                        leaveService.createLeave(leaveDto, personnel);
                    } else {
                        throw new NotFoundException("Personnel not found with the given National Code");
                    }
                    break;
                case 6:
                    List<Personnel> personnelList = personnelService.getAll();
                    for (Personnel personnel1 : personnelList) {
                        System.out.println(personnel1);
                    }
                    break;
                case 7:
                    System.out.println("Are you sure you want to approve Leave?");
                    System.out.println(" y/n");
                    if (scanner.nextLine().equalsIgnoreCase("y")) {
                        System.out.println("Enter LeaveId to approve");
                        Long leaveId = scanner.nextLong();
                        System.out.println("Enter approve personnelId to approve");
                        Long approvePersonnelId = scanner.nextLong();
                        leaveService.approveLeave(approvePersonnelId, leaveId);
                    } else if (scanner.nextLine().equalsIgnoreCase("n")) {
                        System.out.println("Enter LeaveId to approve");
                        Long leaveId = scanner.nextLong();
                        System.out.println("Enter approve personnelId to approve");
                        Long approvePersonnelId = scanner.nextLong();
                        System.out.println("Type reason for rejection");
                        String reason = scanner.nextLine();
                        leaveService.rejectLeave(leaveId, approvePersonnelId, reason);
                    }
                    break;
                case 8:

                    List<Leave> leaveList;
                    System.out.print("Enter your username: ");
                    String username = scanner.nextLine();

                    List<PersonnelDto> personnels = personnelService.findByName(username);
                    if (personnels == null || personnels.isEmpty()) {
                        throw new NotFoundException("Personnel not found with the given Username");
                    }
                    Long personnelId = personnels.get(0).getId();
                    leaveList = leaveService.findLeaveByPersonnelId(personnelId);

                    if (leaveList == null || leaveList.isEmpty()) {
                        System.out.println("No leaves found for this personnel.");
                    } else {
                        for (Leave leave : leaveList) {
                            System.out.println(leave);
                        }
                    }

                    break;
                case 9:
                    System.out.println("Enter Leave Details to Update:");

                    System.out.print("Leave ID: ");
                    long leaveId = scanner.nextLong();

                    System.out.print("Personnel ID: ");
                    long personnelID = scanner.nextLong();
                    scanner.nextLine();

                    System.out.println("New Start Date (YYYY-MM-DD): ");
                    String newStartDateInput = scanner.nextLine();

                    System.out.println("New End Date (YYYY-MM-DD): ");
                    String newEndDateInput = scanner.nextLine();

                    try {
                        // Validate date input
                        LocalDate newStartDate = LocalDate.parse(newStartDateInput);
                        LocalDate newEndDate = LocalDate.parse(newEndDateInput);

                        LeaveDto updateLeaveDto = new LeaveDto();
                        updateLeaveDto.setStartDate(newStartDate);
                        updateLeaveDto.setEndDate(newEndDate);

                        leaveService.updateLeave(leaveId, updateLeaveDto, personnelID);
                        System.out.println("Leave updated successfully.");
                    } catch (DateTimeParseException e) {
                        System.err.println("Invalid date format. Please use YYYY-MM-DD.");
                    } catch (SaveRecordException e) {
                        System.err.println("Error updating leave: " + e.getMessage());
                    } catch (Exception e) {
                        System.err.println("Unexpected error occurred: " + e.getMessage());
                    }
                    break;
                case 10:
                    List<Leave> personnelNameList = leaveService.findAllByPersonnelName("all");
                    // iter
                    for (Leave leave : personnelNameList) {
                        showLeave(leave);
                    }

                    System.out.println("please select one of the id for delete");
                    long id = scanner.nextLong();

                    Optional<Leave> optionalLeave = personnelNameList.stream().filter(x -> x.getId() == id).findFirst();
                    if (optionalLeave.isPresent()) {
                        Leave leave = optionalLeave.get();
                        leaveService.delete(leave.getId());
                    }


                    //TODO: I should write code that if manager/admin doesn't approve , they reject request

//
//                    System.out.println("Are you sure you want to reject Leave?");
//                    System.out.println(" y/n");
//                    if (scanner.nextLine().equalsIgnoreCase("y")) {
//                        System.out.println("Enter LeaveId to reject");
//                        Long personnelNationalCode = scanner.nextLong();
//                        if (personnelService.findListByNationalCode(personnelNationalCode) != null) {
//                            leaveService.rejectLeave(leaveId,approveId,rejectionReason);
//                        }
//                    }

                case 0:
                    // Exit
                    System.out.println("Exiting program.");
                    scanner.close();
                    System.exit(0);
                    break;

                default:
                    System.out.println("Invalid option. Please try again.");
            }  } catch (Exception e) {
                System.err.println("An error occurred: " + e.getMessage());
                System.out.println("Returning to the main menu...");
            }
        }
    }

    private static void showLeave(Leave leave) {
        // chap leave
    }
}
