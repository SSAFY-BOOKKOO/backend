import React, { useEffect, useState } from 'react';
import CurationTab from '@components/Curation/CurationTab';
import { useNavigate } from 'react-router-dom';
import { IoIosArrowBack } from 'react-icons/io';
import { IoIosArrowForward } from 'react-icons/io';
import { axiosInstance, authAxiosInstance } from '../../services/axiosInstance';
import { BsEnvelopeHeart } from 'react-icons/bs';
import Spinner from '@components/@common/Spinner';

const CurationStore = () => {
  const navigate = useNavigate();
  const [storedLetters, setStoredLetters] = useState([]);
  const [page, setPage] = useState(0);
  const [loading, setLoading] = useState(true); // 로딩 상태 추가
  const [countLetters, setCountLetters] = useState(0);

  // 레터 상세보기
  const handleLetterClick = letter => {
    authAxiosInstance
      .get(`/curations/detail/${letter.curationId}`, {
        curationId: letter.curationId,
      })
      .then(res => {
        console.log('Letter Detail:', res);
        navigate(`/curation/letter/${letter.curationId}`, {
          state: { letter, modalVisible: false },
        });
      })
      .catch(err => {
        console.log('error:', err);
      });
  };

  useEffect(() => {
    setLoading(true); // API 호출 시작 전 로딩 상태 설정
    authAxiosInstance
      .get('/curations/store', {
        params: {
          page: page,
        },
      })
      .then(res => {
        setStoredLetters(res.data.curationList);
        setCountLetters(res.data.count);
        console.log(res);
      })
      .catch(err => {
        console.log('error:', err);
      })
      .finally(() => {
        setLoading(false); // API 호출 완료 후 로딩 상태 해제
      });
  }, [page]);

  return (
    <div className='flex flex-col'>
      <CurationTab />
      <p className='font-bold text-green-400 px-8 pt-3 pb-1'>
        보관한 레터 수: {countLetters}
      </p>
      {loading ? (
        <Spinner />
      ) : storedLetters.length > 0 ? (
        <div className='flex-1 overflow-y-auto px-8'>
          {storedLetters.map(letter => (
            <div key={letter.id} className='flex flex-grow'>
              <div
                className={
                  'relative flex items-center mb-6 bg-green-50 rounded-lg shadow w-full h-40 cursor-pointer'
                }
                onClick={() => handleLetterClick(letter)}
              >
                <img
                  src={letter.coverImgUrl}
                  alt={<BsEnvelopeHeart />}
                  className='w-16 h-24 mx-4 rounded-lg'
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
          <p className='text-center text-md font-bold'>보관함이 비었습니다.</p>
        </div>
      )}
      {storedLetters.length > 10 && (
        <div className='flex justify-center space-x-12 text-2xl pb-4'>
          <IoIosArrowBack
            onClick={() => setPage(prevPage => Math.max(prevPage - 1, 0))}
            className='cursor-pointer '
          />
          <IoIosArrowForward
            onClick={() => setPage(prevPage => prevPage + 1)}
            className='cursor-pointer '
          />
        </div>
      )}
    </div>
  );
};

export default CurationStore;
