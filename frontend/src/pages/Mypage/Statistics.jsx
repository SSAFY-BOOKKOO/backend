import { authAxiosInstance } from '@services/axiosInstance';
import React, { useEffect, useState } from 'react';
// 차트 만들기
import { Doughnut } from 'react-chartjs-2';
import 'chart.js/auto';

const Statistics = () => {
  const [myBooks, setMyBooks] = useState([]);

  // 오늘 날짜 받기
  const getTodayDate = () => {
    const today = new Date();
    const year = today.getFullYear();
    const month = String(today.getMonth() + 1).padStart(2, '0');
    const day = String(today.getDate()).padStart(2, '0');
    return `${year}-${month}-${day}`;
  };

  useEffect(() => {
    authAxiosInstance
      .get('/stats/categories')
      .then(res => {
        setMyBooks(res.data);
      })
      .catch(err => {
        console.log(err);
      });
  }, []);

  // 도넛차트 세팅
  const chartData = {
    labels: myBooks.map(book => book.name),
    datasets: [
      {
        data: myBooks.map(book => book.count),
        backgroundColor: [
          '#69A673',
          '#DF6D91',
          '#FFCE56',
          '#4BC0C0',
          '#9966FF',
          '#FF9F40',
        ],
        hoverBackgroundColor: [
          '#69A673',
          '#DF6D91',
          '#FFCE56',
          '#4BC0C0',
          '#9966FF',
          '#FF9F40',
        ],
      },
    ],
  };

  return (
    <div className='w-3/5 h-5/6 ml-20'>
      {myBooks.length > 0 && (
        <Doughnut
          data={chartData}
          options={{
            responsive: true,
            maintainAspectRatio: false,
            plugins: {
              legend: {
                position: 'top',
                labels: {
                  font: {
                    size: 15, // 범례 폰트 크기 설정 (원하는 크기로 변경하세요)
                  },
                  padding: 7, // 범례 항목과 차트 사이의 간격을 줄입니다. 기본값은 10이지만 더 작게 설정할 수 있습니다.
                },
              },
              title: {
                display: true,
                text: '서재 내 책 카테고리',
                font: {
                  size: 24,
                },
                color: 'black', // 제목 폰트 색상 설정
                padding: {
                  top: 10, // 제목과 차트 사이의 간격 조정
                  bottom: 10, // 범례와 제목 사이의 간격 조정
                },
              },
            },
          }}
        />
      )}
    </div>
  );
};

export default Statistics;
