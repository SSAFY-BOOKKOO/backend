import React from 'react';
import { useAtom } from 'jotai';
import { alertAtom, hideAlertAtom } from '@atoms/alertAtom.js';
import Button from './Button';

const Alert = () => {
  const [alert] = useAtom(alertAtom);
  const [, hideAlert] = useAtom(hideAlertAtom);

  if (!alert.isOpen) return null;

  const handleConfirm = () => {
    if (alert.onConfirm) alert.onConfirm();
    hideAlert();
  };

  const handleCancel = () => {
    if (alert.onCancel) alert.onCancel();
    hideAlert();
  };

  return (
    <div className='fixed inset-0 z-50 flex items-center justify-center bg-black bg-opacity-50'>
      <div className='w-5/6 max-w-md p-6 bg-white rounded-lg shadow-lg'>
        <h1 className='mb-2 text-xl font-bold'>알림</h1>
        <p className='mb-4 text-gray-600'>{alert.message}</p>
        <div className='flex justify-end space-x-4'>
          <Button
            onClick={handleConfirm}
            className='px-3 py-2 text-white rounded-lg bg-green-400 hover:bg-green-500'
          >
            확인
          </Button>
          {!alert.confirmOnly && (
            <Button
              onClick={handleCancel}
              color='bg-gray-100 text-gray-700 hover:bg-gray-200'
              className='px-3 py-2 rounded-lg '
            >
              취소
            </Button>
          )}
        </div>
      </div>
    </div>
  );
};

export default Alert;
