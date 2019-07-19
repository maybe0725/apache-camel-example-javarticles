package com.javarticles.camel.jdbc.component.insert;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class ArticleRowMapper implements RowMapper<Article> {
    public Article mapRow(ResultSet rs, int rowNum) throws SQLException {
        return QueryUtils.extractArticleFromRs(rs);
    }       
}