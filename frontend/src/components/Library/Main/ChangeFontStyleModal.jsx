import React, { useState, useEffect } from 'react';

const ChangeFontStyleModal = ({
  showModal,
  fontName,
  setFontName,
  fontSize,
  setFontSize,
  changeFontStyle,
  setShowModal,
}) => {
  const [localFontName, setLocalFontName] = useState(fontName || '');
  const [localFontSize, setLocalFontSize] = useState(fontSize || '0');

  useEffect(() => {
    if (!showModal) {
      setLocalFontName('');
      setLocalFontSize('0');
    }
  }, [showModal]);

  const handleSave = () => {
    changeFontStyle(localFontName, localFontSize);
    setShowModal(false);
  };

  if (!showModal) return null;

  return (
    <div className='fixed inset-0 flex items-center justify-center bg-black bg-opacity-75 z-20'>
      <div className='bg-pink-100 p-6 rounded-lg shadow-lg w-80 max-w-full'>
        <h2 className='text-xl font-bold mb-4'>폰트 스타일 변경</h2>
        <div className='mb-4'>
          <label className='block mb-2 font-bold'>폰트 이름</label>
          <select
            value={localFontName}
            onChange={e => setLocalFontName(e.target.value)}
            className='border p-2 w-full'
            style={{ fontFamily: localFontName }}
          >
            <option value='' style={{ fontFamily: 'Pretendard' }}>
              기본 폰트
            </option>
            <option
              value='BukkeukkeuMyeongjo'
              style={{ fontFamily: 'BukkeukkeuMyeongjo' }}
            >
              부끄끄 명조체
            </option>
            <option value='Eulyoo1945' style={{ fontFamily: 'Eulyoo1945' }}>
              을유1945체
            </option>
            <option
              value='LaundryGothic'
              style={{ fontFamily: 'LaundryGothic' }}
            >
              런드리 고딕체
            </option>
            <option value='LeeSeoYoon' style={{ fontFamily: 'LeeSeoYoon' }}>
              이서윤체
            </option>
            <option
              value='PyeongtaekSemiconductor'
              style={{ fontFamily: 'PyeongtaekSemiconductor' }}
            >
              평택 반도체체
            </option>
            <option value='SFHambaknoon' style={{ fontFamily: 'SFHambaknoon' }}>
              SF함박눈체
            </option>
            <option
              value='HakgyoansimBombanghak'
              style={{ fontFamily: 'HakgyoansimBombanghak' }}
            >
              학교안심 봄방학체
            </option>
            <option
              value='UhBeeBanynany'
              style={{ fontFamily: 'UhBeeBanynany' }}
            >
              어비 바니나니체
            </option>
            <option
              value='GyeonggiCheonnyeon'
              style={{ fontFamily: 'GyeonggiCheonnyeon' }}
            >
              경기천년체
            </option>
            <option value='Inklipquid' style={{ fontFamily: 'Inklipquid' }}>
              잉크립퀴드체
            </option>
            <option value='JejuDoldam' style={{ fontFamily: 'JejuDoldam' }}>
              제주돌담체
            </option>
            <option value='Kdg' style={{ fontFamily: 'Kdg' }}>
              솔뫼 김대건체
            </option>
          </select>
        </div>
        <div className='mb-4'>
          <label className='block mb-2 font-bold'>폰트 크기</label>
          <select
            value={localFontSize}
            onChange={e => setLocalFontSize(e.target.value)}
            className='border p-2 w-full'
          >
            <option value='0'>작게</option>
            <option value='1'>보통</option>
            <option value='2'>크게</option>
            <option value='3'>아주 크게</option>
          </select>
        </div>
        <div className='flex justify-end'>
          <button
            onClick={handleSave}
            className='bg-green-400 text-white p-2 rounded-lg mr-2'
          >
            확인
          </button>
          <button
            onClick={() => setShowModal(false)}
            className='bg-gray-500 text-white p-2 rounded-lg'
          >
            취소
          </button>
        </div>
      </div>
    </div>
  );
};

export default ChangeFontStyleModal;
