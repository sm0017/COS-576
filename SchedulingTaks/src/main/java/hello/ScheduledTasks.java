package hello;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
@Component
public class ScheduledTasks {
	

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
// @Sechduled annotation defines when particular method runs
    @Scheduled(fixedRate = 5000) //the interval between method invocation
    public void reportCurrentTime() {
        System.out.println("The time is now " + dateFormat.format(new Date()));
    }

}
