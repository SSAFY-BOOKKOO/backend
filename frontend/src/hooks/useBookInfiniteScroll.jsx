import { useInfiniteQuery } from '@tanstack/react-query';
import { getAladinBooks } from '@services/Book';

const useBookInfiniteScroll = (text, tag) => {
  return useInfiniteQuery({
    queryKey: ['books', text, tag],
    queryFn: ({ pageParam = 1 }) => getAladinBooks(text, tag, pageParam, 10),
    getNextPageParam: lastPage => {
      const { totalResults, startIndex, itemsPerPage } = lastPage;
      const nextIndex = startIndex + 1;
      console.log(nextIndex, totalResults, startIndex);
      return (nextIndex - 1) * itemsPerPage < totalResults
        ? nextIndex
        : undefined;
    },
    select: data => ({
      pages: data.pages.map(page => ({
        data: page.item,
        totalResults: page.totalResults,
        startIndex: page.startIndex,
        itemsPerPage: page.itemsPerPage,
      })),
      pageParams: data.pageParams,
    }),
  });
};

export default useBookInfiniteScroll;
