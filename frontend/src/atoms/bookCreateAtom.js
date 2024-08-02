import { atom } from 'jotai';

export const bookDataAtom = atom({
  status: 'READ',
  startAt: '',
  endAt: '',
  rating: 0,
  bookColor: '',
  libraryId: '1',
});

export const modalStepAtom = atom(1);
