import React from 'react';
import RegisterInput from './RegisterInput';
import Button from '@components/@common/Button';
import { useSetAtom } from 'jotai';
import { alertAtom } from '@atoms/alertAtom';
import RadioButton from '@components/@common/RadioButton';

const RegisterStep2 = ({
  formData,
  errors,
  handleChange,
  handleCategoryChange,
  categoriesList,
  handlePrevStep,
  handleNextStep,
}) => {
  const setAlert = useSetAtom(alertAtom);

  const handleYearChange = e => {
    const { value } = e.target;
    if (value <= 0) {
      setAlert({
        isOpen: true,
        confirmOnly: true,
        message: '출생연도를 0 이하로 설정할 수 없습니다.',
      });
      return;
    }
    handleChange(e);
  };

  const genderTags = [
    { id: 1, value: 'MALE', name: '남성' },
    { id: 2, value: 'FEMALE', name: '여성' },
    { id: 3, value: 'NONE', name: '선택 안함' },
  ];

  return (
    <>
      <div className='text-center mb-4'>
        <h3 className='text-xl font-bold text-left'>
          추가 정보를 입력해주세요
        </h3>
        <p className='text-gray-500 text-left'>
          연령, 성별, 취향을 토대로 책을 추천해드립니다.
        </p>
      </div>
      <label className='block mb-2 text-md font-medium text-gray-700'>
        출생 연도
        <RegisterInput
          type='number'
          id='year'
          name='year'
          value={formData.year}
          onChange={handleYearChange}
          required
          error={errors.year}
        />
      </label>
      <label className='block mb-2 text-md font-medium text-gray-700'>
        성별
        <RadioButton
          tags={genderTags}
          selectedTag={formData.gender}
          setSelectedTag={value =>
            handleChange({ target: { name: 'gender', value } })
          }
        />
        {errors.gender && (
          <p className='text-red-500 text-sm'>{errors.gender}</p>
        )}
      </label>
      <label className='block mb-2 text-md font-medium text-gray-700'>
        선호 카테고리
        <div className='flex flex-wrap mt-2 justify-center'>
          {categoriesList.map(category => (
            <div key={category.id} className='mr-2 mb-2'>
              <label
                className={`flex items-center border px-2 py-1 rounded-lg cursor-pointer ${
                  formData.categories.includes(category.id)
                    ? 'bg-pink-500 text-white'
                    : 'bg-gray-400 text-white'
                }`}
              >
                <input
                  type='checkbox'
                  name='categories'
                  value={category.id}
                  checked={formData.categories.includes(category.id)}
                  onChange={() => handleCategoryChange(category)}
                  className='hidden'
                />
                <span>{category.name}</span>
              </label>
            </div>
          ))}
        </div>
      </label>
      <div className='flex justify-between mt-6'>
        <Button
          text='이전'
          type='button'
          color='text-white bg-gray-500 active:bg-gray-600'
          size='medium'
          full={false}
          onClick={handlePrevStep}
        />
        <Button
          text='다음'
          type='button'
          color='text-white bg-green-400 active:bg-green-600'
          size='medium'
          full={false}
          onClick={handleNextStep}
        />
      </div>
    </>
  );
};

export default RegisterStep2;
