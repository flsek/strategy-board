import React from 'react';
import {
  Card,
  CardContent,
  Typography,
  Chip,
  Box,
  Avatar
} from '@mui/material';
import { Person } from '@mui/icons-material';

/**
 * 게시글 카드 컴포넌트
 * Material-UI Card를 사용하여 게시글 정보 표시
 */
const PostCard = ({ post }) => {
  // 날짜 포맷팅
  const formatDate = (dateString) => {
    return new Date(dateString).toLocaleDateString('ko-KR', {
      year: 'numeric',
      month: 'short',
      day: 'numeric',
      hour: '2-digit',
      minute: '2-digit'
    });
  };

  // 내용 미리보기 (100자 제한)
  const getPreviewContent = (content) => {
    if (content.length <= 100) return content;
    return content.substring(0, 100) + '...';
  };

  return (
    <Card 
      sx={{ 
        mb: 2, 
        '&:hover': { 
          boxShadow: 3,
          transform: 'translateY(-2px)',
          transition: 'all 0.2s ease-in-out'
        } 
      }}
    >
      <CardContent>
        {/* 제목 */}
        <Typography variant="h6" component="h2" gutterBottom>
          {post.title}
        </Typography>

        {/* 내용 미리보기 */}
        <Typography variant="body2" color="text.secondary" paragraph>
          {getPreviewContent(post.content)}
        </Typography>

        {/* 하단 정보 */}
        <Box 
          display="flex" 
          justifyContent="space-between" 
          alignItems="center"
          mt={2}
        >
          {/* 작성자 정보 */}
          <Box display="flex" alignItems="center" gap={1}>
            <Avatar sx={{ width: 24, height: 24 }}>
              <Person sx={{ fontSize: 16 }} />
            </Avatar>
            <Typography variant="body2" color="text.secondary">
              {post.author}
            </Typography>
          </Box>

          {/* 게시글 ID 및 작성일 */}
          <Box display="flex" alignItems="center" gap={1}>
            <Chip 
              label={`#${post.id}`} 
              size="small" 
              variant="outlined" 
            />
            <Typography variant="caption" color="text.secondary">
              {formatDate(post.createdAt)}
            </Typography>
          </Box>
        </Box>
      </CardContent>
    </Card>
  );
};

export default PostCard;