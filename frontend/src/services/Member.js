import { axiosInstance, authAxiosInstance } from './axiosInstance';

// 이메일 로그인
export const postLogin = async info => {
  try {
    const response = await axiosInstance.post('/auth/login/email', info);
    return response.data;
  } catch (error) {
    console.error('Login failed:', error);
    throw error;
  }
};

// 사용자 알림 가져오기
export const getNotifications = async (page = 0, size = 10) => {
  try {
    const params = {
      page,
      size,
      sort: 'string',
    };

    const response = await authAxiosInstance.get('/notifications', { params });
    return response.data;
  } catch (error) {
    console.error('notifications failed:', error);
    throw error;
  }
};

// 사용자 알림 삭제
export const deleteNotification = async notificationId => {
  try {
    const response = await authAxiosInstance.delete(
      `/notifications/${notificationId}`
    );
    return response.data;
  } catch (error) {
    console.error('notifications failed:', error);
    throw error;
  }
};

// 사용자 정보 가져오기
export const getMemberInfo = async () => {
  try {
    const response = await authAxiosInstance.get('/members/info');
    return response.data;
  } catch (error) {
    console.error('get member info failed:', error);
    throw error;
  }
};

// 팔로워 목록 가져오기
export const getFollowers = async memberId => {
  try {
    const response = await authAxiosInstance.get('/members/follow/followers', {
      memberId,
    });
    return response.data;
  } catch (error) {
    console.error('Get Followers failed:', error);
    throw error;
  }
};

// 팔로잉 목록 가져오기
export const getFollowings = async memberId => {
  try {
    const response = await authAxiosInstance.get('/members/follow/followees', {
      memberId,
    });
    return response.data;
  } catch (error) {
    console.error('Get Followees failed:', error);
    throw error;
  }
};

// 팔로우
export const postFollow = async memberId => {
  const bodyData = {
    memberId,
  };

  try {
    const response = await authAxiosInstance.post(
      'members/follow/follow',
      bodyData
    );
    return response.data;
  } catch (error) {
    console.error('Follow failed:', error);
    throw error;
  }
};

// 언팔로우
export const postUnfollow = async memberId => {
  const bodyData = {
    memberId,
  };
  try {
    const response = await authAxiosInstance.post(
      '/members/follow/unfollow',
      bodyData
    );
    return response.data;
  } catch (error) {
    console.error('Unfollow failed:', error);
    throw error;
  }
};

// 팔로워 삭제
export const deleteFollower = async memberId => {
  try {
    const response = await authAxiosInstance.delete(
      `/members/follow/${memberId}`
    );
    return response.data;
  } catch (error) {
    console.error('delete follwer failed:', error);
    throw error;
  }
};

// 닉네임으로 사용자 정보 가져오기
export const getInfoByNickName = async nickName => {
  try {
    const response = await authAxiosInstance.get(
      `/members/info/name/${nickName}`
    );
    return response.data;
  } catch (error) {
    console.error('Get Followees failed:', error);
    throw error;
  }
};

// 글귀 이미지 텍스트 추출
export const postQuoteOcr = async image => {
  let formData = new FormData();
  formData.append('image', image);

  try {
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
  } catch (error) {
    console.error('OCR failed:', error);
    throw error;
  }
};

// 글귀 목록 조회
export const getQuoteList = async (page = 0, size = 10) => {
  try {
    const params = {
      page,
      size,
      sort: 'string',
    };

    const response = await authAxiosInstance.get('/members/quote', { params });
    return response.data;
  } catch (error) {
    console.error('get quote list failed:', error);
    throw error;
  }
};
// 글귀 생성
export const postQuote = async (content, source, backgroundImg = null) => {
  const quoteDto = JSON.stringify({
    source,
    content,
  });

  let formData = new FormData();
  formData.append('quoteDto', quoteDto);

  if (backgroundImg) {
    formData.append('backgroundImg', backgroundImg);
  }

  try {
    const response = await authAxiosInstance.post('/members/quote', formData, {
      headers: {
        'Content-Type': 'multipart/form-data',
      },
    });
    return response.data;
  } catch (error) {
    console.error('post quote failed:', error);
    throw error;
  }
};

// 글귀 수정
export const putQuote = async (quoteId, content, source) => {
  const quoteData = {
    quoteId,
    source,
    content,
  };
  try {
    const response = await authAxiosInstance.put('/members/quote', quoteData);
    return response.data;
  } catch (error) {
    console.error('put quote failed:', error);
    throw error;
  }
};

// 글귀 상세 보기
export const getQuoteDetail = async quoteId => {
  try {
    const response = await authAxiosInstance.get(
      `/members/quote/detail/${quoteId}`
    );
    return response.data;
  } catch (error) {
    console.error('Get Quote detail failed:', error);
    throw error;
  }
};

// 글귀 삭제
export const deleteQuote = async quoteId => {
  try {
    const response = await authAxiosInstance.get(
      `/members/quote/detail/${quoteId}`
    );
    return response.data;
  } catch (error) {
    console.error('delete Quote detail failed:', error);
    throw error;
  }
};

// 글귀 개수 반환
export const getQuoteCount = async () => {
  try {
    const response = await authAxiosInstance.get(`/members/quote/count`);
    return response.data;
  } catch (error) {
    console.error('Quote count failed:', error);
    throw error;
  }
};
