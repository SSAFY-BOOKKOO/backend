// src/pages/CurationReceive.jsx
import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import Tab from '../components/Curation/Tab';
import { BsBookmarkStar, BsBookmarkStarFill } from 'react-icons/bs';

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
    content:
      '너무 재밌당너무 재밌당너무 재밌당너무 재밌당너무 재밌당너무 재밌당너무 재밌당너무 재밌당너무 재밌당너무 재밌당너무 재밌당너무 재밌당너무 재밌당너무 재밌당너무 재밌당너무 재밌당너무 재밌당너무 재밌당너무 재밌당너무 재밌당너무 재밌당너무 재밌당너무 재밌당너무 재밌당너무 재밌당너무 재밌당너무 재밌당너무 재밌당너무 재밌당너무 재밌당너무 재밌당너무 재밌당너무 재밌당너무 재밌당너무 재밌당너무 재밌당너무 재밌당너무 재밌당너무 재밌당너무 재밌당너무 재밌당너무 재밌당너무 재밌당너무 재밌당너무 재밌당너무 재밌당너무 재밌당너무 재밌당너무 재밌당너무 재밌당너무 재밌당너무 재밌당너무 재밌당너무 재밌당너무 재밌당너무 재밌당너무 재밌당너무 재밌당너무 재밌당너무 재밌당너무 재밌당너무 재밌당너무 재밌당너무 재밌당너무 재밌당너무 재밌당너무 재밌당너무 재밌당너무 재밌당너무 재밌당너무 재밌당너무 재밌당너무 재밌당너무 재밌당너무 재밌당너무 재밌당너무 재밌당너무 재밌당너무 재밌당너무 재밌당너무 재밌당너무 재밌당너무 재밌당너무',
    from: '에이미',
    date: '2024-07-19',
    image: 'https://image.yes24.com/goods/123400303/L',
  },
];

// 받은 편지들 보여주기
const CurationReceive = () => {
  const navigate = useNavigate();
  // 지울 때 현재 레터 확인용
  const [letters, setLetters] = useState(initialLetters);
  // 보관할 레터
  const [storedLetters, setStoredLetters] = useState([]);
  // 슬라이드(해서 삭제 할) 레터
  const [slideId, setSlideId] = useState(null);

  // id로 구분해서 보관할 레터로 구분되면 store 링크로 보내기
  const onStore = letter => {
    if (storedLetters.includes(letter.id)) {
      setStoredLetters(storedLetters.filter(id => id !== letter.id));
    } else {
      setStoredLetters([...storedLetters, letter.id]);
      navigate('/curation/store');
    }
  };

  // 삭제위한 슬라이드
  const handleSlide = id => {
    setSlideId(slideId === id ? null : id);
  };

  // fiter해서 안 보이게
  const handleDelete = id => {
    setLetters(letters.filter(letter => letter.id !== id));
  };

  // 레터 상세보기
  const handleLetterClick = letter => {
    navigate(`/curation/letter/${letter.id}`, { state: { letter } });
  };

  return (
    <div className='flex flex-col min-h-[calc(100vh-56px)]'>
      <Tab />
      <div className='flex-1 overflow-y-auto px-4'>
        {letters.map(letter => (
          <div key={letter.id} className='relative flex items-center my-8'>
            <div
              className={`card flex items-center bg-gray-100 rounded-lg p-4 shadow w-full ${
                slideId === letter.id ? 'slide' : ''
              }`}
              onClick={() => handleLetterClick(letter)}
            >
              <img
                src={letter.image}
                alt='Letter'
                className='w-16 h-24 mr-4 rounded-lg'
              />
              <div className='flex-1 pb-12'>
                <p className='text-sm text-gray-600'>FROM. {letter.from}</p>
                <h2 className='text-lg font-bold'>{letter.title}</h2>
              </div>
              {storedLetters.includes(letter.id) ? (
                <BsBookmarkStarFill
                  className='mt-20 cursor-pointer'
                  onClick={() => onStore(letter)}
                />
              ) : (
                <BsBookmarkStar
                  className='mt-20 cursor-pointer size-7'
                  onClick={() => onStore(letter)}
                />
              )}
            </div>
            {slideId === letter.id && (
              <div
                className='delete-button absolute right-0 top-0 bottom-0'
                onClick={() => handleDelete(letter.id)}
              >
                <svg
                  xmlns='http://www.w3.org/2000/svg'
                  className='h-6 w-6'
                  fill='none'
                  viewBox='0 0 24 24'
                  stroke='currentColor'
                >
                  <path
                    strokeLinecap='round'
                    strokeLinejoin='round'
                    strokeWidth='2'
                    d='M19 7l-1 12H6L5 7m5 4v6m4-6v6m1-14H9m1-4h4m-4 4h4m2 2v2m0 4H7m1-14h4m-4 4h4'
                  />
                </svg>
              </div>
            )}
          </div>
        ))}
      </div>
    </div>
  );
};

export default CurationReceive;
