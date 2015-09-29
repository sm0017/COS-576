package hello;

import java.util.concurrent.CountDownLatch;

//POJO defines the method for receiving the message
// It responds to the published messages
public class Receiver 
{

	private CountDownLatch latch = new CountDownLatch(1);

	public void receiveMessage(String message) {
		System.out.println("Received <" + message + ">");
		latch.countDown();
	}

	public CountDownLatch getLatch() {
		return latch;
	}

}