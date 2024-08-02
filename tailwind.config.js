/** @type {import('tailwindcss').Config} */
export default {
  content: ['./index.html', './src/**/*.{js,ts,jsx,tsx}'],
  theme: {
    extend: {
      colors: {
        pink: {
          50: '#fcf3f6',
          100: '#fae9f0',
          200: '#f6d4e1',
          300: '#f0b1c8',
          400: '#e680a3',
          500: '#df6d91',
          600: '#c83a60',
          700: '#ac2a49',
          800: '#8f253d',
          900: '#782336',
          950: '#480f1c',
        },
        green: {
          50: '#f1f8f2',
          100: '#ddeedd',
          200: '#bedcc0',
          300: '#93c299',
          400: '#69a673',
          500: '#438650',
          600: '#316a3d',
          700: '#275532',
          800: '#21442a',
          900: '#1c3824',
          950: '#0f1f13',
        },
      },
      fontsize: {
        '9xl': '14rem', // '6xl'을 원하는 폰트 크기로 설정
      },
    },
  },
  plugins: [
    // writing-vertical 유틸리티 클래스를 추가하기 위한 플러그인
    function ({ addUtilities }) {
      const newUtilities = {
        '.writing-vertical': {
          writingMode: 'vertical-rl', // 글씨를 세로로 쓰기 위해 writing-mode 설정
          textOrientation: 'upright', // 글씨가 위로 직립하도록 설정
        },
      };

      addUtilities(newUtilities);
    },
  ],
};
