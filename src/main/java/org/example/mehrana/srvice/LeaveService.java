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

import javax.swing.text.html.Option;
import java.time.LocalDate;
import java.util.Optional;

public class LeaveService {

    private final LeaveDao leaveDao = new LeaveDao();
    private final PersonnelService personnelService = new PersonnelService();

    @Transactional
    public void create(Leave entity) throws SaveRecordException {
        try {
            if (!isValidatePersonnel(entity.getPersonnel())) {
                throw new PersonnelNotFoundException();
            }
            // Checking the repetition of leave for personnel
            if (leaveDao.existsByPersonnelId(entity.getPersonnel().getId())) {
                throw new PersonnelAlreadyHasLeaveException();
            }
            leaveDao.create(entity);
        } catch (Exception e) {
            throw new SaveRecordException();
        }
    }

    public void createLeave(LeaveDto leaveDto, PersonnelDto personnelDto) throws SaveRecordException {
        Personnel personnel = personnelService.getByPersonnelId(personnelDto.getId());
        Leave leave = DtoMapper.toEntity(leaveDto, personnel);
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

    public void updateLeave(long leaveId, LeaveDto updateLeaveDto, long personnelId) throws SaveRecordException {
        Leave leave = leaveDao.findById(leaveId);
        Personnel personnel = personnelService.getByPersonnelId(personnelId);
        //checking leave ownership
        if (!leave.getPersonnel().getId().equals(personnel.getId())) {
            throw new SaveRecordException();
        }
        //TODO:I should add method that when manager approve, personnel can't change it
        //checking that date doesn't be before day
        LocalDate localDate = LocalDate.now();
        if (updateLeaveDto.getStartDate().isBefore(localDate) || updateLeaveDto.getEndDate().isAfter(localDate)) {
            throw new SaveRecordException();
        }
        //checking the conflict of new dates with other personnel leaves
        Optional<Leave> overLappingLeave = leaveDao.findByPersonnelIdAndDateRange(personnelId, updateLeaveDto.getStartDate(), updateLeaveDto.getEndDate());
        if (overLappingLeave.isPresent() && !overLappingLeave.get().getId().equals(leave.getId())) {
            throw new SaveRecordException();
        }
        leave.setStartDate(updateLeaveDto.getStartDate());
        leave.setEndDate(updateLeaveDto.getEndDate());
        leaveDao.update(leave);
    }

    public void delete(long leaveId) throws SaveRecordException {
        Leave leaveToDelete = leaveDao.findById(leaveId);
        if (leaveToDelete != null) {
            leaveDao.delete(leaveId);
        }
        throw new SaveRecordException();
    }
    //TODO: I should add (findById, Role(in Personnel), LeaveToDeleteByNationalCode, findByName, findByNationalCode(that show me List)

}
