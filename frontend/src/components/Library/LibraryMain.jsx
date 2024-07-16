import React, { useState, useRef } from 'react';

// 더미 데이터: 사용자 정보
const member = {
  nickname: 'user1',
  followers: 120,
  following: 150,
  profilePicture: 'https://via.placeholder.com/100',
};

const Library = () => {
  const dragItem = useRef(); // 드래그할 아이템의 인덱스를 저장
  const dragOverItem = useRef(); // 드랍할 위치의 아이템의 인덱스를 저장

  const [showMenu, setShowMenu] = useState(false); // 더보기 메뉴 표시 여부
  const [showModal, setShowModal] = useState(false); // 서재명 변경 모달 표시 여부
  const [showCreateModal, setShowCreateModal] = useState(false); // 서재 생성 모달 표시 여부
  const [newLibraryName, setNewLibraryName] = useState(''); // 서재명 변경 시 사용할 새로운 서재명
  const [createLibraryName, setCreateLibraryName] = useState(''); // 새로운 서재 이름
  const [activeLibrary, setActiveLibrary] = useState(0); // 현재 활성화된 서재의 인덱스
  const [libraries, setLibraries] = useState([
    {
      name: '서재 1',
      books: [
        '미움받을 용기',
        '하루의 취향',
        '죽고 싶지만 떡볶이는 먹고 싶어',
        '그릿',
        '나미야 잡화점의 기적',
        '아몬드',
        '종의 기원',
        '멋진 신세계',
        '불편한 편의점',
        '여행의 이유',
        '지적 대화를 위한 넓고 얕은 지식',
        '마법천자문',
        '달러구트 꿈 백화점',
        '봉제인형 살인사건',
        '무례한 사람에게 웃으며 대처하는 법',
        '더 해빙',
      ],
    },
    {
      name: '서재 2',
      books: ['책 1', '책 2', '책 3', '책 4', '책 5', '책 6', '책 7'],
    },
  ]);

  // 드래그 시작 시 호출되는 함수
  const dragStart = (e, position) => {
    dragItem.current = position; // 드래그할 아이템의 인덱스를 저장
  };

  // 드래그 중인 아이템이 다른 아이템 위로 이동할 때 호출되는 함수
  const dragEnter = (e, position) => {
    dragOverItem.current = position; // 드랍할 위치의 아이템의 인덱스를 저장
  };

  // 드랍 시 호출되는 함수
  const drop = e => {
    const newList = [...libraries[activeLibrary].books]; // 현재 서재의 책 목록 복사
    const dragItemValue = newList[dragItem.current]; // 드래그한 아이템의 값을 저장
    newList.splice(dragItem.current, 1); // 드래그한 아이템을 목록에서 제거
    newList.splice(dragOverItem.current, 0, dragItemValue); // 새로운 위치에 아이템 추가
    dragItem.current = null; // 드래그 아이템 인덱스 초기화
    dragOverItem.current = null; // 드랍 위치 인덱스 초기화
    setLibraries(prev => {
      const newLibraries = [...prev];
      newLibraries[activeLibrary].books = newList; // 수정된 책 목록으로 업데이트
      return newLibraries;
    });
  };

  // 서재명을 변경하는 함수
  const changeLibraryName = () => {
    if (newLibraryName.trim()) {
      setLibraries(prev => {
        const newLibraries = [...prev];
        newLibraries[activeLibrary].name = newLibraryName; // 새로운 서재명으로 업데이트
        return newLibraries;
      });
      setNewLibraryName(''); // 입력 필드 초기화
      setShowModal(false); // 모달 닫기
    }
  };

  // 서재의 모든 책을 삭제하는 함수
  const clearLibrary = () => {
    setLibraries(prev => {
      const newLibraries = [...prev];
      newLibraries[activeLibrary].books = []; // 현재 서재의 책 목록을 빈 배열로 설정
      return newLibraries;
    });
    setShowMenu(false); // 메뉴 닫기
  };

  // 새로운 서재를 생성하는 함수
  const createLibrary = () => {
    if (createLibraryName.trim()) {
      setLibraries([...libraries, { name: createLibraryName, books: [] }]); // 새로운 서재 추가
      setActiveLibrary(libraries.length); // 새 서재로 전환
      setCreateLibraryName(''); // 입력 필드 초기화
      setShowCreateModal(false); // 모달 닫기
    }
  };

  // 각 층을 렌더링하는 함수
  const renderShelf = start => {
    const books = libraries[activeLibrary].books.slice(start, start + 9); // 현재 층의 책 목록 슬라이싱
    const emptySlots = 9 - books.length; // 빈 슬롯 계산

    return (
      <div className='flex justify-center mb-4'>
        <div className='flex flex-nowrap justify-center w-full bg-gray-700 p-2 rounded-xl shadow-lg'>
          {books.map((item, idx) => (
            <div
              key={start + idx}
              className='bg-blue-500 hover:bg-blue-600 text-white m-1 p-1 w-20 h-48 sm:w-24 sm:h-64 text-center rounded-lg cursor-pointer shadow-md flex items-center justify-center'
              draggable
              onDragStart={e => dragStart(e, start + idx)}
              onDragEnter={e => dragEnter(e, start + idx)}
              onDragEnd={drop}
              onDragOver={e => e.preventDefault()}
            >
              <span className='writing-vertical'>
                {item.length > 10 ? `${item.substring(0, 10)}...` : item}
              </span>
            </div>
          ))}
          {Array.from({ length: emptySlots }).map((_, idx) => (
            <div
              key={`empty-${start + books.length + idx}`}
              className='bg-transparent m-1 p-1 w-20 h-48 sm:w-24 sm:h-64 text-center rounded-lg flex items-center justify-center'
            />
          ))}
        </div>
      </div>
    );
  };

  return (
    <>
      {/* 사용자 프로필 영역 */}
      <div className='text-center p-4 bg-blue-100 relative'>
        <div className='flex items-center justify-center flex-col sm:flex-row'>
          <img
            src={member.profilePicture}
            alt={member.nickname}
            className='rounded-full w-24 h-24 sm:w-32 sm:h-32'
          />
          <div className='mt-4 sm:mt-0 sm:ml-8 text-center sm:text-left'>
            <h1 className='text-2xl sm:text-3xl font-bold text-gray-700'>
              {member.nickname}
            </h1>
            <div className='flex justify-center sm:justify-start space-x-4 mt-2'>
              <div>
                <span className='font-bold'>{member.followers}</span> 팔로워
              </div>
              <div>
                <span className='font-bold'>{member.following}</span> 팔로잉
              </div>
            </div>
          </div>
        </div>
        <button
          className='absolute top-4 right-4 text-sm text-gray-500'
          onClick={() => setShowMenu(!showMenu)}
        >
          더보기
        </button>
        {showMenu && (
          <div className='absolute top-16 right-4 bg-blue-100 shadow-lg rounded-lg p-4 z-10'>
            <button
              className='absolute top-2 right-2 text-gray-400 hover:text-gray-600'
              onClick={() => setShowMenu(false)}
            >
              ✕
            </button>
            <button
              onClick={() => setShowModal(true)}
              className='bg-blue-500 text-white p-2 rounded-lg w-full mb-2'
            >
              서재명 변경
            </button>
            <button
              onClick={clearLibrary}
              className='bg-red-500 text-white p-2 rounded-lg w-full'
            >
              모든 책 삭제
            </button>
            <button
              onClick={() => setShowCreateModal(true)}
              className='bg-green-500 text-white p-2 rounded-lg w-full mt-2'
            >
              서재 생성
            </button>
          </div>
        )}
      </div>

      {/* 서재명 변경 모달 */}
      {showModal && (
        <div className='fixed inset-0 flex items-center justify-center bg-black bg-opacity-50'>
          <div className='bg-blue-100 p-6 rounded-lg shadow-lg'>
            <h2 className='text-xl font-bold mb-4'>서재명 변경</h2>
            <input
              type='text'
              placeholder='새 서재명'
              value={newLibraryName}
              onChange={e => setNewLibraryName(e.target.value)}
              className='border p-2 mb-4 w-full'
            />
            <div className='flex justify-end'>
              <button
                onClick={changeLibraryName}
                className='bg-blue-500 text-white p-2 rounded-lg mr-2'
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
      )}

      {/* 서재 생성 모달 */}
      {showCreateModal && (
        <div className='fixed inset-0 flex items-center justify-center bg-black bg-opacity-50'>
          <div className='bg-blue-100 p-6 rounded-lg shadow-lg'>
            <h2 className='text-xl font-bold mb-4'>새 서재 생성</h2>
            <input
              type='text'
              placeholder='서재 이름'
              value={createLibraryName}
              onChange={e => setCreateLibraryName(e.target.value)}
              className='border p-2 mb-4 w-full'
            />
            <div className='flex justify-end'>
              <button
                onClick={createLibrary}
                className='bg-green-500 text-white p-2 rounded-lg mr-2'
              >
                확인
              </button>
              <button
                onClick={() => setShowCreateModal(false)}
                className='bg-gray-500 text-white p-2 rounded-lg'
              >
                취소
              </button>
            </div>
          </div>
        </div>
      )}

      {/* 서재 전환 버튼 */}
      <div className='flex justify-center bg-blue-100 p-4'>
        {libraries.map((library, index) => (
          <button
            key={index}
            onClick={() => setActiveLibrary(index)}
            className={`p-2 m-2 ${
              activeLibrary === index
                ? 'bg-blue-500 text-white'
                : 'bg-gray-300 text-black'
            } rounded-lg`}
          >
            {library.name}
          </button>
        ))}
      </div>

      {/* 현재 서재명 표시 */}
      <div className='text-center p-4 bg-blue-100'>
        <h2 className='text-xl sm:text-2xl font-bold text-gray-700'>
          {libraries[activeLibrary].name}
        </h2>
      </div>

      {/* 책장 렌더링 */}
      <div className='p-4 min-h-screen flex flex-col items-center bg-blue-100'>
        <div className='p-2 bg-gray-700 rounded-xl shadow-lg w-full max-w-full overflow-x-auto'>
          {renderShelf(0)} {/* 1층 */}
          {renderShelf(9)} {/* 2층 */}
          {renderShelf(18)} {/* 3층 */}
        </div>
      </div>
    </>
  );
};

export default Library;
