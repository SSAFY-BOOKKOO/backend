# 📚 북꾸북꾸 - 독서 큐레이션 SNS

**: 나만의 서재를 가꾸고, 독서 기록을 쉽게 관리하세요!**

![로고](./images/logo_white.jpg)

## 🛠 프로젝트 소개
- **개발 기간**: 2024.07.02 ~ 2024.08.16 (약 6주)
- **개발 인원**: 6인 (프론트엔드 3인, 백엔드 3인)
- **배포 URL**: [북꾸북꾸](https://i11a506.p.ssafy.io)

<b>북꾸북꾸</b>는 사용자들이 자신만의 독서 서재를 관리하고, 독서 기록을 체계적으로 정리할 수 있는 웹 서비스입니다. 사용자는 읽은 책을 등록하고, 책에 대한 한줄평을 남길 수 있으며, 큐레이션 레터를 통해 다른 사용자들과 독서 취향을 공유할 수 있습니다.

## 🌟 주요 기능

### 회원 관리
- **이메일 가입**: 이메일, 비밀번호, 닉네임을 입력하여 가입. 추가 정보(연령, 성별, 선호 카테고리) 입력 필수.
- **소셜 회원가입/로그인**: 카카오, 네이버 소셜 로그인을 통해 간편하게 가입 및 로그인 가능.
- **비밀번호 찾기**: 이메일을 통해 비밀번호 재설정 가능.

### 서재 관리
- **서재 생성**: 사용자 맞춤형 서재 생성. 서재 이름, 책 색상 테마 설정 가능. 최대 3개의 서재 생성 가능.
- **서재 조회**: 서재 필터링, 정렬 기능 제공. 사용자는 draggable로 책을 이동할 수 있으며, 무한스크롤 지원.
- **책 등록 및 관리**: 책의 기본 정보를 등록하고, 읽은 상태, 별점 등을 기록. 책을 서재에 저장하여 관리.

### 한줄평 기능
- **한줄평 등록**: 최대 70자까지 한줄평 작성 가능.
- **한줄평 수정/삭제**: 등록된 한줄평을 수정하거나 삭제 가능.
- **한줄평 파도타기**: 다른 사용자의 한줄평을 탐색하며, 마음에 드는 서재를 방문 가능.

### 큐레이션
- **큐레이션 챗봇**: 사용자가 선호하는 장르나 기분에 따라 책 추천을 받을 수 있음.
- **큐레이션 레터**: 선호 카테고리가 비슷한 사용자에게 큐레이션 레터 전송 가능. 레터는 무작위로 3명의 사용자와 팔로워에게 전송됨.
- **큐레이션 보관**: 받은 큐레이션 레터를 보관하고 관리할 수 있음.

### 마이페이지
- **개인정보 수정**: 프로필 사진, 닉네임, 비밀번호 및 추가 정보(연령, 성별, 선호 카테고리) 수정 가능.
- **독서 기록 통계**: 도넛 차트를 통해 카테고리별 독서 통계를 시각적으로 확인 가능.
- **친구 관리**: 팔로잉, 팔로우 관리 및 닉네임을 통한 사용자 검색 가능.
- **독서 기록 달력**: 책을 읽은 날짜를 달력에 표시하여 독서 기록을 쉽게 확인 가능.

### 독서 커뮤니티
- **커뮤니티 메인화면**: 내가 참여한 도서와 최근 인기 도서를 확인할 수 있음.
- **채팅방 생성 및 관리**: 책 검색을 통해 채팅방을 생성하고, 독서에 대해 사용자들과 소통 가능.
- **다른 사용자 서재 구경**: 채팅방 내 다른 사용자의 서재를 탐색할 수 있음.

## ⚙ 개발환경

### Management Tool

![Git-F05032.svg](https://img.shields.io/badge/Git-F05032.svg?&style=for-the-badge&logo=Git&logoColor=white)
![GitLab-FC6D26.svg](https://img.shields.io/badge/GitLab-FC6D26.svg?&style=for-the-badge&logo=GitLab&logoColor=white)
![Jira Software-0052CC.svg](https://img.shields.io/badge/Jira_Software-0052CC.svg?&style=for-the-badge&logo=JiraSoftware&logoColor=white)
![Mattermost-0058CC.svg](https://img.shields.io/badge/Mattermost-0058CC.svg?&style=for-the-badge&logo=Mattermost&logoColor=white)
![Notion-000000.svg](https://img.shields.io/badge/Notion-000000.svg?&style=for-the-badge&logo=Notion&logoColor=white)
![Figma-F24E1E.svg](https://img.shields.io/badge/Figma-F24E1E.svg?&style=for-the-badge&logo=Figma&logoColor=white)

### IDE

![VS-CODE](https://img.shields.io/badge/Visual%20Studio%20Code-007ACC.svg?&style=for-the-badge&logo=Visual%20Studio%20Code&logoColor=white)
![IntelliJ](https://img.shields.io/badge/IntelliJ%20IDEA-000000.svg?&style=for-the-badge&logo=IntelliJ%20IDEA&logoColor=white)

### Infra

![AWS](https://img.shields.io/badge/Amazon_AWS-232F3E.svg?&style=for-the-badge&logo=AmazonAWS_&logoColor=white)
![Amazon_EC2](https://img.shields.io/badge/Amazon%20EC2-FF9900.svg?&style=for-the-badge&logo=Amazon%20EC2&logoColor=white)
![Amazon_S3](https://img.shields.io/badge/Amazon%20S3-569A31.svg?&style=for-the-badge&logo=Amazon%20S3&logoColor=white)
![nginx](https://img.shields.io/badge/NGINX-009639.svg?&style=for-the-badge&logo=NGINX&logoColor=white)
![docker](https://img.shields.io/badge/Docker-2496ED.svg?&style=for-the-badge&logo=Docker&logoColor=white)
![jenkins](https://img.shields.io/badge/Jenkins-D24939.svg?&style=for-the-badge&logo=Jenkins&logoColor=white)
![postgresql](https://img.shields.io/badge/PostgreSQL-4169E1.svg?&style=for-the-badge&logo=PostgreSQL&logoColor=white)
![Redis](https://img.shields.io/badge/Redis-DC3872D.svg?&style=for-the-badge&logo=Redis&logoColor=white)
![Prometheus](https://img.shields.io/badge/Prometheus-E6522C.svg?&style=for-the-badge&logo=Prometheus&logoColor=white)
![Grafana](https://img.shields.io/badge/Grafana-F46800.svg?&style=for-the-badge&logo=Grafana&logoColor=white)

### Frontend

![HTML5](https://img.shields.io/badge/HTML5-E34F26.svg?&style=for-the-badge&logo=HTML5&logoColor=white)
![CSS3](https://img.shields.io/badge/CSS3-1572B6.svg?&style=for-the-badge&logo=CSS3&logoColor=white)
![JavaScript](https://img.shields.io/badge/JavaScript-F7DF1E.svg?&style=for-the-badge&logo=JavaScript&logoColor=white)
![TypeScript](https://img.shields.io/badge/TypeScript-3172C6.svg?&style=for-the-badge&logo=TypeScript&logoColor=white)
![React](https://img.shields.io/badge/React-61DAFB.svg?&style=for-the-badge&logo=React&logoColor=white)
![SWC](https://img.shields.io/badge/Node\.js-339933.svg?&style=for-the-badge&logo=Node\.js&logoColor=white)
![NodeJS](https://img.shields.io/badge/Amazon%20EC2-FF9900.svg?&style=for-the-badge&logo=Amazon%20EC2&logoColor=white)
![Vite](https://img.shields.io/badge/Vite-646CFF.svg?&style=for-the-badge&logo=Vite&logoColor=white)
![Axios](https://img.shields.io/badge/Axios-5A29E4.svg?&style=for-the-badge&logo=Axios&logoColor=white)
![TailwindCSS](https://img.shields.io/badge/Tailwind_CSS-06B6D4.svg?&style=for-the-badge&logo=TailwindCSS&logoColor=white)
![PostCSS](https://img.shields.io/badge/PostCSS-DD3A0A.svg?&style=for-the-badge&logo=PostCSS&logoColor=white)
![Sass](https://img.shields.io/badge/Sass-CC6699.svg?&style=for-the-badge&logo=Sass&logoColor=white)
![Autoprefixer](https://img.shields.io/badge/Autoprefixer-DD3735.svg?&style=for-the-badge&logo=Autoprefixer&logoColor=white)
![Redux](https://img.shields.io/badge/Redux-764ABC.svg?&style=for-the-badge&logo=Redux&logoColor=white)
![OpenVidu](https://img.shields.io/badge/Openvidu-05D261.svg?&style=for-the-badge)
![Canvas](https://img.shields.io/badge/Canvas-000000.svg?&style=for-the-badge)
![ffmpegWasm](https://img.shields.io/badge/FfmpegWasm-654FF0.svg?&style=for-the-badge&logo=FFmpeg&logoColor=white)

### Backend

![Java](https://img.shields.io/badge/Java-634533.svg?&style=for-the-badge)
![SpringBoot](https://img.shields.io/badge/Spring_Boot-6DB33F.svg?&style=for-the-badge&logo=SpringBoot&logEoColor=white)
![SpringSecurity](https://img.shields.io/badge/Spring_Security-6DB33F.svg?&style=for-the-badge&logo=SpringSecurity&logoColor=white)
![SpringJPA](https://img.shields.io/badge/Spring_JPA-6DB33F.svg?&style=for-the-badge)

### **외부 API**
- Google Cloud Vision, Aladdin API

## 📜 설계 문서
- **아키텍처 구조**: MSA와 Event Driven Architecture를 적용하여 확장성과 유지보수성을 확보.
- **API 명세**: 회원 관리, 서재 관리, 큐레이션, 커뮤니티 등 주요 기능에 대한 API 명세 제공.
- **ERD**: 관계형 데이터베이스(MariaDB)와 그래프 데이터베이스(Neo4j)를 활용한 데이터 모델 설계.

## 👨‍👩‍👧‍👧 팀원 소개
- **Frontend**
  - 김지윤: 팀장, UI디자인, 프론트엔드 개발
  - 김혁: UI디자인, 프론트엔드 개발
  - 송예진: UI디자인, 프론트엔드 개발
- **Backend**
  - 김인엽: 서재(서재, 통계) 서버 개발, 책 서버(책, 카테고리, 한줄평) 개발
  - 김종호: 큐레이션 서버 개발, 북톡 서버 (채팅 서비스) 개발, 인프라 설정
  - 왕승철: 유저/인증 서버 개발, 알림 서버 개발, 글귀 기능 개발

MyBookShelf는 독서 경험을 더 풍부하게 만들고, 사용자가 자신의 독서 취향을 공유하며 다른 독서가들과 교류할 수 있는 플랫폼을 목표로 합니다.
