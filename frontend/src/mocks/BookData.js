const books = [
  {
    book_id: 1,
    title: '미움받을 용기',
    author: '기시미 이치로',
    published_at: 2014,
    publisher: '인플루엔셜',
    summary: '자기계발서의 새로운 기준을 제시한 책',
    cover_img_url:
      'https://image.aladin.co.kr/product/32974/50/cover500/k422936456_1.jpg',
    library_id: 1,
    color: 'bg-red-500',
    slot_id: 0,
  },
  {
    book_id: 2,
    title: '하루의 취향',
    author: '밀란 쿤데라',
    published_at: 2006,
    publisher: '문학동네',
    summary: '일상의 소중함을 느끼게 해주는 책',
    cover_img_url:
      'https://image.aladin.co.kr/product/32974/50/cover500/k422936456_1.jpg',
    library_id: 1,
    color: 'bg-green-500',
    slot_id: 1,
  },
  {
    book_id: 3,
    title: '죽고 싶지만 떡볶이는 먹고 싶어',
    author: '백세희',
    published_at: 2018,
    publisher: '흔',
    summary: '우울증을 극복한 자전적 에세이',
    cover_img_url:
      'https://image.aladin.co.kr/product/32974/50/cover500/k422936456_1.jpg',
    library_id: 1,
    color: 'bg-blue-500',
    slot_id: 2,
  },
  {
    book_id: 4,
    title: '그릿',
    author: '앤젤라 더크워스',
    published_at: 2016,
    publisher: '비즈니스북스',
    summary: '성공의 새로운 정의를 제시하는 책',
    cover_img_url:
      'https://image.aladin.co.kr/product/32974/50/cover500/k422936456_1.jpg',
    library_id: 1,
    color: 'bg-yellow-500',
    slot_id: 3,
  },
  {
    book_id: 5,
    title: '나미야 잡화점의 기적',
    author: '히가시노 게이고',
    published_at: 2012,
    publisher: '비채',
    summary: '감동적인 이야기가 담긴 소설',
    cover_img_url:
      'https://image.aladin.co.kr/product/32974/50/cover500/k422936456_1.jpg',
    library_id: 2,
    color: 'bg-purple-500',
    slot_id: 0,
  },
  {
    book_id: 6,
    title: '아몬드',
    author: '손원평',
    published_at: 2017,
    publisher: '창비',
    summary: '감정을 느끼지 못하는 소년의 이야기',
    cover_img_url:
      'https://image.aladin.co.kr/product/32974/50/cover500/k422936456_1.jpg',
    library_id: 2,
    color: 'bg-pink-500',
    slot_id: 1,
  },
  {
    book_id: 7,
    title: '종의 기원',
    author: '정유정',
    published_at: 2016,
    publisher: '은행나무',
    summary: '살인자의 심리를 탐구한 소설',
    cover_img_url:
      'https://image.aladin.co.kr/product/32974/50/cover500/k422936456_1.jpg',
    library_id: 2,
    color: 'bg-orange-500',
    slot_id: 2,
  },
  {
    book_id: 8,
    title: '멋진 신세계',
    author: '올더스 헉슬리',
    published_at: 1932,
    publisher: '현대문학',
    summary: '디스토피아적 미래 사회를 그린 소설',
    cover_img_url:
      'https://image.aladin.co.kr/product/32974/50/cover500/k422936456_1.jpg',
    library_id: 2,
    color: 'bg-gray-500',
    slot_id: 3,
  },
  {
    book_id: 9,
    title: '불편한 편의점',
    author: '김호연',
    published_at: 2021,
    publisher: '재미주의',
    summary: '소소한 일상의 이야기를 담은 소설',
    cover_img_url:
      'https://image.aladin.co.kr/product/32974/50/cover500/k422936456_1.jpg',
    library_id: 2,
    color: 'bg-blue-500',
    slot_id: 4,
  },
  {
    book_id: 10,
    title: '여행의 이유',
    author: '김영하',
    published_at: 2019,
    publisher: '문학동네',
    summary: '여행을 통해 삶을 돌아보게 하는 책',
    library_id: 2,
    cover_img_url:
      'https://image.aladin.co.kr/product/32974/50/cover500/k422936456_1.jpg',
    color: 'bg-green-500',
    slot_id: 5,
  },
];

const book = {
  book_id: 3,
  title: '나미야 잡화점의 기적',
  author: '히가시노 게이고',
  published_at: 2012,
  publisher: '비채',
  summary:
    '집에서 나오지 않는 집주인과 커뮤니케이션을 하는 조건으로 동경하던 일본 가옥에서 한 달간 살게 된 노아. 어떤 사람일까 걱정하던 노아의 앞에 나타난 것은 사람의 손을 타지 않은 고양이처럼 말 없고 무표정한 청년, 케이였다.',
  cover_img_url:
    'https://image.aladin.co.kr/product/32974/50/cover500/k422936456_1.jpg',
  library_id: 2,
  color: 'bg-blue-500',
};

export { books, book };
