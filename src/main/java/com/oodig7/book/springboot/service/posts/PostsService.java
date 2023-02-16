package com.oodig7.book.springboot.service.posts;

import com.oodig7.book.springboot.domain.posts.Posts;
import com.oodig7.book.springboot.domain.posts.PostsRepository;
import com.oodig7.book.springboot.web.dto.PostsListResponseDto;
import com.oodig7.book.springboot.web.dto.PostsResponseDto;
import com.oodig7.book.springboot.web.dto.PostsSaveRequestDto;
import com.oodig7.book.springboot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

// Controller와 DAO 중간역할
// 트랜잭션과 도메인 간 순서 보장

@RequiredArgsConstructor
@Service
public class PostsService {
    private final PostsRepository postsRepository;

    // 등록
    @Transactional
    public Long save(PostsSaveRequestDto requestDto){
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    // 수정
    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto){
        Posts posts = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));
            // .orElseThrow() = Optional클래스에서 제공하는 메소드
            // * Optional클래스 ( https://mangkyu.tistory.com/70 )
            // = 자바에서 null참조시 NullPointerException을 방지해주는 클래스
            // 아무때나 사용하는게 아니라 메소드의 반환 타입으로 사용되도록 설계되었음.
        posts.update(requestDto.getTitle(), requestDto.getContent());
        // db에 쿼리를 날리는 부분이 없음. -> JPA의 영속성 컨텍스트 때문 !
        // 동일한 @Transactional안에서 데이터베이스에서 데이터를 가져오면 (postsRepository.findById(id))
        // 해당 데이터는 영속성 컨텍스트가 유지된 상태.
        // 이 상태에서 해당 데이터의 값을 변경하면 트랜잭션이 끝나는 시점에 해당 테이블에 변경분을 반영.
        // 즉 Entity객체의 값만 변경하면 별도로 update쿼리를 날릴 필요가 없음.
        // 이 과정을 더티체킹이라고 함.
        // JPA에서는 트랜잭션이 끝나는 시점에 변화가 있는 모든 엔티티 객체를 데이터베이스에 자동으로 반영해줌
        // 변화가 있다의 기준은 최초 조회 상태 !
        return id;
    }
    
    // 조회
    public PostsResponseDto findById(Long id){
        Posts entity = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));
        
        return new PostsResponseDto(entity);
    }

    // 전체조회
    @Transactional(readOnly = true)
    public List<PostsListResponseDto> findAllDesc(){
        return postsRepository.findAllDesc().stream()
                .map(PostsListResponseDto::new)
                .collect(Collectors.toList());
    }

    // 삭제
    @Transactional
    public void delete(Long id){
        Posts posts = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id = "+id));

        postsRepository.delete(posts);
    }
}
