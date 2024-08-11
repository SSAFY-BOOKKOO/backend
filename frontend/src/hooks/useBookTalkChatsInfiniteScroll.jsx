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
      return lastPage[0].createdAt;
    },
    initialPageParam: '',
  });
};

export default useBookTalkChatsInfiniteScroll;
