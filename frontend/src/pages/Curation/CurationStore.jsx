import React, { useEffect, useState } from 'react';
import CurationTab from '@components/Curation/CurationTab';
import { useLocation } from 'react-router-dom';
import axios from 'axios';
import { axiosInstance, authAxiosInstance } from '../../services/axiosInstance';

const CurationStore = () => {
  const location = useLocation();
  // const { storedLetters } = location.state || { storedLetters: [] };

  const [storedLetters, setLetters] = useState([]);

  useEffect(
    () => {
      authAxiosInstance
        // axios로 get요청 보내기
        .get('/curations/mycuration')
        // 요청 성공하면 받아와서 letters에 할당
        .then(res => {
          setLetters(res.data);
          console.log(res);
        })

        // 요청 실패하면 오류 일단 console에
        .catch(err => {
          console.log('error:', err);
        });
    },
    // 화면에 처음 렌더링될 때만 실행
    []
  );

  return (
    <div className='flex flex-col'>
      <CurationTab />
      <div className='flex flex-col items-center bg-gray-100 space-y-4 p-4 m-4 rounded'>
        {storedLetters.length > 0 ? (
          storedLetters.map(id => (
            <div key={id} className='p-2 bg-white shadow rounded'>
              <p>Letter ID: {id}</p>
            </div>
          ))
        ) : (
          <p>보관함이 비었습니다.</p>
        )}
      </div>
    </div>
  );
};

export default CurationStore;
