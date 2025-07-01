package com.pentasecurity.strategyboard.strategy.impl;

import com.pentasecurity.strategyboard.dto.PostDto;
import com.pentasecurity.strategyboard.dto.PostListRequest;
import com.pentasecurity.strategyboard.dto.PageResponse;
import com.pentasecurity.strategyboard.entity.Post;
import com.pentasecurity.strategyboard.repository.PostRepository;
import com.pentasecurity.strategyboard.strategy.LoadStrategy;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 무한스크롤 기반 로딩 전략 구현체
 * 커서(Cursor) 기반 페이징을 제공하여 무한스크롤 구현
 */
@Component
public class InfiniteScrollLoadStrategy implements LoadStrategy {

    private final PostRepository postRepository;

    public InfiniteScrollLoadStrategy(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public PageResponse<PostDto> loadPosts(PostListRequest request) {
        List<Post> posts;

        // 첫 번째 로딩인지 확인 (lastId가 없는 경우)
        if (request.getLastId() == null) {
            // 첫 페이지: 최신 게시글부터 size만큼 조회
            Pageable pageable = PageRequest.of(0, request.getSize());
            posts = postRepository.findTop10ByOrderByIdDesc();

            // 요청한 size만큼만 가져오기
            if (posts.size() > request.getSize()) {
                posts = posts.subList(0, request.getSize());
            }
        } else {
            // 다음 페이지: lastId보다 작은 ID의 게시글들을 조회
            Pageable pageable = PageRequest.of(0, request.getSize());
            posts = postRepository.findByIdLessThanOrderByIdDesc(request.getLastId(), pageable);
        }

        // Entity를 DTO로 변환
        List<PostDto> postDtos = posts.stream()
                .map(PostDto::new)
                .collect(Collectors.toList());

        // 다음 페이지가 있는지 확인
        boolean hasNext = posts.size() == request.getSize();
        Long nextCursor = null;

        if (hasNext && !posts.isEmpty()) {
            // 마지막 게시글의 ID를 다음 커서로 설정
            nextCursor = posts.get(posts.size() - 1).getId();
        }

        // PageResponse 생성 (무한스크롤 정보 포함)
        return new PageResponse<>(postDtos, hasNext, nextCursor);
    }

    @Override
    public String getStrategyType() {
        return "infinite";
    }
}