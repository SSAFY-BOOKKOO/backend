import { useInfiniteQuery } from '@tanstack/react-query';
import { postBookTalkSearch } from '@services/BookTalk';

const useBookTalkSearchInfiniteScroll = (text, tag, limit = 10) => {
  return useInfiniteQuery({
    queryKey: ['booktalk', text, tag],
    queryFn: ({ pageParam = 0 }) =>
      postBookTalkSearch(text, tag, pageParam, limit),
    getNextPageParam: (lastPage, allPages) => {
      if (lastPage.length < limit) {
        return undefined;
      }
      return allPages.length * limit;
    },
    select: data => ({
      pages: data.pages.map(page => ({ data: page })),
    }),
    refetchOnWindowFocus: false,
    refetchOnMount: false,
  });
};

export default useBookTalkSearchInfiniteScroll;
