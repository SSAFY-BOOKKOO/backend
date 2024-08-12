import { atom } from 'jotai';

export const isAuthenticatedAtom = atom(!!localStorage.getItem('ACCESS_TOKEN'));
