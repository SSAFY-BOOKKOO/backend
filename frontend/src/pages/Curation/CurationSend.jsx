import React, { useState, useEffect } from 'react';
import CurationTab from '@components/Curation/CurationTab';
import { useLocation,useNavigate } from 'react-router-dom';
import axios from 'axios';
import { authAxiosInstance } from '../../services/axiosInstance';
import { IoIosArrowBack } from 'react-icons/io';
import { IoIosArrowForward } from 'react-icons/io';


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

const CurationSend = () => {
  const location = useLocation();
  const navigate = useNavigate();

  // const { sendLetters } = location.state || { sendLetters: [] };

  const [sendLetters, setLetters] = useState(initialLetters);
  const [page, setPage] = useState(1); // 페이지 상태 추가
  

  // 레터 상세보기
  const handleLetterClick = letter => {
    navigate(`/curation/letter/${letter.id}`, { state: { letter } });
  };

  // useEffect(() => {
  //   authAxiosInstance
  //     .get('/curations/mycuration', {
  //       params: {
  //         page: page,
  //       },
  //     })
  //     .then(res => {
  //       setLetters(res.data);
  //       console.log(res);
  //     })
  //     .catch(err => {
  //       console.log('error:', err);
  //     });
  // }, [page]); // 페이지 상태를 의존성 배열에 추가

  return (
    <div className='flex flex-col'>
      <CurationTab />
      <p className='font-bold text-green-400 pl-6 pr-4 py-3'>
          보낸 레터 수: {sendLetters.length}
        </p>
        {sendLetters.length>0?(<div className='flex-1 overflow-y-auto px-4'>
        {sendLetters.map(letter => (
          <div className='flex flex-grow'>
          <div 
            key={letter.id} 
            className={'relative flex items-center mb-6 bg-green-50 rounded-lg shadow w-full h-40'}
          >
            <img
              src={letter.image}
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
              FROM. {letter.from}
            </p>
            
          </div>
          
          </div>
        ))}
        </div>):(
          <p>레터를 작성해 주세요!</p>
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

export default CurationSend;
