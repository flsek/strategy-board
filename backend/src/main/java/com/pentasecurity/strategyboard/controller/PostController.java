package com.pentasecurity.strategyboard.controller;

import com.pentasecurity.strategyboard.dto.PostDto;
import com.pentasecurity.strategyboard.dto.PostListRequest;
import com.pentasecurity.strategyboard.dto.PageResponse;
import com.pentasecurity.strategyboard.service.PostService;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 게시글 관련 REST API Controller
 * 프론트엔드와의 HTTP 통신을 담당
 */
@RestController
@RequestMapping("/api/posts")
@CrossOrigin(origins = "http://localhost:3000") // React 개발 서버
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    /**
     * 게시글 목록 조회 (전략패턴 적용)
     * 
     * @param strategy 로딩 전략 ("pagination" 또는 "infinite")
     * @param page     페이지 번호 (pagination용)
     * @param size     페이지 크기
     * @param lastId   마지막 게시글 ID (infinite scroll용)
     * @return 게시글 목록과 페이징 정보
     */
    @GetMapping
    public ResponseEntity<PageResponse<PostDto>> getPosts(
            @RequestParam(defaultValue = "pagination") String strategy,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) Long lastId) {

        // 요청 파라미터를 DTO로 변환
        PostListRequest request = new PostListRequest();
        request.setStrategy(strategy);
        request.setPage(page);
        request.setSize(size);
        request.setLastId(lastId);

        // 서비스 호출
        PageResponse<PostDto> response = postService.getPosts(request);

        return ResponseEntity.ok(response);
    }

    /**
     * 특정 게시글 조회
     * 
     * @param id 게시글 ID
     * @return 게시글 상세 정보
     */
    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPost(@PathVariable Long id) {
        PostDto post = postService.getPost(id);
        return ResponseEntity.ok(post);
    }

    /**
     * 헬스 체크용 API
     * 
     * @return 서버 상태 정보
     */
    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> health() {
        Map<String, Object> status = new HashMap<>();
        status.put("status", "UP");
        status.put("timestamp", LocalDateTime.now());
        status.put("availableStrategies", postService.getAvailableStrategies());
        return ResponseEntity.ok(status);
    }

    /**
     * 사용 가능한 로딩 전략 목록 조회
     * 
     * @return 지원하는 전략 목록
     */
    @GetMapping("/strategies")
    public ResponseEntity<?> getAvailableStrategies() {
        return ResponseEntity.ok(postService.getAvailableStrategies());
    }
}