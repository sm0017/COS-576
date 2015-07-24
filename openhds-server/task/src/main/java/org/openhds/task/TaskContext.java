package org.openhds.task;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class TaskContext {
    private File destinationFile;
    private Map<String, String> extraData = new HashMap<String, String>();

    public TaskContext(File destinationFile) {
        this.destinationFile = destinationFile;
    }

    public File getDestinationFile() {
        return destinationFile;
    }

    public void addExtraData(String name, String value) {
        extraData.put(name, value);
    }

    public String getExtraData(String name) {
        return extraData.get(name);
    }
}
