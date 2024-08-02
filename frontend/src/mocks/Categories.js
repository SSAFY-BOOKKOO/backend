const categoriesMapping = {
  '추리/스릴러': 0,
  로맨스: 1,
  인문학: 2,
  철학: 3,
  '경제/경영': 4,
  역사: 5,
  시: 6,
  에세이: 7,
  소설: 8,
  과학: 9,
  사회과학: 10,
  자기계발: 11,
  기타: 12,
};

const categoriesList = Object.keys(categoriesMapping);

const getCategoryNumber = categoryName => categoriesMapping[categoryName];

const getCategoryName = categoryNumber => {
  return Object.keys(categoriesMapping).find(
    key => categoriesMapping[key] === categoryNumber
  );
};

export { categoriesList, getCategoryNumber, getCategoryName };
