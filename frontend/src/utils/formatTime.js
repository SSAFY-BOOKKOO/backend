import {
  formatDistanceToNow,
  isToday,
  isYesterday,
  isThisYear,
  format,
  differenceInMinutes,
  differenceInHours,
  differenceInDays,
  addHours,
  isValid,
} from 'date-fns';
import { ko } from 'date-fns/locale';

const TIME_DIFFERENCE = 9; // 서버랑 시간 차이

export const formatRelativeTime = date => {
  const now = new Date();
  const dateObj = new Date(date);

  if (!isValid(dateObj)) {
    return '';
  }

  const adjustedDate = addHours(dateObj, TIME_DIFFERENCE);
  const minutesDiff = differenceInMinutes(now, adjustedDate);
  const hoursDiff = differenceInHours(now, adjustedDate);
  const daysDiff = differenceInDays(now, adjustedDate);

  if (minutesDiff < 60) {
    // 1시간 이내
    return `${minutesDiff}분 전`;
  } else if (hoursDiff < 24) {
    // 1일 이내
    return `${hoursDiff}시간 전`;
  } else if (daysDiff < 7) {
    // 7일 이내
    return `${daysDiff}일 전`;
  } else {
    // 7일 이상
    return format(adjustedDate, 'yyyy년 MM월 dd일', { locale: ko });
  }
};

export const formatChatTime = date => {
  const dateObj = new Date(date);
  if (!isValid(dateObj)) {
    return '';
  }

  const adjustedDate = addHours(dateObj, TIME_DIFFERENCE);

  if (isToday(adjustedDate)) {
    // 오늘: 시:분 (오전/오후)
    return format(adjustedDate, 'a h:mm', { locale: ko });
  } else if (isYesterday(adjustedDate)) {
    // 어제
    return '어제';
  } else if (isThisYear(adjustedDate)) {
    // 올해 내의 다른 날: 월/일
    return format(adjustedDate, 'M월 d일');
  } else {
    // 작년 이전: 년/월/일
    return format(adjustedDate, 'yyyy년 M월 d일');
  }
};
