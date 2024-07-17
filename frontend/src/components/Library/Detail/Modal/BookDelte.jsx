// components/Library/Detail/Modal/Bookcolork.jsx
import React from "react";

//home에서 id,delte 내려옴
const BookDelete=({bookId, onDelete})=>{

  // handleDelete : bookId param으로 받아서 onDelete 실행
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