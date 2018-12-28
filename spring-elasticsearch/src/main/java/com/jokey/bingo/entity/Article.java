package com.jokey.bingo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;

import java.io.Serializable;
import java.util.Date;

/**
 * @author jokey
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "article_index", type = "article", refreshInterval = "-1")
public class Article implements Serializable {
    private static final long serialVersionUID = 551589397625941750L;
    @Id
    private Long id;
    /**
     * 标题@Field(type = FieldType.String, indexAnalyzer="ik", searchAnalyzer="ik", store = true)
     */
    @Field(analyzer = "ik", searchAnalyzer = "ik", store = true, type = FieldType.Text)
    private String title;
    /**
     * 摘要
     */
    private String abstracts;
    /**
     * 内容
     */
    private String content;
    /**
     * 发表时间
     */
    @Field(format = DateFormat.date_time, store = true, type = FieldType.Object)
    private Date postTime;
    /**
     * 点击率
     */
    private Long clickCount;
}
