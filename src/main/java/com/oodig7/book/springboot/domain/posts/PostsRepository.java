package com.oodig7.book.springboot.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

// 비즈니스 로직 담당
// JPARepository를 상속받음으로써 기본적인 CRUD로직 수행가능함.

// 조회, 등록
public interface PostsRepository extends JpaRepository<Posts, Long> {

    @Query("SELECT p FROM Posts p ORDER BY p.id DESC")
    List<Posts> findAllDesc();
}