package com.enestuncay.database.entity;


import javax.persistence.*;

@Entity
@Table
public class Log {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String timeStamp;

    @Column
    private String city;

    @Column
    private String level;

    @Column
    private String detail;

    //def ctor
    public Log(){}


    public Log(String line){
        String [] parsedLine = line.split(" ");

        timeStamp = parsedLine[0] + " " + parsedLine[1];
        level = parsedLine[2];
        city = parsedLine[3];
        detail = parsedLine[4];
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    @Override
    public String toString() {
        return "Log{" +
                "id=" + id +
                ", timeStamp='" + timeStamp + '\'' +
                ", city='" + city + '\'' +
                ", level='" + level + '\'' +
                ", detail='" + detail + '\'' +
                '}';
    }
}
