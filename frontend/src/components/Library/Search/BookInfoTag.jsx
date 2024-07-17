import { useEffect } from 'react';

const BookInfoTag = ({ selectedTag, setSelectedTag }) => {
  const tags = [
    { id: 1, name: '제목', value: 'author' },
    { id: 2, name: '지은이' },
    { id: 3, name: '출판사' },
  ];

  const handleTagClick = id => {
    setSelectedTag(id === selectedTag ? null : id);
  };

  useEffect(() => {
    console.log(selectedTag);
  }, [selectedTag]);

  return (
    <div className='flex flex-wrap gap-4 my-2'>
      {tags.map(tag => (
        <button
          type='radio'
          key={tag.id}
          onClick={() => handleTagClick(tag.id)}
          className={`
            px-3 py-1 rounded-lg text-white font-medium text-sm
            transition-colors duration-200 ease-in-out
            ${selectedTag === tag.id ? 'bg-blue-500' : 'bg-gray-400'}
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
