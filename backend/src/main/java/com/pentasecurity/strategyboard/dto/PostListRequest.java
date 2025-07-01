package com.pentasecurity.strategyboard.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;

public class PostListRequest {

    @Min(value = 0, message = "페이지는 0 이상이어야 합니다")
    private int page = 0;

    @Min(value = 1, message = "크기는 1 이상이어야 합니다")
    @Max(value = 100, message = "크기는 100 이하여야 합니다")
    private int size = 10;

    // 무한스크롤용 마지막 ID (커서)
    private Long lastId;

    // 로딩 전략 타입
    private String strategy = "pagination"; // 기본값: pagination

    // 기본 생성자
    public PostListRequest() {
    }

    // 생성자
    public PostListRequest(int page, int size, String strategy) {
        this.page = page;
        this.size = size;
        this.strategy = strategy;
    }

    // Getters and Setters
    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Long getLastId() {
        return lastId;
    }

    public void setLastId(Long lastId) {
        this.lastId = lastId;
    }

    public String getStrategy() {
        return strategy;
    }

    public void setStrategy(String strategy) {
        this.strategy = strategy;
    }
}