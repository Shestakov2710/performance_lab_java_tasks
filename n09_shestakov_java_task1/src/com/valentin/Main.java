package com.valentin;

import ru.pflb.mq.dummy.exception.DummyException;
import ru.pflb.mq.dummy.implementation.ConnectionImpl;
import ru.pflb.mq.dummy.implementation.DestinationImpl;
import ru.pflb.mq.dummy.implementation.SessionImpl;
import ru.pflb.mq.dummy.implementation.ProducerImpl;
import ru.pflb.mq.dummy.interfaces.Connection;
import ru.pflb.mq.dummy.interfaces.Session;
import ru.pflb.mq.dummy.interfaces.Destination;
import ru.pflb.mq.dummy.interfaces.Producer;

import java.util.*;

public class Main {

    public static void main(String[] args) throws DummyException {

    	Collection Messages = new ArrayList<>();
	    Messages.add("Раз");
		Messages.add("Два");
		Messages.add("Три");
		Iterator Iter = Messages.iterator();

    	Connection iConnect = new ConnectionImpl();
	    iConnect.start();
		Session firstSession = iConnect.createSession(true);
		Destination finalDestination = firstSession.createDestination("180");
		Producer Spielberg = firstSession.createProducer(finalDestination);

		Timer timer = new Timer();

		timer.scheduleAtFixedRate(new TimerTask() {
				@Override
				public void run() {
					if (Iter.hasNext()){
					Object Output = Iter.next();
					Spielberg.send(Output.toString());
					}else{
						firstSession.close();
						iConnect.close();
						timer.cancel();
					}
				}
			}, 0, 2000);
    }

}
