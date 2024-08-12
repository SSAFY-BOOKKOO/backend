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
