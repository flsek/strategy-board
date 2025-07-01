package com.pentasecurity.strategyboard.service.impl;

import com.pentasecurity.strategyboard.dto.PostDto;
import com.pentasecurity.strategyboard.dto.PostListRequest;
import com.pentasecurity.strategyboard.dto.PageResponse;
import com.pentasecurity.strategyboard.entity.Post;
import com.pentasecurity.strategyboard.exception.PostNotFoundException;
import com.pentasecurity.strategyboard.repository.PostRepository;
import com.pentasecurity.strategyboard.service.PostService;
import com.pentasecurity.strategyboard.strategy.LoadStrategy;
import com.pentasecurity.strategyboard.strategy.LoadStrategyFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

/**
 * 게시글 비즈니스 로직 구현체
 * 전략패턴을 활용하여 다양한 로딩 방식 지원
 */
@Service
@Transactional(readOnly = true)
public class PostServiceImpl implements PostService {

    private final LoadStrategyFactory strategyFactory;
    private final PostRepository postRepository;

    public PostServiceImpl(LoadStrategyFactory strategyFactory, PostRepository postRepository) {
        this.strategyFactory = strategyFactory;
        this.postRepository = postRepository;
    }

    @Override
    public PageResponse<PostDto> getPosts(PostListRequest request) {
        // 전략 팩토리에서 적절한 로딩 전략 선택
        LoadStrategy strategy = strategyFactory.getStrategy(request.getStrategy());

        // 선택된 전략으로 게시글 로딩
        return strategy.loadPosts(request);
    }

    @Override
    public PostDto getPost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException("게시글을 찾을 수 없습니다. ID: " + id));

        return new PostDto(post);
    }

    @Override
    public Set<String> getAvailableStrategies() {
        return strategyFactory.getAvailableStrategies();
    }
}