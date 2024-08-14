import { authAxiosInstance } from './axiosInstance';

// 알라딘 전체 검색(title, author, publisher별)
export const getAladinBooks = async (text, tag, start = 1, maxResult = 4) => {
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
};

// 알라딘 단일 검색(isbn)
export const getAladinBookByIsbn = async isbn => {
  const response = await authAxiosInstance.get(`/books/aladin/books/${isbn}`);

  return response.data;
};

// 책 카테고리 조회
export const postCategories = async () => {
  const response = await authAxiosInstance.post('/categories/search');

  return response.data;
};

// 책 생성
export const postBook = async book => {
  const response = await authAxiosInstance.post('/books/isbn', book);

  return response.data;
};

// isbn으로 책 조회
export const getLibraryBookByIsbn = async isbn => {
  const response = await authAxiosInstance.get(`/books/isbn/${isbn}`);

  return response.data;
};
