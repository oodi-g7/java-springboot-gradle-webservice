package com.oodig7.book.springboot.web.dto;

import com.oodig7.book.springboot.domain.posts.Posts;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

// DTO : Posts 등록시 데이터를 담음(Request), Posts 조회시 데이터를 담음(Response)
// 계층간 데이터를 전달할때 사용하는 계층

// 등록
@Getter
@NoArgsConstructor
public class PostsSaveRequestDto {
    private String title;
    private String content;
    private String author;

    @Builder
    public PostsSaveRequestDto(String title, String content, String author){
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public Posts toEntity(){
        return Posts.builder()
                .title(title)
                .content(content)
                .author(author)
                .build();
    }
}
