import { authAxiosInstance } from './axiosInstance';

// 내 서재 검색
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
          field: tag.toLowerCase(),
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
    console.error('library search failed:', error);
    throw error;
  }
};

// 서재에 책 등록
export const postLibraryBook = async (libraryId, info) => {
  try {
    const response = await authAxiosInstance.post(
      `libraries/${libraryId}/books`,
      info
    );
    return response.data;
  } catch (error) {
    console.error('library book create failed:', error);
    throw error;
  }
};

// 내가 가진 서재 목록 조회
export const getLibraryList = async () => {
  try {
    const response = await authAxiosInstance.get('libraries/me');
    return response.data;
  } catch (error) {
    console.error('library me failed:', error);
    throw error;
  }
};
