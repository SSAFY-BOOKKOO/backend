import { authAxiosInstance } from './axiosInstance';

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

export const getAladinBookByIsbn = async isbn => {
  try {
    const response = await authAxiosInstance.get(`/books/aladin/books/${isbn}`);

    return response.data;
  } catch (error) {
    console.error('aladin books by isbn failed:', error);
    throw error;
  }
};
