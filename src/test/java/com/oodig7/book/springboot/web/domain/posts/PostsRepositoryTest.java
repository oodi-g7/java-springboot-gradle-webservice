package com.oodig7.book.springboot.web.domain.posts;

import com.oodig7.book.springboot.domain.posts.Posts;
import com.oodig7.book.springboot.domain.posts.PostsRepository;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PostsRepositoryTest {

    @Autowired
    PostsRepository postsRepository;

    @After
    public void cleanup(){
        postsRepository.deleteAll();
    }

    @Test
    public void 게시글저장_불러오기(){
        //given
        String title = "테스트 게시글";
        String content = "테스트 본문";

        postsRepository.save(Posts.builder()
                                .title(title)
                                .content(content)
                                .author("oodig7@gmail.com")
                                .build());

        //when
        List<Posts> postsList = postsRepository.findAll();

        //then
        Posts posts = postsList.get(0);
        assertThat(posts.getTitle()).isEqualTo(title);
        assertThat(posts.getContent()).isEqualTo(content);
    }

    @Test
    public void BaseTimeEntity_등록() {
        //given
        LocalDateTime now = LocalDateTime.of(2023,2,9,0,0,0);
        postsRepository.save(Posts.builder()
                                    .title("title")
                                    .content("content")
                                    .author("author")
                                    .build()); // entity생성 -> 해당 entity는 basetimeentity를 상속받고있으므로,
                                                // 생성과 동시에 auditing이 적용되어 createDate, modifiedDate를 컬럼으로 인식하고
                                                // @createDate필드의 @CreateDate 어노테이션으로 인해 생성날짜 정보가 자동 저장됨.
                                                // ** 그럼 modifiedDate(조회)는 null값이려나 ..?

        //when
        List<Posts> postsList = postsRepository.findAll();

        //then
        Posts posts = postsList.get(0);

        System.out.println(">>>>>>>> createDate= " + posts.getCreateDate()
                            + ", modifiedDate= " + posts.getModifiedDate());
        assertThat(posts.getCreateDate()).isAfter(now);
        assertThat(posts.getModifiedDate().isAfter(now));
    }
}