import { useInfiniteQuery } from '@tanstack/react-query';
import { getLibrarySearchBooks } from '@services/Library';

const useLibraryInfiniteScroll = (text, tag, limit = 10) => {
  return useInfiniteQuery({
    queryKey: ['library', text, tag],
    queryFn: ({ pageParam = 0 }) =>
      getLibrarySearchBooks(text, tag, pageParam, limit),
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

export default useLibraryInfiniteScroll;
