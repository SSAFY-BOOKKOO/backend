import { useEffect } from 'react';

const BookInfoTag = ({ selectedTag, setSelectedTag }) => {
  const tags = [
    { id: 1, name: '제목', value: 'title' },
    { id: 2, name: '지은이', value: 'author' },
    { id: 3, name: '출판사', value: 'publisher' },
  ];

  const handleTagClick = value => {
    setSelectedTag(value === selectedTag ? '' : value);
  };

  return (
    <div className='flex flex-wrap gap-4 my-2'>
      {tags.map(tag => (
        <button
          type='radio'
          key={tag.value}
          onClick={() => handleTagClick(tag.value)}
          className={`
            px-3 py-1 rounded-lg text-white font-medium text-sm
            transition-colors duration-200 ease-in-out
            ${selectedTag === tag.value ? 'bg-pink-500' : 'bg-gray-400'}
            hover:opacity-90
          `}
        >
          {tag.name}
        </button>
      ))}
    </div>
  );
};

export default BookInfoTag;
