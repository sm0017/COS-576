package org.openhds.controller.service.refactor;


import org.openhds.controller.exception.ConstraintViolations;
import org.openhds.domain.annotations.Authorized;
import org.openhds.domain.model.AuditableEntity;

import java.util.List;

import static org.openhds.domain.model.PrivilegeConstants.*;

public interface EntityService<T extends AuditableEntity> {

    @Authorized({VIEW_ENTITY})
    public List<T> getAll();

    @Authorized({VIEW_ENTITY})
    public T getByExtId(String id);

    @Authorized({VIEW_ENTITY})
    public T getByUuid(String id);

    @Authorized({VIEW_ENTITY})
    public boolean isEligibleForCreation(T entity, ConstraintViolations cv);

    @Authorized({DELETE_ENTITY})
    public void delete(T entity) throws IllegalArgumentException;

    @Authorized({CREATE_ENTITY})
    public void create(T entity) throws ConstraintViolations;

    @Authorized({EDIT_ENTITY})
    public void save(T entity) throws ConstraintViolations;

}
