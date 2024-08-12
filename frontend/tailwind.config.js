export default {
  content: ['./index.html', './src/**/*.{js,ts,jsx,tsx}'],
  theme: {
    extend: {
      animation: {
        bounce200: 'bounce 1s infinite 200ms',
        bounce400: 'bounce 1s infinite 400ms',
      },
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
    },
  },
  plugins: [
    require('@tailwindcss/line-clamp'),
    function ({ addUtilities }) {
      const newUtilities = {
        '.writing-vertical': {
          writingMode: 'vertical-rl',
          textOrientation: 'upright',
        },
      };

      addUtilities(newUtilities);
    },
  ],
};
