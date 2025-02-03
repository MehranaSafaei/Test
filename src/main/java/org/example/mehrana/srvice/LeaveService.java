package org.example.mehrana.srvice;

import jakarta.transaction.Transactional;
import org.example.mehrana.dao.LeaveDao;
import org.example.mehrana.entity.Leave;
import org.example.mehrana.entity.Personnel;
import org.example.mehrana.entity.dto.LeaveDto;
import org.example.mehrana.exception.SaveRecordException;
import org.example.mehrana.mapper.DtoMapper;

public class LeaveService {
    private LeaveDao leaveDao = new LeaveDao();

    public void create(Leave entity) throws SaveRecordException {
        try {
            if (isValidatePersonnel(entity.getPersonnel())) {
                throw new NullPointerException("Personnel already exists in this leave");
            } else {
                leaveDao.create(entity);
            }
        } catch (Exception e) {
            throw new SaveRecordException();

        }
    }
        public void createLeave (LeaveDto leaveDto) throws SaveRecordException {
            Leave leave = DtoMapper.toEntity(leaveDto);
            create(leave);
        }
        public boolean isValidatePersonnel (Personnel personnel){
            return personnel.getNationalCode() == null;
        }

    }
