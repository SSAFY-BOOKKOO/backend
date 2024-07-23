#!/bin/bash

# Backend 디렉토리로 이동
cd backend

# 변경사항 빌드
docker compose build

# 변경된 컨테이너 실행
docker compose up -d
