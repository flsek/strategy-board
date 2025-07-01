import axios from 'axios';

const API_BASE_URL = 'http://localhost:8080/api';

// Axios 인스턴스 생성
const apiClient = axios.create({
  baseURL: API_BASE_URL,
  timeout: 10000,
  headers: {
    'Content-Type': 'application/json',
  },
});

// 요청/응답 인터셉터 (에러 처리)
apiClient.interceptors.response.use(
  (response) => response,
  (error) => {
    console.error('API Error:', error.response?.data || error.message);
    return Promise.reject(error);
  }
);

/**
 * 게시글 목록 조회
 * @param {Object} params - 조회 파라미터
 * @param {string} params.strategy - 로딩 전략 ('pagination' | 'infinite')
 * @param {number} params.page - 페이지 번호 (pagination용)
 * @param {number} params.size - 페이지 크기
 * @param {number} params.lastId - 마지막 게시글 ID (infinite용)
 */
export const fetchPosts = async (params = {}) => {
  const {
    strategy = 'pagination',
    page = 0,
    size = 10,
    lastId
  } = params;

  const queryParams = new URLSearchParams({
    strategy,
    page: page.toString(),
    size: size.toString(),
  });

  if (lastId) {
    queryParams.append('lastId', lastId.toString());
  }

  const response = await apiClient.get(`/posts?${queryParams}`);
  return response.data;
};

/**
 * 특정 게시글 조회
 * @param {number} id - 게시글 ID
 */
export const fetchPost = async (id) => {
  const response = await apiClient.get(`/posts/${id}`);
  return response.data;
};

/**
 * 사용 가능한 전략 목록 조회
 */
export const fetchAvailableStrategies = async () => {
  const response = await apiClient.get('/posts/strategies');
  return response.data;
};

/**
 * 서버 헬스 체크
 */
export const healthCheck = async () => {
  const response = await apiClient.get('/posts/health');
  return response.data;
};