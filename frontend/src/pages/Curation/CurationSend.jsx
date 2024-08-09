import React, { useState, useEffect } from 'react';
import CurationTab from '@components/Curation/CurationTab';
import { useLocation, useNavigate } from 'react-router-dom';
import axios from 'axios';
import { authAxiosInstance } from '../../services/axiosInstance';
import { IoIosArrowBack } from 'react-icons/io';
import { IoIosArrowForward } from 'react-icons/io';

const CurationSend = () => {
  const location = useLocation();
  const navigate = useNavigate();

  // const { sendLetters } = location.state || { sendLetters: [] };

  useEffect(() => {
    console.log(sendLetters);
  }, []);

  const [sendLetters, setLetters] = useState([]);
  const [page, setPage] = useState(0); // 페이지 상태 추가

  // 레터 상세보기
  const handleLetterClick = letter => {
    authAxiosInstance
      .get(`/curations/detail/${letter.curationId}`, {
        curationId: letter.curationId,
      })
      .then(res => {
        console.log('Letter Detail:', res);
        navigate(`/curation/letter/${letter.curationId}`, {
          state: { letter },
        });
      })
      .catch(err => {
        console.log('error:', err);
      });
  };

  useEffect(() => {
    authAxiosInstance
      .get('/curations/mycuration', { params: { page } })
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
      <p className='font-bold text-green-400 px-8 pt-3 pb-1'>
        보낸 레터 수: {sendLetters.length}
      </p>
      {sendLetters.length > 0 ? (
        <div className='flex-1 overflow-y-auto px-8'>
          {sendLetters.map(letter => (
            <div key={letter.id} className='flex flex-grow'>
              <div
                key={letter.id}
                className={
                  'relative flex items-center mb-6 bg-green-50 rounded-lg shadow w-full h-40 cursor-pointer'
                }
                onClick={() => handleLetterClick(letter)}
              >
                <img
                  src={letter.coverImgUrl}
                  alt='Letter'
                  className='w-16 h-24 mx-4 rounded-lg'
                  onClick={() => handleLetterClick(letter)}
                />
                <div className='flex-1 pb-7'>
                  <h2 className='text-lg font-bold'>{letter.title}</h2>
                  <p>{letter.content}</p>
                  <p>{letter.date}</p>
                </div>

                <p className='absolute bottom-2 right-4 text-sm text-gray-600'>
                  FROM. {letter.writer}
                </p>
              </div>
            </div>
          ))}
        </div>
      ) : (
        <div className='bg-gray-200 p-4 mx-4 rounded-lg'>
          <p className='text-center text-md font-bold'>레터를 작성해 주세요!</p>
        </div>
      )}

      <div className='flex justify-center space-x-12 text-2xl pb-4'>
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
