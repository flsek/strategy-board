package com.pentasecurity.strategyboard.strategy;

import com.pentasecurity.strategyboard.dto.PostDto;
import com.pentasecurity.strategyboard.dto.PostListRequest;
import com.pentasecurity.strategyboard.dto.PageResponse;

/**
 * 게시글 로딩 전략을 정의하는 인터페이스
 * Strategy Pattern의 핵심 인터페이스
 */
public interface LoadStrategy {

    /**
     * 게시글 목록을 로딩하는 전략 메서드
     * 
     * @param request 로딩 요청 정보 (페이지, 크기, 커서 등)
     * @return 로딩된 게시글 목록과 페이징 정보
     */
    PageResponse<PostDto> loadPosts(PostListRequest request);

    /**
     * 전략의 타입을 반환
     * 
     * @return 전략 타입 문자열
     */
    String getStrategyType();
}