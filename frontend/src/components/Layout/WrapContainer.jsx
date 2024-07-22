const WrapContainer = ({ children, className }) => {
  return (
    <div className={`w-full px-4 box-border ${className}`}>{children}</div>
  );
};

export default WrapContainer;
