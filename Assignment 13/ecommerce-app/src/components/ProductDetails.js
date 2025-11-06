import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import './ProductDetails.css';

function ProductDetails() {
  const { id } = useParams();
  const [product, setProduct] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    fetch(`https://fakestoreapi.com/products/${id}`)
      .then((response) => response.json())
      .then((data) => {
        setProduct(data);
        setLoading(false);
      })
      .catch((error) => {
        console.error('Error fetching product:', error);
        setLoading(false);
      });
  }, [id]);

  if (loading) return <div className="container">Loading...</div>;
  if (!product) return <div className="container">Product not found.</div>;

  return (
   <div className="product-details container">
  <img src={product.image} alt={product.title} />
  <div className="content">
    <h1>{product.title}</h1>
    <p>{product.description}</p>
    <p className="price">${product.price}</p>
    <p className="category">{product.category}</p>
    <p className="rating">
      <span className="stars">★★★★★</span> {product.rating.rate} ({product.rating.count} reviews)
    </p>
  </div>
</div>
  );
}

export default ProductDetails;