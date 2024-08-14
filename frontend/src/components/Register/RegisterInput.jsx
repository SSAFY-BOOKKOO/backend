import React from 'react';
import Input from '@components/@common/Input';
import Textarea from '@components/@common/Textarea';

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
  const commonProps = {
    labelText,
    id,
    name,
    onChange,
    isRequired: required,
    isValid: !error,
    errorMessage: error,
    value,
  };

  return (
    <div className='mb-4'>
      {type === 'textarea' ? (
        <Textarea
          {...commonProps}
          inputWidth='w-full'
          customClass={`${error ? 'border-red-500' : ''} max-h-36`}
        />
      ) : (
        <Input
          {...commonProps}
          type={type}
          inputWidth='w-full'
          customClass={`${error ? 'border-red-500' : ''}`}
        />
      )}
    </div>
  );
};

export default RegisterInput;
