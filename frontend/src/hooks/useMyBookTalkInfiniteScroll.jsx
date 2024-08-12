import { useInfiniteQuery } from '@tanstack/react-query';
import { getMyBookTalk } from '@services/BookTalk';

const useMyBookTalkInfiniteScroll = (order = 'time') => {
  return useInfiniteQuery({
    queryKey: ['myBookTalks', order],
    queryFn: ({ pageParam = 0 }) => getMyBookTalk(order, pageParam),
    getNextPageParam: (lastPage, allPages) => {
      if (lastPage.length < 10) {
        return undefined;
      }
      return allPages.length * 10;
    },
    select: data => ({
      pages: data.pages.map(page => ({ data: page })),
    }),
    refetchOnWindowFocus: false,
    refetchOnMount: false,
  });
};

export default useMyBookTalkInfiniteScroll;
