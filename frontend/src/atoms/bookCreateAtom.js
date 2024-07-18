import { atom } from 'jotai';

export const bookDataAtom = atom({
  status: '',
  startDate: '',
  endDate: '',
  rating: 0,
  color: '',
  library: '',
});

export const modalStepAtom = atom(1);
