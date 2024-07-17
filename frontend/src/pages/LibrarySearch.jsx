import { useState, useEffect } from 'react';
import { useNavigate, useSearchParams } from 'react-router-dom';
import Input from '../components/@common/Input';
import WrapContainer from '../components/Layout/WrapContainer';
import Button from '../components/@common/Button';
import BookSearchListItem from '../components/Library/Search/BookSearchListItem';
import BookInfoTag from '../components/Library/Search/BookInfoTag';
import useModal from '../hooks/useModal';
import { books } from '../mocks/BookData';

const LibrarySearch = () => {
  const navigate = useNavigate();

  const [searchText, setSearchText] = useState('');
  const [selectedTag, setSelectedTag] = useState(null);

  const { isOpen, openModal, closeModal } = useModal();

  const handleSearch = searchText => {
    // 검색 로직
  };

  // 검색 버튼 클릭 시 호출되는 함수
  const onSearchButtonClick = e => {
    e.preventDefault();
  };

  const showDetailPage = id => {
    navigate(`${id}`);
  };

  return (
    <WrapContainer>
      <form className='' onSubmit={onSearchButtonClick}>
        <Input
          id='searchText'
          placeholder='검색어를 입력해주세요'
          value={searchText}
          onChange={e => setSearchText(e.target.value)}
          onClick={handleSearch}
        />
      </form>
      <BookInfoTag selectedTag={selectedTag} setSelectedTag={setSelectedTag} />
      {books?.length > 0 ? <p className='mt-2 mb-4'>도서 검색 결과</p> : ''}
      <div>
        {books?.map(book => (
          <BookSearchListItem
            key={book.book_id}
            book={book}
            onClick={() => showDetailPage(book.book_id)}
          />
        ))}
      </div>
    </WrapContainer>
  );
};
export default LibrarySearch;
