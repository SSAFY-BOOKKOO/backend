import React, { useState, useEffect } from 'react';
import { useAtom } from 'jotai';
import { showAlertAtom } from '@atoms/alertAtom';
import Input from '@components/@common/Input';
import Textarea from '@components/@common/Textarea';
import Button from '@components/@common/Button';
import QuotePhotoUploader from './QuotePhotoUploader';
import RadioButton from '@components/@common/RadioButton';

const QuoteInput = ({
  addQuote,
  setShowInput,
  initialQuote,
  initialSource,
  initialBackgroundImg,
  isEdit,
  onClose,
  quoteId,
}) => {
  const [content, setContent] = useState(initialQuote || '');
  const [source, setSource] = useState(initialSource || '');
  const [backgroundImg, setBackgroundImg] = useState(null);
  const [previewImage, setPreviewImage] = useState(
    initialBackgroundImg || null
  );
  const [, showAlert] = useAtom(showAlertAtom);
  const [fontColor, setFontColor] = useState('text-black');
  const fontOptions = [
    { id: 1, value: 'text-black', name: '검정색' },
    { id: 2, value: 'text-white', name: '흰색' },
  ];

  useEffect(() => {
    setContent(initialQuote || '');
    setSource(initialSource || '');
    setPreviewImage(initialBackgroundImg || null);
  }, [initialQuote, initialSource, initialBackgroundImg]);

  const handleSubmit = e => {
    e.preventDefault();
    if (!content.trim()) {
      showAlert('글귀 내용을 입력해주세요.', true);
      return;
    }
    if (!source.trim()) {
      showAlert('출처를 입력해주세요.', true);
      return;
    }
    const quoteData = { quoteId, content, source, fontColor };
    if (backgroundImg) {
      quoteData.backgroundImg = backgroundImg;
    }
    addQuote(quoteData);
  };

  const resetForm = () => {
    setContent('');
    setSource('');
    setBackgroundImg(null);
    setPreviewImage(null);
    setShowInput(false);
  };

  const handleClose = () => {
    resetForm();
    if (onClose) onClose();
  };

  const handlePhotoSelect = (file, previewSrc) => {
    setBackgroundImg(file);
    setPreviewImage(previewSrc);
  };

  const handleFontColorChange = value => {
    setFontColor(value);
  };

  return (
    <div className='fixed inset-0 flex items-center justify-center bg-black bg-opacity-75 z-20'>
      <div className='bg-white p-6 rounded-lg shadow-lg w-11/12 max-w-md max-h-[90vh] flex flex-col'>
        <h2 className='text-xl font-bold mb-4'>나만의 글귀 입력</h2>
        <form
          onSubmit={handleSubmit}
          className='flex flex-col flex-grow overflow-auto'
        >
          <div className='space-y-2 p-1'>
            <Textarea
              placeholder='여기에 문장을 입력해주세요'
              value={content}
              onChange={e => setContent(e.target.value)}
              maxLength={200}
              customClass='h-44'
            />
            <Input
              type='text'
              placeholder='페이지 및 기타 정보 (예: p.29)'
              value={source}
              onChange={e => setSource(e.target.value)}
              customClass='mb-1'
            />
            <div>
              <label className='block text-sm font-medium text-gray-700 mb-1'>
                폰트 색상
              </label>
              <RadioButton
                tags={fontOptions}
                selectedTag={fontColor}
                setSelectedTag={handleFontColorChange}
              />
            </div>
            <QuotePhotoUploader
              onPhotoSelect={handlePhotoSelect}
              initialImage={previewImage}
            />
          </div>
          <div className='flex justify-end mt-4 pt-3'>
            <Button type='submit' className='mr-3'>
              {isEdit ? '수정' : '등록'}
            </Button>
            <Button
              onClick={handleClose}
              color='bg-gray-100 text-gray-700 hover:bg-gray-200'
            >
              취소
            </Button>
          </div>
        </form>
      </div>
    </div>
  );
};

export default QuoteInput;
