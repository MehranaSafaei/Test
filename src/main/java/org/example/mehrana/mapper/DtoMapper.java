package org.example.mehrana.mapper;

import org.example.mehrana.entity.Personnel;
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
}
