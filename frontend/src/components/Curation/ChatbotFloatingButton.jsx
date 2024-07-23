import { TbMessageChatbot } from 'react-icons/tb';
import { useNavigate } from 'react-router-dom';

const ChatbotFloatingButton = () => {
  const navigate = useNavigate();

  const handleChatBotPage = () => {
    navigate('/curation/chatbot');
  };

  return (
    <div className='flex justify-end'>
      <div className='fixed bottom-20 z-10 cursor-pointer'>
        <button
          className='font-bold p-4 bg-gray-50 rounded-full shadow-lg mr-4'
          onClick={handleChatBotPage}
        >
          <TbMessageChatbot className='w-10 h-10' />
        </button>
      </div>
    </div>
  );
};

export default ChatbotFloatingButton;
