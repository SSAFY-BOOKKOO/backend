import React from 'react';
import IconButton from '@components/@common/IconButton';
import { MdOutlineFileDownload } from 'react-icons/md';
import domtoimage from 'dom-to-image';
import { saveAs } from 'file-saver';
import { showAlertAtom } from '@atoms/alertAtom';
import { useAtom } from 'jotai';

const CaptureButton = ({ targetRef, onCapture, filename = 'library' }) => {
  const [, showAlert] = useAtom(showAlertAtom);

  const handleDownload = async () => {
    if (targetRef.current) {
      try {
        onCapture?.(true);

        const scale = 4;
        const style = {
          transform: `scale(${scale})`,
          transformOrigin: 'top left',
          width: targetRef.current.scrollWidth + 'px',
          height: targetRef.current.scrollHeight + 'px',
        };

        const blob = await domtoimage.toBlob(targetRef.current, {
          quality: 1,
          height: targetRef.current.scrollHeight * scale,
          width: targetRef.current.scrollWidth * scale,
          style: {
            ...style,
          },
        });
        saveAs(blob, `${filename}.png`);
      } catch (error) {
        showAlert('오류가 발생했습니다. 다시 시도해주세요.', true, () => {});
      } finally {
        onCapture?.(false); // 캡처 종료
      }
    }
  };

  return (
    <IconButton
      onClick={handleDownload}
      icon={MdOutlineFileDownload}
      iconClassName='w-7 h-7'
    />
  );
};

export default CaptureButton;
