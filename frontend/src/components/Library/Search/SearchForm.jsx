import React from 'react';
import Input from '@components/@common/Input';
import IconButton from '@components/@common/IconButton';
import { useNavigate } from 'react-router-dom';
import { IoSearchSharp, IoArrowBack } from 'react-icons/io5';

const SearchForm = ({ searchText, setSearchText, onSubmit, placeholder }) => {
  const navigate = useNavigate();

  const handleBack = () => {
    navigate(-1);
  };

  return (
    <form
      className='mb-4 w-full flex items-center justify-between'
      onSubmit={onSubmit}
    >
      <IconButton onClick={handleBack} icon={IoArrowBack} />
      <div className='flex-grow mx-2'>
        <Input
          id='searchText'
          placeholder={placeholder}
          value={searchText}
          onChange={e => setSearchText(e.target.value)}
          customClass='w-full'
        />
      </div>
      <IconButton type='submit' icon={IoSearchSharp} />
    </form>
  );
};

export default SearchForm;
