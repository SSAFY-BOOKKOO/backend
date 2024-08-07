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
