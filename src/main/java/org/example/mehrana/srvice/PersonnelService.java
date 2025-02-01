package org.example.mehrana.srvice;

import org.example.mehrana.dao.PersonnelDao;
import org.example.mehrana.entity.Personnel;
import org.example.mehrana.entity.dto.PersonnelDto;
import org.example.mehrana.exception.DuplicateDataException;
import org.example.mehrana.exception.DuplicateNationalCodeException;
import org.example.mehrana.exception.SaveRecordException;
import org.example.mehrana.mapper.DtoMapper;

public class PersonnelService {

    private final PersonnelDao personnelDao = new PersonnelDao();

    public void create(PersonnelDto personnelDTO) throws  SaveRecordException, DuplicateNationalCodeException {

        Personnel personnel = DtoMapper.toEntity(personnelDTO);
        personnel.setId(null);
        createPersonnel(personnel);
    }

    public void createPersonnel(Personnel personnel) throws SaveRecordException, DuplicateNationalCodeException {
        if (canSavePersonnel(personnel)) {
            personnelDao.create(personnel);
        } else {
            throw new SaveRecordException();
        }
    }

    private boolean canSavePersonnel(Personnel personnel) {
        return !isDuplicate(personnel);
    }

    private boolean isDuplicate(Personnel personnel) {
        return personnelDao.isNationalIdDuplicated(personnel.getNationalCode());
    }


}
