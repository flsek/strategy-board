# Strategy Board

전략패턴을 활용하여 무한스크롤과 페이징 로딩 방식을 선택할 수 있는 게시판 시스템

## 🛠 기술 스택

### 백엔드

- **Java 21**
- **Spring Boot 3.5.3**
- **Spring Data JPA**
- **H2 Database** (인메모리)
- **Maven**

### 프론트엔드

- **React 18**
- **Material-UI (MUI)**
- **Axios**
- **JavaScript ES6+**

### 인프라

- **Docker & Docker Compose**

## 🎯 주요 기능

### 전략패턴 (Strategy Pattern) 구현

- **LoadStrategy 인터페이스**: 로딩 전략의 공통 계약 정의
- **PaginationLoadStrategy**: 전통적인 페이지 번호 기반 페이징
- **InfiniteScrollLoadStrategy**: 커서 기반 무한스크롤
- **LoadStrategyFactory**: 팩토리 패턴으로 전략 객체 생성/관리

### 사용자 인터페이스

- **AppBar 전략 토글**: 페이징 ↔ 무한스크롤 실시간 전환
- **게시글 카드**: Material-UI Card로 게시글 정보 표시
- **반응형 디자인**: 다양한 화면 크기 지원
- **로딩/에러 상태**: 사용자 피드백 제공

## 🚀 실행 방법

### 1. Docker 실행 (추천)

```bash
# 저장소 클론
git clone <repository-url>
cd strategy-board
```

### 2. Docker 실행 (모든 서비스 자동 시작)

```bash
./run.sh
```

### 3. 브라우저에서 접속

- **프론트엔드**: http://localhost:3000
- **백엔드 API**: http://localhost:8080/api
- **H2 콘솔**: http://localhost:8080/h2-console

## 개발 환경 실행

```bash
## Docker 환경
./run.sh
# 또는
docker-compose up -d
```

### 백엔드 실행

```bash
cd backend
./mvnw spring-boot:run
```

### 프론트엔드 실행 (새 터미널)

```bash
cd frontend
npm install
npm start
```

## 🔧 시스템 종료

```bash
## Docker 환경
./stop.sh
# 또는
docker-compose down

# 개발 환경
## Ctrl+C로 각 서버 종료
```

## 📋 API 엔드포인트

게시글 조회

## 페이징 전략

GET /api/posts?strategy=pagination&page=0&size=10

## 무한스크롤 전략

GET /api/posts?strategy=infinite&size=10&lastId=50

## 특정 게시글 조회

GET /api/posts/{id}

## 서버 상태 확인

GET /api/posts/health

## 사용 가능한 전략 목록

GET /api/posts/strategies

## 🏗️ 프로젝트 구조

```
strategy-board/
├── backend/ # Spring Boot 백엔드
│ ├── src/
│ │ └── main/
│ │ ├── java/com/pentasecurity/strategyboard/
│ │ │ ├── controller/ # REST API 컨트롤러
│ │ │ ├── service/ # 비즈니스 로직
│ │ │ ├── repository/ # 데이터 접근 계층
│ │ │ ├── entity/ # JPA 엔티티
│ │ │ ├── dto/ # 데이터 전송 객체
│ │ │ ├── strategy/ # 전략패턴 구현
│ │ │ ├── exception/ # 예외 처리
│ │ │ └── config/ # 설정 클래스
│ │ └── resources/
│ │ └── application.yml
│ ├── Dockerfile
│ └── pom.xml
├── frontend/ # React 프론트엔드
│ ├── src/
│ │ ├── components/ # React 컴포넌트
│ │ ├── services/ # API 클라이언트
│ │ └── App.js
│ ├── Dockerfile
│ └── package.json
├── docker-compose.yml # Docker 서비스 정의
├── run.sh # 시스템 시작 스크립트
├── stop.sh # 시스템 종료 스크립트
└── README.md
```

## 🎨 화면 구성

### 메인 화면

- **상단 AppBar**: 프로젝트 제목 및 전략 선택 토글
- **게시글 목록**: 카드 형태로 게시글 표시
- **페이징/무한스크롤**: 선택된 전략에 따라 동적 변경
- **새로고침 버튼**: 우하단 플로팅 액션 버튼

### 게시글 카드

- **제목**: 게시글 제목 표시
- **내용**: 100자 미리보기
- **작성자**: 아바타와 함께 표시
- **작성일**: 한국어 날짜 형식
- **게시글 ID**: 칩 형태로 표시

## 🔍 설계 패턴

### Strategy Pattern (전략패턴)

```java
// 전략 인터페이스
public interface LoadStrategy {
    PageResponse<PostDto> loadPosts(PostListRequest request);
    String getStrategyType();
}

// 구체적 전략 구현
@Component
public class PaginationLoadStrategy implements LoadStrategy {
    // 페이징 구현
}

@Component
public class InfiniteScrollLoadStrategy implements LoadStrategy {
    // 무한스크롤 구현
}

// 팩토리 패턴
@Component
public class LoadStrategyFactory {
    public LoadStrategy getStrategy(String strategyType) {
        // 전략 선택 로직
    }
}
```

## Factory Pattern (팩토리패턴)

LoadStrategyFactory를 통해 전략 객체 생성 및 관리
Spring의 의존성 주입과 결합하여 유연한 구조 제공

## 🛡️ 예외 처리

### 백엔드

- **GlobalExceptionHandler**: 전역 예외 처리
- **PostNotFoundException**: 404 에러 처리
- **IllegalArgumentException**: 잘못된 전략 파라미터 처리
- **Validation**: Spring Validation으로 입력 검증

### 프론트엔드

- **API 에러 처리**: Axios 인터셉터로 일관된 에러 처리
- **로딩 상태**: 사용자 피드백 제공
- **네트워크 오류**: 친화적인 에러 메시지 표시

## 🧪 테스트

### 백엔드 테스트

```bash
cd backend
./mvnw test
```

### API 테스트 (cURL)

```bash
# 헬스 체크
curl http://localhost:8080/api/posts/health

# 페이징 조회
curl "http://localhost:8080/api/posts?strategy=pagination&page=0&size=5"

# 무한스크롤 조회
curl "http://localhost:8080/api/posts?strategy=infinite&size=10"
```

## 🔧 개발 환경 설정

### 필수 요구사항

- Docker Desktop (또는 Docker Engine)
- Java 21 (개발 환경 실행 시)
- Node.js 18+ (개발 환경 실행 시)
- Git

## 📝 주요 특징

### 확장성

- **개방-폐쇄 원칙**: 새로운 로딩 전략 추가 용이
- **의존성 역전**: 인터페이스 기반 설계
- **단일 책임**: 각 클래스가 하나의 역할만 담당

### 성능

- **JPA 최적화**: 적절한 쿼리 및 페이징 처리
- **React 최적화**: useCallback, useMemo 활용
- **Docker 최적화**: 멀티 스테이지 빌드로 이미지 크기 최소화

### 사용자 경험

- **즉시 피드백**: 로딩 상태 및 에러 처리
- **직관적 UI**: Material Design 가이드라인 준수
- **반응형 디자인**: 모바일 친화적 인터페이스
