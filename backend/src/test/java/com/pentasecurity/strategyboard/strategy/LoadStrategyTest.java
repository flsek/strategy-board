package com.pentasecurity.strategyboard.strategy;

import com.pentasecurity.strategyboard.dto.PostListRequest;
import com.pentasecurity.strategyboard.dto.PageResponse;
import com.pentasecurity.strategyboard.dto.PostDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class LoadStrategyTest {

    @Autowired
    private LoadStrategyFactory strategyFactory;

    @Test
    public void testPaginationStrategy() {
        // Given
        LoadStrategy strategy = strategyFactory.getStrategy("pagination");
        PostListRequest request = new PostListRequest(0, 10, "pagination");

        // When
        PageResponse<PostDto> response = strategy.loadPosts(request);

        // Then
        assertNotNull(response);
        assertNotNull(response.getContent());
        assertTrue(response.getContent().size() <= 10);
    }

    @Test
    public void testInfiniteScrollStrategy() {
        // Given
        LoadStrategy strategy = strategyFactory.getStrategy("infinite");
        PostListRequest request = new PostListRequest(0, 10, "infinite");

        // When
        PageResponse<PostDto> response = strategy.loadPosts(request);

        // Then
        assertNotNull(response);
        assertNotNull(response.getContent());
        assertTrue(response.getContent().size() <= 10);
    }
}