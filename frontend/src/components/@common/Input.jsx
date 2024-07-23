import { forwardRef } from 'react';

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
      inputWidth = 'w-full',
      customClass,
      inputClassName,
      ...props
    },
    ref
  ) => {
    const inputClasses = `
      ${inputWidth} px-3 py-2 bg-white border border-gray-300
      text-gray-700 text-sm placeholder-gray-400 
      focus:outline-none focus:ring-1 focus:ring-green-200 focus:border-transparent rounded-lg h-fit 
      ${readOnly ? 'bg-gray-100' : ''}
      ${isValid === false ? 'border-red-500' : ''} ${customClass}
    `;

    return (
      <div className={inputClassName}>
        {labelText && (
          <label
            htmlFor={id}
            className='block mb-2 text-base font-medium text-gray-700'
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

Input.displayName = 'Input';

export default Input;
