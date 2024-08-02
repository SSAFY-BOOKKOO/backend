const WrapContainer = ({ children, className = '' }) => {
  return (
    <div className={`w-full px-4 py-1 box-border ${className}`}>{children}</div>
  );
};

export default WrapContainer;
