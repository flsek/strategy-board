package com.pentasecurity.strategyboard.strategy;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * LoadStrategy 인스턴스를 관리하는 팩토리 클래스
 * Strategy Pattern에서 전략 객체들을 생성하고 관리
 */
@Component
public class LoadStrategyFactory {

    private final Map<String, LoadStrategy> strategies;

    // 생성자에서 모든 LoadStrategy 구현체들을 주입받아 Map으로 관리
    public LoadStrategyFactory(List<LoadStrategy> loadStrategies) {
        this.strategies = new HashMap<>();

        // 각 전략을 타입별로 Map에 저장
        for (LoadStrategy strategy : loadStrategies) {
            strategies.put(strategy.getStrategyType(), strategy);
        }
    }

    /**
     * 전략 타입에 따라 적절한 LoadStrategy 구현체를 반환
     * 
     * @param strategyType 전략 타입 ("pagination" 또는 "infinite")
     * @return 해당하는 LoadStrategy 구현체
     * @throws IllegalArgumentException 지원하지 않는 전략 타입인 경우
     */
    public LoadStrategy getStrategy(String strategyType) {
        LoadStrategy strategy = strategies.get(strategyType);

        if (strategy == null) {
            throw new IllegalArgumentException(
                    "지원하지 않는 로딩 전략입니다: " + strategyType +
                            ". 지원하는 전략: " + strategies.keySet());
        }

        return strategy;
    }

    /**
     * 사용 가능한 모든 전략 타입들을 반환
     * 
     * @return 전략 타입 목록
     */
    public java.util.Set<String> getAvailableStrategies() {
        return strategies.keySet();
    }
}