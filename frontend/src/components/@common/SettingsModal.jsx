import React, { useRef, useEffect } from 'react';

const SettingsModal = ({ isOpen, onClose, onToggle, actions }) => {
  const menuRef = useRef();

  useEffect(() => {
    const handleClickOutside = event => {
      if (menuRef.current && !menuRef.current.contains(event.target)) {
        onClose();
      }
    };

    if (isOpen) {
      document.addEventListener('mousedown', handleClickOutside);
    }

    return () => {
      document.removeEventListener('mousedown', handleClickOutside);
    };
  }, [isOpen, onClose]);

  return (
    <div ref={menuRef} className='absolute top-2 right-2 z-20'>
      <button className='p-4' onClick={onToggle}>
        <div className='absolute top-1 right-3 z-10 cursor-pointer text-3xl'>
          &#x22EE;
        </div>
      </button>
      {isOpen && (
        <div className='absolute right-0 mt-1 w-28 bg-white border rounded-lg shadow-lg'>
          {actions.map((action, index) => (
            <button
              key={index}
              className='block px-4 py-2 text-gray-800 hover:bg-gray-200 w-full text-center'
              onClick={e => {
                e.stopPropagation();
                action.onClick();
                onClose();
              }}
            >
              {action.label}
            </button>
          ))}
        </div>
      )}
    </div>
  );
};

export default SettingsModal;
