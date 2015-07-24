package org.openhds.webservice.resources;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.openhds.dao.service.GenericDao.RangeProperty;
import org.openhds.dao.service.GenericDao.ValueProperty;
import org.openhds.dao.util.RangePropertyHelper;
import org.openhds.dao.util.ValuePropertyHelper;
import org.openhds.controller.exception.ConstraintViolations;
import org.openhds.controller.service.FieldWorkerService;
import org.openhds.domain.model.FieldWorker;
import org.openhds.domain.model.ErrorLog;
import org.openhds.errorhandling.service.ErrorHandlingPropertiesService;
import org.openhds.errorhandling.service.ErrorHandlingService;
import org.openhds.webservice.response.WebserviceResult;
import org.openhds.webservice.response.WebserviceResultHelper;
import org.openhds.webservice.response.constants.ResultCodes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/errors")
public class ErrorLogResource {
    private static final Logger logger = LoggerFactory.getLogger(ErrorLogResource.class);

    @Autowired
    private FieldWorkerService fieldWorkerService;

    @Autowired
    private ErrorHandlingService errorService;

    @Autowired
    private ErrorHandlingPropertiesService errorProperties;

    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> getErrors(@RequestParam(value="resolutionStatus", required=false) String resolutionStatus,
                                       @RequestParam(value="assignedTo", required=false) String assignedTo,
                                       @RequestParam(value="fieldWorkerExtId", required=false) String fieldWorkerExtId,
                                       @RequestParam(value="entityType", required=false) String entityType,
                                       @RequestParam(value="minDate", required = false) String minDate,
                                       @RequestParam(value = "maxDate", required = false) String maxDate) throws NoSuchMethodException, SecurityException, ConstraintViolations {

        List<ValueProperty> properties = new ArrayList<ValueProperty>();
        WebserviceResult result = new WebserviceResult();

        if (resolutionStatus != null && !resolutionStatus.isEmpty()) {
            properties.add(ValuePropertyHelper.getValueProperty("resolutionStatus", resolutionStatus));
        }

        if (assignedTo != null && !assignedTo.isEmpty()) {
            properties.add(ValuePropertyHelper.getValueProperty("assignedTo", assignedTo));
        }

        if (entityType != null && !entityType.isEmpty()) {
            properties.add(ValuePropertyHelper.getValueProperty("entityType", entityType));
        }

        if (fieldWorkerExtId != null && !fieldWorkerExtId.isEmpty()) {
            FieldWorker fieldWorker = fieldWorkerService.findFieldWorkerByExtId(fieldWorkerExtId);
            if (fieldWorker != null) {
                properties.add(ValuePropertyHelper.getValueProperty("fieldWorker", fieldWorker));
            } else {
                result.setParameterResultsMessage("Non-existent fieldworker: "+ fieldWorkerExtId);
            }
        }

        RangeProperty range = null;

        SimpleDateFormat format = new SimpleDateFormat("MM-dd-yyyy");

        Calendar minRange = Calendar.getInstance();
        Calendar maxRange = Calendar.getInstance();
        try {

            if (minDate != null && !minDate.isEmpty()) {
                minRange.setTime(format.parse(minDate));
            } else {
                //default to 7 days previous
                long dayInMill = 1000 * 60 * 60 * 24;
                minRange.setTime(new Date(System.currentTimeMillis() - (7 * dayInMill)));
            }

            if (maxDate != null && !maxDate.isEmpty()) {
                maxRange.setTime(format.parse(maxDate));
            }
        } catch (ParseException e) {
            result.setResultMessage(e.getMessage());
            result.setResultCode(ResultCodes.BAD_PARAMETER_CODE);
            result.setStatus(ResultCodes.ERROR);

            return new ResponseEntity<WebserviceResult>(result, HttpStatus.BAD_REQUEST);
        }

        result.setDateRangeMessage("Showing results from "+format.format(minRange.getTime())+ " to "+format.format(maxRange.getTime()));
        range = RangePropertyHelper.getRangeProperty("insertDate", minRange, maxRange);

        List<ErrorLog> errors = errorService.findAllErrorsByFilters(range, properties.toArray(new ValueProperty[properties.size()]));

        List<ErrorLog> shallowCopies = new ArrayList<ErrorLog>();

        for (ErrorLog error : errors) {
            shallowCopies.add(shallowCopyErrorLog(error));
        }

        result.addDataElement("errors", shallowCopies);
        result.setResultCode(ResultCodes.SUCCESS_CODE);
        result.setStatus(ResultCodes.SUCCESS);
        result.setResultMessage("There are " + errors.size() + " matching records");

        return new ResponseEntity<WebserviceResult>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/{uuid}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> getErrorLogById(@PathVariable String uuid) {
        ErrorLog error = errorService.findErrorById(uuid);

        if (error == null) {
            return WebserviceResultHelper.entityNotFoundResponse("Error record not found", "error");
        }

        WebserviceResult result = new WebserviceResult();
        result.addDataElement("error", shallowCopyErrorLog(error));
        result.setResultCode(ResultCodes.SUCCESS_CODE);
        result.setStatus(ResultCodes.SUCCESS);
        result.setResultMessage("Error log was successfully retrieved");

        return new ResponseEntity<WebserviceResult>(result, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PUT, produces = "application/json")
    public ResponseEntity<?> updateErrorLog(@RequestBody ErrorLog error) {

        ErrorLog existingError = errorService.findErrorById(error.getUuid());

        if (existingError == null) {
            return WebserviceResultHelper.entityNotFoundResponse("Error record was not found,  unable to update", "error");
        }

        if (error.getAssignedTo() != null) {
            existingError.setAssignedTo(error.getAssignedTo());
        }
        if (error.getDateOfResolution() != null) {
            existingError.setDateOfResolution(error.getDateOfResolution());
        }
        if (error.getFieldWorker() != null) {
            existingError.setFieldWorker(error.getFieldWorker());
        }
        if (error.getResolutionStatus() != null) {
            existingError.setResolutionStatus(error.getResolutionStatus());
        }

        WebserviceResult result = new WebserviceResult();
        result.addDataElement("error", existingError);
        result.setResultCode(ResultCodes.SUCCESS_CODE);
        result.setStatus(ResultCodes.SUCCESS);
        result.setResultMessage("Error record has been updated");

        errorService.updateErrorLog(existingError);

        return new ResponseEntity<WebserviceResult>(result, HttpStatus.OK);
    }

    public static ErrorLog shallowCopyErrorLog(ErrorLog errorLog) {
        ErrorLog shallowErrorLog = new ErrorLog();
        shallowErrorLog.setAssignedTo(errorLog.getAssignedTo());
        shallowErrorLog.setDataPayload(errorLog.getDataPayload());
        shallowErrorLog.setDateOfResolution(errorLog.getDateOfResolution());
        shallowErrorLog.setErrors(errorLog.getErrors());
        shallowErrorLog.setInsertDate(errorLog.getInsertDate());
        shallowErrorLog.setResolutionStatus(errorLog.getResolutionStatus());
        shallowErrorLog.setUuid(errorLog.getUuid());
        shallowErrorLog.setEntityType(errorLog.getEntityType());

        FieldWorker fieldWorker = new FieldWorker();
        fieldWorker.setExtId(errorLog.getFieldWorker().getExtId());
        shallowErrorLog.setFieldWorker(fieldWorker);

        return shallowErrorLog;
    }
}