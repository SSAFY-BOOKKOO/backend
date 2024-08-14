import React, { useRef, useState } from 'react';
import { useAtom } from 'jotai';
import { showAlertAtom } from '@atoms/alertAtom';
import Button from '@components/@common/Button';

const QuotePhotoUploader = ({ onPhotoSelect, initialImage }) => {
  const [imageSrc, setImageSrc] = useState(initialImage || null);
  const imageInput = useRef();
  const [, showAlert] = useAtom(showAlertAtom);

  const onClickImageUpload = () => {
    imageInput.current.click();
  };

  const onChangeFile = e => {
    const file = e.target?.files[0];
    if (file) {
      if (file.size > 3 * 1024 * 1024) {
        // 3MB 제한
        showAlert('파일 크기는 3MB를 초과할 수 없습니다.', true, () => {});
      }

      const reader = new FileReader();
      reader.onloadend = () => {
        const base64data = reader.result;
        setImageSrc(base64data);
        onPhotoSelect(file, base64data);
      };
      reader.readAsDataURL(file);
    }
  };

  const onDeleteImage = () => {
    setImageSrc(null);
    onPhotoSelect(null, null);
  };

  return (
    <div>
      <input
        type='file'
        accept='image/jpg, image/png, image/jpeg'
        className='hidden'
        ref={imageInput}
        onChange={onChangeFile}
      />
      {!imageSrc ? (
        <Button onClick={onClickImageUpload} size='small' type='button'>
          배경 이미지 선택
        </Button>
      ) : (
        <div className='relative'>
          <img
            src={imageSrc}
            alt='선택된 배경'
            className='w-full h-auto max-h-48 object-contain rounded-lg'
          />
          <Button
            onClick={onDeleteImage}
            type='button'
            size='small'
            className='absolute top-2 right-2 bg-pink-500 hover:bg-pink-600 focus:bg-pink-600 active:bg-pink-700 text-white rounded-full p-1'
          >
            삭제
          </Button>
        </div>
      )}
    </div>
  );
};

export default QuotePhotoUploader;
