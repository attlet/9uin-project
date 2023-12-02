import './App.css';
import { Outlet } from 'react-router-dom';
import Header from './ components/header/Header';
import Footer from './ components/header/Footer';

function App() {
  return (
    <>
      <Header />
      <div className="content">
        <Outlet />
      </div>
      <Footer />
    </>
  );
}

export default App;
