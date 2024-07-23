import { useState, useEffect } from 'react';
import { useNavigate, useSearchParams } from 'react-router-dom';
import Input from '@components/@common/Input';
import WrapContainer from '@components/Layout/WrapContainer';
import Button from '@components/@common/Button';
import BookSearchListItem from '@components/Library/Search/BookSearchListItem';
import BookInfoTag from '@components/Library/Search/BookInfoTag';
import { books } from '@mocks/BookData';
import { IoSearchSharp, IoArrowBack } from 'react-icons/io5';
import BookCreateModal from '@components/Library/BookCreate/BookCreateModal';
import useModal from '@hooks/useModal';

const LibrarySearch = () => {
  const navigate = useNavigate();
  const [searchParams, setSearchParams] = useSearchParams();

  const [searchText, setSearchText] = useState('');
  const [selectedTag, setSelectedTag] = useState('');
  const [searchBooks, setSearchBooks] = useState([]);
  const [isSearched, setIsSearched] = useState(false);

  const { isOpen, toggleModal } = useModal();

  useEffect(() => {
    const queryText = searchParams.get('text');
    const queryCategory = searchParams.get('category');

    if (queryText) {
      setSearchText(queryText);
      setSelectedTag(queryCategory);
      handleSearch(queryText);
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
      <form
        className='mb-4 w-full flex flex-row items-center justify-center'
        onSubmit={onSearchButtonClick}
      >
        <button
          type='button'
          className='p-2'
          onClick={() => navigate(`/library`)}
        >
          <IoArrowBack />
        </button>
        <div className='flex-grow'>
          <Input
            id='searchText'
            placeholder='검색어를 입력해주세요'
            value={searchText}
            onChange={e => setSearchText(e.target.value)}
          />
        </div>

        <button type='submit' onClick={handleSearch} className='p-2'>
          <IoSearchSharp className='w-5 h-5' />
        </button>
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
                onCreateClick={toggleModal}
              />
            ))}
          </div>
        </>
      )}
      <BookCreateModal
        isCreateModalOpen={isOpen}
        toggleCreateModal={toggleModal}
      />
    </WrapContainer>
  );
};

export default LibrarySearch;
