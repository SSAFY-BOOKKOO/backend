import { authAxiosInstance } from './axiosInstance';

export const getLibrarySearchBooks = async (
  text,
  tag,
  offset = 0,
  limit = 4
) => {
  try {
    const bodyData = {
      conditions: [
        {
          field: tag,
          values: [text],
        },
      ],
      offset,
      limit,
    };

    const response = await authAxiosInstance.post(
      'libraries/me/books/search',
      bodyData
    );
    return response.data;
  } catch (error) {
    console.error('aladin books failed:', error);
    throw error;
  }
};
