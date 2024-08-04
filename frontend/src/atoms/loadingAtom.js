import { atom } from 'jotai';

export const loadingAtom = atom(0);
export const isLoadingAtom = atom(get => get(loadingAtom) > 0);
