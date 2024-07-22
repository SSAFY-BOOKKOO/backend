import React from 'react';
import { Link } from 'react-router-dom';
import DecoIcon from '@assets/icons/intro_deco.png';
import WrapContainer from '../../components/Layout/WrapContainer';

const Intro = () => {
  return (
    <WrapContainer>
      <div className='h-screen flex flex-col items-center justify-center p-4 bg-white overflow-hidden'>
        <div className='text-left relative'>
          <h1 className='text-3xl font-bold mb-2 relative z-10'>
            지금 가입해서
            <br />
            나만의 서재를 꾸며보세요!
          </h1>
          <img
            src={DecoIcon}
            alt='deco'
            className='absolute top-0 right-0 -z-0 opacity-65'
          />
          <Link
            to='/login'
            className='inline-block text-black font-medium mt-3 mb-5'
          >
            꾸미러 가기 →
          </Link>
          <div className='mt-6 bg-yellow-700 p-4 rounded-xl shadow-lg w-80'>
            <div className='bg-yellow-900 h-32 w-full mb-4 rounded-xl'></div>
            <div className='bg-yellow-900 h-32 w-full mb-4 rounded-xl'></div>
            <div className='bg-yellow-900 h-32 w-full mb-4 rounded-xl'></div>
          </div>
        </div>
      </div>
    </WrapContainer>
  );
};

export default Intro;
