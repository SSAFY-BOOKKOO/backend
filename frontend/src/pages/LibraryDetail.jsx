// src/pages/LibraryDetail.jsx
import React, { useEffect, useState } from 'react';
import { useLocation, useParams, useNavigate } from 'react-router-dom';
import BookDelete from '../components/Library/Detail/Modal/BookDelete';

// 책 정보 반환
const BookInfo = ({ book, onImageClick }) => (
  <div>
    <img src={book.cover_url} alt={book.title} onClick={onImageClick} />
    <p> {book.author}</p>
    <p> {book.publisher}</p>
    <p> {book.year}</p>
    <p> {book.summary}</p>
  </div>
);


// 이미지 클릭하면 리뷰 화면으로 전환
const BookReview = ({ review, currentUserId, onAddReview, onEditReview, onDeleteReview, onBackClick }) => {
  const [newReview, setNewReview] = useState('');
  const [editingReview, setEditingReview] = useState(false);
  const [editingReviewText, setEditingReviewText] = useState(review?.text || '');

  const handleAddReview = () => {
    if (newReview.trim()) {
      onAddReview({ text: newReview, userId: currentUserId });
      setNewReview('');
    }
  };

  const handleEditReview = () => {
    if (editingReviewText.trim()) {
      onEditReview(review.id, editingReviewText);
      setEditingReview(false);
    }
  };

  const canEditOrDelete = review && review.userId === currentUserId;

  return (
    <div>
      <h1>한줄평</h1>
      {review ? (
        <div>
          {editingReview ? (
            <div>
              <input
                type="text"
                value={editingReviewText}
                onChange={(e) => setEditingReviewText(e.target.value)}
              />
              <button onClick={handleEditReview}>수정 완료</button>
            </div>
          ) : (
            <div>
              <p>{review.text}</p>
              {canEditOrDelete && (
                <>
                  <button onClick={() => setEditingReview(true)}>수정</button>
                  <button onClick={onDeleteReview}>삭제</button>
                </>
              )}
            </div>
          )}
        </div>
      ) : (
        <div>
          <input
            type="text"
            value={newReview}
            onChange={(e) => setNewReview(e.target.value)}
            placeholder="한줄평 작성"
          />
          <button onClick={handleAddReview}>추가</button>
          <p>파도 타기</p>
        </div>
      )}
      <button onClick={onBackClick}>책 정보 보기</button>
    </div>
  );
};

const LibraryDetail = () => {
  const { bookId } = useParams();
  const location = useLocation();
  const navigate = useNavigate();
  const [book, setBook] = useState(location.state?.book || null);
  const [menuVisible, setMenuVisible] = useState(false);
  const [turn, setTurn] = useState(false);
  const [review, setReview] = useState(null);

  const currentUserId = 'current-user-id'; // 현재 사용자의 ID

  useEffect(() => {
    if (!book) {
      // Simulating fetching book data
      const fetchBook = async () => {
        const books = [
          {
            id: 1,
            title: "모순",
            author: "양귀자",
            publisher: "홍익출판사",
            year: 1997,
            ISBN: "9788970651034",
            summary: "고려 시대의 승려 일연이 저술한 역사서로, 한국 고대사의 중요한 문헌이다.",
            cover_url: "https://image.yes24.com/momo/TopCate249/MidCate003/24823257.jpg"
          },
          {
            id: 2,
            title: "돈의 심리학",
            author: "모건 하우철",
            publisher: "마로니에북스",
            year: 2012,
            ISBN: "9788960530971",
            summary: "한국의 역사와 사람들의 삶을 그린 대하소설로, 일제 강점기부터 해방까지의 이야기를 담고 있다.",
            cover_url: "https://image.yes24.com/goods/96547408/XL"
          },
          {
            id: 3,
            title: "이처럼 사소한 것들",
            author: "클레어 키건",
            publisher: "해냄출판사",
            year: 1994,
            ISBN: "9788973376316",
            summary: "한국 현대사의 격동기를 배경으로 한 대하소설로, 여순사건부터 한국 전쟁까지의 이야기를 다루고 있다.",
            cover_url: "https://image.yes24.com/goods/123400303/L"
          }
        ];
        const book = books.find((book) => book.id === parseInt(bookId));
        if (book) {
          setBook(book);
        } else {
          navigate('/'); // Redirect to home if book not found
        }
      };

      fetchBook();
    }
  }, [bookId, book, navigate]);

  const toggleMenu = () => {
    setMenuVisible(!menuVisible);
  };

  const toggleTurn = () => {
    setTurn(!turn);
  };

  const handleOptionClick = (option) => {
    console.log(option + ' 클릭됨');
    setMenuVisible(false);
  };

  const handleAddReview = (newReview) => {
    setReview({ ...newReview, id: Date.now() });
  };

  const handleEditReview = (id, text) => {
    if (review && review.id === id && review.userId === currentUserId) {
      setReview({ ...review, text });
    } else {
      console.error('수정 권한이 없습니다.');
    }
  };

  const handleDeleteReview = () => {
    if (review && review.userId === currentUserId) {
      setReview(null);
    } else {
      console.error('삭제 권한이 없습니다.');
    }
  };

  const handleDeleteBook = (id) => {
    // Add logic to delete the book from the state or server
    console.log(`Book with id ${id} deleted`);
  };

  if (!book) {
    return <div>Loading...</div>;
  }

  return (
    <div className="detail" style={{ position: 'relative' }}>
      <div className="menu-icon" onClick={toggleMenu} style={{ position: 'absolute', top: 0, right: 0, zIndex: 1 }}>
        &#x22EE;
      </div>
      {menuVisible && (
        <div className="menu" style={{ position: 'absolute', top: 20, right: 0, background: 'white', border: '1px solid #ccc', zIndex: 0 }}>
          <div className="menu-item" onClick={() => handleOptionClick('삭제')}>삭제</div>
          <div className="menu-item" onClick={() => handleOptionClick('서재 이동')}>서재 이동</div>
          <div className="menu-item" onClick={() => handleOptionClick('색 변경')}>색 변경</div>
        </div>
      )}
      {turn ? (
        <BookReview
          review={review}
          currentUserId={currentUserId}
          onAddReview={handleAddReview}
          onEditReview={handleEditReview}
          onDeleteReview={handleDeleteReview}
          onBackClick={toggleTurn}
        />
      ) : (
        <BookInfo book={book} onImageClick={toggleTurn} />
      )}
      <BookDelete bookId={book.id} onDelete={handleDeleteBook} />
    </div>
  );
};

export default LibraryDetail;
