import { authAxiosInstance } from './axiosInstance';

// 알라딘 전체 검색(title, author, publisher별)
export const getAladinBooks = async (text, tag, start = 1, maxResult = 4) => {
  try {
    const params = {
      query: text,
      queryType: tag,
      maxResult,
      start,
    };

    const response = await authAxiosInstance.get('/books/aladin/books', {
      params,
    });

    return response.data;
  } catch (error) {
    console.error('aladin books failed:', error);
    throw error;
  }
};

// 알라딘 단일 검색(isbn)
export const getAladinBookByIsbn = async isbn => {
  try {
    const response = await authAxiosInstance.get(`/books/aladin/books/${isbn}`);

    return response.data;
  } catch (error) {
    console.error('aladin books by isbn failed:', error);
    throw error;
  }
};
