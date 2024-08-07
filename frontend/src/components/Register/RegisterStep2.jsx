import React from 'react';
import RegisterInput from './RegisterInput';
import Button from '../@common/Button';

const RegisterStep2 = ({
  formData,
  errors,
  handleChange,
  handleCategoryChange,
  categoriesList,
  handlePrevStep,
  handleNextStep,
}) => {
  return (
    <>
      <h3 className='text-xl font-bold mb-4 text-center'>
        추가 정보를 알려주세요.
      </h3>
      <RegisterInput
        labelText='연령'
        type='number'
        id='year'
        name='year'
        value={formData.year}
        onChange={handleChange}
        required
        error={errors.year}
      />
      <label className='block mb-2 text-sm font-medium text-gray-700'>
        성별
        <div className='flex space-x-4 mt-2 justify-center'>
          <label className='flex items-center'>
            <input
              type='radio'
              id='gender-male'
              name='gender'
              value='MALE'
              checked={formData.gender === 'MALE'}
              onChange={handleChange}
            />
            <span className='ml-2'>남성</span>
          </label>
          <label className='flex items-center'>
            <input
              type='radio'
              id='gender-female'
              name='gender'
              value='FEMALE'
              checked={formData.gender === 'FEMALE'}
              onChange={handleChange}
            />
            <span className='ml-2'>여성</span>
          </label>
          <label className='flex items-center'>
            <input
              type='radio'
              id='gender-none'
              name='gender'
              value='NONE'
              checked={formData.gender === 'NONE'}
              onChange={handleChange}
            />
            <span className='ml-2'>선택 안함</span>
          </label>
        </div>
        {errors.gender && (
          <p className='text-red-500 text-sm'>{errors.gender}</p>
        )}
      </label>
      <RegisterInput
        labelText='소개글'
        type='textarea'
        id='introduction'
        name='introduction'
        value={formData.introduction}
        onChange={handleChange}
        required
        error={errors.introduction}
      />
      <label className='block mb-2 text-sm font-medium text-gray-700'>
        선호 카테고리
        <div className='flex flex-wrap mt-2 justify-center'>
          {categoriesList.map(category => (
            <div key={category.id} className='mr-2 mb-2'>
              <label
                className={`flex items-center border px-2 py-1 rounded-lg cursor-pointer ${
                  formData.categories.includes(category.id)
                    ? 'bg-green-400 text-white'
                    : 'bg-gray-200 text-gray-700'
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
          size='large'
          full={false}
          onClick={handlePrevStep}
        />
        <Button
          text='다음'
          type='button'
          color='text-white bg-green-400 active:bg-green-600'
          size='large'
          full={false}
          onClick={handleNextStep}
        />
      </div>
    </>
  );
};

export default RegisterStep2;
