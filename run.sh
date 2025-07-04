#!/bin/bash

echo "🚀 Strategy Board 시스템 시작..."

if ! command -v docker &> /dev/null; then
    echo "❌ Docker가 설치되지 않았습니다."
    exit 1
fi

if ! command -v docker-compose &> /dev/null; then
    echo "❌ Docker Compose가 설치되지 않았습니다."
    exit 1
fi

echo "🧹 기존 컨테이너 정리 중..."
docker-compose down

echo "🔨 Docker 이미지 빌드 중..."
docker-compose build

echo "🚀 컨테이너 시작 중..."
docker-compose up -d

echo "⏳ 서비스 시작 대기 중..."
sleep 30

echo ""
echo "🎉 Strategy Board 시스템이 실행되었습니다!"
echo ""
echo "📱 프론트엔드: http://localhost:3000"
echo "🔧 백엔드 API: http://localhost:8080/api"
echo ""
echo "⏹️  종료하려면: docker-compose down"