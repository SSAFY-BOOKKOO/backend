import { useEffect, useState } from 'react';
import { GoStarFill } from 'react-icons/go';

const StarRating = ({ selected, onChange }) => {
  const [rating, setRating] = useState(selected);

  useEffect(() => {
    setRating(selected);
  }, [selected]);

  const handleClick = value => {
    const newRating = value === rating ? 0 : value;

    setRating(newRating);
    onChange(newRating);
  };

  return (
    <div>
      <div className='flex flex-row mb-3'>
        {[1, 2, 3, 4, 5].map(index => (
          <GoStarFill
            className={`cursor-pointer ${index <= rating ? 'text-yellow-500' : 'text-gray-300'}`}
            key={index}
            size={32}
            onClick={() => handleClick(index)}
          />
        ))}
      </div>
    </div>
  );
};

export default StarRating;
