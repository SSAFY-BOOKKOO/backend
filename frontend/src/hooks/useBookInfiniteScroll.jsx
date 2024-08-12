import { useInfiniteQuery } from '@tanstack/react-query';
import { getAladinBooks } from '@services/Book';

const useBookInfiniteScroll = (text, tag) => {
  return useInfiniteQuery({
    queryKey: ['books', text, tag],
    queryFn: ({ pageParam = 1 }) => getAladinBooks(text, tag, pageParam, 10),
    getNextPageParam: lastPage => {
      const { totalResults, startIndex, itemsPerPage } = lastPage;
      const nextIndex = startIndex + 1;
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
    // 검색어가 있을 때만 쿼리 활성화
    enabled: !!text && text.trim() !== '',
    refetchOnWindowFocus: false,
    refetchOnMount: false,
  });
};

export default useBookInfiniteScroll;
