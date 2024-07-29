import React, { useState } from 'react';

const CreateSelectButton = ({ options, selected, setSelected }) => {
  const handleClick = value => {
    setSelected(value);
  };

  return (
    <div>
      {options.map(option => (
        <button
          key={option.index}
          onClick={() => handleClick(option.value)}
          className={`w-full py-3 px-4 mb-2 text-left rounded-lg transition-colors duration-200 ease-in-out ${
            option.value === selected
              ? 'bg-pink-500 text-white'
              : 'bg-gray-100 text-gray-700 hover:bg-gray-200'
          }`}
        >
          {option.text}
        </button>
      ))}
    </div>
  );
};

export default CreateSelectButton;
