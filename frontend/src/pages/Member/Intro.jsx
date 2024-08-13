import React from 'react';
import { Link } from 'react-router-dom';
import DecoIcon from '@assets/icons/intro_deco.png';
import WrapContainer from '@components/Layout/WrapContainer';
import LibraryImg from '@assets/images/intro_library.png';

const Intro = () => {
  return (
    <WrapContainer className='flex items-center justify-center min-h-screen '>
      <div className='flex flex-col items-center justify-center p-3 bg-white mx-3'>
        <div className='text-left relative w-full'>
          <h1 className='text-3xl font-bold mb-4 relative z-10'>
            지금 가입해서
            <br />
            나만의 서재를 꾸며보세요!
          </h1>
          <img
            src={DecoIcon}
            alt='deco'
            className='absolute top-0 right-0 -z-0 opacity-65 w-24'
          />
          <Link
            to='/login'
            className='inline-block text-black font-medium mt-4 mb-6 hover:underline'
          >
            꾸미러 가기 →
          </Link>
          <div className='mt-4'>
            <img src={LibraryImg} alt='library' className='w-full' />
          </div>
        </div>
      </div>
    </WrapContainer>
  );
};

export default Intro;
