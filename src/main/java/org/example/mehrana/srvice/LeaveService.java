package org.example.mehrana.srvice;

import org.example.mehrana.dao.LeaveDao;
import org.example.mehrana.entity.Leave;
import org.example.mehrana.entity.Personnel;
import org.example.mehrana.entity.dto.LeaveDto;
import org.example.mehrana.entity.dto.PersonnelDto;
import org.example.mehrana.entity.enums.Role;
import org.example.mehrana.exception.NotFoundException;
import org.example.mehrana.exception.PersonnelNotFoundException;
import org.example.mehrana.exception.SaveRecordException;
import org.example.mehrana.mapper.DtoMapper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LeaveService {

    private final LeaveDao leaveDao = new LeaveDao();
    private final PersonnelService personnelService = new PersonnelService();

    public void create(Leave entity) throws SaveRecordException {
        try {
            if (!isValidatePersonnel(entity.getPersonnel())) {
                throw new PersonnelNotFoundException();
            }
//            // Checking the repetition of leave for personnel
//            if (leaveDao.existsByPersonnelId(entity.getPersonnel().getId())) {
//                throw new PersonnelAlreadyHasLeaveException();
//            }
            leaveDao.create(entity);
        } catch (Exception e) {
            throw new SaveRecordException();
        }
    }


    public List<Leave> findAllByPersonnelUername(String username) {
        return leaveDao.findAllByUsername(username);

    }

    public void createLeave(LeaveDto leaveDto, PersonnelDto personnelDto) throws SaveRecordException {
        Personnel personnel = personnelService.getByPersonnelId(personnelDto.getId());  //I made mistake because I don't set ID in DtoMapper

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

    //TODO: I should add (findById, Role(in Personnel), LeaveToDeleteByNationalCode, findByUsername, findByNationalCode(that show me List)
    public Leave findById(long leaveId) throws NotFoundException {
        Leave leave = leaveDao.findById(leaveId);
        if (leave == null) {
            throw new NotFoundException("Leave not found with ID: " + leaveId);
        }
        return leave;
    }

    public List<Personnel> findByRole(String role) {
        return personnelService.findByRole(role);
    }


    public void deleteLeaveByNationalCode(long nationalCode) throws SaveRecordException, NotFoundException {
        Optional<PersonnelDto> personnelOpt = Optional.ofNullable(personnelService.getByNationalCode(nationalCode));
        if (personnelOpt.isPresent()) {
            leaveDao.deleteByPersonnelId(personnelOpt.get().getId());
        } else {
            throw new NotFoundException("Personnel with National Code " + nationalCode + " not found.");
        }
    }

        public void approveLeave(Long leaveId, Long approverId) throws SaveRecordException {
        Personnel approver = personnelService.getByPersonnelId(approverId);
        Leave leave = leaveDao.findById(leaveId);

        if (personnelService.hasRole(approver, Role.ADMIN) || personnelService.hasRole(approver, Role.MANAGER)) {
            leave.setApproved(true);
            leaveDao.update(leave);
            System.out.println("Leave approved successfully.");
        } else {
            throw new SaveRecordException("You do not have permission to approve this leave.");
        }
    }

    public void rejectLeave(Long leaveId, Long approverId, String rejectionReason) throws SaveRecordException {
        try {
            Personnel approver = personnelService.getByPersonnelId(approverId);
            Leave leave = leaveDao.findById(leaveId);
            if (leave == null) {
                throw new NotFoundException("Leave not found with ID: " + leaveId);
            }
            if (approver == null) {
                throw new PersonnelNotFoundException("Approver not found with ID: " + approverId);
            }
            if (personnelService.hasRole(approver, Role.ADMIN) || personnelService.hasRole(approver, Role.MANAGER)) {
                if (leave.isRejected()) {
                    System.out.println("Leave has already been rejected.");
                    return;
                }
                if (leave.isApproved()) {
                    System.out.println("Leave has already been approved and cannot be rejected.");
                    return;
                }

                leave.setRejected(true);
                leave.setRejectReason(rejectionReason); //reason for rejection
                leaveDao.update(leave);
                System.out.println("Leave rejected successfully.");
            } else {
                throw new SaveRecordException("You do not have permission to reject this leave.");
            }
        } catch (Exception e) {
            throw new SaveRecordException("Error occurred while rejecting leave: " + e.getMessage());
        }
    }


    public List<PersonnelDto> findByUsername(String username) {
        return personnelService.findByUsername(username);
    }

    public List<PersonnelDto> findByNationalCode(long nationalCode) {
        return personnelService.findListByNationalCode(nationalCode);
    }
    public List<Leave> findLeaveByPersonnelId(Long personnelId) {
        List<Leave> leaves = leaveDao.findLeaveByPersonnelId(personnelId);
        return leaves != null ? leaves : new ArrayList<>();
    }

    public List<Leave> getAll() {
        return leaveDao.findAll();
    }

}
