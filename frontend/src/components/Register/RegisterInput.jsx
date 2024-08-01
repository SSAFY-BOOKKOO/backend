import React from 'react';

const RegisterInput = ({
  labelText,
  type,
  id,
  name,
  value,
  onChange,
  required,
  error,
}) => {
  return (
    <div className='mb-4'>
      <label htmlFor={id} className='block text-gray-700 font-medium'>
        {labelText}
      </label>
      {type === 'textarea' ? (
        <textarea
          id={id}
          name={name}
          value={value}
          onChange={onChange}
          required={required}
          className={`mt-1 p-2 block w-full border rounded-md ${
            error ? 'border-red-500' : ''
          }`}
        />
      ) : (
        <input
          type={type}
          id={id}
          name={name}
          value={value}
          onChange={onChange}
          required={required}
          className={`mt-1 p-2 block w-full border rounded-md ${
            error ? 'border-red-500' : ''
          }`}
        />
      )}
      {error && <p className='text-red-500 text-xs italic'>{error}</p>}
    </div>
  );
};

export default RegisterInput;
