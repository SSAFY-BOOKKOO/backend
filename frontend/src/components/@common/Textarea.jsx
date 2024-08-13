import { forwardRef, useEffect, useRef } from 'react';

const Textarea = forwardRef(
  (
    {
      labelText,
      errorMessage,
      onChange,
      id,
      readOnly = false,
      isValid,
      isRequired,
      inputWidth = 'w-full',
      customClass,
      inputClassName,
      placeholder,
      value,
      maxLength,
      ...props
    },
    ref
  ) => {
    const textareaRef = useRef(null);

    useEffect(() => {
      if (textareaRef.current) {
        textareaRef.current.style.height = 'auto';
        textareaRef.current.style.height = `${textareaRef.current.scrollHeight}px`;
      }
    }, [value]);

    const textareaClasses = `
      ${inputWidth} px-3 py-2 bg-white border border-gray-300 
      text-gray-700 text-sm placeholder-gray-400 
      focus:outline-none focus:ring-1 focus:ring-green-200 focus:border-transparent rounded-lg resize-none overflow-auto
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
        <textarea
          id={id}
          name={id}
          onChange={onChange}
          ref={node => {
            textareaRef.current = node;
            if (ref) {
              if (typeof ref === 'function') {
                ref(node);
              } else {
                ref.current = node;
              }
            }
          }}
          readOnly={readOnly}
          required={isRequired}
          placeholder={placeholder}
          value={value}
          maxLength={maxLength}
          className={textareaClasses}
          {...props}
        />
        {!isValid && errorMessage && (
          <p className='mt-1 text-sm ml-3 text-red-600'>{errorMessage}</p>
        )}
      </div>
    );
  }
);

Textarea.displayName = 'Textarea';

export default Textarea;
