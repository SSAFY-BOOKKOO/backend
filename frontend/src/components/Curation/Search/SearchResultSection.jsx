import React from 'react';
import BookItem from '../../Curation/Search/BookItem';

const SearchResultSection = ({
  title,
  books,
  // onItemClick,
  onSeeMore,
  type,
}) => {
  return (
    <div className='mb-6 flex flex-col'>
      {books?.length === 0 ? (
        <div className='flex flex-col items-center bg-gray-100 space-y-4 p-4 m-4 rounded'>
          <p>검색 결과가 없습니다.</p>
        </div>
      ) : (
        <div className='flex flex-col'>
          {books?.map((book, index) => (
            <BookItem
              key={book.book_id || index}
              book={book}
              onClick={() => onItemClick(book)}
              className='mb-4' //책 사이의 간격
            />
          ))}
        </div>
      )}
      <p>{books.length}</p>
      {books?.length > 0 && (
        <div className='flex justify-end text-sm cursor-pointer'>
          <button className='text-gray-500' onClick={onSeeMore}>
            더보기
          </button>
        </div>
      )}
    </div>
  );
};

export default SearchResultSection;
