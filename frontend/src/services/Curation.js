import { authAxiosInstance } from './axiosInstance';

// 챗봇
export const postChatbot = async content => {
  const bodyData = [
    {
      role: 'user',
      content,
    },
  ];

  const response = await authAxiosInstance.post(`/curations/chat`, bodyData);
  return response.data;
};
