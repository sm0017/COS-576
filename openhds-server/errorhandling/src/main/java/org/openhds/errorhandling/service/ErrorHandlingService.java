package org.openhds.errorhandling.service;

import java.util.List;
import org.openhds.dao.service.GenericDao.ValueProperty;
import org.openhds.dao.service.GenericDao.RangeProperty;
import org.openhds.domain.model.FieldWorker;
import org.openhds.domain.model.ErrorLog;

public interface ErrorHandlingService {

    List<ErrorLog> findAllErrorsByEntityType(String entityType);

    ErrorLog logError(ErrorLog error);
    
    ErrorLog updateErrorLog(ErrorLog error);

    ErrorLog findErrorById(String id);
    
    List<ErrorLog> findAllErrorsByResolutionStatus(String resolutionStatus);
    
    List<ErrorLog> findAllErrorsByAssignment(String assignedTo);
    
    List<ErrorLog> findAllErrorsByFieldWorker(FieldWorker fieldWorker);

    List<ErrorLog> findAllErrorsByFilters(RangeProperty range, ValueProperty... properties);
}
