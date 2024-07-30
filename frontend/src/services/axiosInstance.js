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
  baseURL: VITE_API_BASE_URL,
  timeout: 10000,
  headers: {
    'Content-Type': 'application/json',
  },
});

// 요청 인터셉터
authAxiosInstance.interceptors.request.use(
  config => {
    // const token = localStorage.getItem('token');
    // if (token) {
    //   config.headers.Authorization = `Bearer ${token}`;
    // }
    return config;
  },
  error => {
    return Promise.reject(error);
  }
);

// 응답 인터셉터
const applyResponseInterceptor = instance => {
  instance.interceptors.response.use(
    response => response,
    error => {
      // if (error.response && error.response.status === 401) {
      //   localStorage.removeItem('token');
      //   window.location.href = '/login';
      // }
      return Promise.reject(error);
    }
  );
};

applyResponseInterceptor(axiosInstance);
applyResponseInterceptor(authAxiosInstance);

axiosInstance.defaults.withCredentials = true;
authAxiosInstance.defaults.withCredentials = true;

export { axiosInstance, authAxiosInstance };
