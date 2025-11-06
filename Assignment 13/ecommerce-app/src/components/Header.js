import React from 'react';
import './Header.css';
import { Link } from 'react-router-dom';
import { BsCart2 } from "react-icons/bs";
import { IoIosNotificationsOutline } from "react-icons/io";
import { CgProfile } from "react-icons/cg";
import { IoHome } from "react-icons/io5";




function Header() {
  const handleCartClick = () => alert('Cart clicked!');
  const handleNotificationsClick = () => alert('Notifications clicked!');
  const handleProfileClick = () => alert('Profile clicked!');

  return (
    <header className="header">
      <div className="container header-content">
       <div className='container-1'> <nav className="nav-left">
            <Link to="/"><IoHome size={26} title='home'/></Link>
           </nav>
      </div>
      <div className='container-2'>  
        <div className="logo">
          <Link to="/">NexCart</Link>
        </div>
        <div className="icons">
          <button onClick={handleCartClick} aria-label="Cart">
            <BsCart2 size={20} title='cart'></BsCart2>
          </button>
          <button onClick={handleNotificationsClick} aria-label="Notifications">
            <IoIosNotificationsOutline size={25} title='notification'></IoIosNotificationsOutline>
          </button>
          <button onClick={handleProfileClick} aria-label="Profile">
            <CgProfile size={24} title='profile'></CgProfile>
          </button>
        </div>
      </div>
      </div>
    </header>
  );
}

export default Header;