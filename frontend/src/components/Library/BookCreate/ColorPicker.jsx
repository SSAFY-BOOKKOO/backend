import { useState } from 'react';

const ColorPicker = ({ presetColors, selected, onChange }) => {
  const [selectedColor, setSelectedColor] = useState(selected);

  const handleColorChange = color => {
    setSelectedColor(color);
    onChange(color);
  };

  return (
    <div className='grid grid-cols-6 gap-4 place-items-center'>
      {presetColors.map(color => (
        <button
          key={color}
          className={`w-8 h-8 rounded-full focus:outline-none focus:ring-gray-400 focus:ring-2 focus:ring-offset-4 ${
            color === selectedColor ? 'ring-2 ring-gray-400 ring-offset-1' : ''
          } ${color}`}
          onClick={() => handleColorChange(color)}
        />
      ))}
    </div>
  );
};

export default ColorPicker;
