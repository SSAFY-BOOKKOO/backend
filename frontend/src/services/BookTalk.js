import { authAxiosInstance } from './axiosInstance';

// 북톡 생성
export const postBookTalks = async bookId => {
  const response = await authAxiosInstance.post('/booktalks', { bookId });
  return response.data;
};

// 북톡 참여
export const postBookTalkEnter = async bookTalkId => {
  const response = await authAxiosInstance.post(
    `/booktalks/enter/${bookTalkId}`
  );
  return response.data;
};

// 내가 참여한 북톡 리스트 조회(10개씩 페이징)
export const getMyBookTalk = async (order = 'time', page = 0) => {
  const response = await authAxiosInstance.get('/booktalks/my', {
    params: {
      order,
      page,
    },
  });
  return response.data;
};

// 책 번호로 북톡 조회
export const getBookTalkByBookId = async bookId => {
  const response = await authAxiosInstance.get(`/booktalks/book/${bookId}`);
  return response.data;
};

// 인기 북톡 리스트 조회(10개)
export const getPopularBookTalk = async () => {
  const response = await authAxiosInstance.get('/booktalks');
  return response.data;
};

// 북톡 검색
export const postBookTalkSearch = async (text, tag, offset = 0, limit = 4) => {
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
    '/booktalks/me/books/search',
    bodyData
  );
  return response.data;
};

// 채팅 기록 가져오기(by BookTalkId) -> 최근순으로 10개씩
export const getBookTalkChats = async (bookTalkId, time) => {
  const response = await authAxiosInstance.get(
    `/booktalks/chat/${bookTalkId}`,
    {
      params: {
        time,
      },
    }
  );
  return response.data;
};

// 채팅 보내기
export const postBookTalkChat = async (bookTalkId, content) => {
  const response = await authAxiosInstance.post(
    `/booktalks/chat/${bookTalkId}`,
    { content }
  );
  return response.data;
};

// 채팅 좋아요
export const postBookTalkLike = async chatMessageId => {
  const response = await authAxiosInstance.post(
    `/booktalks/chat/like/${chatMessageId}`
  );
  return response.data;
};
