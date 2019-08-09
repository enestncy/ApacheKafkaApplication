package com.enestuncay.thread;


import com.enestuncay.log.LogCreator;
import com.enestuncay.producer.Producer;

import java.io.IOException;

public class MultiThread {

    private String path = "";

    private Task task = Task.LOG;

    private Object lock = new Object();

    LogCreator logCreator = new LogCreator();

    Producer producer = new Producer("buyukveri");



    Thread writeLog = new Thread(new Runnable() {
        @Override
        public void run() {
            int fileCounter = 1;

            while (true) {

                synchronized (lock) {

                    if(!task.equals(Task.LOG)){
                        try {
                            lock.wait();
                        }catch (InterruptedException e){
                            e.printStackTrace();
                        }
                    }

                    path = String.format("C:\\ServerLogs\\log-%d.log", fileCounter);

                    try {
                        if (!logCreator.createLogFile(path)) {
                            fileCounter++;
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (SecurityException ex) {
                        ex.printStackTrace();
                    }



                    lock.notify();
                    try {
                        Thread.sleep(2500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    task = Task.PRODUCE;


                }
            }
        }
    });

    Thread produce = new Thread(new Runnable() {
        @Override
        public void run() {

            String line="";

            while (true){
                synchronized (lock){
                    if(!task.equals(Task.PRODUCE)){
                        try {
                            lock.wait();
                        }catch (InterruptedException e){
                            e.printStackTrace();
                        }
                    }

                    producer.sendMsj(producer.readFile(path));


                    lock.notify();
                    try {
                        Thread.sleep(2500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    task = Task.LOG;
                }
            }
        }
    });



    public void run(){

        try{
            Thread.sleep(5000);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }

        writeLog.start();
        produce.start();

        try {
            writeLog.join();
            produce.join();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}


enum Task{

    LOG , PRODUCE;
}

