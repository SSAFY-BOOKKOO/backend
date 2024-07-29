// src/pages/CurationReceive.jsx
import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { Swiper, SwiperSlide } from 'swiper/react';
import 'swiper/css';
import CurationTab from '@components/Curation/CurationTab';
import { BsBookmarkStar, BsBookmarkStarFill } from 'react-icons/bs';
import { BsTrash3 } from 'react-icons/bs';
import { AiFillAlert } from 'react-icons/ai';

// 임시 레터 데이터
const initialLetters = [
  {
    id: 1,
    title: '레터1',
    content: '너무 유익했다!',
    from: '양귀자',
    date: '2024-07-19',
    image: 'https://image.yes24.com/momo/TopCate249/MidCate003/24823257.jpg',
  },
  {
    id: 2,
    title: '키움 우승',
    from: '홍원기',
    content: '영웅질주',
    date: '2024-07-19',
    image:
      'https://yt3.googleusercontent.com/HmU-cGuNTGaoyJ2dSCW7CrdNMLVXq8xgKQ2Tsri543dTS7RMSgcseDb8p9w-g2amOoNJkXxT=s900-c-k-c0x00ffffff-no-rj',
  },
  {
    id: 3,
    title: '레터2',
    content: '너무 재밌당',
    from: '에이미',
    date: '2024-07-19',
    image: 'https://image.yes24.com/goods/123400303/L',
  },
];

// 받은 편지들 보여주기
const CurationReceive = () => {
  const navigate = useNavigate();
  const [letters, setLetters] = useState(initialLetters);
  // 보관함 관리 위한 useState
  const [storedLetters, setStoredLetters] = useState([]);
  const [slideId, setSlideId] = useState(null);

  const navigateToStore = () => {
    navigate('/curation/store', { state: { storedLetters } });
  };

  // 보관함 등록 로직
  const onStore = (event, letter) => {
    // event 객체 추가
    event.stopPropagation();
    // 등록되어 있으면 해제
    if (storedLetters.includes(letter.id)) {
      setStoredLetters(storedLetters.filter(id => id !== letter.id));
    }
    // 등록 안 되어 있으면 등록(추가)
    else {
      setStoredLetters([...storedLetters, letter.id]);
      // navigateToStore();
      // navigate('/curation/store');
    }
  };

  const handleSlide = id => {
    setSlideId(slideId === id ? null : id);
  };

  const handleDelete = id => {
    setLetters(letters.filter(letter => letter.id !== id));
  };

  const handleLetterClick = letter => {
    navigate(`/curation/letter/${letter.id}`, { state: { letter } });
  };

  return (
    <div className='flex flex-col'>
      <CurationTab />
      <div className='flex justify-center items-center space-x-2 pt-4 rounded'>
        <AiFillAlert className='text-red-500 ' />
        <p className='font-bold'>받은 날부터 15일 후 자동 삭제됩니다!</p>
      </div>
      <p className='pl-6 pt-3'>받은 레터 수: {letters.length}</p>
      <div className='flex-1 overflow-y-auto px-4'>
        {letters.map(letter => (
          <Swiper
            key={letter.id}
            onSlideChange={() => handleSlide(letter.id)}
            className='relative flex items-center my-4 cursor-pointer'
            slidesPerView={1}
          >
            {/* 레터 */}
            <SwiperSlide>
              <div
                className={`relative flex items-center bg-gray-100 rounded-lg p-4 shadow w-full h-40 transition-transform duration-300 ease-in-out ${
                  slideId === letter.id ? 'transform translate-x-2/3' : ''
                }`}
                onClick={() => handleLetterClick(letter)}
              >
                <img
                  src={letter.image}
                  alt='Letter'
                  className='w-16 h-24 mr-4 rounded-lg'
                />
                <div className='flex-1 pb-7'>
                  <h2 className='text-lg font-bold'>{letter.title}</h2>
                  <p>책 제목</p>
                  <p>2024.07.26</p>
                </div>
                {/* 보관x: 빈 아이콘 보관o: 꽉찬 아이콘 */}
                {storedLetters.includes(letter.id) ? (
                  <BsBookmarkStarFill
                    key={letter.id}
                    onClick={event => onStore(event, letter)}
                    className='absolute top-2 right-2 cursor-pointer size-7'
                  />
                ) : (
                  <BsBookmarkStar
                    key={letter.id}
                    className='absolute top-7 right-3 cursor-pointer size-7'
                    onClick={event => onStore(event, letter)}
                  />
                )}
                <p className='absolute bottom-2 right-4 text-sm text-gray-600'>
                  FROM. {letter.from}
                </p>
              </div>
            </SwiperSlide>

            {/* 삭제 영역 */}
            <SwiperSlide>
              <div
                className={`flex justify-center items-center w-1/3 h-40 bg-red-500 rounded-r-lg ml-44 transition-transform duration-300 ease-in-out ${
                  slideId === letter.id ? 'transform translate-x-2/3' : ''
                }`}
                onClick={() => handleDelete(letter.id)}
              >
                <BsTrash3 className='text-black text-2xl' />
              </div>
            </SwiperSlide>
          </Swiper>
        ))}
      </div>
    </div>
  );
};

export default CurationReceive;
