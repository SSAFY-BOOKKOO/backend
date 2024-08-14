import React, { useState, useEffect } from 'react';
import { useParams, useLocation, useNavigate } from 'react-router-dom';
import { useAtom } from 'jotai';
import useModal from '@hooks/useModal';
import SettingsModal from '@components/@common/SettingsModal';
import { authAxiosInstance } from '@services/axiosInstance';
import { showAlertAtom } from '@atoms/alertAtom';
import Alert from '@components/@common/Alert';

const CurationLetterDetail = () => {
  const { id } = useParams();
  const location = useLocation();
  const navigate = useNavigate();
  const { isOpen, closeModal, toggleModal } = useModal();
  const [nickName, setNickName] = useState('');
  const [letter, setLetter] = useState({});
  const [, showAlert] = useAtom(showAlertAtom);
  const modalVisible = location.state?.modalVisible ?? true; // 기본값은 true로 설정

  useEffect(() => {
    const fetchMemberInfo = async () => {
      try {
        const res = await authAxiosInstance.get('/members/info');
        setNickName(res.data.nickName);
      } catch (err) {
        console.error('Failed to fetch member info:', err);
      }
    };

    fetchMemberInfo();
  }, []);

  useEffect(() => {
    const fetchLetterDetail = async () => {
      try {
        const res = await authAxiosInstance.get(`/curations/detail/${id}`);
        setLetter(res.data);
      } catch (err) {
        console.error('Failed to fetch letter detail:', err);
      }
    };

    fetchLetterDetail();
  }, [id]);

  // 레터 보관/보관 해제
  const handleLetterStoreToggle = async () => {
    try {
      await authAxiosInstance.post(`/curations/store/${id}`, {});
      const newStoredStatus = !letter.isStored;
      setLetter(prevLetter => ({ ...prevLetter, isStored: newStoredStatus }));
      showAlert(
        newStoredStatus
          ? '레터가 성공적으로 보관되었습니다!'
          : '레터 보관이 성공적으로 해제되었습니다!',
        true,
        () => {
          navigate('/curation/receive');
        }
      );
    } catch (err) {
      console.error('Failed to store/unstore letter:', err);
      showAlert(
        letter.isStored
          ? '레터 보관 해제에 실패했습니다. 다시 시도해 주세요.'
          : '레터 보관에 실패했습니다. 다시 시도해 주세요.',
        true
      );
    }
  };

  // 레터 삭제
  const handleLetterDelete = async () => {
    try {
      await authAxiosInstance.delete(`/curations/${id}`, {});
      showAlert('레터가 성공적으로 삭제되었습니다!', true, () => {
        navigate('/curation/receive');
      });
    } catch (err) {
      console.error('Failed to delete letter:', err);
      showAlert('레터 삭제에 실패했습니다. 다시 시도해 주세요.', true);
    }
  };

  const actions = [
    {
      label: letter.isStored ? '보관 해제' : '레터 보관',
      onClick: handleLetterStoreToggle,
    },
    { label: '레터 삭제', onClick: handleLetterDelete },
  ];

  return (
    <div className='flex flex-col items-center justify-start p-4 scrollbar-none'>
      <Alert />
      <div className='relative bg-white rounded-lg shadow-lg w-full max-w-md mx-auto mt-32 scrollbar-none'>
        <div className='absolute -top-28 w-full flex justify-center z-20'>
          <img
            src={letter.coverImgUrl}
            alt={letter.title}
            className='w-48 h-64 rounded-md shadow-lg z-10'
          />
        </div>
        {/* 설정 모달 */}
        <div className='relative flex flex-col items-center p-6 pt-32 z-30'>
          {nickName !== letter.writer && modalVisible && (
            <SettingsModal
              isOpen={isOpen}
              onClose={closeModal}
              onToggle={toggleModal}
              actions={actions}
            />
          )}
        </div>
        <div className='min-h-44 px-6 py-8 text-center scrollbar-none'>
          <h2 className='text-xl font-bold mb-2'>{letter.title}</h2>
          <div className='text-gray-600 mb-4 scrollbar-none'>
            {letter.content}
          </div>
        </div>
        <div className='bg-green-400 px-6 py-3 rounded-b-lg flex justify-between text-sm text-gray-700'>
          <span>{new Date(letter.createdAt).toLocaleDateString()}</span>
          <span>FROM: {letter.writer}</span>
        </div>
      </div>
    </div>
  );
};

export default CurationLetterDetail;
