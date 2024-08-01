// utils/validateForm.js
export const validateForm = (formData, validationConfig) => {
  const newErrors = {};

  if (validationConfig.email) {
    if (!formData.email) newErrors.email = '이메일을 입력하세요.';
    if (
      !/^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/.test(formData.email)
    ) {
      newErrors.email = '올바른 이메일 형식을 입력하세요.';
    }
  }

  if (validationConfig.password) {
    if (!formData.password) newErrors.password = '비밀번호를 입력하세요.';
    if (
      !/^(?=.*[a-zA-Z])(?=.*\d)(?=.*[!@#$%^&*]).{8,16}$/.test(formData.password)
    ) {
      newErrors.password =
        '비밀번호는 영문, 숫자, 특수문자 조합으로 이루어진 8~16자여야 합니다.';
    }
  }

  if (validationConfig.confirmPassword) {
    if (formData.password !== formData.confirmPassword) {
      newErrors.confirmPassword = '비밀번호가 일치하지 않습니다.';
    }
  }

  if (validationConfig.nickname) {
    if (!formData.nickname) newErrors.nickname = '닉네임을 입력하세요.';
    if (formData.nickname.length > 10) {
      newErrors.nickname = '닉네임은 10자 이내로 설정해야 합니다.';
    }
  }

  if (validationConfig.year && !formData.year)
    newErrors.year = '연령을 입력하세요.';
  if (validationConfig.gender && !formData.gender)
    newErrors.gender = '성별을 선택하세요.';
  if (validationConfig.categories && formData.categories.length === 0) {
    newErrors.categories = '선호 카테고리를 선택하세요.';
  }
  if (validationConfig.introduction && !formData.introduction) {
    newErrors.introduction = '소개글을 입력하세요.';
  }

  return newErrors;
};
