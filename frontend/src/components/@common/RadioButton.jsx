import React from 'react';

const RadioButton = ({ tags, selectedTag, setSelectedTag }) => {
  return (
    <div className='flex flex-wrap gap-4 my-2'>
      {tags.map(tag => (
        <button
          key={tag.id}
          onClick={() => setSelectedTag(tag.value)}
          type='button'
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

export default RadioButton;
