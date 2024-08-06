import React, { useState, useEffect } from 'react';
import { DndProvider } from 'react-dnd';
import { MultiBackend, TouchTransition } from 'react-dnd-multi-backend';
import { HTML5Backend } from 'react-dnd-html5-backend';
import { TouchBackend } from 'react-dnd-touch-backend';
import { useDrag, useDrop } from 'react-dnd';
import mailBoxEmptyImg from '@assets/icons/mailbox_empty.png';
import mailBoxFullImg from '@assets/icons/mailbox_full.png';
import letterImg from '@assets/icons/letter.png';
import { axiosInstance } from '../../services/axiosInstance';

const ItemTypes = {
  LETTER: 'letter',
};

// const [sendLetters, setSendLetters] = useState([]);

// useEffect(
//   () => {
//     axiosInstance
//       // axios로 get요청 보내기
//       .get('/curations/mycuration')
//       // 요청 성공하면 받아와서 letters에 할당
//       .then(res => {
//         setSendLetters(res.data);
//         console.log(res);
//       })

//       // 요청 실패하면 오류 일단 console에
//       .catch(err => {
//         console.log(err);
//       });
//   },
//   // 화면에 처음 렌더링될 때만 실행
//   []
// );

const Letter = () => {
  const [{ isDragging }, dragRef] = useDrag({
    type: ItemTypes.LETTER,
    item: {},
    collect: monitor => ({
      isDragging: monitor.isDragging(),
    }),
  });

  return (
    <img
      src={letterImg}
      ref={dragRef}
      className={`w-36 ${isDragging ? 'opacity-90' : 'opacity-100'} cursor-move`}
    />
  );
};

const MailBox = ({ onDrop, isFull }) => {
  const [, dropRef] = useDrop({
    accept: ItemTypes.LETTER,
    drop: () => onDrop(),
  });

  return (
    <div ref={dropRef} className='flex justify-start'>
      <img src={isFull ? mailBoxFullImg : mailBoxEmptyImg} className='w-72' />
    </div>
  );
};

const CurationLetterSend = () => {
  const [isFull, setIsFull] = useState(false);
  const [isLetterVisible, setIsLetterVisible] = useState(true);

  const handleDrop = () => {
    if (!isFull) {
      setIsFull(true);
      setIsLetterVisible(false);
      alert('레터가 전송되었습니다!');
    }
  };

  const HTML5toTouch = {
    backends: [
      {
        id: 'html5',
        backend: HTML5Backend,
      },
      {
        id: 'touch',
        backend: TouchBackend,
        options: { enableMouseEvents: true },
        preview: true,
        transition: TouchTransition,
      },
    ],
  };

  return (
    <DndProvider backend={MultiBackend} options={HTML5toTouch}>
      <div className='min-h-[calc(100vh-121px)] flex flex-col justify-between'>
        <MailBox onDrop={handleDrop} isFull={isFull} />
        <div className='flex justify-center items-end mb-20'>
          {isLetterVisible && <Letter onDropComplete={handleDrop} />}
        </div>
      </div>
    </DndProvider>
  );
};

export default CurationLetterSend;
