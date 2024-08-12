import axios from 'axios';
import { getDefaultStore } from 'jotai';
import { loadingAtom } from '@atoms/loadingAtom';
import { isAuthenticatedAtom } from '@atoms/authAtom';

const { MODE } = import.meta.env;

const baseURL = MODE === 'production' ? 'https://api.i11a506.ssafy.io' : '/api';

const createAxiosInstance = (useAuth = false) => {
  const instance = axios.create({
    baseURL,
    timeout: 10000,
    headers: {
      'Content-Type': 'application/json',
    },
  });

  // 요청 인터셉터
  instance.interceptors.request.use(
    config => {
      getDefaultStore().set(loadingAtom, prev => prev + 1);
      if (useAuth) {
        const accessToken = localStorage.getItem('ACCESS_TOKEN');
        if (accessToken) {
          config.headers.Authorization = `Bearer ${accessToken}`;
        }
      }
      return config;
    },
    error => {
      getDefaultStore().set(loadingAtom, prev => Math.max(0, prev - 1));
      return Promise.reject(error);
    }
  );

  // 응답 인터셉터
  instance.interceptors.response.use(
    response => {
      getDefaultStore().set(loadingAtom, prev => Math.max(0, prev - 1));
      return response;
    },
    async error => {
      getDefaultStore().set(loadingAtom, prev => Math.max(0, prev - 1));
      if (useAuth && error.response && error.response?.status === 401) {
        try {
          const { accessToken: newAccessToken } = error.response.data.data;

          localStorage.setItem('ACCESS_TOKEN', newAccessToken);

          // 원래 요청을 재시도
          const retryConfig = {
            ...error.config,
            headers: {
              ...error.config.headers,
              Authorization: `Bearer ${newAccessToken}`,
            },
          };

          return instance(retryConfig);
        } catch (refreshError) {
          localStorage.removeItem('ACCESS_TOKEN');
          getDefaultStore().set(isAuthenticatedAtom, false);
          window.location.href = '/login';
          return Promise.reject(refreshError);
        }
      }
      return Promise.reject(error);
    }
  );

  instance.defaults.withCredentials = true;

  return instance;
};

const axiosInstance = createAxiosInstance(false);
const authAxiosInstance = createAxiosInstance(true);

export { axiosInstance, authAxiosInstance };
