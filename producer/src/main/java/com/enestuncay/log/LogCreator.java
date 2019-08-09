package com.enestuncay.log;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Random;

public class LogCreator {

    //Class Elements
    private String[] cities = new String[]{"Istanbul" , "Tokyo" , "Moscow" , "Beijing" , "London"};

    private String[] levels = new String[]{"INFO" , "WARN" , "FATAL" , "DEBUG" , "ERROR"};

    private Random r = new Random();

    public int fileCounter = 1;

    public String path;

    public LogCreator(){
        File dir = new File("C:\\ServerLogs");

        if(!dir.exists())
            dir.mkdir();
    }


    //Private Methods
    private String getLogCity(){
        return cities[r.nextInt(5)];
    }

    private String getLogLevel(){
        return levels[r.nextInt(5)];
    }

    private String createLog(){

        //Log TimeStamp
        LocalTime time = LocalTime.now();
        LocalDate date = LocalDate.now();
        String timeStamp = String.format("%s %s",date.toString(),time.toString());

        //Log City and Level
        String logCity = getLogCity();
        String logLevel = getLogLevel();

        //Log detail message
        String detail = String.format("Hello-from-%s",logCity);


        return String.format("%s %s %s %s%n", timeStamp, logLevel , logCity , detail);

    }

    public boolean createLogFile(String path)throws SecurityException , IOException {

        File file = new File(path);
        boolean flag = true;

        if(!file.exists())
        {
            file.createNewFile();
        }
        else{
            if(file.length() > 2097151){ // max log file size 2MB
                flag = false;
            }
        }

        if(flag)
        {
            String log = createLog();
            FileWriter fileWriter = new FileWriter(file , true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(log);
            bufferedWriter.close();
        }

        return flag;
    }



}
