package org.openhds.task.support;

public interface TaskExecutor {

    void executeIndividualXmlWriterTask();
    
    void executeLocationXmlWriterTask();
    
    void executeRelationshipXmlWriterTask();
    
    void executeSocialGroupXmlWriterTask();
    
    void executeResidencyXmlWriterTask();
    
    void executeMembershipXmlWriterTask();
    
    void executeVisitWriterTask(int roundNumber);

}
