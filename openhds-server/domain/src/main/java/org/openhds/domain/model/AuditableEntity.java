package org.openhds.domain.model;

import org.openhds.domain.annotations.Description;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.util.Calendar;

/**
 * An AuditableEntity can be any entity stored in the database that needs to be audited
 */
@MappedSuperclass
public abstract class AuditableEntity implements UuidIdentifiable, Serializable {

    private static final long serialVersionUID = -4703049354466276068L;

    @Id
    @Column(length=32)
    String uuid;

    @ManyToOne(fetch=FetchType.LAZY)
    @Description(description="The user that voided the data.")
    protected User voidBy;

    @Description(description="Reason for voiding the data.")
    protected String voidReason;

    @Description(description="Indicator for signaling some data to be deleted.")
    protected boolean deleted = false;

    @Description(description="Date that the data was voided.")
    protected Calendar voidDate;

    @ManyToOne(fetch=FetchType.LAZY)
    @Description(description="User who inserted the data.")
    protected User insertBy;

    @Description(description="Date of insertion.")
    protected Calendar insertDate;

    @Override
    public String getUuid() {
        return uuid;
    }

    @Override
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public User getVoidBy() {
        return voidBy;
    }

    public void setVoidBy(User voidBy) {
        this.voidBy = voidBy;
    }

    public String getVoidReason() {
        return voidReason;
    }

    public void setVoidReason(String voidReason) {
        this.voidReason = voidReason;
    }

    @XmlTransient
    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public Calendar getVoidDate() {
        return voidDate;
    }

    public void setVoidDate(Calendar voidDate) {
        this.voidDate = voidDate;
    }

    public User getInsertBy() {
        return insertBy;
    }

    public void setInsertBy(User insertBy) {
        this.insertBy = insertBy;
    }

    public Calendar getInsertDate() {
        return insertDate;
    }

    public void setInsertDate(Calendar insertDate) {
        this.insertDate = insertDate;
    }

    @Override
    public int hashCode() {
        if (null == uuid) {
            return 0;
        }
        return uuid.hashCode();
    }
}
