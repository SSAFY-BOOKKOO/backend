import React, { forwardRef } from 'react';

/**
 * input 컴포넌트
 * @param {string} labelText label태그 내 텍스트
 * @param {string} errorMessage error일때 텍스트
 * @param {string} type input태그 type을 지정
 * @param {string} id label의 htmlfor, input의 id, name값
 * @param {event: ChangeEvent<HTMLInputElement>} onChange
 * @param {React.RefObject<HTMLInputElement>} ref
 * @param {boolean} isValid 유효성 검사 결과값
 * @param {string} inputWidth input의 width. 기본값은 290px
 * @returns
 */

const Input = forwardRef(
  (
    {
      labelText,
      errorMessage,
      type,
      onChange,
      id,
      readOnly = false,
      isValid,
      isRequired,
      inputWidth = 'w-full max-w-[290px]',
      ...props
    },
    ref
  ) => {
    const inputClasses = `
      ${inputWidth} px-3 py-2 bg-white border border-gray-300
      text-gray-700 text-sm placeholder-gray-400 
      focus:outline-none focus:ring-1 focus:ring-blue-200 focus:border-transparent rounded-lg h-fit
      ${readOnly ? 'bg-gray-100' : ''}
      ${isValid === false ? 'border-red-500' : ''}
    `;

    return (
      <div>
        {labelText && (
          <label
            htmlFor={id}
            className='block mb-2 text-sm font-medium text-gray-700'
          >
            {labelText}
          </label>
        )}
        <input
          type={type}
          id={id}
          name={id}
          onChange={onChange}
          ref={ref}
          readOnly={readOnly}
          required={isRequired}
          className={inputClasses}
          {...props}
        />
        {!isValid && errorMessage && (
          <p className='mt-1 text-sm ml-3 text-red-600'>{errorMessage}</p>
        )}
      </div>
    );
  }
);

export default Input;
