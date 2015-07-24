package org.openhds.controller.exception;

import java.util.ArrayList;
import java.util.List;

public class ConstraintViolations extends Exception {

    public static final String INVALID_FIELD_WORKER_EXT_ID = "Invalid Field Worker Ext Id";
    public static final String INVALID_FIELD_WORKER_UUID = "Invalid Field Worker Uuid";

    public static final String INVALID_INDIVIDUAL_EXT_ID = "Invalid Individual Ext Id";
    public static final String INVALID_INDIVIDUAL_UUID = "Invalid Individual Uuid";

    public static final String INVALID_VISIT_EXT_ID = "Invalid Visit Ext Id";
    public static final String INVALID_VISIT_UUID = "Invalid Visit Uuid";

    public static final String INVALID_LOCATION_EXT_ID = "Invalid Location Ext Id";
    public static final String INVALID_LOCATION_UUID = "Invalid Location Uuid";

    public static final String INVALID_LOCATION_HIERARCHY_EXT_ID = "Invalid Location Hierarchy Ext Id";
    public static final String INVALID_LOCATION_HIERARCHY_UUID = "Invalid Location Hierarchy Uuid";


    public static final String INVALID_SOCIAL_GROUP_EXT_ID = "Invalid Social Group Ext Id";
    public static final String INVALID_SOCIAL_GROUP_UUID = "Invalid Social Group UUID";

    public static final String INVALID_IN_MIGRATION = "Invalid Inmigration";

    public static final String INVALID_OUT_MIGRATION = "Invalid Outmigration";

    public static final String ENTITY_REFERENCES_INDIVIDUAL_WITH_DEATH_EVENT = "The entity references an Individual that has a Death event";
    public static final String ENTITY_REFERENCES_INDIVIDUAL_WITHOUT_OPEN_RESIDENCY = "The entity references an Individual that does not have an open Residency";

    public static final String OUT_MIGRATION_BEFORE_INDIVIDUAL_RESIDENCY_START = "The OutMigration's date takes place before the Residency's startDate.";

    public static final String INVALID_PREGNANCY_OUTCOME_CORE = "Error Processing Pregnancy Outcome Core Form.";
    public static final String INVALID_PREGNANCY_OUTCOME_CHILD = "Error Processing Pregnancy Outcome Child Form.";

    private static final long serialVersionUID = 4392790814928552607L;
	private List<String> violations = new ArrayList<String>();
	
	public ConstraintViolations() {}
	
	public ConstraintViolations(String msg, List<String> violations) {
		super(msg);
		this.violations = violations;
	}
	
	public ConstraintViolations(String msg) {
		super(msg);
		violations.add(msg);
	}
	
	public List<String> getViolations() {
		return violations;
	}

	public void addViolations(String violation) {
		violations.add(violation);
	}

	public boolean hasViolations() {
		return violations != null && violations.size() > 0;
	}

    public static void addViolationIfNotNull(ConstraintViolations cv, String violation){
        if(null!= cv){
            cv.addViolations(violation);
        }
    }
}

