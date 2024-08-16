import React, { useState, useEffect, useRef } from 'react';
import { useSetAtom } from 'jotai';
import Button from '@components/@common/Button';
import Alert from '@components/@common/Alert';
import { alertAtom } from '@atoms/alertAtom';
import { authAxiosInstance } from '@services/axiosInstance';
import { validateForm } from '@utils/ValidateForm';

const ProfileUpdate = ({ member, categories, onSave, onCancel }) => {
  const [formData, setFormData] = useState({
    nickname: '',
    profileImgUrl: '',
    introduction: '',
    categories: [],
  });
  const [errors, setErrors] = useState({});
  const setAlert = useSetAtom(alertAtom);
  const introductionRef = useRef(null);

  useEffect(() => {
    if (member) {
      setFormData({
        nickname: member.nickName || '',
        profileImgUrl: member.profileImgUrl || '',
        introduction: member.introduction || '',
        categories: member.categories || [],
      });
    }
  }, [member]);

  useEffect(() => {
    if (introductionRef.current) {
      introductionRef.current.style.height = 'auto';
      introductionRef.current.style.height = `${introductionRef.current.scrollHeight}px`;
    }
  }, [formData.introduction]);

  const handleChange = e => {
    const { name, value, type, checked } = e.target;
    setFormData({
      ...formData,
      [name]: type === 'checkbox' ? checked : value,
    });
    setErrors(prevErrors => ({
      ...prevErrors,
      [name]: '',
    }));
  };

  const handleCategoryChange = categoryId => {
    setFormData(prevState => {
      const { categories } = prevState;
      if (categories.includes(categoryId)) {
        return {
          ...prevState,
          categories: categories.filter(c => c !== categoryId),
        };
      } else {
        return {
          ...prevState,
          categories: [...categories, categoryId],
        };
      }
    });
  };

  const handleFileChange = e => {
    const file = e.target.files[0];
    if (file) {
      if (file.size > 3 * 1024 * 1024) {
        // 3MB 제한
        setAlert({
          isOpen: true,
          confirmOnly: true,
          message: '파일 크기는 3MB를 초과할 수 없습니다.',
        });
        return;
      }
      if (file.type !== 'image/webp') {
        setFormData({
          ...formData,
          profileImgUrl: file,
        });
        setErrors(prevErrors => ({
          ...prevErrors,
          profileImgUrl: '',
        }));
      } else {
        setErrors(prevErrors => ({
          ...prevErrors,
          profileImgUrl: 'WEBP 형식의 파일은 지원되지 않습니다.',
        }));
      }
    }
  };

  const handleSubmit = async e => {
    e.preventDefault();

    const validationConfig = {
      nickname: true,
      introduction: true,
    };

    const newErrors = validateForm(formData, validationConfig);
    setErrors(newErrors);

    if (Object.keys(newErrors).length === 0) {
      try {
        const formDataToSend = new FormData();

        const blob = new Blob(
          [
            JSON.stringify({
              nickName: formData.nickname,
              categories: formData.categories,
              introduction: formData.introduction,
            }),
          ],
          {
            type: 'application/json',
          }
        );

        formDataToSend.append('requestUpdateMemberInfoDto', blob);

        if (formData.profileImgUrl && formData.profileImgUrl instanceof File) {
          formDataToSend.append('profileImg', formData.profileImgUrl);
        }

        const response = await authAxiosInstance.put(
          '/members/info',
          formDataToSend,
          {
            headers: {
              'Content-Type': 'multipart/form-data',
            }
          }
        );
        setAlert({
          isOpen: true,
          confirmOnly: true,
          message: '프로필이 성공적으로 업데이트되었습니다.',
        });

        onSave({
          nickName: formData.nickname,
          categories: formData.categories,
          introduction: formData.introduction,
          profileImgUrl: response.data.profileImgUrl,
        });

        window.location.reload();
      } catch (error) {
        console.error('Failed to update profile:', error);
        setAlert({
          isOpen: true,
          confirmOnly: true,
          message: '프로필 업데이트 중 오류가 발생했습니다.',
        });
      }
    } else {
      setAlert({
        isOpen: true,
        confirmOnly: true,
        message: '폼에 오류가 있습니다. 다시 확인해 주세요.',
      });
    }
  };

  return (
    <div className='max-w-md mx-4 mt-10 p-4 bg-white border border-gray-300 rounded-lg'>
      <h2 className='text-xl font-bold mb-4'>회원 정보 수정</h2>
      <div className='border-t border-gray-300 mb-4'></div>
      <form onSubmit={handleSubmit}>
        <div className='mb-4'>
          <label
            className='block mb-2 text-sm font-medium text-gray-700'
            htmlFor='nickname'
          >
            닉네임
          </label>
          <input
            type='text'
            id='nickname'
            name='nickname'
            value={formData.nickname}
            onChange={handleChange}
            className={`shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline ${
              errors.nickname ? 'border-red-500' : ''
            }`}
          />
          {errors.nickname && (
            <p className='text-red-500 text-xs italic'>{errors.nickname}</p>
          )}
        </div>
        <div className='mb-4'>
          <label className='block mb-2 text-sm font-medium text-gray-700'>
            프로필 이미지
          </label>
          <div className='flex items-center mt-2'>
            <input
              type='file'
              accept='image/*'
              onChange={handleFileChange}
              className='hidden'
              id='profile_img_input'
            />
            <Button
              text='찾기'
              type='button'
              color='text-white bg-green-400 active:bg-green-600'
              size='small'
              full={false}
              onClick={() =>
                document.getElementById('profile_img_input').click()
              }
            />
          </div>
          {formData.profileImgUrl && formData.profileImgUrl instanceof File && (
            <img
              src={URL.createObjectURL(formData.profileImgUrl)}
              alt='Profile Preview'
              className='mt-2 w-32 h-32 object-cover rounded-full inline-block'
            />
          )}
          {errors.profileImgUrl && (
            <p className='text-red-500 text-xs italic'>
              {errors.profileImgUrl}
            </p>
          )}
        </div>
        <div className='mb-4'>
          <label className='block mb-2 text-sm font-medium text-gray-700'>
            선호 카테고리
          </label>
          <div className='flex flex-wrap'>
            {categories.map(category => (
              <span
                key={category.id}
                className={`mr-2 mb-2 px-2 py-1 border rounded-lg cursor-pointer text-gray-700 ${
                  formData.categories.includes(category.id)
                    ? 'bg-pink-100'
                    : 'bg-gray-100'
                }`}
                onClick={() => handleCategoryChange(category.id)}
              >
                {category.name}
              </span>
            ))}
          </div>
        </div>
        <div className='mb-4'>
          <label
            className='block mb-2 text-sm font-medium text-gray-700'
            htmlFor='introduction'
          >
            소개글
          </label>
          <textarea
            id='introduction'
            name='introduction'
            value={formData.introduction}
            onChange={handleChange}
            ref={introductionRef}
            className={`shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline ${
              errors.introduction ? 'border-red-500' : ''
            }`}
            rows={1}
            style={{ resize: 'none', overflow: 'hidden' }}
          />
          {errors.introduction && (
            <p className='text-red-500 text-xs italic'>{errors.introduction}</p>
          )}
        </div>
        <div className='flex justify-end mt-14'>
          <Button
            text='저장'
            type='submit'
            color='text-white bg-green-400 active:bg-green-600 mr-4'
            size='medium'
            full={false}
          />
          <Button
            text='취소'
            type='button'
            color='text-white bg-gray-500 active:bg-gray-600'
            size='medium'
            full={false}
            onClick={onCancel}
          />
        </div>
      </form>
      <Alert />
    </div>
  );
};

export default ProfileUpdate;
