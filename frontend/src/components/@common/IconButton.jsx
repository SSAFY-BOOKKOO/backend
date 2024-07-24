import React from 'react';

const IconButton = ({
  icon: Icon,
  onClick,
  type = 'button',
  className = '',
  iconClassName = 'w-6 h-6',
  ...props
}) => {
  return (
    <button
      type={type}
      onClick={onClick}
      className={`p-2 ${className}`}
      {...props}
    >
      <Icon className={`${iconClassName}`} />
    </button>
  );
};

export default IconButton;
