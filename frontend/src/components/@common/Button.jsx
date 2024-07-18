import React from 'react';

/**
 * size= "small" | "medium" | "large" (default: 'medium')
 * color= 커스텀 색상 클래스 적용 (default: 'text-white bg-blue-500 active:bg-blue-600')
 * full="full" (full 너비 필요한 경우)
 */

const Button = ({
  children,
  color,
  size,
  onClick,
  full,
  className,
  ...props
}) => {
  const sizeClasses = {
    small: 'px-3 py-1 text-sm',
    medium: 'px-4 py-2 text-base',
    large: 'px-6 py-3 text-lg',
  };

  const baseClasses =
    'font-medium rounded-lg transition-all duration-150 ease-in-out text-center h-fit';
  const fullClass = full ? 'w-full' : '';
  const colorClass = color || 'text-white bg-blue-500 active:bg-blue-600';

  const buttonClasses = `
    ${baseClasses}
    ${sizeClasses[size || 'medium']} 
    ${fullClass}
    ${colorClass}
    ${className || ''}
  `.trim();

  return (
    <button className={buttonClasses} onClick={onClick} {...props}>
      {children}
    </button>
  );
};

export default Button;
