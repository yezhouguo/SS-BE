package com.example.springweb.dao;

import java.io.Serializable;

// 词频信息类
// word：词
// count：词频
// date：年月
public class WFInfo implements Serializable {
    // private String id;
    private String word;
    private int count;
    private String date;

    public WFInfo(){
        word = null;
        count = 0;
        date = null;
    }
    public WFInfo(String id,String word,int count,String date){
        this.word=word;
        this.count=count;
        this.date=date;
    }

    @Override
    public String toString() {
        return word + "," + count + "," + date;
    }

    public String getWord() {
        return word;
    }

    public int getCount() {
        return count;
    }

    public String getDate() {
        return date;
    }

}


