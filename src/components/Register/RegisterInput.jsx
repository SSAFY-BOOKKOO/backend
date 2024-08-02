import React from 'react';

const RegisterInput = ({ labelText, error, ...props }) => {
  return (
    <div className='mb-4'>
      <label
        htmlFor={props.id}
        className='block mb-2 text-sm font-medium text-gray-700'
      >
        {labelText}
      </label>
      <input
        {...props}
        className={`shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline ${
          error ? 'border-red-500' : ''
        }`}
      />
      {error && <p className='text-red-500 text-xs italic'>{error}</p>}
    </div>
  );
};

export default RegisterInput;
