import { useInfiniteQuery } from '@tanstack/react-query';
import { getBookTalkChats } from '@services/BookTalk';

const useBookTalkChatsInfiniteScroll = bookTalkId => {
  return useInfiniteQuery({
    queryKey: ['bookTalkChats', bookTalkId],
    queryFn: ({ pageParam = '' }) => getBookTalkChats(bookTalkId, pageParam),
    getNextPageParam: lastPage => {
      if (lastPage.length < 10) {
        return undefined;
      }
      return lastPage[lastPage.length - 1].createdAt;
    },
    initialPageParam: '',
    refetchOnWindowFocus: false,
    refetchOnMount: true,
    cacheTime: 0,
    staleTime: 0,
  });
};

export default useBookTalkChatsInfiniteScroll;
