import { useState, useEffect } from 'react';
import { useNavigate, useSearchParams } from 'react-router-dom';
import Input from '../components/@common/Input';
import WrapContainer from '../components/Layout/WrapContainer';
import Button from '../components/@common/Button';
import BookSearchListItem from '../components/Library/Search/BookSearchListItem';
import BookInfoTag from '../components/Library/Search/BookInfoTag';
import { books } from '../mocks/BookData';

const LibrarySearch = () => {
  const navigate = useNavigate();
  const [searchParams, setSearchParams] = useSearchParams();

  const [searchText, setSearchText] = useState('');
  const [selectedTag, setSelectedTag] = useState('');
  const [searchBooks, setSearchBooks] = useState([]);
  const [isSearched, setIsSearched] = useState(false);

  useEffect(() => {
    const query = searchParams.get('query');
    if (query) {
      setSearchText(query);
      handleSearch(query);
    }
  }, []);

  const handleSearch = text => {
    // 검색 연동 필요

    setSearchBooks(books);
    setIsSearched(true);
    setSearchParams({ text: text, category: selectedTag });
  };

  const onSearchButtonClick = e => {
    e.preventDefault();
    handleSearch(searchText);
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
        />
        <Button type='submit'>검색</Button>
      </form>
      <BookInfoTag selectedTag={selectedTag} setSelectedTag={setSelectedTag} />
      {isSearched && (
        <>
          {searchBooks?.length > 0 ? (
            <p className='mt-2 mb-4'>도서 검색 결과 ({searchBooks.length}건)</p>
          ) : (
            <p className='mt-2 mb-4'>검색 결과가 없습니다.</p>
          )}
          <div>
            {searchBooks.map(book => (
              <BookSearchListItem
                key={book.book_id}
                book={book}
                onClick={() => showDetailPage(book.book_id)}
              />
            ))}
          </div>
        </>
      )}
    </WrapContainer>
  );
};

export default LibrarySearch;
