package com.pentasecurity.strategyboard.repository;

import com.pentasecurity.strategyboard.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    // 페이징을 위한 기본 메서드 (이미 JpaRepository에 포함됨)
    Page<Post> findAllByOrderByCreatedAtDesc(Pageable pageable);

    // 무한스크롤을 위한 커서 기반 조회
    @Query("SELECT p FROM Post p WHERE p.id < :lastId ORDER BY p.id DESC")
    List<Post> findByIdLessThanOrderByIdDesc(@Param("lastId") Long lastId, Pageable pageable);

    // 첫 페이지 조회 (무한스크롤용)
    List<Post> findTop10ByOrderByIdDesc();
}
