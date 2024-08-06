import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
// import { Swiper, SwiperSlide } from 'swiper/react';
import 'swiper/css';
import CurationTab from '@components/Curation/CurationTab';
import { BsBookmarkStar, BsBookmarkStarFill } from 'react-icons/bs';
import { AiFillAlert } from 'react-icons/ai';
import { IoIosArrowBack } from 'react-icons/io';
import { IoIosArrowForward } from 'react-icons/io';
import { FaTrashCan } from 'react-icons/fa6';

// 연동
import { authAxiosInstance } from '../../services/axiosInstance';

const CurationReceive = () => {
  const navigate = useNavigate();
  const [letters, setLetters] = useState([]);
  const [page, setPage] = useState(0); // 페이지 상태 추가

  /////목록 조회//////
  useEffect(() => {
    authAxiosInstance
      .get('/curations', {
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
  }, [page]);

  // ///////////////////////////////////삭제

  // 변수 설정
  const [selectedLetters, setSelectedLetters] = useState([]); // 삭제할 레터 선택
  const [isDeleting, setIsDeleting] = useState(false); // 삭제 모드 플래그

  // 함수: 삭제할 레터 선택
  // 레터를 객체로 받아서 레터 아이디로 식별
  const handleSelectLetter = letter => {
    if (selectedLetters.includes(letter)) {
      setSelectedLetters(
        selectedLetters.filter(selectedId => selectedId !== letter)
      );
    } else {
      setSelectedLetters([...selectedLetters, letter]);
      console.log([...selectedLetters]);
    }
  };

  // 함수: 레터 삭제
  const handleDeleteSelected = () => {
    if (selectedLetters.length === 0) {
      return; // 선택된 레터가 없으면 아무 작업도 하지 않음
    }

    console.log('Selected letters for deletion:', selectedLetters);

    // 선택한 레터 삭제 기능
    const deleteRequests = selectedLetters.map(letter =>
      authAxiosInstance.delete(`/curations/${letter.curationId}`, {
        curationId: letter.curationId,
      })
    );

    Promise.all(deleteRequests)
      .then(responses => {
        console.log('Letters deleted successfully:', responses);
        setLetters(
          letters.filter(letter => !selectedLetters.includes(letter.id))
        );
        setSelectedLetters([]);
      })
      .catch(err => {
        console.log('Error deleting letters:', err);
      });
  };

  ///////////// 보관
  // 보관함 관리 위한 useState
  const [storedLetters, setStoredLetters] = useState([]);
  // const navigateToStore = () => {
  //   navigate('/curation/store', { state: { storedLetters } });
  // };

  // 보관함 등록 로직
  const onStore = (event, letter) => {
    event.stopPropagation();
    if (storedLetters.includes(letter)) {
      setStoredLetters(storedLetters.filter(storeId => storeId !== letter));
    } else {
      setStoredLetters([...storedLetters, letter]);
      console.log('보관함으로 슝');
      // navigateToStore();
      authAxiosInstance
        .post(`/curations/store/${letter.curationId}`, {
          curationId: letter.curationId,
        })
        .then(res => {
          console.log('Letter stored successfully:', res);
        })
        .catch(err => {
          console.log('error:', err);
        });
    }
  };

  // 레터 상세보기
  const handleLetterClick = letter => {
    // navigate(`/curations/detail/${letter.id}`, { state: { letter } });
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

  return (
    <div className='flex flex-col'>
      <CurationTab />
      <div className='flex justify-center items-center space-x-2 pt-4 rounded'>
        <AiFillAlert className='text-red-500 ' />
        <p className='font-bold text-pink-400'>
          받은 날부터 15일 후 자동 삭제됩니다!
        </p>
      </div>
      <div className='flex justify-between items-center pl-6 pr-4 py-3'>
        <p className='font-bold text-green-400'>
          받은 레터 수: {letters.length}
        </p>
        <FaTrashCan
          className='text-xl cursor-pointer'
          onClick={() => setIsDeleting(!isDeleting)}
        />
      </div>
      {/* 편지들 css */}
      <div className='flex-1 overflow-y-auto px-4'>
        {letters.map(letter => (
          <div key={letter.id} className='flex flex-grow'>
            <div
              className={`relative flex items-center mb-6 bg-green-50 rounded-lg shadow w-full h-40 transition-transform duration-300 ${isDeleting ? 'transform -translate-x-1/5' : ''}`}
              onClick={() => handleLetterClick(letter)}
            >
              <img
                src={letter.image}
                alt='Letter'
                className='w-16 h-24 mx-4 rounded-lg'
                // onClick={() => handleLetterClick(letter)}
              />
              <div className='flex-1 pb-7'>
                <h2 className='text-lg font-bold'>{letter.title}</h2>
                <p>{letter.content}</p>
                <p>{letter.date}</p>
              </div>
              {storedLetters.includes(letter.id) ? (
                <BsBookmarkStarFill
                  onClick={event => onStore(event, letter)}
                  className='absolute top-7 right-3 cursor-pointer size-7'
                />
              ) : (
                <BsBookmarkStar
                  className='absolute top-7 right-3 cursor-pointer size-7'
                  onClick={event => onStore(event, letter)}
                />
              )}
              <p className='absolute bottom-2 right-4 text-sm text-gray-600'>
                FROM. {letter.from}
              </p>
            </div>
            {isDeleting && (
              <div className='w-16 h-full flex items-center justify-center bg-white'>
                <input
                  type='checkbox'
                  className='form-checkbox h-6 w-6 mt-14 ml-3'
                  checked={selectedLetters.includes(letter)}
                  onChange={() => handleSelectLetter(letter)}
                />
              </div>
            )}
          </div>
        ))}
      </div>
      {isDeleting && (
        <div className='flex justify-center pb-6 px-4'>
          <button
            className='bg-pink-500 text-white px-4 p-2 w-full rounded-lg'
            onClick={handleDeleteSelected}
          >
            선택한 레터 삭제
          </button>
        </div>
      )}
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

export default CurationReceive;
