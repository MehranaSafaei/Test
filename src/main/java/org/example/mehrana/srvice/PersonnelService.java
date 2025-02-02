package org.example.mehrana.srvice;

import org.example.mehrana.dao.PersonnelDao;
import org.example.mehrana.entity.Personnel;
import org.example.mehrana.entity.dto.PersonnelDto;
import org.example.mehrana.exception.DuplicateNationalCodeException;
import org.example.mehrana.exception.NotFoundException;
import org.example.mehrana.exception.SaveRecordException;
import org.example.mehrana.mapper.DtoMapper;

import java.util.Optional;

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
        Optional<Personnel> personnelToDelete = personnelDao.findById(deleteId);

        if (personnelToDelete.isPresent()) {
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
}
