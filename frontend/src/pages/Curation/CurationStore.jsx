import React, { useEffect, useState } from 'react';
import CurationTab from '@components/Curation/CurationTab';
import { useLocation } from 'react-router-dom';
import { IoIosArrowBack } from 'react-icons/io';
import { IoIosArrowForward } from 'react-icons/io';
import axios from 'axios';
import { axiosInstance, authAxiosInstance } from '../../services/axiosInstance';

const CurationStore = () => {
  const location = useLocation();
  const [storedLetters, setStoredLetters] = useState([]);
  const [page, setPage] = useState(0);

  useEffect(() => {
    authAxiosInstance
      .get('/curations/store', {
        params: {
          page: page,
        },
      })
      .then(res => {
        setStoredLetters(res.data);
        console.log(res);
      })
      .catch(err => {
        console.log('error:', err);
      });
  }, [page]);

  return (
    <div className='flex flex-col'>
      <CurationTab />
      <div className='flex flex-col items-center bg-gray-100 space-y-4 p-4 m-4 rounded'>
        {storedLetters.length > 0 ? (
          storedLetters.map(letter => (
            <div
              key={letter.curationId}
              className='p-2 bg-white shadow rounded'
            >
              <p>Letter ID: {letter.curationId}</p>
              <p>Writer: {letter.writer}</p>
              <p>Title: {letter.title}</p>
              <img
                src={letter.coverImgUrl}
                alt={letter.title}
                className='w-16 h-24 rounded'
              />
            </div>
          ))
        ) : (
          <p>보관함이 비었습니다.</p>
        )}
      </div>
      <div className='flex justify-center space-x-4'>
        <IoIosArrowBack
          onClick={() => setPage(prevPage => Math.max(prevPage - 1, 0))}
          className='cursor-pointer text-xl'
        />
        <IoIosArrowForward
          onClick={() => setPage(prevPage => prevPage + 1)}
          className='cursor-pointer text-xl'
        />
      </div>
    </div>
  );
};

export default CurationStore;
