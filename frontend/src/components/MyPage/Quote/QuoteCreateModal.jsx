import React, { useRef, useEffect, useState } from 'react';
import { postQuoteOcr } from '@services/Member'; // Assume this is the correct import path

const QuoteCreateModal = ({ toggleModal, setShowInput, onImageUpload }) => {
  const modalRef = useRef();
  const imageInput = useRef();
  const [isLoading, setIsLoading] = useState(false);

  const handleClickOutside = event => {
    if (modalRef.current && !modalRef.current.contains(event.target)) {
      toggleModal();
    }
  };

  useEffect(() => {
    document.addEventListener('mousedown', handleClickOutside);
    return () => {
      document.removeEventListener('mousedown', handleClickOutside);
    };
  }, []);

  const onClickImageUpload = () => {
    imageInput.current.click();
  };

  const onChangeFile = async e => {
    const imageFile = e.target?.files[0];
    if (imageFile) {
      setIsLoading(true);
      try {
        const extractedText = await postQuoteOcr(imageFile);
        onImageUpload(extractedText);
        toggleModal();
      } catch (error) {
        console.error('Error extracting text:', error);
      } finally {
        setIsLoading(false);
      }
    }
  };

  return (
    <div className='fixed inset-0 bg-black bg-opacity-50 z-50 flex items-end justify-center'>
      <div
        ref={modalRef}
        className='bg-white w-full max-w-md rounded-t-3xl p-6 shadow-lg'
      >
        <input
          type='file'
          accept='image/jpg, image/png, image/jpeg'
          style={{ display: 'none' }}
          ref={imageInput}
          onChange={onChangeFile}
        />
        <button
          onClick={onClickImageUpload}
          className='w-full bg-pink-500 text-white text-center py-3 px-4 mb-4 rounded-lg flex items-center justify-center'
          disabled={isLoading}
        >
          {isLoading ? '처리 중...' : '이미지로 텍스트 등록'}
        </button>
        <button
          onClick={() => {
            setShowInput(true);
            toggleModal();
          }}
          className='w-full bg-green-400 text-white text-center py-3 px-4 mb-2 rounded-lg'
        >
          입력으로 텍스트 등록
        </button>
      </div>
    </div>
  );
};

export default QuoteCreateModal;
