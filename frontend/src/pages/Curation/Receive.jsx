// src/pages/CurationReceive.jsx
import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { Swiper, SwiperSlide } from 'swiper/react';
import 'swiper/css';
import CurationTab from '@components/Curation/CurationTab';
import { BsBookmarkStar, BsBookmarkStarFill } from 'react-icons/bs';
import { BsTrash3 } from 'react-icons/bs';
import { AiFillAlert } from 'react-icons/ai';
import axios from 'axios';
import { authAxiosInstance } from '../../services/axiosInstance';

// 받은 편지들 보여주기
const CurationReceive = () => {
  //   const navigate = useNavigate();
  const [letters, setLetters] = useState([]);

  useEffect(
    () => {
      authAxiosInstance
        // axios로 get요청 보내기
        .get('/curations')
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

  return <div>test</div>;
};

export default CurationReceive;
