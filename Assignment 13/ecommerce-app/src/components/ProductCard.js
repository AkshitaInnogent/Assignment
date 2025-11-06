import React from 'react';
import { useNavigate } from 'react-router-dom';
import './ProductCard.css';

function ProductCard({ product }) {
  const navigate = useNavigate();

  const handleClick = () => {
    navigate(`/product/${product.id}`);
  };

  return (
    <div className="product-card" onClick={handleClick}>
        <div style={{position: 'relative'}}>
            <img src={product.image} alt={product.title} />
        </div>
        <div className="info">
            <h3>{product.title}</h3>
            <p>${product.price}</p>
        </div>
    </div>
  );
}

export default ProductCard;