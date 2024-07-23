import React from 'react';

/**
 * size= "small" | "medium" | "large" (default: 'medium')
 * color= 커스텀 색상 클래스 적용 (default: 'text-white bg-blue-500 active:bg-blue-600')
 * full="full" (full 너비 필요한 경우)
 * disabled= 버튼 비활성화 상태 (default: false)
 */

const Button = ({
  text,
  color,
  size,
  onClick,
  full,
  className,
  children,
  disabled,
  ...props
}) => {
  const sizeClasses = {
    small: 'px-3 py-1 text-sm',
    medium: 'px-4 py-2 text-base',
    large: 'px-6 py-3 text-lg',
  };

  const baseClasses = 'font-medium rounded-lg  ease-in-out text-center h-fit';
  const fullClass = full ? 'w-full' : '';
  const colorClass = color || 'text-white bg-green-400 ';
  const disabledClass = disabled
    ? 'cursor-not-allowed opacity-50'
    : 'active:bg-green-500';

  const buttonClasses = `
    ${baseClasses}
    ${sizeClasses[size || 'medium']} 
    ${fullClass}
    ${colorClass}
    ${disabledClass}
    ${className || ''}
  `.trim();

  return (
    <button
      className={buttonClasses}
      onClick={disabled ? undefined : onClick}
      disabled={disabled}
      {...props}
    >
      {text || children}
    </button>
  );
};

export default Button;
