import React, { useRef, useEffect, useState } from 'react';
import Spinner from '@components/@common/Spinner';
import QuoteImageCrop from './QuoteImageCrop';
import { postQuoteOcr } from '@services/Member';
import { useAtom } from 'jotai';
import { showAlertAtom } from '@atoms/alertAtom';
import Alert from '@components/@common/Alert';

const QuoteCreateModal = ({ toggleModal, setShowInput, onImageUpload }) => {
  const modalRef = useRef();
  const imageInput = useRef();
  const [isLoading, setIsLoading] = useState(false);
  const [imageSrc, setImageSrc] = useState(null); // 이미지 소스 상태 추가
  const [, showAlert] = useAtom(showAlertAtom);

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

  const onChangeFile = e => {
    const file = e.target.files[0];
    if (file) {
      const reader = new FileReader();
      reader.onload = () => {
        setImageSrc(reader.result); // 이미지 소스 설정
      };
      reader.readAsDataURL(file);
    }
  };
  const handleImageCrop = async croppedImageBlob => {
    if (croppedImageBlob) {
      setIsLoading(true);
      try {
        const extractedText = await postQuoteOcr(croppedImageBlob);
        onImageUpload(extractedText);
      } catch (error) {
        showAlert(error?.response?.data, true, () => {
          setImageSrc(null);
        });
      } finally {
        setIsLoading(false);
        toggleModal();
      }
    }
  };

  return (
    <div className='fixed inset-0 bg-black bg-opacity-50 z-50 flex items-end justify-center'>
      <Alert />
      {isLoading && <Spinner />}
      <div
        ref={modalRef}
        className='bg-white w-full max-w-md rounded-t-3xl p-6 shadow-lg'
      >
        {!imageSrc ? (
          <>
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
              이미지로 텍스트 등록
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
          </>
        ) : (
          <QuoteImageCrop
            src={imageSrc}
            onCropComplete={handleImageCrop}
            onCancel={() => {
              setImageSrc(null);
            }}
          />
        )}
      </div>
    </div>
  );
};

export default QuoteCreateModal;
