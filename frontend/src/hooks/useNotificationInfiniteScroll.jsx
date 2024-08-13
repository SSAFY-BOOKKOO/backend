import { useInfiniteQuery } from '@tanstack/react-query';
import { getNotifications } from '@services/Member';

const useNotificationInfiniteScroll = () => {
  return useInfiniteQuery({
    queryKey: ['notifications'],
    queryFn: ({ pageParam = 0 }) => getNotifications(pageParam, 10),
    getNextPageParam: (lastPage, allPages) => {
      if (lastPage.length === 0) {
        return undefined;
      }
      return allPages.length;
    },
    refetchOnWindowFocus: false,
    refetchOnMount: false,
  });
};

export default useNotificationInfiniteScroll;
