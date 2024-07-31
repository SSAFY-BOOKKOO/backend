import { axiosInstance, authAxiosInstance } from './axiosInstance';

export const postLogin = async info => {
  try {
    const response = await axiosInstance.post('/auth/login/email', info);
    return response.data;
  } catch (error) {
    console.error('Login failed:', error);
    throw error;
  }
};
