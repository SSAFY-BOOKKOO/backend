import React, { useRef, useState, useEffect } from 'react';
import backgroundImage from '@assets/images/quote_background.png';
import IconButton from '@components/@common/IconButton';
import { FaAngleLeft, FaAngleRight } from 'react-icons/fa';
import { IoCloseSharp } from 'react-icons/io5';
import { BiSolidQuoteAltLeft } from 'react-icons/bi';
import CaptureButton from '@components/Library/Main/CaptureButton';
import Spinner from '@components/@common/Spinner';
import { getQuoteDetail } from '@services/Member';
import { useAtom } from 'jotai';
import { showAlertAtom } from '@atoms/alertAtom';

const DEFAULT_BACKGROUND_URL =
  'https://api.i11a506.ssafy.io/commons/file/bookkoobookkoo-quote/Default.jpg';

const QuoteDetailModal = ({
  quoteId,
  setSelectedQuote,
  onNextQuote,
  onPrevQuote,
}) => {
  const ref = useRef(null);
  const [isCapturing, setIsCapturing] = useState(false);
  const [quoteData, setQuoteData] = useState(null);
  const [isLoading, setIsLoading] = useState(true);
  const [, showAlert] = useAtom(showAlertAtom);

  useEffect(() => {
    const fetchQuoteDetail = async () => {
      setIsLoading(true);
      try {
        const data = await getQuoteDetail(quoteId);
        setQuoteData(data);
      } catch (error) {
        showAlert('앗 오류가 발생했습니다. 다시 시도해주세요!', true, () => {});
      } finally {
        setIsLoading(false);
      }
    };

    fetchQuoteDetail();
  }, [quoteId]);

  const handleCapture = capturing => {
    setIsCapturing(capturing);
  };

  const backgroundImgSrc =
    quoteData?.backgroundImgUrl === DEFAULT_BACKGROUND_URL
      ? backgroundImage
      : quoteData?.backgroundImgUrl || backgroundImage;

  const isDefaultBackground =
    quoteData?.backgroundImgUrl === DEFAULT_BACKGROUND_URL ||
    !quoteData?.backgroundImgUrl;

  return (
    <div className='fixed inset-0 flex items-center justify-center bg-black bg-opacity-75 z-20'>
      {isLoading ? (
        <Spinner />
      ) : (
        <div
          ref={ref}
          className={`bg-white rounded-lg shadow-lg w-11/12 max-w-md relative mx-2 h-3/5 ${quoteData?.fontColor}`}
        >
          <img
            src={backgroundImgSrc}
            alt='Background'
            className={`w-full h-full object-cover rounded-lg ${isDefaultBackground ? 'object-top' : 'object-center'}`}
          />
          <div className='absolute inset-0 flex flex-col justify-center p-8'>
            <BiSolidQuoteAltLeft className='mb-1' />
            <p className='text-lg font-semibold mb-4'>{quoteData?.content}</p>
            <p className='text-sm'>{quoteData?.source}</p>
          </div>
          {!isCapturing && (
            <div>
              <div className='absolute top-0 right-0 mt-4 mr-4 z-10'>
                <IconButton
                  onClick={() => setSelectedQuote(null)}
                  icon={IoCloseSharp}
                  className='bg-transparent p-2 rounded-full text-2xl'
                />
              </div>
              <div className='absolute bottom-0 right-0 mb-4 mr-4 z-10'>
                <IconButton
                  onClick={onNextQuote}
                  icon={FaAngleRight}
                  className='bg-transparent  p-2 rounded-full text-2xl'
                />
              </div>
              <div className='absolute inset-0 flex justify-center items-end mb-4'>
                <CaptureButton
                  targetRef={ref}
                  filename='quote'
                  onCapture={handleCapture}
                />
              </div>
              <div className='absolute bottom-0 left-0 mb-4 ml-4 z-10'>
                <IconButton
                  onClick={onPrevQuote}
                  icon={FaAngleLeft}
                  className='bg-transparent  p-2 rounded-full text-2xl'
                />
              </div>
            </div>
          )}
        </div>
      )}
    </div>
  );
};

export default QuoteDetailModal;
