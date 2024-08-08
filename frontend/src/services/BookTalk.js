import { authAxiosInstance } from './axiosInstance';

// 북톡 생성
export const postBookTalks = async bookId => {
  try {
    const response = await authAxiosInstance.post('/booktalks', { bookId });
    return response;
  } catch (error) {
    console.error('booktalk create failed:', error);
    throw error;
  }
};

// 북톡 참여
export const postBookTalkEnter = async bookTalkId => {
  try {
    const response = await authAxiosInstance.post(
      `/booktalks/enter/${bookTalkId}`
    );
    return response.data;
  } catch (error) {
    console.error('booktalk enter failed:', error);
    throw error;
  }
};

// 내가 참여한 북톡 리스트 조회(10개씩 페이징)
export const getMyBookTalk = async (order = 'time', page = 0) => {
  try {
    const response = await authAxiosInstance.get('/booktalks/my', {
      params: {
        order,
        page,
      },
    });

    return response.data;
  } catch (error) {
    console.error('get my booktalk list failed:', error);
    throw error;
  }
};

// 책 번호로 북톡 조회
export const getBookTalkByBookId = async bookId => {
  try {
    const response = await authAxiosInstance.get('/booktalks/book', {
      params: {
        bookId,
      },
    });
    return response.data;
  } catch (error) {
    console.error('get booktalk by bookId', error);
    throw error;
  }
};

// 인기 북톡 리스트 조회(10개)
export const getPopularBookTalk = async () => {
  try {
    const response = await authAxiosInstance.get('/booktalks');
    return response.data;
  } catch (error) {
    console.error('get popular booktalks', error);
    throw error;
  }
};

// 채팅 기록 가져오기(by BookTalkId) -> 최근순으로 10개씩
export const getBookTalkChats = async (bookTalkId, page = 0) => {
  try {
    const response = await authAxiosInstance.get(
      `/booktalks/chat/${bookTalkId}`,
      {
        params: {
          page: page,
        },
      }
    );
    return response.data;
  } catch (error) {
    console.error('get booktalk chats failed', error);
    throw error;
  }
};

// 채팅 보내기
export const postBookTalkChat = async (bookTalkId, content) => {
  try {
    const response = await authAxiosInstance.post(
      `/booktalks/chat/${bookTalkId}`,
      { content }
    );
    return response.data;
  } catch (error) {
    console.error('post booktalk chat failed:', error);
    throw error;
  }
};
