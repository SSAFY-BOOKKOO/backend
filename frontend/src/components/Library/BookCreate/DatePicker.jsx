import { forwardRef, useEffect, useId, useRef, useState } from 'react';
import { DayPicker } from 'react-day-picker';
import { format, isValid, parse } from 'date-fns';
import { ko } from 'date-fns/locale';
import 'react-day-picker/dist/style.css';
import Input from '../../@common/Input';

const DatePicker = forwardRef(({ onChange, ...props }, ref) => {
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
        <DayPicker
          locale={ko}
          month={month}
          onMonthChange={setMonth}
          initialFocus
          mode='single'
          selected={selectedDate}
          onSelect={handleDayPickerSelect}
        />
      </dialog>
    </div>
  );
});

DatePicker.displayName = 'DatePicker';

export default DatePicker;
