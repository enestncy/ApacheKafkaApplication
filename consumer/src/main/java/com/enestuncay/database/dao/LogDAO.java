package com.enestuncay.database.dao;


import com.enestuncay.database.entity.Log;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;


@Repository
@Transactional
public class LogDAO {


    @Autowired
    private SessionFactory sessionFactory;


    public void insert(Log log) {

        sessionFactory.getCurrentSession().save(log);
    }


}

