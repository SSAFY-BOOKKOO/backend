import axios from 'axios';

const axiosInstance = axios.create({
  baseURL: 'https://localhost:',
  timeout: 10000,
});

// 요청 인터셉터
axiosInstance.interceptors.request.use(
  config => {
    // 저장된 토큰 가져오기
    const token = localStorage.getItem('token');
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  error => {
    return Promise.reject(error);
  }
);

// 응답 인터셉터
axiosInstance.interceptors.response.use(
  response => {
    // 2xx
    return response;
  },
  error => {
    // 2xx 외
    if (error.response && error.response.status === 401) {
      // 인증 오류(토큰 없음)
      localStorage.removeItem('token');
      window.location.href = '/login'; // 로그인 페이지로
    }
    return Promise.reject(error);
  }
);

export default axiosInstance;
