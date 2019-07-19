package com.javarticles.camel.jdbc.component.insert.xpath;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public final class QueryUtils {
    public static Article extractArticleFromRs(ResultSet rs) throws SQLException {
        Article article = new Article();
        article.setId(rs.getInt("ID"));
        article.setName(rs.getString("NAME"));
        article.setAuthor(rs.getString("AUTHOR"));
        article.setCategory(rs.getString("CATEGORY"));
        article.setTags(rs.getString("TAGS"));
        return article;
    }
    
    public static List<Article> extractArticleListFromRs(ResultSet rs) throws SQLException {
        List<Article> articleList = new ArrayList<Article>();
        while(rs.next()) {                
            articleList.add(extractArticleFromRs(rs));
        }
        return articleList;
    }
}
