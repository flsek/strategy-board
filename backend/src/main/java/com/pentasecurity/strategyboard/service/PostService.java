package com.pentasecurity.strategyboard.service;

import com.pentasecurity.strategyboard.dto.PostDto;
import com.pentasecurity.strategyboard.dto.PostListRequest;
import com.pentasecurity.strategyboard.dto.PageResponse;

import java.util.Set;

/**
 * 게시글 비즈니스 로직 인터페이스
 */
public interface PostService {

    /**
     * 게시글 목록 조회 (전략패턴 적용)
     * 
     * @param request 조회 요청 정보
     * @return 게시글 목록과 페이징 정보
     */
    PageResponse<PostDto> getPosts(PostListRequest request);

    /**
     * 특정 게시글 조회
     * 
     * @param id 게시글 ID
     * @return 게시글 정보
     */
    PostDto getPost(Long id);

    /**
     * 사용 가능한 로딩 전략 목록 조회
     * 
     * @return 전략 목록
     */
    Set<String> getAvailableStrategies();
}