import { axiosInstance, authAxiosInstance } from './axiosInstance';

// 이메일 로그인
export const postLogin = async info => {
  const response = await axiosInstance.post('/auth/login/email', info);
  return response.data;
};

// 사용자 알림 가져오기
export const getNotifications = async (page = 0, size = 10) => {
  const params = {
    page,
    size,
    sort: 'string',
  };

  const response = await authAxiosInstance.get('/notifications', { params });
  return response.data;
};

// 사용자 알림 삭제
export const deleteNotification = async notificationId => {
  const response = await authAxiosInstance.delete(
    `/notifications/${notificationId}`
  );
  return response.data;
};

// 사용자 정보 가져오기
export const getMemberInfo = async () => {
  const response = await authAxiosInstance.get('/members/info');
  return response.data;
};

// 팔로워 목록 가져오기
export const getFollowers = async memberId => {
  const response = await authAxiosInstance.get('/members/follow/followers', {
    params: {
      memberId,
    },
  });
  return response.data;
};

// 팔로잉 목록 가져오기
export const getFollowings = async memberId => {
  const response = await authAxiosInstance.get('/members/follow/followees', {
    params: {
      memberId,
    },
  });
  return response.data;
};

// 팔로우
export const postFollow = async memberId => {
  const bodyData = {
    memberId,
  };

  const response = await authAxiosInstance.post(
    'members/follow/follow',
    bodyData
  );
  return response.data;
};

// 언팔로우
export const postUnfollow = async memberId => {
  const bodyData = {
    memberId,
  };

  const response = await authAxiosInstance.post(
    '/members/follow/unfollow',
    bodyData
  );
  return response.data;
};

// 팔로워 삭제
export const deleteFollower = async memberId => {
  const response = await authAxiosInstance.delete(
    `/members/follow/${memberId}`
  );
  return response.data;
};

// 닉네임으로 사용자 정보 가져오기
export const getInfoByNickName = async nickName => {
  const response = await authAxiosInstance.get(
    `/members/info/name/${nickName}`
  );
  return response.data;
};

// 닉네임으로 사용자 정보 가져오기(like)
export const getInfoByNickNameLike = async nickName => {
  const response = await authAxiosInstance.get(
    `/members/info/name/like/${nickName}`
  );
  return response.data;
};

// 글귀 이미지 텍스트 추출
export const postQuoteOcr = async image => {
  let formData = new FormData();
  formData.append('image', image);

  const response = await authAxiosInstance.post(
    '/members/quote/ocr',
    formData,
    {
      headers: {
        'Content-Type': 'multipart/form-data',
      },
    }
  );
  return response.data;
};

// 글귀 목록 조회
export const getQuoteList = async (page = 0, size = 10) => {
  const params = {
    page,
    size,
    sort: 'string',
  };

  const response = await authAxiosInstance.get('/members/quote', { params });
  return response.data;
};

// 글귀 생성
export const postQuote = async (
  content,
  source,
  fontColor,
  backgroundImg = null
) => {
  const quoteDto = JSON.stringify({
    source,
    content,
    fontColor,
  });

  let formData = new FormData();
  formData.append('quoteDto', quoteDto);

  if (backgroundImg) {
    formData.append('backgroundImg', backgroundImg);
  }

  const response = await authAxiosInstance.post('/members/quote', formData, {
    headers: {
      'Content-Type': 'multipart/form-data',
    },
  });
  return response.data;
};

// 글귀 수정
export const putQuote = async (
  quoteId,
  content,
  source,
  fontColor,
  backgroundImg = null
) => {
  const quoteDto = JSON.stringify({
    quoteId,
    source,
    content,
    fontColor,
  });

  let formData = new FormData();
  formData.append('quoteDto', quoteDto);

  if (backgroundImg) {
    formData.append('backgroundImg', backgroundImg);
  }

  const response = await authAxiosInstance.put('/members/quote', formData, {
    headers: {
      'Content-Type': 'multipart/form-data',
    },
  });
  return response.data;
};

// 글귀 상세 보기
export const getQuoteDetail = async quoteId => {
  const response = await authAxiosInstance.get(
    `/members/quote/detail/${quoteId}`
  );
  return response.data;
};

// 글귀 삭제
export const deleteQuote = async quoteId => {
  const response = await authAxiosInstance.delete(`/members/quote/${quoteId}`);
  return response.data;
};

// 글귀 개수 반환
export const getQuoteCount = async () => {
  const response = await authAxiosInstance.get(`/members/quote/count`);
  return response.data;
};
