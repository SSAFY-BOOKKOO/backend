// src/pages/Home.jsx
import axios from 'axios';

import React, { useState, useEffect } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';

const Home = () => {
  const [books, setBooks] = useState([
    {
      id: 1,
      title: '모순',
      author: '양귀자',
      publisher: '홍익출판사',
      year: 1997,
      ISBN: '9788970651034',
      summary:
        '고려 시대의 승려 일연이 저술한 역사서로, 한국 고대사의 중요한 문헌이다.',
      cover_url:
        'https://image.yes24.com/momo/TopCate249/MidCate003/24823257.jpg',
    },
    {
      id: 2,
      title: '돈의 심리학',
      author: '모건 하우철',
      publisher: '마로니에북스',
      year: 2012,
      ISBN: '9788960530971',
      summary:
        '한국의 역사와 사람들의 삶을 그린 대하소설로, 일제 강점기부터 해방까지의 이야기를 담고 있다.',
      cover_url: 'https://image.yes24.com/goods/96547408/XL',
    },
    {
      id: 3,
      title: '이처럼 사소한 것들',
      author: '클레어 키건',
      publisher: '해냄출판사',
      year: 1994,
      ISBN: '9788973376316',
      summary:
        '한국 현대사의 격동기를 배경으로 한 대하소설로, 여순사건부터 한국 전쟁까지의 이야기를 다루고 있다.',
      cover_url: 'https://image.yes24.com/goods/123400303/L',
    },
  ]);

  // // 정보 받을 빈 객체 생성
  // const [books, setBooks] = useState([]);
  // // api받을 axios
  // useEffect(() => {
  //   const apiUrl = 'https://api.i11a506.ssafy.io';
  //   const endpoint = '/libraries';

  //   axios
  //     .get(apiUrl + endpoint, {
  //       params: {
  //         memberId: memberId,
  //       },
  //     })
  //     .then(res => {
  //       setBooks(res.data);
  //     })
  //     .catch(err => {
  //       console.log(err);
  //     });
  // }, [memberId]);

  const navigate = useNavigate();
  const location = useLocation();

  // 삭제 로직
  useEffect(() => {
    if (location.state?.deleteBookId) {
      // 삭제될 bookId
      console.log('state:', location.state);
      console.log('useEffect triggered:', location.state.deleteBookId);
      handleDeleteBook(location.state.deleteBookId);
    }
  }, [location.state]);

  useEffect(() => {
    console.log('Updated books:', books);
  }, [books]);

  // 개별 책 삭제
  const handleDeleteBook = bookId => {
    console.log('Deleting book with ID:', bookId);
    setBooks(prevBooks => prevBooks.filter(book => book.id !== bookId));
  };

  // 상세 페이지 이동
  const handleBookClick = book => {
    navigate(`/detail/${book.id}`, { state: { book } });
  };

  return (
    <div>
      <h1>홈 페이지</h1>
      <ul>
        {books.map(book => (
          <li key={book.id}>
            <button onClick={() => handleBookClick(book)}>{book.title}</button>
            {/* <BookDelete bookId={book.id} onDelete={handleDeleteBook} /> */}
          </li>
        ))}
      </ul>
    </div>
  );
};

export default Home;
