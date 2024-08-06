import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import 'swiper/css';
import CurationTab from '@components/Curation/CurationTab';
import { BsBookmarkStar, BsBookmarkStarFill } from 'react-icons/bs';
import { AiFillAlert } from 'react-icons/ai';
import { IoIosArrowBack, IoIosArrowForward } from 'react-icons/io';
import { FaTrashCan } from 'react-icons/fa6';
// import { useAtom } from 'jotai';
// import { storedLettersAtom } from '../../atoms/CurationStoreAtom';
import { authAxiosInstance } from '../../services/axiosInstance';

const CurationReceive = () => {
  const navigate = useNavigate();
  const [letters, setLetters] = useState([]);
  const [page, setPage] = useState(0);
  // const [storedLetters, setStoredLetters] = useAtom(storedLettersAtom);
  const [storeLetters, setStoreLetters] = useState([]);
  const [selectedLetters, setSelectedLetters] = useState([]);
  const [isDeleting, setIsDeleting] = useState(false);

  // 컴포넌트가 마운트될 때 로컬 스토리지에서 storeLetters를 불러옴
  useEffect(() => {
    const stored = localStorage.getItem('storeLetters');
    if (stored) {
      setStoreLetters(JSON.parse(stored));
    }
  }, []);

  // storeLetters가 변경될 때마다 로컬 스토리지에 저장
  useEffect(() => {
    localStorage.setItem('storeLetters', JSON.stringify(storeLetters));
  }, [storeLetters]);

  useEffect(() => {
    authAxiosInstance
      .get('/curations/store', { params: { page } })
      .then(res => {
        setStoreLetters(res.data);
        console.log(storeLetters);
      })
      .catch(err => {
        console.log('error:', err);
      });
  }, [page]);

  useEffect(() => {
    authAxiosInstance
      .get('/curations', { params: { page } })
      .then(res => {
        setLetters(res.data);
        console.log(res);
      })
      .catch(err => {
        console.log('error:', err);
      });
  }, [page]);

  const onStore = (event, letter) => {
    event.stopPropagation();
    let updatedStoreLetters;
    if (
      storeLetters.some(
        storedLetter => storedLetter.curationId === letter.curationId
      )
    ) {
      updatedStoreLetters = storeLetters.filter(
        storedLetter => storedLetter.curationId !== letter.curationId
      );
    } else {
      updatedStoreLetters = [...storeLetters, letter];
    }
    setStoreLetters(updatedStoreLetters);
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
  };

  const handleSelectLetter = letter => {
    if (selectedLetters.includes(letter)) {
      setSelectedLetters(
        selectedLetters.filter(selectedId => selectedId !== letter)
      );
    } else {
      setSelectedLetters([...selectedLetters, letter]);
    }
  };

  const handleDeleteSelected = () => {
    if (selectedLetters.length === 0) return;

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

  return (
    <div className='flex flex-col'>
      <CurationTab />
      <div className='flex justify-center items-center space-x-2 pt-4 rounded'>
        <AiFillAlert className='text-red-500' />
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
      <div className='flex-1 overflow-y-auto px-4'>
        {letters.map(letter => (
          <div key={letter.id} className='flex flex-grow'>
            <div
              className={`relative flex items-center mb-6 bg-green-50 rounded-lg shadow w-full h-40 transition-transform duration-300 cursor-pointer ${isDeleting ? 'transform -translate-x-1/5' : ''}`}
              onClick={() => handleLetterClick(letter)}
            >
              <img
                src={letter.image}
                alt='Letter'
                className='w-16 h-24 mx-4 rounded-lg'
              />
              <div className='flex-1 pb-7'>
                <h2 className='text-lg font-bold'>{letter.title}</h2>
                <p>{letter.content}</p>
                <p>{letter.date}</p>
              </div>
              {storeLetters.some(
                storedLetter => storedLetter.curationId === letter.curationId
              ) ? (
                <BsBookmarkStarFill
                  onClick={event => onStore(event, letter)}
                  className='absolute top-7 right-3 cursor-pointer size-7'
                />
              ) : (
                <BsBookmarkStar
                  onClick={event => onStore(event, letter)}
                  className='absolute top-7 right-3 cursor-pointer size-7'
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

export default CurationReceive;
