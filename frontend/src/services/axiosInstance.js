import axios from 'axios';

const { VITE_API_BASE_URL } = import.meta.env;

// 기본 인스턴스
const axiosInstance = axios.create({
  baseURL: '/api',
  timeout: 10000,
  headers: {
    'Content-Type': 'application/json',
  },
});

// 인증이 필요한 인스턴스
const authAxiosInstance = axios.create({
  baseURL: '/api',
  timeout: 10000,
  headers: {
    'Content-Type': 'application/json',
  },
});

// 요청 인터셉터
authAxiosInstance.interceptors.request.use(
  config => {
    // 요청이 전달되기 전 작업
    const accessToken = localStorage.getItem('ACCESS_TOKEN');
    if (accessToken) {
      config.headers.Authorization = `Bearer ${accessToken}`;
    }
    return config;
  },
  error => {
    // 요청 오류가 있는 작업
    return Promise.reject(error);
  }
);

// 응답 인터셉터
const applyResponseInterceptor = instance => {
  instance.interceptors.response.use(
    // 2xx 범위에서 응답 데이터가 있는 작업
    response => response,
    async error => {
      // 2xx 외의 범위 응답 오류가 있는 작업
      // 토큰 만료
      if (error.response && error.response?.status === 401) {
        try {
          const { accessToken } = error.response.data.data;

          localStorage.setItem('ACCESS_TOKEN', accessToken);

          // 원래 요청을 재시도
          const retryConfig = {
            ...error.config,
            headers: {
              ...error.config.headers,
              Authorization: `Bearer ${accessToken}`,
            },
          };

          return authAxiosInstance(retryConfig);
        } catch (refreshError) {
          localStorage.removeItem('ACCESS_TOKEN');
          window.location.href = '/login';
          return Promise.reject(refreshError);
        }
      }
      return Promise.reject(error);
    }
  );
};

applyResponseInterceptor(axiosInstance);
applyResponseInterceptor(authAxiosInstance);

axiosInstance.defaults.withCredentials = true;
authAxiosInstance.defaults.withCredentials = true;

export { axiosInstance, authAxiosInstance };
