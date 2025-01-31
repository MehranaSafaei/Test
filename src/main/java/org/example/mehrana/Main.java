package org.example.mehrana;

import org.example.mehrana.entity.dto.PersonnelDto;
import org.example.mehrana.exception.DuplicateDataException;
import org.example.mehrana.exception.DuplicateNationalCodeException;
import org.example.mehrana.exception.SaveRecordException;
import org.example.mehrana.srvice.PersonnelService;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SaveRecordException, DuplicateDataException, DuplicateNationalCodeException {
        PersonnelService personnelService = new PersonnelService();

        Scanner scanner = new Scanner(System.in);

        System.out.println("Please enter your name: ");
        String name = scanner.nextLine();
        System.out.println("Please enter your password: ");
        String password = scanner.nextLine();
        System.out.println("Please enter your nationalCode: ");
        long nationalCode = scanner.nextLong();

        PersonnelDto personnelDto = new PersonnelDto();
        personnelDto.setUsername(name);
        personnelDto.setPassword(password);
        personnelDto.setNationalCode(nationalCode);

        personnelService.create(personnelDto);
    }
}