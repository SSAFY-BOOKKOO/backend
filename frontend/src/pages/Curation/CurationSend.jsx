import React, { useState, useEffect } from 'react';
import CurationTab from '@components/Curation/CurationTab';
import { useLocation, useNavigate } from 'react-router-dom';
import { authAxiosInstance } from '@services/axiosInstance';
import { IoIosArrowBack } from 'react-icons/io';
import { IoIosArrowForward } from 'react-icons/io';
import { BsEnvelopeHeart } from 'react-icons/bs';
import Spinner from '@components/@common/Spinner';

const CurationSend = () => {
  const location = useLocation();
  const navigate = useNavigate();
  const [countLetters, setCountLetters] = useState(0);
  const [sendLetters, setLetters] = useState([]);
  const [page, setPage] = useState(0); // 페이지 상태 추가
  const [loading, setLoading] = useState(true); // 로딩 상태 추가
  const [pageNumber, setPageNumber] = useState(0);

  useEffect(() => {
    setLoading(true); // API 호출 시작 전 로딩 상태 설정
    authAxiosInstance
      .get('/curations/mycuration', { params: { page } })
      .then(res => {
        setLetters(res.data.curationList);
        setCountLetters(res.data.count);
      })
      .catch(err => {
        console.log('error:', err);
      })
      .finally(() => {
        setLoading(false); // API 호출 완료 후 로딩 상태 해제
      });
  }, [page]); // 페이지 상태를 의존성 배열에 추가

  // 레터 상세보기
  const handleLetterClick = letter => {
    authAxiosInstance
      .get(`/curations/detail/${letter.curationId}`, {
        curationId: letter.curationId,
      })
      .then(res => {
        navigate(`/curation/letter/${letter.curationId}`, {
          state: { letter },
        });
      })
      .catch(err => {
        console.log('error:', err);
      });
  };
  useEffect(() => {
    setPageNumber(Math.floor(countLetters / 10));
  }, [countLetters]);
  return (
    <div className='flex flex-col'>
      {loading ? (
        <Spinner />
      ) : (
        <>
          <CurationTab />
          <p className='font-bold text-green-400 px-8 pt-3 pb-1'>
            보낸 레터 수: {countLetters}
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
                      alt={<BsEnvelopeHeart />}
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
              <p className='text-center text-md font-bold'>
                레터를 작성해 주세요!
              </p>
            </div>
          )}

          {countLetters > 0 && (
            <div className='flex justify-center space-x-12 text-2xl pb-4'>
              <IoIosArrowBack
                onClick={() => setPage(prevPage => Math.max(prevPage - 1, 0))}
                className={`cursor-pointer ${page === 0 ? 'text-gray-400' : ''}`} // 비활성화 시 회색으로 표시
              />
              <IoIosArrowForward
                onClick={() => {
                  if (page < pageNumber) {
                    setPage(prevPage => prevPage + 1);
                  }
                }}
                className={`cursor-pointer ${
                  page >= pageNumber ? 'text-gray-400 cursor-not-allowed' : ''
                }`} // 비활성화 시 회색으로 표시 및 커서 스타일 변경
                disabled={page >= pageNumber} // 클릭 불가
              />
            </div>
          )}
        </>
      )}
    </div>
  );
};

export default CurationSend;
