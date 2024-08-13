import { useInfiniteQuery } from '@tanstack/react-query';
import { getQuoteList } from '@services/Member';

const useQuoteInfiniteScroll = () => {
  return useInfiniteQuery({
    queryKey: ['quotes'],
    queryFn: ({ pageParam = 0 }) => getQuoteList(pageParam, 10),
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

export default useQuoteInfiniteScroll;
