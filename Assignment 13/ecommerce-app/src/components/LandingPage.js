import React, { useState, useEffect } from 'react';
import ProductCard from './ProductCard';
import './LandingPage.css';

function LandingPage() {
  const [products, setProducts] = useState([]);
  const [filteredProducts, setFilteredProducts] = useState([]);
  const [categories, setCategories] = useState([]);
  const [searchTerm, setSearchTerm] = useState('');
  const [selectedCategory, setSelectedCategory] = useState('');

  useEffect(() => {
    fetch('https://fakestoreapi.com/products')
      .then((response) => response.json())
      .then((data) => {
        setProducts(data);
        setFilteredProducts(data);
        const uniqueCategories = [...new Set(data.map((product) => product.category))];
        setCategories(uniqueCategories);
      })
      .catch((error) => console.error('Error fetching products:', error));
  }, []);

  useEffect(() => {
    let filtered = products.filter((product) =>
      product.title.toLowerCase().includes(searchTerm.toLowerCase())
    );
    if (selectedCategory) {
      filtered = filtered.filter((product) => product.category === selectedCategory);
    }
    setFilteredProducts(filtered);
  }, [searchTerm, selectedCategory, products]);

  const groupedProducts = filteredProducts.reduce((acc, product) => {
    if (!acc[product.category]) acc[product.category] = [];
    acc[product.category].push(product);
    return acc;
  }, {});

  return (
    <div className="landing-page container">
      <h1>Products</h1>
      <div className="filters">
        <input
          type="text"
          placeholder="Search products..."
          value={searchTerm}
          onChange={(e) => setSearchTerm(e.target.value)}
        />
        <select value={selectedCategory} onChange={(e) => setSelectedCategory(e.target.value)}>
          <option value="">All Categories</option>
          {categories.map((category) => (
            <option key={category} value={category}>
              {category}
            </option>
          ))}
        </select>
      </div>


      {filteredProducts.length === 0 ? (
        <p className="no-products">No products found.</p>
      ) : (
        Object.keys(groupedProducts).map((category) => (
          <div key={category} className="category-section">
            <h2>{category}</h2>
            <div className="products-grid">
              {groupedProducts[category].map((product) => (
                <ProductCard key={product.id} product={product} />
              ))}
            </div>
          </div>
        ))
      )}
    </div>
  );
}

export default LandingPage;