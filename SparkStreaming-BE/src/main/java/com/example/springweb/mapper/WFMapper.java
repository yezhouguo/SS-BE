package com.example.springweb.mapper;

import com.example.springweb.dao.WFInfo;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface WFMapper {

    @Select("SELECT * FROM (SELECT * FROM (SELECT *,ROW_NUMBER() OVER (PARTITION BY date ORDER BY count DESC) as ranking FROM words ) new WHERE ranking<=#{k}) new1 WHERE date = #{date}; ")
    @Results({
            @Result(property = "word", column = "word"),
            @Result(property = "count", column = "count"),
            @Result(property = "date", column = "date")
    })
    List<WFInfo> findTopkWordsbyMonth(int k,String date);


    @Select("SELECT word, sum(count) as total FROM (SELECT * FROM words where substring(date,1,4) = #{year}) new GROUP BY word ORDER BY total DESC LIMIT #{k};")
    @Results({
            @Result(property = "word", column = "word"),
            @Result(property = "count", column = "total"),
            @Result(property = "date", column = "date")
    })
    List<WFInfo> findTopkWordsbyYear(int k,String year);


    @Select("SELECT word, sum(count) as total FROM words GROUP BY word ORDER BY total DESC LIMIT #{k};")
    @Results({
            @Result(property = "word", column = "word"),
            @Result(property = "count", column = "total"),
            @Result(property = "date", column = "date")
    })
    List<WFInfo> findTopkWords(int k); 

}
