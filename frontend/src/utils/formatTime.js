import {
  formatDistanceToNow,
  format,
  differenceInMinutes,
  differenceInHours,
  differenceInDays,
} from 'date-fns';
import { ko } from 'date-fns/locale';

export const formatRelativeTime = date => {
  const now = new Date();
  const minutesDiff = differenceInMinutes(now, date);
  const hoursDiff = differenceInHours(now, date);
  const daysDiff = differenceInDays(now, date);

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
    return format(date, 'yyyy년 MM월 dd일', { locale: ko });
  }
};
