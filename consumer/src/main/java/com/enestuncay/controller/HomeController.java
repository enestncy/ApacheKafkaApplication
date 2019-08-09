package com.enestuncay.controller;


import com.enestuncay.consumer.Consumer;


import com.enestuncay.database.entity.Log;
import com.enestuncay.database.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;


@Controller
public class HomeController {

    @Autowired
    public LogService logService;

    private int[] data = new int[5];


    @GetMapping(value = {"" , "/" , "/index"})
    public String index(HttpServletRequest request) {

        return "index";
    }


    @PostMapping(value = "/index")
    public ResponseEntity<int[]> sendLogs(HttpServletRequest request){

        setData();
        resetData();

        return new ResponseEntity<int[]>(data, HttpStatus.CREATED);
    }


    @PostMapping(value = "/saveLogs")
    public void saveData(HttpServletRequest request){

        saveLog();
    }


    public void setData(){

        for(int i = 0; i < data.length; i++)
            data[i] = Consumer.logPerCity[i];
    }

    public void resetData(){
        for (int i = 0; i< Consumer.logPerCity.length; i++)
            Consumer.logPerCity[i] = 0;
    }

    public void saveLog(){
        if(Consumer.flag){
            logService.saveLog(new Log(Consumer.line));
            Consumer.flag = false;
        }
    }

}
