package org.openhds.community.beans;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.faces.event.ValueChangeEvent;
import org.openhds.controller.service.LocationHierarchyService;
import org.openhds.domain.service.SitePropertiesService;

public class DHISDocumentBean {
	
	String startDate;
	String endDate;
	String startMonth;
	String endMonth;
	String startYear;
	String endYear;
		
	Calendar sDate;
	Calendar eDate;

	String period;
	
	String hierarchyExtId;

	SitePropertiesService siteProperties;
	LocationHierarchyService locationService;
		
	DHISDocumentBean(SitePropertiesService siteProperties, LocationHierarchyService locationService) {
		this.siteProperties = siteProperties;
		this.locationService = locationService;
	}

	public void updatePeriod(ValueChangeEvent event) {
		period = event.getNewValue().toString();
	}
		
	public String getStartDate() {
		return startDate;
	}
	
	public void setStartDate(String startDate) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(siteProperties.getDateFormat());
			Date date = sdf.parse(startDate);
			sDate = Calendar.getInstance();
			sDate.setTime(date);
		}
		catch (Exception e) { 
			e.printStackTrace();
		}
	}
	
	public String getEndDate() {
		return endDate;
	}
	
	public void setEndDate(String endDate) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(siteProperties.getDateFormat());
			Date date = sdf.parse(endDate);
			eDate = Calendar.getInstance();
			eDate.setTime(date);
		}
		catch (Exception e) { 
			e.printStackTrace();
		}
	}
	
	public String getPeriod() {
		return period;
	}
	
	public void setPeriod(String period) {
		this.period = period;
	}
	
	public Calendar getsDate() {
		return sDate;
	}

	public void setsDate(Calendar sDate) {
		this.sDate = sDate;
	}

	public Calendar geteDate() {
		return eDate;
	}

	public void seteDate(Calendar eDate) {
		this.eDate = eDate;
	}
	
	public String getStartMonth() {
		return startMonth;
	}

	public void setStartMonth(String startMonth) {
		try {		
			Integer month = Integer.parseInt(startMonth);
			month--;
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.DAY_OF_MONTH, 1);
			cal.set(Calendar.MONTH, month);
			cal.set(Calendar.YEAR, Integer.parseInt(startYear));
			sDate = cal;
		}
		catch (Exception e) { 
			e.printStackTrace();
		}
	}

	public String getEndMonth() {
		return endMonth;
	}

	public void setEndMonth(String endMonth) {
		try {		
			Integer month = Integer.parseInt(endMonth);
			month--;
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.DAY_OF_MONTH, 1);
			cal.set(Calendar.MONTH, month);
			cal.set(Calendar.YEAR, Integer.parseInt(endYear));
			eDate = cal;		
		}
		catch (Exception e) { 
			e.printStackTrace();
		}
	}
	
	public String getStartYear() {
		return startYear;
	}

	public void setStartYear(String startYear) {
		this.startYear = startYear;
	}

	public String getEndYear() {
		return endYear;
	}

	public void setEndYear(String endYear) {
		this.endYear = endYear;
	}
	
	public String getHierarchyExtId() {
		return hierarchyExtId;
	}

	public void setHierarchyExtId(String hierarchyExtId) {
		this.hierarchyExtId = hierarchyExtId;
	}
}
