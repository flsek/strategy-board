import React, { useState, useEffect, useCallback } from 'react';
import {
  Container,
  AppBar,
  Toolbar,
  Typography,
  Switch,
  FormControlLabel,
  Box,
  CircularProgress,
  Alert,
  Pagination,
  Fab
} from '@mui/material';
import { Refresh } from '@mui/icons-material';
import PostCard from './PostCard';
import { fetchPosts } from '../services/postService';

/**
 * 게시글 목록 메인 컴포넌트
 * 전략 선택 및 게시글 목록 표시
 */
const PostList = () => {
  // 상태 관리
  const [posts, setPosts] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  const [strategy, setStrategy] = useState('pagination'); // 'pagination' | 'infinite'
  
  // 페이지네이션 상태
  const [currentPage, setCurrentPage] = useState(0);
  const [totalPages, setTotalPages] = useState(0);
  const [totalElements, setTotalElements] = useState(0);
  
  // 무한스크롤 상태
  const [hasNextPage, setHasNextPage] = useState(false);
  const [nextCursor, setNextCursor] = useState(null);

  const pageSize = 10;

  /**
   * 게시글 목록 조회
   */
  const loadPosts = useCallback(async (params = {}) => {
    try {
      setLoading(true);
      setError(null);

      const requestParams = {
        strategy,
        size: pageSize,
        ...params
      };

      const response = await fetchPosts(requestParams);

      if (strategy === 'pagination') {
        // 페이지네이션: 새로운 데이터로 교체
        setPosts(response.content);
        setCurrentPage(response.page);
        setTotalPages(response.totalPages);
        setTotalElements(response.totalElements);
      } else {
        // 무한스크롤: 기존 데이터에 추가
        if (params.lastId) {
          setPosts(prev => [...prev, ...response.content]);
        } else {
          setPosts(response.content);
        }
        setHasNextPage(response.hasNext);
        setNextCursor(response.nextCursor);
      }
    } catch (err) {
      setError('게시글을 불러오는 중 오류가 발생했습니다.');
      console.error('Error loading posts:', err);
    } finally {
      setLoading(false);
    }
  }, [strategy]);

  /**
   * 전략 변경 핸들러
   */
  const handleStrategyChange = (event) => {
    const newStrategy = event.target.checked ? 'infinite' : 'pagination';
    setStrategy(newStrategy);
    
    // 상태 초기화
    setPosts([]);
    setCurrentPage(0);
    setNextCursor(null);
  };

  /**
   * 페이지 변경 핸들러 (페이지네이션)
   */
  const handlePageChange = (event, newPage) => {
    setCurrentPage(newPage - 1); // MUI Pagination은 1부터 시작
    loadPosts({ page: newPage - 1 });
  };

  /**
   * 더 보기 핸들러 (무한스크롤)
   */
  const handleLoadMore = () => {
    if (hasNextPage && nextCursor && !loading) {
      loadPosts({ lastId: nextCursor });
    }
  };

  /**
   * 새로고침 핸들러
   */
  const handleRefresh = () => {
    setPosts([]);
    setCurrentPage(0);
    setNextCursor(null);
    loadPosts();
  };

  // 초기 로딩 및 전략 변경 시 데이터 로딩
  useEffect(() => {
    loadPosts();
  }, [loadPosts]);

  return (
    <Box sx={{ flexGrow: 1 }}>
      {/* AppBar with 전략 선택 토글 */}
      <AppBar position="static">
        <Toolbar>
          <Typography variant="h6" component="div" sx={{ flexGrow: 1 }}>
            Strategy Board
          </Typography>
          
          <FormControlLabel
            control={
              <Switch 
                checked={strategy === 'infinite'}
                onChange={handleStrategyChange}
                color="default"
              />
            }
            label={strategy === 'infinite' ? '무한스크롤' : '페이지네이션'}
            sx={{ color: 'white' }}
          />
        </Toolbar>
      </AppBar>

      {/* 메인 컨텐츠 */}
      <Container maxWidth="md" sx={{ mt: 4, mb: 4 }}>
        {/* 현재 전략 정보 */}
        <Box mb={3}>
          <Typography variant="h5" gutterBottom>
            게시글 목록
          </Typography>
          <Typography variant="body2" color="text.secondary">
            현재 로딩 전략: <strong>{strategy === 'infinite' ? '무한스크롤' : '페이지네이션'}</strong>
            {strategy === 'pagination' && totalElements > 0 && (
              <span> | 총 {totalElements}개의 게시글</span>
            )}
          </Typography>
        </Box>

        {/* 에러 메시지 */}
        {error && (
          <Alert severity="error" sx={{ mb: 2 }}>
            {error}
          </Alert>
        )}

        {/* 게시글 목록 */}
        {posts.map((post) => (
          <PostCard key={post.id} post={post} />
        ))}

        {/* 로딩 인디케이터 */}
        {loading && (
          <Box display="flex" justifyContent="center" my={4}>
            <CircularProgress />
          </Box>
        )}

        {/* 페이지네이션 (페이지네이션 전략) */}
        {strategy === 'pagination' && totalPages > 1 && (
          <Box display="flex" justifyContent="center" mt={4}>
            <Pagination 
              count={totalPages}
              page={currentPage + 1}
              onChange={handlePageChange}
              color="primary"
              size="large"
            />
          </Box>
        )}

        {/* 더 보기 버튼 (무한스크롤 전략) */}
        {strategy === 'infinite' && hasNextPage && (
          <Box display="flex" justifyContent="center" mt={4}>
            <Fab 
              variant="extended" 
              onClick={handleLoadMore}
              disabled={loading}
            >
              더 보기
            </Fab>
          </Box>
        )}

        {/* 데이터 없음 */}
        {!loading && posts.length === 0 && (
          <Box textAlign="center" py={8}>
            <Typography variant="h6" color="text.secondary">
              게시글이 없습니다.
            </Typography>
          </Box>
        )}
      </Container>

      {/* 새로고침 버튼 */}
      <Fab
        color="primary"
        aria-label="refresh"
        sx={{ position: 'fixed', bottom: 16, right: 16 }}
        onClick={handleRefresh}
      >
        <Refresh />
      </Fab>
    </Box>
  );
};

export default PostList;