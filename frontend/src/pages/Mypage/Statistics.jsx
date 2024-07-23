import React from 'react';
import { Doughnut } from 'react-chartjs-2';
import { Chart, ArcElement, Tooltip, Legend } from 'chart.js';
import 'chart.js/auto';

Chart.register(ArcElement, Tooltip, Legend);

const Statistics = ({ books }) => {
  // 더미 데이터 예제
  const dummyData = [
    { genre: 'Fantasy', count: 10 },
    { genre: 'Science Fiction', count: 5 },
    { genre: 'Romance', count: 8 },
    { genre: 'Horror', count: 3 },
    { genre: 'Non-fiction', count: 4 },
  ];

  const totalBooks = dummyData.reduce((acc, item) => acc + item.count, 0);

  // Chart.js 데이터 설정
  const data = {
    labels: dummyData.map(item => item.genre),
    datasets: [
      {
        data: dummyData.map(item => item.count),
        backgroundColor: [
          '#FF6384',
          '#36A2EB',
          '#FFCE56',
          '#FF9F40',
          '#4BC0C0',
        ],
        borderWidth: 1,
      },
    ],
  };

  // Chart.js 옵션 설정
  const options = {
    plugins: {
      tooltip: {
        callbacks: {
          label: function (context) {
            const label = context.label || '';
            const value = context.raw || 0;
            const percentage = ((value / totalBooks) * 100).toFixed(2) + '%';
            return `${label}: ${percentage}`;
          },
        },
      },
      legend: {
        display: true,
        position: 'bottom',
      },
    },
  };

  return (
    <div className='p-8'>
      <h2 className='text-2xl font-bold mb-6'>읽은 책 통계</h2>
      <div className='w-2/3 mx-auto'>
        <Doughnut data={data} options={options} />
      </div>
    </div>
  );
};

export default Statistics;
