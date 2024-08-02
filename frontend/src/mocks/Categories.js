const categoriesMapping = [
  { id: 1, name: '추리/스릴러' },
  { id: 2, name: '판타지' },
  { id: 3, name: '로맨스' },
  { id: 4, name: '인문학' },
  { id: 5, name: '철학' },
  { id: 6, name: '경제/경영' },
  { id: 7, name: '역사' },
  { id: 8, name: '시' },
  { id: 9, name: '소설' },
  { id: 10, name: '사회' },
  { id: 11, name: '과학/기술' },
  { id: 12, name: '교육' },
  { id: 13, name: '자기계발' },
  { id: 14, name: '에세이' },
  { id: 15, name: '기타' },
];

const categoriesList = categoriesMapping.map(category => category.name);

const getCategoryNumber = categoryName => {
  const category = categoriesMapping.find(
    category => category.name === categoryName
  );
  return category ? category.id : null;
};

const getCategoryName = categoryNumber => {
  const category = categoriesMapping.find(
    category => category.id === categoryNumber
  );
  return category ? category.name : null;
};

export { categoriesList, getCategoryNumber, getCategoryName };
