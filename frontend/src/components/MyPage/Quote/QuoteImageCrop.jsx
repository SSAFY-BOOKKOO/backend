import React, { useState, useCallback, useRef } from 'react';
import ReactCrop from 'react-image-crop';
import 'react-image-crop/dist/ReactCrop.css';
import Button from '@components/@common/Button';

const QuoteImageCrop = ({ src, onCropComplete, onCancel }) => {
  const [crop, setCrop] = useState();
  const [completedCrop, setCompletedCrop] = useState(null);
  const imgRef = useRef(null);

  const onLoad = useCallback(img => {
    imgRef.current = img;
    const aspect = 1;
    const width = img.width < img.height ? 100 : (img.height / img.width) * 100;
    const height =
      img.height < img.width ? 100 : (img.width / img.height) * 100;
    const y = (100 - height) / 2;
    const x = (100 - width) / 2;

    setCrop({
      unit: '%',
      width,
      height,
      x,
      y,
      aspect,
    });
  }, []);

  const createCroppedImage = useCallback(crop => {
    if (!imgRef.current || !crop.width || !crop.height) return null;

    const canvas = document.createElement('canvas');
    const scaleX = imgRef.current.naturalWidth / imgRef.current.width;
    const scaleY = imgRef.current.naturalHeight / imgRef.current.height;
    canvas.width = crop.width;
    canvas.height = crop.height;
    const ctx = canvas.getContext('2d');

    ctx.drawImage(
      imgRef.current,
      crop.x * scaleX,
      crop.y * scaleY,
      crop.width * scaleX,
      crop.height * scaleY,
      0,
      0,
      crop.width,
      crop.height
    );

    return new Promise(resolve => {
      canvas.toBlob(blob => {
        if (blob) {
          resolve(blob);
        }
      }, 'image/jpeg');
    });
  }, []);

  const handleCropComplete = useCallback(async () => {
    if (completedCrop) {
      const croppedImageBlob = await createCroppedImage(completedCrop);
      if (croppedImageBlob) {
        onCropComplete(croppedImageBlob);
      }
    }
  }, [completedCrop, createCroppedImage, onCropComplete]);

  return (
    <div>
      <ReactCrop
        crop={crop}
        onChange={c => setCrop(c)}
        onComplete={c => setCompletedCrop(c)}
        aspect={crop?.aspect}
      >
        <img
          ref={imgRef}
          alt='crop'
          src={src}
          onLoad={e => onLoad(e.currentTarget)}
        />
      </ReactCrop>
      <div className='flex justify-end mt-4'>
        <Button onClick={handleCropComplete} className='mr-3'>
          크롭하기
        </Button>
        <Button
          onClick={onCancel}
          color='bg-gray-100 text-gray-700 hover:bg-gray-200'
        >
          취소
        </Button>
      </div>
    </div>
  );
};

export default QuoteImageCrop;
