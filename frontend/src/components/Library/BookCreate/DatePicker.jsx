import React, { useEffect, useId, useRef, useState, forwardRef } from 'react';
import { DayPicker } from 'react-day-picker';
import { format, isValid, parse } from 'date-fns';
import { ko } from 'date-fns/locale';
import Input from '@components/@common/Input';
import IconButton from '@components/@common/IconButton';
import { IoCloseSharp } from 'react-icons/io5';
import 'react-day-picker/dist/style.css';
import './DatePicker.css';

const customLocale = {
  ...ko,
  formatCaption: (date, options) => {
    const year = format(date, 'yyyy', options);
    const month = format(date, 'MM', options);
    return `${year}년 ${month}월`;
  },
};

const DatePicker = forwardRef(
  ({ onChange, startDate, endDate, ...props }, ref) => {
    const dialogRef = useRef(null);
    const dialogId = useId();
    const headerId = useId();

    const [month, setMonth] = useState(new Date());
    const [selectedDate, setSelectedDate] = useState(undefined);
    const [inputValue, setInputValue] = useState('');
    const [isDialogOpen, setIsDialogOpen] = useState(false);

    const toggleDialog = () => setIsDialogOpen(!isDialogOpen);

    useEffect(() => {
      const handleBodyScroll = isOpen => {
        document.body.style.overflow = isOpen ? 'hidden' : '';
      };

      if (!dialogRef.current) return;

      if (isDialogOpen) {
        handleBodyScroll(true);
        dialogRef.current.showModal();
      } else {
        handleBodyScroll(false);
        dialogRef.current.close();
      }

      return () => {
        handleBodyScroll(false);
      };
    }, [isDialogOpen]);

    const handleDayPickerSelect = date => {
      if (!date) {
        setInputValue('');
        setSelectedDate(undefined);
      } else {
        setSelectedDate(date);
        setInputValue(format(date, 'yyyy-MM-dd'));
      }
      dialogRef.current?.close();
      setIsDialogOpen(false);
      if (onChange) {
        onChange({ target: { value: date ? format(date, 'yyyy-MM-dd') : '' } });
      }
    };

    const handleInputChange = e => {
      setInputValue(e.target.value);
      const parsedDate = parse(e.target.value, 'yyyy-MM-dd', new Date());
      if (isValid(parsedDate)) {
        setSelectedDate(parsedDate);
        setMonth(parsedDate);
      } else {
        setSelectedDate(undefined);
      }
      if (onChange) {
        onChange(e);
      }
    };

    const handleClose = () => {
      setIsDialogOpen(false);
      dialogRef.current?.close();
    };

    return (
      <div className='relative'>
        <div className='flex items-center'>
          <Input
            {...props}
            ref={ref}
            type='text'
            value={inputValue}
            onChange={handleInputChange}
            placeholder='yyyy-MM-dd'
            disabled
          />
          <button
            className='ml-2 text-gray-500 hover:text-gray-700'
            onClick={toggleDialog}
            aria-controls='dialog'
            aria-haspopup='dialog'
            aria-expanded={isDialogOpen}
            aria-label='Open calendar to choose date'
          >
            📅
          </button>
        </div>
        <dialog
          role='dialog'
          ref={dialogRef}
          id={dialogId}
          aria-modal
          aria-labelledby={headerId}
          onClose={() => setIsDialogOpen(false)}
          className='p-4 bg-white rounded-lg shadow-xl'
        >
          <div className='flex justify-end'>
            <IconButton onClick={handleClose} icon={IoCloseSharp} />
          </div>
          <DayPicker
            captionLayout='dropdown'
            fromYear={new Date().getFullYear() - 10}
            toYear={new Date().getFullYear() + 10}
            locale={customLocale}
            month={month}
            onMonthChange={setMonth}
            initialFocus
            mode='single'
            selected={selectedDate}
            onSelect={handleDayPickerSelect}
            disabled={[{ before: startDate, after: new Date() }]}
            className='custom-day-picker'
          />
        </dialog>
      </div>
    );
  }
);

DatePicker.displayName = 'DatePicker';

export default DatePicker;
