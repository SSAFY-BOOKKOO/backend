import React, { useState, useEffect } from 'react';
import CurationTab from '@components/Curation/CurationTab';
import { useLocation } from 'react-router-dom';
import axios from 'axios';
import { authAxiosInstance } from '../../services/axiosInstance';
import { IoIosArrowBack } from 'react-icons/io';
import { IoIosArrowForward } from 'react-icons/io';

const CurationSend = () => {
  const location = useLocation();
  // const { sendLetters } = location.state || { sendLetters: [] };

  const [sendLetters, setLetters] = useState([]);
  const [page, setPage] = useState(1); // 페이지 상태 추가

  useEffect(() => {
    authAxiosInstance
      .get('/curations/mycuration', {
        params: {
          page: page,
        },
      })
      .then(res => {
        setLetters(res.data);
        console.log(res);
      })
      .catch(err => {
        console.log('error:', err);
      });
  }, [page]); // 페이지 상태를 의존성 배열에 추가

  return (
    <div className='flex flex-col'>
      <CurationTab />
      <div className='flex flex-col items-center bg-gray-100 space-y-4 p-4 m-4 rounded'>
        {sendLetters.length > 0 ? (
          sendLetters.map(letter => (
            <div key={letter.id} className='p-2 bg-white shadow rounded'>
              <p>Letter ID: {letter.id}</p>
              <p>Title: {letter.title}</p>
              <p>Content: {letter.content}</p>
            </div>
          ))
        ) : (
          <p className='text-center'>
            보낸 레터가 없습니다. <br /> 지금 바로 작성해 보세요!
          </p>
        )}
      </div>
      <div className='flex justify-center space-x-4'>
        <IoIosArrowBack
          onClick={() => setPage(prevPage => Math.max(prevPage - 1, 1))}
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

export default CurationSend;
