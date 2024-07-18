import React from "react";
import { useNavigate } from "react-router-dom";

import community from '../../assets/community.png';
import curation from '../../assets/curation.png';
import library from '../../assets/library.png';
import myPage from '../../assets/mypage.png';

const Footer = () => {
  const navigate = useNavigate();

  const navigateTo = (path) => {
    navigate(path);
  };

  return (
    <div className="flex justify-around items-center py-2.5 bg-white border-t">
      <img
        src={library}
        alt='library'
        className="cursor-pointer w-8 h-8"
        onClick={() => navigateTo('/')}
      />
      <img
        src={community}
        alt='community'
        className="cursor-pointer w-8 h-8"
        onClick={() => navigateTo('/community')}
      />
      <img
        src={curation}
        alt='curation'
        className="cursor-pointer w-8 h-8"
        onClick={() => navigateTo('/curation')}
      />
      <img
        src={myPage}
        alt='mypage'
        className="cursor-pointer w-8 h-8"
        onClick={() => navigateTo('/mypage')}
      />
    </div>
  );
};

export default Footer;
