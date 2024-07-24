#!/bin/bash

# 변경사항 빌드
docker compose build

# 변경된 컨테이너 실행
docker compose up -d
