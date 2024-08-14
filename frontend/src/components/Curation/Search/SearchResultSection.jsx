import React from 'react';
import PropTypes from 'prop-types';
import BookItem from '@components/Curation/Search/BookItem';

const SearchResultSection = ({ books, onSeeMore }) => {
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
              className='mb-4' //책 사이의 간격
            />
          ))}
          <button
            onClick={onSeeMore}
            className='mt-4 p-2 bg-blue-500 text-white rounded'
          >
            더보기
          </button>
        </div>
      )}
    </div>
  );
};

SearchResultSection.propTypes = {
  title: PropTypes.string.isRequired,
  books: PropTypes.arrayOf(PropTypes.object).isRequired,
  onSeeMore: PropTypes.func.isRequired,
};

export default SearchResultSection;
