import React, { useEffect, useState } from 'react';
import { useParams, useSearchParams, useNavigate } from 'react-router-dom';
import WrapContainer from '@components/Layout/WrapContainer';
import SearchBookItem from '@components/Curation/Search/BookItem';
// 무한 스크롤
import useBookInfiniteScroll from '@hooks/useBookInfiniteScroll';
import { useInView } from 'react-intersection-observer';
import useModal from '@hooks/useModal';
import BookCreateModal from '@components/Library/BookCreate/BookCreateModal';

const SearchMore = () => {
  const { type } = useParams();
  const [searchParams] = useSearchParams();
//   const navigate = useNavigate();
  const searchText = searchParams.get('text');
  const selectedTag = searchParams.get('tag');
  const { isOpen, toggleModal } = useModal();
  const [selectedBook, setSelectedBook] = useState(null);
  
  // 수정
  const getQuery = () => {
    if (type === 'book') {
      return useBookInfiniteScroll(searchText, selectedTag);
    }
    return {
      data: null,
      fetchNextPage: () => {},
      hasNextPage: false,
      isFetchingNextPage: false,
    };
  };

  const { data, fetchNextPage, hasNextPage, isFetchingNextPage } = getQuery();
  const { ref, inView } = useInView();

  useEffect(() => {
    if (inView && hasNextPage) {
      fetchNextPage();
    }
  }, [inView, hasNextPage, fetchNextPage]);


    // 상세는 볼 필요 없음 삭제
    //   const handleBookClick = book => {
    //     navigate(`/ ${type}/detail/${book.book_id}`, { state: { book } });
    //   };

  const handleBookCreateButton = book => {
    setSelectedBook(book);
    toggleModal();
  };

  const renderBookItem = book => {
        return (
          <SearchBookItem
            key={book.book_id}
            book={book}
            // onClick={() => handleBookClick(book)}
            onCreateClick={() => handleBookCreateButton(book)}
          />
        );
  };

  

  return (
    <WrapContainer className='mt-4'>
      {data?.pages?.map((page, index) => (
        <div key={index}>{page.data?.map(renderBookItem)}</div>
      ))}
      {isFetchingNextPage && <p className='text-center'>로딩중...</p>}
      {!hasNextPage && data?.pages?.length > 0 && (
        <p className='text-center'>더 이상 결과가 없습니다.</p>
      )}
      <div ref={ref}></div>
      <BookCreateModal
        isCreateModalOpen={isOpen}
        toggleCreateModal={toggleModal}
        selectedBook={selectedBook}
      />
    </WrapContainer>
  );
};

export default SearchMore;
