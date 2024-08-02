import React, { useEffect } from 'react';
import { useParams, useSearchParams, useNavigate } from 'react-router-dom';
import WrapContainer from '@components/Layout/WrapContainer';
import SearchBookItem from '@components/Library/Search/SearchBookItem';
import SearchLibraryItem from '@components/Library/Search/SearchLibraryItem';
import BookTalkItem from '@components/@common/Book/BookTalkItem';
import useBookInfiniteScroll from '@hooks/useBookInfiniteScroll';
import { useInView } from 'react-intersection-observer';

const SearchMore = () => {
  const { type } = useParams();
  const [searchParams] = useSearchParams();
  const navigate = useNavigate();
  const searchText = searchParams.get('text');
  const selectedTag = searchParams.get('tag');

  const getQuery = () => {
    switch (type) {
      case 'book':
        return useBookInfiniteScroll(searchText, selectedTag);
      case 'library':
      case 'booktalk':
        return {
          data: null,
          fetchNextPage: () => {},
          hasNextPage: false,
          isFetchingNextPage: false,
        };
      default:
        return;
    }
  };

  const { data, fetchNextPage, hasNextPage, isFetchingNextPage } = getQuery();
  const { ref, inView } = useInView();

  useEffect(() => {
    if (inView && hasNextPage) {
      fetchNextPage();
    }
  }, [inView, hasNextPage, fetchNextPage]);

  const handleBookClick = book => {
    navigate(`/${type}/detail/${book.book_id}`, { state: { book } });
  };

  const renderBookItem = book => {
    const components = {
      library: SearchLibraryItem,
      book: SearchBookItem,
      booktalk: BookTalkItem,
    };
    const Component = components[type];
    return Component ? (
      <Component
        key={book.book_id}
        book={book}
        onClick={() => handleBookClick(book)}
      />
    ) : null;
  };

  const getTitle = () => {
    const titles = {
      library: '내 서재',
      book: '도서',
      booktalk: '북톡',
    };
    return `${titles[type] || ''} 검색 결과`;
  };

  return (
    <WrapContainer className='mt-4'>
      <h1 className='text-2xl font-bold mb-4'>{getTitle()}</h1>
      {data?.pages?.map((page, index) => (
        <div key={index}>{page.data?.map(renderBookItem)}</div>
      ))}
      {isFetchingNextPage && <p className='text-center'>로딩중...</p>}
      {!hasNextPage && data?.pages?.length > 0 && (
        <p className='text-center'>더 이상 결과가 없습니다.</p>
      )}
      <div ref={ref}></div>
    </WrapContainer>
  );
};

export default SearchMore;
