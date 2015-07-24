package org.openhds.domain.model.bioko;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.util.Calendar;

@XmlRootElement(name = "locationForm")
@XmlAccessorType(XmlAccessType.FIELD)
public class LocationForm implements Serializable{

    //core form fields
    @XmlElement(name = "entity_uuid")
    private String uuid;

    @XmlElement(name = "entity_ext_id")
    private String entityExtId;

    @XmlElement(name = "processed_by_mirth")
    private boolean processedByMirth;

    @XmlElement(name = "field_worker_ext_id")
    private String fieldWorkerExtId;

    @XmlElement(name = "field_worker_uuid")
    private String fieldWorkerUuid;

    @XmlElement(name = "collection_date_time")
    @XmlJavaTypeAdapter(org.openhds.domain.util.CalendarAdapter.class)
    private Calendar collectionDateTime;

    //location form fields
    @XmlElement(name = "hierarchy_ext_id")
    private String hierarchyExtId;

    @XmlElement(name = "hierarchy_uuid")
    private String hierarchyUuid;

    @XmlElement(name = "hierarchy_parent_uuid")
    private String hierarchyParentUuid;

    @XmlElement(name = "location_ext_id")
    private String locationExtId;

    @XmlElement(name = "location_name")
    private String locationName;

    @XmlElement(name = "location_type")
    private String locationType;

    @XmlElement(name = "community_name")
    private String communityName;

    @XmlElement(name = "community_code")
    private String communityCode;

    @XmlElement(name = "map_area_name")
    private String mapAreaName;

    @XmlElement(name = "locality_name")
    private String localityName;

    @XmlElement(name = "sector_name")
    private String sectorName;

    @XmlElement(name = "location_building_number")
    private String buildingNumber;

    @XmlElement(name = "location_floor_number")
    private String floorNumber;

    @XmlElement(name = "description")
    private String description;

    @XmlElement(name = "longitude")
    private String longitude;

    @XmlElement(name = "latitude")
    private String latitude;

    public String getHierarchyParentUuid() {
        return hierarchyParentUuid;
    }

    public void setHierarchyParentUuid(String hierarchyParentUuid) {
        this.hierarchyParentUuid = hierarchyParentUuid;
    }

    public String getEntityExtId() {
        return entityExtId;
    }

    public void setEntityExtId(String entityExtId) {
        this.entityExtId = entityExtId;
    }

    public String getFieldWorkerUuid() {
        return fieldWorkerUuid;
    }

    public void setFieldWorkerUuid(String fieldWorkerUuid) {
        this.fieldWorkerUuid = fieldWorkerUuid;
    }

    public String getHierarchyUuid() {
        return hierarchyUuid;
    }

    public void setHierarchyUuid(String hierarchyUuid) {
        this.hierarchyUuid = hierarchyUuid;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBuildingNumber() {
        return buildingNumber;
    }

    public void setBuildingNumber(String buildingNumber) {
        this.buildingNumber = buildingNumber;
    }

    public String getFloorNumber() {
        return floorNumber;
    }

    public void setFloorNumber(String floorNumber) {
        this.floorNumber = floorNumber;
    }

    public boolean isProcessedByMirth() {
        return processedByMirth;
    }

    public void setProcessedByMirth(boolean processedByMirth) {
        this.processedByMirth = processedByMirth;
    }

    public String getFieldWorkerExtId() {
        return fieldWorkerExtId;
    }

    public void setFieldWorkerExtId(String fieldWorkerExtId) {
        this.fieldWorkerExtId = fieldWorkerExtId;
    }

    public Calendar getCollectionDateTime() {
        return collectionDateTime;
    }

    public void setCollectionDateTime(Calendar collectionDateTime) {
        this.collectionDateTime = collectionDateTime;
    }

    public String getHierarchyExtId() {
        return hierarchyExtId;
    }

    public void setHierarchyExtId(String hierarchyExtId) {
        this.hierarchyExtId = hierarchyExtId;
    }

    public String getLocationExtId() {
        return locationExtId;
    }

    public void setLocationExtId(String locationExtId) {
        this.locationExtId = locationExtId;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getLocationType() {
        return locationType;
    }

    public void setLocationType(String locationType) {
        this.locationType = locationType;
    }

    public String getCommunityName() {
        return communityName;
    }

    public void setCommunityName(String communityName) {
        this.communityName = communityName;
    }

    public String getCommunityCode() {
        return communityCode;
    }

    public void setCommunityCode(String communityCode) {
        this.communityCode = communityCode;
    }

    public String getMapAreaName() {
        return mapAreaName;
    }

    public void setMapAreaName(String mapAreaName) {
        this.mapAreaName = mapAreaName;
    }

    public String getLocalityName() {
        return localityName;
    }

    public void setLocalityName(String localityName) {
        this.localityName = localityName;
    }

    public String getSectorName() {
        return sectorName;
    }

    public void setSectorName(String sectorName) {
        this.sectorName = sectorName;
    }
}
