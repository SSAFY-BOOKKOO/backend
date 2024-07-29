import React from 'react';
import RegisterInput from './RegisterInput';
import Button from '../@common/Button';

const RegisterStep2 = ({
  formData,
  errors,
  handleChange,
  handleCategoryChange,
  handlePrevStep,
  handleSubmit,
}) => {
  return (
    <>
      <h3 className='text-xl font-bold mb-4 text-center'>
        추가 정보를 알려주세요.
      </h3>
      <RegisterInput
        labelText='연령'
        type='number'
        id='age'
        name='age'
        value={formData.age}
        onChange={handleChange}
        required
        error={errors.age}
      />
      <label className='block mb-2 text-sm font-medium text-gray-700'>
        성별
        <div className='flex space-x-4 mt-2 justify-center'>
          <label className='flex items-center'>
            <input
              type='radio'
              id='gender-male'
              name='gender'
              value='male'
              checked={formData.gender === 'male'}
              onChange={handleChange}
            />
            <span className='ml-2'>남성</span>
          </label>
          <label className='flex items-center'>
            <input
              type='radio'
              id='gender-female'
              name='gender'
              value='female'
              checked={formData.gender === 'female'}
              onChange={handleChange}
            />
            <span className='ml-2'>여성</span>
          </label>
          <label className='flex items-center'>
            <input
              type='radio'
              id='gender-none'
              name='gender'
              value='none'
              checked={formData.gender === 'none'}
              onChange={handleChange}
            />
            <span className='ml-2'>선택 안함</span>
          </label>
        </div>
        {errors.gender && (
          <p className='text-red-500 text-sm'>{errors.gender}</p>
        )}
      </label>
      <label className='block mb-2 text-sm font-medium text-gray-700'>
        선호 카테고리
        <div className='flex flex-wrap mt-2 justify-center'>
          {[
            '추리/스릴러',
            '로맨스',
            '인문학',
            '철학',
            '경제/경영',
            '역사',
            '시',
            '에세이',
            '소설',
            '과학',
            '사회과학',
            '자기계발',
            '기타',
          ].map(category => (
            <div key={category} className='mr-2 mb-2'>
              <label className='flex items-center border px-2 py-1 rounded-lg cursor-pointer'>
                <input
                  type='checkbox'
                  name='categories'
                  value={category}
                  checked={formData.categories.includes(category)}
                  onChange={() => handleCategoryChange(category)}
                  className='hidden'
                />
                <span
                  className={`ml-2 ${
                    formData.categories.includes(category)
                      ? 'text-green-500'
                      : 'text-gray-700'
                  }`}
                >
                  {category}
                </span>
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
          text='가입 완료'
          type='submit'
          color='text-white bg-green-400 active:bg-green-600'
          size='large'
          full={false}
          onClick={handleSubmit}
        />
      </div>
    </>
  );
};

export default RegisterStep2;
