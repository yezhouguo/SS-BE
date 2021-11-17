package com.example.springweb.service;

import com.example.springweb.dao.WFInfo;
import com.example.springweb.mapper.WFMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.tags.ParamAware;

import javax.annotation.Resource;

import java.time.Month;
import java.util.List;
import java.util.Map;

@Service
public class WFService {
    @Resource
    private WFMapper wfMapper;

    public List<WFInfo> getWFListbyMonth(int k,String year,String month) {

        // if(Integer.valueOf(month)<=9)
        //     month = "0" + month;
        // 具体看月份的格式

        String date = year + "-" + month;

        // System.out.println(date);

        List<WFInfo> list = wfMapper.findTopkWordsbyMonth(k,date);
        // System.out.println(list);
        
        return list;
    }

    public List<WFInfo> getWFListbyYear(int k,String year) {
        List<WFInfo> list = wfMapper.findTopkWordsbyYear(k,year);
        //System.out.println(list);
        return list;
    }
    

    public List<WFInfo> getWFList(int k) {
        List<WFInfo> list = wfMapper.findTopkWords(k);
        //System.out.println(list);
        return list;
    }
    

}
