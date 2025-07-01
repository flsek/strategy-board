package com.pentasecurity.strategyboard.strategy.impl;

import com.pentasecurity.strategyboard.dto.PostDto;
import com.pentasecurity.strategyboard.dto.PostListRequest;
import com.pentasecurity.strategyboard.dto.PageResponse;
import com.pentasecurity.strategyboard.entity.Post;
import com.pentasecurity.strategyboard.repository.PostRepository;
import com.pentasecurity.strategyboard.strategy.LoadStrategy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 페이징 기반 로딩 전략 구현체
 * 전통적인 페이지 번호 기반 페이징을 제공
 */
@Component
public class PaginationLoadStrategy implements LoadStrategy {

    private final PostRepository postRepository;

    public PaginationLoadStrategy(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public PageResponse<PostDto> loadPosts(PostListRequest request) {
        // 페이징 정보 생성 (최신순 정렬)
        Pageable pageable = PageRequest.of(
                request.getPage(),
                request.getSize(),
                Sort.by(Sort.Direction.DESC, "createdAt"));

        // 데이터베이스에서 페이징 조회
        Page<Post> postPage = postRepository.findAll(pageable);

        // Entity를 DTO로 변환
        List<PostDto> postDtos = postPage.getContent()
                .stream()
                .map(PostDto::new)
                .collect(Collectors.toList());

        // PageResponse 생성 (페이징 정보 포함)
        return new PageResponse<>(
                postDtos,
                postPage.getNumber(),
                postPage.getSize(),
                postPage.getTotalElements());
    }

    @Override
    public String getStrategyType() {
        return "pagination";
    }
}