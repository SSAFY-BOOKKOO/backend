// components/Library/Detail/Modal/Bookcolork.jsx

const BookDelete=({bookId, onDelete})=>{
  const handleDelete=()=>{
    onDelete(bookId);
  }
  
  return (
    <button onClick={handleDelete} style={{display:'none'}}>
      Delete Book
    </button>
  )
}


export default BookDelete;