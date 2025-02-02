package org.example.mehrana.mapper;

import org.example.mehrana.entity.Leave;
import org.example.mehrana.entity.Personnel;
import org.example.mehrana.entity.dto.LeaveDto;
import org.example.mehrana.entity.dto.PersonnelDto;

public class DtoMapper {
    //Convert dto to entity
    public static Personnel toEntity(PersonnelDto dto) {
        Personnel personnel = new Personnel();
        personnel.setUsername(dto.getUsername());
        personnel.setPassword(dto.getPassword());
        personnel.setNationalCode(dto.getNationalCode());
        return personnel;
    }

    //Convert entity to dto
    public static PersonnelDto toDTO(Personnel entity) {
        PersonnelDto personnelDto = new PersonnelDto();
        personnelDto.setUsername(entity.getUsername());
        personnelDto.setPassword(entity.getPassword());
        personnelDto.setNationalCode(entity.getNationalCode());
        return personnelDto;
    }

    //Convert entity to dto
    public static LeaveDto toDTO(Leave leave) {
        LeaveDto dto = new LeaveDto();
        dto.setId(leave.getId());
        dto.setStartDate(leave.getStartDate());
        dto.setEndDate(leave.getEndDate());
        dto.setDescription(leave.getDescription());
        dto.setPersonnelId(leave.getPersonnel().getId());
        return dto;
    }

    //Convert dto to entity
    public static Leave toEntity(LeaveDto dto) {
        Leave leave = new Leave();
        leave.setId(dto.getId());
        leave.setStartDate(dto.getStartDate());
        leave.setEndDate(dto.getEndDate());
        leave.setDescription(dto.getDescription());
        // لازم است پرسنل را جستجو کرده و تنظیم کنیم
        // فرض کنید personnelService موجود است
        // leave.setPersonnel(personnelService.findById(dto.getPersonnelId()));
        return leave;
    }
}

