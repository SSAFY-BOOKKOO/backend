import React, { useState, useEffect } from 'react';
import { useNavigate, useLocation } from 'react-router-dom';
import { DndProvider } from 'react-dnd';
import { MultiBackend, TouchTransition } from 'react-dnd-multi-backend';
import { HTML5Backend } from 'react-dnd-html5-backend';
import { TouchBackend } from 'react-dnd-touch-backend';
import MemberProfile from '@components/Library/Main/MemberProfile';
import LibraryOptions from '@components/Library/Main/LibraryOptions';
import BookShelf from '@components/Library/Main/BookShelf';
import Spinner from '@components/@common/Spinner';
import { authAxiosInstance } from '@services/axiosInstance';

const HTML5toTouch = {
  backends: [
    {
      id: 'html5',
      backend: HTML5Backend,
    },
    {
      id: 'touch',
      backend: TouchBackend,
      options: { enableMouseEvents: true },
      preview: true,
      transition: TouchTransition,
    },
  ],
};

const LibraryOthers = () => {
  const [activeLibrary, setActiveLibrary] = useState(0);
  const [isLoading, setIsLoading] = useState(true);
  const navigate = useNavigate();
  const location = useLocation();
  const [member, setMember] = useState(null);
  const [libraries, setLibraries] = useState([]);

  useEffect(() => {
    const nickname = location.state?.nickname;
    const fetchLibraries = async () => {
      try {
        if (nickname) {
          const response = await authAxiosInstance.get(`/libraries`, {
            params: { nickname },
          });
          const libraries = response.data;
          const libraryDetailsPromises = libraries.map(library =>
            authAxiosInstance.get(`/libraries/${library.id}`)
          );
          const librariesDetails = await Promise.all(libraryDetailsPromises);
          setLibraries(librariesDetails.map(response => response.data));
        }
      } catch (error) {
        console.error(error);
      } finally {
        setIsLoading(false);
      }
    };

    fetchLibraries();
  }, [location.state?.nickname]);

  useEffect(() => {
    const fetchMemberInfo = async () => {
      try {
        const nickname = location.state?.nickname;
        const response = await authAxiosInstance.get(
          `/members/info/name/${nickname}`,
          {
            params: { nickname },
          }
        );
        setMember(response.data);
      } catch (error) {
        console.error(error);
      }
    };

    fetchMemberInfo();
  }, [location.state?.nickname]);

  const handleBookClick = item => {
    navigate(`/library/${libraries[activeLibrary].id}/detail/${item.book.id}`, {
      state: { nickname: location.state.nickname },
    });
  };

  if (isLoading) {
    return (
      <div className='flex items-center justify-center min-h-screen'>
        <Spinner />
      </div>
    );
  }

  return (
    <DndProvider backend={MultiBackend} options={HTML5toTouch}>
      <div className='bg-white min-h-screen'>
        {member && <MemberProfile member={member} />}

        <LibraryOptions
          activeLibrary={activeLibrary}
          setActiveLibrary={setActiveLibrary}
          libraries={libraries}
          viewOnly={true}
        />

        {libraries.length > 0 && (
          <BookShelf
            books={libraries[activeLibrary]?.books || []}
            onBookClick={handleBookClick}
            viewOnly={true}
            libraryStyleDto={libraries[activeLibrary]?.libraryStyleDto}
          />
        )}
      </div>
    </DndProvider>
  );
};

export default LibraryOthers;
