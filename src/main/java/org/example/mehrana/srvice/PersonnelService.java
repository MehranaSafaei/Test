package org.example.mehrana.srvice;

import org.example.mehrana.dao.PersonnelDao;
import org.example.mehrana.entity.Personnel;
import org.example.mehrana.entity.dto.PersonnelDto;
import org.example.mehrana.entity.enums.Role;
import org.example.mehrana.exception.DuplicateNationalCodeException;
import org.example.mehrana.exception.NotFoundException;
import org.example.mehrana.exception.SaveRecordException;
import org.example.mehrana.mapper.DtoMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class PersonnelService {

    private final PersonnelDao personnelDao = new PersonnelDao();

    public void create(PersonnelDto personnelDTO) throws SaveRecordException, DuplicateNationalCodeException {
        Personnel personnel = DtoMapper.toEntity(personnelDTO);
        personnel.setId(null);

        if (isDuplicate(personnel)) {
            throw new DuplicateNationalCodeException();
        }

        try {
            personnelDao.create(personnel);
        } catch (Exception e) {
            throw new SaveRecordException();
        }
    }

    private boolean isDuplicate(Personnel personnel) {
        return personnelDao.isNationalIdDuplicated(personnel.getNationalCode());
    }

    public void deleteByNationalCode(Long deleteNationalCode) throws NotFoundException {
        Optional<Personnel> personnelToDelete = personnelDao.findByNationalCode(deleteNationalCode);

        if (personnelToDelete.isPresent()) {
            personnelDao.deleteByNationalCode(deleteNationalCode);
        } else {
            throw new NotFoundException("Personnel with national code " + deleteNationalCode + " not found.");
        }
    }


    public PersonnelDto getByNationalCode(long searchNationalCode) throws NotFoundException {
        Optional<Personnel> personnel = personnelDao.findByNationalCode(searchNationalCode);

        if (personnel.isPresent()) {
            return DtoMapper.toDTO(personnel.get());
        } else {
            throw new NotFoundException("Personnel with national code " + searchNationalCode + " not found.");
        }
    }

    public void deleteById(Long deleteId) throws NotFoundException {
        Personnel personnelToDelete = personnelDao.findById(deleteId);

        if (personnelToDelete != null) {
            personnelDao.delete(deleteId);
        } else {
            throw new NotFoundException("Personnel with ID " + deleteId + " not found.");
        }
    }

    public void updatePersonnel(PersonnelDto personnelDto) throws NotFoundException, DuplicateNationalCodeException {
        Optional<Personnel> existingPersonnel = personnelDao.findByNationalCode(personnelDto.getNationalCode());

        if (existingPersonnel.isPresent()) {
            Personnel updatedPersonnel = DtoMapper.toEntity(personnelDto);
            updatedPersonnel.setId(existingPersonnel.get().getId());

            personnelDao.update(updatedPersonnel);
        } else {
            throw new NotFoundException("Personnel with national code " + personnelDto.getNationalCode() + " not found.");
        }
    }

    public Personnel getByPersonnelId(Long id) {
        System.out.println("Looking up Personnel by ID: " + id);
        Personnel personnel = personnelDao.findById(id);
        if (personnel == null) {
            System.out.println("Personnel not found for ID: " + id);
        }
        return personnel;
    }

    public boolean hasRole(Personnel personnel, Role role) {
        return personnel.getRole() == role;
    }

    public List<Personnel> getAll() {
        return personnelDao.findAll();
    }

    public List<Personnel> findByRole(String roleName) {
        Role role = Role.valueOf(roleName.toUpperCase());
        return personnelDao.findByRole(role);
    }

    public List<PersonnelDto> findByUsername(String Username) {
        List<Personnel> personnelList = personnelDao.findByUsername(Username);
        return personnelList.stream()
                .map(DtoMapper::toDTO)
                .collect(Collectors.toList());
    }

    // Find personnel by national code
    public List<PersonnelDto> findListByNationalCode(long nationalCode) {
        Optional<Personnel> personnel = personnelDao.findByNationalCode(nationalCode);
        return personnel.stream()
                .map(DtoMapper::toDTO)
                .collect(Collectors.toList());
    }
}
