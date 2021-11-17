package com.example.springweb.controller;

import com.example.springweb.service.WFService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import org.springframework.ui.Model;
import com.example.springweb.dao.WFInfo;
import com.example.springweb.mapper.WFMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping(value = "/")
@CrossOrigin
public class WFController {
    @Autowired
    WFService wfService;
    WFInfo wfInfo;
    WFMapper wfMapper;
    public final static Logger logger = LoggerFactory.getLogger(WFController.class);

    int Topk = 100;
    boolean ssflag = false;

    @GetMapping("/SparkStreaming")
    @CrossOrigin
    public List<WFInfo> getStarted() {

        // TODO
        // 这里应该是点击按钮事件的响应程序
        // 开始Spark Streaming计算，从数据库中取TopK并发给前端
        // 只有第一次才做流计算，之后就跳过流计算直接读取数据库
        if (!ssflag) {
            startSparkStreaming();
        }

        return getWF();
    }

    public void startSparkStreaming() {
        System.out.println("SparkStreamingStarted.");
        String[] cmdArr = {"/bin/sh", "/root/startup.sh"};
        Process process = null;
        try {
            process = Runtime.getRuntime().exec(cmdArr);
            ssflag = true;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        BufferedReader stdout = new BufferedReader(new InputStreamReader(process.getInputStream()));
        BufferedReader stderr = new BufferedReader(new InputStreamReader(process.getErrorStream()));
        String line;
        try {
            while ((line = stderr.readLine()) != null) {
                logger.info(line);
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            while ((line = stdout.readLine()) != null) {
                logger.info(line);
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        ssflag = true;
    }

    // @Scheduled(fixedRate = 500) //定时任务，每0.5秒读一次数据库
    public List<WFInfo> getWF() {
        // System.out.println("getWF");
        List<WFInfo> list = wfService.getWFList(Topk);
        return list;
    }

    @GetMapping("/SparkStreaming/{year}")
    @CrossOrigin
    public List<WFInfo> sendTopkWFbyYear(@PathVariable("year") String year) {

        logger.info("wf logging" + wfService.getWFListbyYear(Topk, year));

        return wfService.getWFListbyYear(Topk, year);
    }

    @GetMapping("/SparkStreaming/{year}/{month}")
    @CrossOrigin
    public List<WFInfo> sendTopkWFbyMonth(@PathVariable("year") String year, @PathVariable("month") String month) {

        //logger.info("wf logging" + wfService.getWFListbyMonth(Topk, year, month));

        return wfService.getWFListbyMonth(Topk, year, month);
    }

}
