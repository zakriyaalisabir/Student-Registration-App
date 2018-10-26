package com.qr.studentregistrationapp;

public class Course {

    public String name,preReq,semester;

    public Course(){

    }

    public Course(String a,String b){
        this.name=a;
        this.preReq=b;
    }

    public Course(String a,String b,String c){
        this.name=a;
        this.preReq=b;
        this.semester=c;
    }

}
