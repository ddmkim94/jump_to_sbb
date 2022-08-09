package com.ll.exam.sbb;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Article {

    private static Long lastId = 0L;
    private Long id;
    private String title;
    private String body;

    public Article(String title, String body) {
        this(++lastId, title, body);
    }

    public void changeArticle(String title, String body) {
        this.title = title;
        this.body = body;
    }
}
