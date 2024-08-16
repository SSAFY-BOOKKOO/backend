import React, { useEffect, useState } from 'react';
import { useSetAtom } from 'jotai';
import { useNavigate } from 'react-router-dom';
import 'swiper/css';
import CurationTab from '@components/Curation/CurationTab';
import { BsBookmarkStar, BsBookmarkStarFill } from 'react-icons/bs';
import { AiFillAlert } from 'react-icons/ai';
import { IoIosArrowBack, IoIosArrowForward } from 'react-icons/io';
import { FaTrashCan } from 'react-icons/fa6';
import { authAxiosInstance } from '@services/axiosInstance';
import { BsEnvelopeHeart } from 'react-icons/bs';
import Alert from '@components/@common/Alert';
import { alertAtom } from '@atoms/alertAtom';

const CurationReceive = () => {
  const navigate = useNavigate();
  const [letters, setLetters] = useState([]);
  const [page, setPage] = useState(0);
  const [storeLetters, setStoreLetters] = useState([]);
  const [selectedLetters, setSelectedLetters] = useState([]);
  const [isDeleting, setIsDeleting] = useState(false);
  const [countLetters, setCountLetters] = useState(0);
  const setAlert = useSetAtom(alertAtom);
  const [pageNumber, setPageNumber] = useState(0);

  // 받은 레터 조회
  useEffect(() => {}, []);

  // storeLetters가 변경될 때마다 로컬 스토리지에 저장
  useEffect(() => {
    localStorage.setItem('storeLetters', JSON.stringify(storeLetters));
  }, [storeLetters]);

  useEffect(() => {
    authAxiosInstance
      .get('/curations/store', { params: { page } })
      .then(res => {
        setStoreLetters(res.data.curationList);
      })
      .catch(err => {
        console.log('error:', err);
      });
  }, [page]);

  // 내가 받은 레터 보기
  useEffect(() => {
    authAxiosInstance
      .get('/curations', { params: { page } })
      .then(res => {
        setLetters(res.data.curationList);
        setCountLetters(res.data.count);
        // countLetters의 숫자를 10으로 나눈 값의 몫을 pageNumber로 지정
      })
      .catch(err => {
        console.log('error:', err);
      });
  }, [page]);

  const onStore = (event, letter) => {
    event.stopPropagation();
    let updatedStoreLetters;
    let isStoring;

    if (
      storeLetters.some(
        storedLetter => storedLetter.curationId === letter.curationId
      )
    ) {
      updatedStoreLetters = storeLetters.filter(
        storedLetter => storedLetter.curationId !== letter.curationId
      );
      isStoring = false;
    } else {
      updatedStoreLetters = [...storeLetters, letter];
      isStoring = true;
    }

    setStoreLetters(updatedStoreLetters);

    // 큐레이션 보관상태 변경
    authAxiosInstance
      .post(`/curations/store/${letter.curationId}`, {
        curationId: letter.curationId,
      })
      .then(res => {
        if (isStoring) {
          setAlert({
            isOpen: true,
            confirmOnly: true,
            message: '레터가 성공적으로 보관되었습니다.',
          });
        } else {
          setAlert({
            isOpen: true,
            confirmOnly: true,
            message: '레터가 성공적으로 보관이 해제되었습니다.',
          });
        }
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

  // 큐레이션 삭제
  const handleDeleteSelected = () => {
    if (selectedLetters.length === 0) return;

    const deleteRequests = selectedLetters.map(letter =>
      authAxiosInstance.delete(`/curations/${letter.curationId}`, {
        curationId: letter.curationId,
      })
    );

    Promise.all(deleteRequests)
      .then(responses => {
        setLetters(
          letters.filter(letter => !selectedLetters.includes(letter.id))
        );
        setSelectedLetters([]);
        window.location.reload();
      })
      .catch(err => {
        console.log('Error deleting letters:', err);
      });
  };

  // 레터 상세 보기
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
      <CurationTab />
      <div className='flex justify-center items-center space-x-2 pt-4 rounded'>
        <AiFillAlert className='text-red-500' />
        <p className='font-bold text-pink-400'>
          받은 날부터 15일 후 자동 삭제됩니다!
        </p>
      </div>
      <div className='flex justify-between items-center px-8 pt-3 pb-1'>
        <p className='font-bold text-green-400'>받은 레터 수: {countLetters}</p>
        <FaTrashCan
          className='text-xl cursor-pointer'
          onClick={() => setIsDeleting(!isDeleting)}
        />
      </div>
      <div className='flex-1 overflow-y-auto px-8'>
        {letters.map(letter => (
          <div key={letter.id} className='flex flex-grow'>
            <div
              className={`relative flex items-center mb-6 bg-green-50 rounded-lg shadow w-full h-40 transition-transform duration-300 cursor-pointer ${
                isDeleting ? 'transform -translate-x-1/5' : ''
              }`}
              onClick={() => handleLetterClick(letter)}
            >
              <img
                src={letter.coverImgUrl}
                alt={<BsEnvelopeHeart />}
                className='w-16 h-24 mx-4 rounded-lg'
              />
              <div className='flex-1 pb-7'>
                <h2 className='text-lg font-bold text-overflow-1'>
                  {letter.title}
                </h2>
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
              <p className='absolute bottom-2 right-4 text-sm text-gray-600 text-overflow-1'>
                FROM. {letter.writer}
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
      {countLetters > 0 && (
        <div className='flex justify-center space-x-12 text-2xl pb-4'>
          <IoIosArrowBack
            onClick={() => setPage(prevPage => Math.max(prevPage - 1, 0))}
            className={`cursor-pointer ${page === 0 ? 'text-gray-400' : ''}`}
          />
          <IoIosArrowForward
            onClick={() => {
              if (page < pageNumber) {
                setPage(prevPage => prevPage + 1);
              }
            }}
            className={`cursor-pointer ${
              page >= pageNumber ? 'text-gray-400 cursor-not-allowed' : ''
            }`}
          />
        </div>
      )}
      <Alert />
    </div>
  );
};

export default CurationReceive;
