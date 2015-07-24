package org.openhds.errorhandling.service.impl;

import java.util.List;

import org.openhds.controller.service.FieldWorkerService;
import org.openhds.dao.service.GenericDao.RangeProperty;
import org.openhds.dao.service.GenericDao.ValueProperty;
import org.openhds.domain.model.FieldWorker;
import org.openhds.errorhandling.dao.ErrorLogDAO;
import org.openhds.domain.model.ErrorLog;
import org.openhds.errorhandling.endpoint.ErrorServiceEndPoint;
import org.openhds.errorhandling.service.ErrorHandlingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ErrorHandlingServiceImpl implements ErrorHandlingService {

    @Autowired
    private List<ErrorServiceEndPoint> errorEndPoints;

    @Autowired
    private FieldWorkerService fieldWorkerService;
    
    @Autowired
    private ErrorLogDAO errorLogDao;

    @Override
    public ErrorLog logError(ErrorLog error) {
        if (null == error.getFieldWorker()) {
            error.setFieldWorker(fieldWorkerService.getUnknownFieldWorker());
        }

        for (ErrorServiceEndPoint errorEndPoint : errorEndPoints) {
            errorEndPoint.logError(error);
        }

        return error;
    }

    @Override
    public ErrorLog findErrorById(String id) {
        return errorLogDao.findById(id);
    }

    @Override
    public List<ErrorLog> findAllErrorsByEntityType(String entityType) {
        return errorLogDao.findByEntityType(entityType);
    }

    @Override
    public ErrorLog updateErrorLog(ErrorLog error) {
        for (ErrorServiceEndPoint errorEndPoint : errorEndPoints) {
            errorEndPoint.updateError(error);
        }

        return error;
    }

    @Override
    public List<ErrorLog> findAllErrorsByResolutionStatus(String resolutionStatus) {
        return errorLogDao.findByResolutionStatus(resolutionStatus);
    }

    @Override
    public List<ErrorLog> findAllErrorsByAssignment(String assignedTo) {
        return errorLogDao.findByAssignedTo(assignedTo);
    }

    @Override
    public List<ErrorLog> findAllErrorsByFieldWorker(FieldWorker fieldWorker) {
        return errorLogDao.findByFieldWorker(fieldWorker);
    }

    @Override
    public List<ErrorLog> findAllErrorsByFilters(RangeProperty range, ValueProperty... properties) {
        return errorLogDao.findAllByFilters(range, properties);
    }
}
