import { useCallback, useState } from 'react';

const useInput = initialValue => {
  const [input, setInput] = useState(initialValue);

  const onChange = useCallback(e => {
    const { id, value } = e.target;
    setInput(input => ({ ...input, [id]: value }));
  }, []);

  const reset = useCallback(() => setInput(initialValue), [initialValue]);

  return { input, onChange, reset };
};

export default useInput;
