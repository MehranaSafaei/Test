package org.example.mehrana.srvice;

import jakarta.transaction.Transactional;
import org.example.mehrana.dao.LeaveDao;
import org.example.mehrana.entity.Leave;
import org.example.mehrana.entity.Personnel;
import org.example.mehrana.entity.dto.LeaveDto;
import org.example.mehrana.entity.dto.PersonnelDto;
import org.example.mehrana.exception.NotFoundException;
import org.example.mehrana.exception.PersonnelAlreadyHasLeaveException;
import org.example.mehrana.exception.PersonnelNotFoundException;
import org.example.mehrana.exception.SaveRecordException;
import org.example.mehrana.mapper.DtoMapper;

public class LeaveService {

    private final LeaveDao leaveDao = new LeaveDao();
    private final PersonnelService personnelService = new PersonnelService();

    @Transactional
    public void create(Leave entity) throws SaveRecordException {
        try {
//            if (!isValidatePersonnel(entity.getPersonnel())) {
//                throw new PersonnelNotFoundException();
//            }
//            // Checking the repetition of leave for personnel
//            if (leaveDao.existsByPersonnelId(entity.getPersonnel().getId())) {
//                throw new PersonnelAlreadyHasLeaveException();
//            }
            leaveDao.create(entity);
        } catch (Exception e) {
            throw new SaveRecordException();
        }
    }

    public void createLeave(LeaveDto leaveDto) throws SaveRecordException {
        Leave leave = DtoMapper.toEntity(leaveDto);
        create(leave);
    }


    //checking that nationalCode belong to Personnel
    public boolean isValidatePersonnel(Personnel personnel) throws NotFoundException {
        if (personnel == null || personnel.getNationalCode() == null) {
            return false;
        }
        PersonnelDto existingPersonnel = personnelService.getByNationalCode(personnel.getNationalCode());
        return existingPersonnel != null;
    }


}
