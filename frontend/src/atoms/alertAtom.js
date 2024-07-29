import { atom } from 'jotai';

export const alertAtom = atom({
  isOpen: false,
  message: '',
  confirmOnly: true,
  onConfirm: null,
  onCancel: null,
});

export const showAlertAtom = atom(
  null,
  (
    get,
    set,
    message,
    confirmOnly = true,
    onConfirm = null,
    onCancel = null
  ) => {
    set(alertAtom, { isOpen: true, message, confirmOnly, onConfirm, onCancel });
  }
);

export const hideAlertAtom = atom(null, (get, set) => {
  set(alertAtom, { ...get(alertAtom), isOpen: false });
});
