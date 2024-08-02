import React, { useState } from 'react';
import { useSetAtom } from 'jotai';
import Button from '@components/@common/Button';
import Alert from '@components/@common/Alert';
import { alertAtom } from '@atoms/alertAtom';
import { validateForm } from '@utils/ValidateForm';
import {
  categoriesList,
  getCategoryNumber,
  getCategoryName,
} from '@mocks/Categories';

const ProfileUpdate = ({ member, onSave, onCancel }) => {
  const [formData, setFormData] = useState(member);
  const [errors, setErrors] = useState({});
  const setAlert = useSetAtom(alertAtom);

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

  const handleCategoryChange = category => {
    setFormData(prevState => {
      const categoryNumber = getCategoryNumber(category);
      const { categories } = prevState;
      if (categories.includes(categoryNumber)) {
        return {
          ...prevState,
          categories: categories.filter(c => c !== categoryNumber),
        };
      } else {
        return {
          ...prevState,
          categories: [...categories, categoryNumber],
        };
      }
    });
  };

  const handleFileChange = e => {
    const file = e.target.files[0];
    if (file) {
      setFormData({
        ...formData,
        profileImgUrl: URL.createObjectURL(file),
      });
      setErrors(prevErrors => ({
        ...prevErrors,
        profileImgUrl: '',
      }));
    }
  };

  const handleSubmit = e => {
    e.preventDefault();

    const validationConfig = {
      email: true,
      nickname: true,
      introduction: true,
    };

    const newErrors = validateForm(formData, validationConfig);
    setErrors(newErrors);

    if (Object.keys(newErrors).length === 0) {
      onSave(formData);
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
            htmlFor='email'
          >
            이메일
          </label>
          <input
            type='email'
            id='email'
            name='email'
            value={formData.email}
            onChange={handleChange}
            className={`shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline ${
              errors.email ? 'border-red-500' : ''
            }`}
          />
          {errors.email && (
            <p className='text-red-500 text-xs italic'>{errors.email}</p>
          )}
        </div>
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
          <label
            className='block mb-2 text-sm font-medium text-gray-700'
            htmlFor='profileImgUrl'
          >
            프로필 이미지
          </label>
          <input
            type='file'
            id='profileImgUrl'
            name='profileImgUrl'
            onChange={handleFileChange}
            className='mt-1 p-2 block w-full border rounded-md'
          />
          {formData.profileImgUrl && (
            <img
              src={formData.profileImgUrl}
              alt='Profile Preview'
              className='mt-2 w-32 h-32 object-cover rounded-full inline-block'
            />
          )}
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
            className={`shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline ${
              errors.introduction ? 'border-red-500' : ''
            }`}
          ></textarea>
          {errors.introduction && (
            <p className='text-red-500 text-xs italic'>{errors.introduction}</p>
          )}
        </div>
        <div className='mb-4'>
          <label className='block mb-2 text-sm font-medium text-gray-700'>
            선호 카테고리
          </label>
          <div className='flex flex-wrap'>
            {categoriesList.map(category => (
              <span
                key={category}
                className={`mr-2 mb-2 px-2 py-1 border rounded-lg cursor-pointer text-gray-700 ${
                  formData.categories.includes(getCategoryNumber(category))
                    ? 'bg-pink-100'
                    : 'bg-gray-100'
                }`}
                onClick={() => handleCategoryChange(category)}
              >
                {category}
              </span>
            ))}
          </div>
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
