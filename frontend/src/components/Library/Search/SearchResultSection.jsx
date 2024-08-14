import React from 'react';
import BookItem from '@components/@common/Book/BookItem';
import BookTalkItem from '@components/@common/Book/BookTalkItem';

const SearchResultSection = ({
  title,
  books,
  onItemClick,
  onSeeMore,
  type,
}) => {
  return (
    <div className='mb-6 flex justify-center flex-col'>
      <h2 className='text-lg font-bold mb-2'>{title}</h2>
      {books?.length === 0 ? (
        <div className='flex flex-col items-center bg-gray-100 space-y-4 p-4 m-4 rounded'>
          <p>검색 결과가 없습니다.</p>
        </div>
      ) : (
        <div
          className={`flex mb-2 ${type === 'booktalk' ? 'flex-col' : 'justify-center'}`}
        >
          {books
            ?.slice(0, 3)
            .map(book =>
              type === 'booktalk' ? (
                <BookTalkItem
                  key={book.book_id}
                  book={book}
                  onClick={() => onItemClick(book)}
                />
              ) : (
                <BookItem
                  key={book.isbn}
                  book={book}
                  onClick={() => onItemClick(book)}
                />
              )
            )}
        </div>
      )}
      {books?.length > 3 && (
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
