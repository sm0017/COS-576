package org.openhds.ddi.service;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import edu.umich.icpsr.ddi.CodeBookDocument;

public interface CodeBookService {
		
	void createCodeBook();
	
	void buildDocumentDescription();
	
	void buildStudyDescription();
	
	void buildFileDescription(String tableName, String classDesc, String[] propertyNames, String uri);
	
	void buildDataDescription(String tableName,
			String fieldName, String type, String iteration, String desc, List<?> enumList);
	
	CodeBookDocument getDoc(); 
	
	void setMetadata(Map<?, ?> metadata);
	
	void setProperties(Properties properties);
}
