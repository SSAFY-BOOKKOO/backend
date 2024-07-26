import React from 'react';
import SearchResultSectionItem from '@components/Library/Search/SearchResultSectionItem';

const SearchResultSection = ({ title, books, onItemClick, onSeeMore }) => {
  return (
    <div className='mb-6 flex justify-center flex-col'>
      <h2 className='text-lg font-bold mb-2'>{title}</h2>
      <div className='flex mb-2 justify-center'>
        {books.slice(0, 3).map(book => (
          <SearchResultSectionItem
            key={book.book_id}
            book={book}
            onClick={() => onItemClick(book)}
          />
        ))}
      </div>
      <div className='flex justify-end text-sm cursor-pointer'>
        <button className='text-gray-500' onClick={onSeeMore}>
          더보기
        </button>
      </div>
    </div>
  );
};

export default SearchResultSection;
