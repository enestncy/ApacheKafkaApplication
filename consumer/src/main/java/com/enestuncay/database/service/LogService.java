package com.enestuncay.database.service;

import com.enestuncay.database.dao.LogDAO;
import com.enestuncay.database.entity.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LogService {


    @Autowired
    private LogDAO logDAO;

    public void saveLog(Log log){
        logDAO.insert(log);
    }

}
