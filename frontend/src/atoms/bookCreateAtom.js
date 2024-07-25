import { atom } from 'jotai';

export const bookDataAtom = atom({
  status: 'read',
  startDate: '',
  endDate: '',
  rating: 0,
  color: '',
  library: 'library1',
});

export const modalStepAtom = atom(1);
