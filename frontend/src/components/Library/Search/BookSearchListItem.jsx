import Button from '../../@common/Button';

const BookSearchListItem = ({ book, onClick, onCreateClick }) => {
  const handleButtonClick = e => {
    e.stopPropagation();

    // 등록 페이지로 이동
    onCreateClick();
  };

  return (
    <div
      className='flex items-start space-x-4 p-3 mb-2 bg-white cursor-pointer'
      onClick={onClick}
    >
      <div className='w-36 h-36 flex'>
        <img className='object-contain' src={book.cover_img_url} />
      </div>

      <div className='flex flex-col justify-between h-36 w-full'>
        <div className='flex flex-col space-y-1 overflow-hidden'>
          <p className='text-overflow text-lg font-semibold'>{book.title}</p>
          <p className='text-sm text-gray-600'>{book.author}</p>
          <p className='text-sm text-gray-600'>
            {book.publisher} | {book.published_at}
          </p>
        </div>
        <Button className='w-14 mt-2' size='small' onClick={handleButtonClick}>
          등록
        </Button>
      </div>
    </div>
  );
};

export default BookSearchListItem;
