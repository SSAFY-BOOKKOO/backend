import { useState, useEffect } from 'react';
import { useNavigate, useSearchParams } from 'react-router-dom';
import Input from '@components/@common/Input';
import WrapContainer from '@components/Layout/WrapContainer';
import Button from '@components/@common/Button';
import BookSearchListItem from '@components/Library/Search/BookSearchListItem';
import BookInfoTag from '@components/Library/Search/BookInfoTag';
import { books } from '@mocks/BookData';
import { IoSearchSharp } from 'react-icons/io5';
import BookCreateModal from '@components/Library/BookCreate/BookCreateModal';
import useModal from '@hooks/useModal';
import IconButton from '@components/@common/IconButton';

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
        <div className='flex-grow'>
          <Input
            id='searchText'
            placeholder='책을 검색하세요'
            value={searchText}
            onChange={e => setSearchText(e.target.value)}
          />
        </div>
        <IconButton type='submit' onClick={handleSearch} icon={IoSearchSharp} />
      </form>
      <BookInfoTag selectedTag={selectedTag} setSelectedTag={setSelectedTag} />
      {isSearched && (
        <>
          {searchBooks?.length > 0 ? (
            <p className='mt-2 mb-4'>도서 검색 결과 ({searchBooks.length}건)</p>
          ) : (
            <p className='mt-2 mb-4'>검색 결과가 없습니다.</p>
          )}
          <div className='overflow-y-auto scrollbar-none'>
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
