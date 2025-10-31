from sqlalchemy import Column, Integer, String, Float, ForeignKey
from sqlalchemy.orm import relationship
from product_management_api.database import Base

class Product(Base):
    __tablename__ = "products"

    id = Column(Integer, primary_key=True, index=True)
    name = Column(String, index=True)
    price = Column(Float, nullable=False)
    company_id = Column(Integer, ForeignKey("companies.id"))
    category_id = Column(Integer, ForeignKey("categories.id"))

    # Relationships
    company = relationship("Company", back_populates="products")
    category = relationship("Category", back_populates="products")
