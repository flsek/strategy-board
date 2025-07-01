package com.pentasecurity.strategyboard.config;

import com.pentasecurity.strategyboard.entity.Post;
import com.pentasecurity.strategyboard.repository.PostRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {
    private final PostRepository postRepository;

    public DataInitializer(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // 기존 데이터가 있으면 생성하지 않음
        if (postRepository.count() > 0) {
            return;
        }

        // 테스트용 게시글 50개 생성
        for (int i = 1; i <= 50; i++) {
            Post post = new Post(
                    "전략패턴 게시판 테스트 " + i,
                    "이것은 " + i + "번째 게시글입니다.\n\n" +
                            "전략패턴(Strategy Pattern)을 활용하여 무한스크롤과 페이징을 구현하는 " +
                            "게시판 시스템의 테스트 데이터입니다.\n\n" +
                            "Spring Boot + React를 사용하여 개발되었습니다.",
                    "개발자" + (i % 10 + 1));
            postRepository.save(post);
        }

        System.out.println("✅ 초기 데이터 50개 생성 완료!");
    }
}
