/** @type {import('tailwindcss').Config} */
export default {
  content: ['./index.html', './src/**/*.{js,ts,jsx,tsx}'],
  theme: {
    extend: {},
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
