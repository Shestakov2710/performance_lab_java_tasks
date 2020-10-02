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

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Main {

    public static void main(String[] args) throws DummyException {

        //"D:\\messages.dat"
        //args[0]

        List<String> Messages = new ArrayList<>();
        BufferedReader content;
        try {
            content = new BufferedReader(new FileReader(args[0]));
        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
            return;
        }
        String line = null;
        try {
            line = content.readLine();
            while (line != null) {
                Messages.add(line);
                line = content.readLine();
            }
        } catch (IOException e) {
            System.out.println("I/O error!");
            return;
        }

        Connection iConnect = new ConnectionImpl();
        iConnect.start();
        Session firstSession = iConnect.createSession(true);
        Destination finalDestination = firstSession.createDestination("180");
        Producer Spielberg = firstSession.createProducer(finalDestination);

        Timer timer = new Timer();

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Spielberg.send(Messages.get(1));
                Collections.rotate(Messages,-1);
            }
        }, 0, 2000);

    }

}
