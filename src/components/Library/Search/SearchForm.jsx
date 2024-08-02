import React from 'react';
import Input from '@components/@common/Input';
import IconButton from '@components/@common/IconButton';
import { IoSearchSharp } from 'react-icons/io5';

const SearchForm = ({ searchText, setSearchText, onSubmit }) => {
  return (
    <form
      className='mb-4 w-full flex flex-row items-center justify-center'
      onSubmit={onSubmit}
    >
      <div className='flex-grow'>
        <Input
          id='searchText'
          placeholder='책을 검색하세요'
          value={searchText}
          onChange={e => setSearchText(e.target.value)}
        />
      </div>
      <IconButton type='submit' icon={IoSearchSharp} />
    </form>
  );
};

export default SearchForm;
