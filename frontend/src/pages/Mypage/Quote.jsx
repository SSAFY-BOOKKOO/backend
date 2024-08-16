import React, { useEffect, useState } from 'react';
import QuoteList from '@components/MyPage/Quote/QuoteList.jsx';
import QuoteInput from '@components/MyPage/Quote/QuoteInput.jsx';
import QuoteDetailModal from '@components/MyPage/Quote/QuoteDetailModal.jsx';
import QuoteCreateModal from '@components/MyPage/Quote/QuoteCreateModal.jsx';
import useModal from '@hooks/useModal';
import Button from '@components/@common/Button';
import WrapContainer from '@components/Layout/WrapContainer';
import { useAtom } from 'jotai';
import { showAlertAtom } from '@atoms/alertAtom';
import Spinner from '@components/@common/Spinner';
import useQuoteInfiniteScroll from '@hooks/useQuoteInfiniteScroll';
import { useInView } from 'react-intersection-observer';
import {
  postQuote,
  deleteQuote,
  putQuote,
  getQuoteCount,
} from '@services/Member';

const Quote = () => {
  const [quotes, setQuotes] = useState([]);
  const [quoteCount, setQuoteCount] = useState();
  const [showInput, setShowInput] = useState(false);
  const [selectedQuoteIndex, setSelectedQuoteIndex] = useState(null);
  const [extractedText, setExtractedText] = useState('');
  const [editQuote, setEditQuote] = useState(null); // 수정할 quote
  const { isOpen, toggleModal } = useModal();
  const [, showAlert] = useAtom(showAlertAtom);

  const {
    data,
    fetchNextPage,
    hasNextPage,
    isFetchingNextPage,
    isLoading,
    refetch,
  } = useQuoteInfiniteScroll();

  const { ref, inView } = useInView();

  useEffect(() => {
    if (inView && hasNextPage) {
      fetchNextPage();
    }
  }, [inView, hasNextPage, fetchNextPage]);

  useEffect(() => {
    if (data?.pages) {
      const fetchedQuotes = data.pages.flatMap(page => page);
      setQuotes(fetchedQuotes);
    }
  }, [data]);

  useEffect(() => {
    handleQuoteCount();
  }, []);

  const handleCloseQuoteInput = () => {
    setShowInput(false);
    setExtractedText('');
    setEditQuote(null);
  };

  const createQuote = async quoteData => {
    try {
      if (quoteData.backgroundImg) {
        await postQuote(
          quoteData.content,
          quoteData.source,
          quoteData.fontColor,
          quoteData.backgroundImg
        );
      } else {
        await postQuote(
          quoteData.content,
          quoteData.source,
          quoteData.fontColor
        );
      }
      refetch();
      showAlert('글귀가 생성되었습니다.', true, () => {
        handleQuoteCount();
      });
    } catch (error) {
      showAlert('앗 오류가 발생했습니다. 다시 시도해주세요!', true, () => {});
    } finally {
      handleCloseQuoteInput();
    }
  };

  const updateQuote = async quoteData => {
    try {
      if (quoteData.backgroundImg) {
        await putQuote(
          quoteData.quoteId,
          quoteData.content,
          quoteData.source,
          quoteData.fontColor,
          quoteData.backgroundImg
        );
      } else {
        await putQuote(
          quoteData.quoteId,
          quoteData.content,
          quoteData.source,
          quoteData.fontColor
        );
      }
      refetch();
      showAlert('글귀가 수정되었습니다.', true, () => {});
    } catch (error) {
      showAlert('앗 오류가 발생했습니다. 다시 시도해주세요!', true, () => {});
    } finally {
      handleCloseQuoteInput();
    }
  };

  const deleteQuoteHandler = async quoteId => {
    try {
      await deleteQuote(quoteId);

      showAlert('글귀가 삭제되었습니다.', true, () => {
        handleQuoteCount();
        refetch();
      });
    } catch (error) {
      showAlert('앗 오류가 발생했습니다. 다시 시도해주세요!', true, () => {});
    }
  };

  const handleQuoteClick = index => {
    setSelectedQuoteIndex(index);
  };

  const handleNextQuote = () => {
    setSelectedQuoteIndex(prevIndex =>
      prevIndex === quotes.length - 1 ? 0 : prevIndex + 1
    );
  };

  const handlePrevQuote = () => {
    setSelectedQuoteIndex(prevIndex =>
      prevIndex === 0 ? quotes.length - 1 : prevIndex - 1
    );
  };

  const handleImageUpload = text => {
    setExtractedText(text);
    setShowInput(true);
  };

  const handleQuoteCount = async () => {
    try {
      const data = await getQuoteCount();
      setQuoteCount(data);
    } catch (e) {
      console.log('error');
    }
  };

  const handleEditClick = quote => {
    setEditQuote(quote);
    setShowInput(true);
  };

  return (
    <WrapContainer>
      <div className='flex justify-between items-center mb-4'>
        <h1 className='text-2xl font-bold'>나의 글귀함</h1>
        <div className='flex items-center'>
          <Button size='small' onClick={toggleModal}>
            글귀 만들기
          </Button>
        </div>
      </div>
      <p className='mb-3'>총 {quoteCount}개</p>
      {isLoading ? (
        <Spinner />
      ) : (
        <QuoteList
          quotes={quotes}
          onQuoteClick={handleQuoteClick}
          onEditClick={handleEditClick}
          onDeleteClick={deleteQuoteHandler}
        />
      )}
      <div ref={ref}></div>
      {isFetchingNextPage && <Spinner infiniteScroll />}
      {isOpen && (
        <QuoteCreateModal
          toggleModal={toggleModal}
          setShowInput={setShowInput}
          onImageUpload={handleImageUpload}
        />
      )}
      {showInput && (
        <QuoteInput
          addQuote={editQuote ? updateQuote : createQuote}
          setShowInput={setShowInput}
          initialQuote={editQuote ? editQuote.content : extractedText}
          initialSource={editQuote ? editQuote.source : ''}
          isEdit={!!editQuote}
          quoteId={editQuote ? editQuote.quoteId : null}
          onClose={handleCloseQuoteInput}
        />
      )}
      {selectedQuoteIndex !== null && (
        <QuoteDetailModal
          quoteId={quotes[selectedQuoteIndex].quoteId}
          setSelectedQuote={setSelectedQuoteIndex}
          onNextQuote={handleNextQuote}
          onPrevQuote={handlePrevQuote}
        />
      )}
    </WrapContainer>
  );
};

export default Quote;
