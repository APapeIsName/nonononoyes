package com.example.teamproject_1;

import org.json.JSONObject;

public class Note {
    //<%tv_1_1%true or false(전공true 교양false)%과목이름%11(시작시간종료시간)%교수님성함%건물이름%호실>
    private String isMajor;
    private String subject;
    private String firstToLast;
    private String professor;
    private String book;
    private String building;
    private String room;

    public String getIsMajor() {
        return isMajor;
    }

    public void setIsMajor(String isMajor) {
        this.isMajor = isMajor;
    }

    public String getFirstToLast() {
        return firstToLast;
    }

    public void setFirstToLast(String firstToLast) {
        this.firstToLast = firstToLast;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getProfessor() {
        return professor;
    }

    public void setProfessor(String professor) {
        this.professor = professor;
    }

    public String getBook() {
        return book;
    }

    public void setBook(String book) {
        this.book = book;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }
}
