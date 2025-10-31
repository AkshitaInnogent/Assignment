from fastapi import APIRouter, Depends, HTTPException, Query
from sqlalchemy.orm import Session
from sqlalchemy import or_
from product_management_api.database import get_db
from product_management_api.models.product import Product as ProductModel
from product_management_api.schemas import ProductCreate, Product
import logging
from typing import List
from typing import Optional


router = APIRouter(prefix="/products", tags=["products"])
logging.basicConfig(filename="logs/api.log", level=logging.INFO)

@router.post("/", response_model=Product)
def create_product(product: ProductCreate, db: Session = Depends(get_db)):
    db_product = db.query(ProductModel).filter(ProductModel.name == product.name).first()
    if db_product:
        raise HTTPException(status_code=400, detail="Product already exists")
    new_product = ProductModel(**product.dict())
    db.add(new_product)
    db.commit()
    db.refresh(new_product)
    logging.info(f"Created product: {new_product.id}")
    return new_product

@router.get("/", response_model=List[Product])
def read_products(skip: int = 0, limit: int = 10, db: Session = Depends(get_db)):
    products = db.query(ProductModel).offset(skip).limit(limit).all()
    return products

@router.get("/{product_id}", response_model=Product)
def read_product(product_id: int, db: Session = Depends(get_db)):
    product = db.query(ProductModel).filter(ProductModel.id == product_id).first()
    if not product:
        raise HTTPException(status_code=404, detail="Product not found")
    return product

@router.put("/{product_id}", response_model=Product)
def update_product(product_id: int, product: ProductCreate, db: Session = Depends(get_db)):
    db_product = db.query(ProductModel).filter(ProductModel.id == product_id).first()
    if not db_product:
        raise HTTPException(status_code=404, detail="Product not found")
    for key, value in product.dict().items():
        setattr(db_product, key, value)
    db.commit()
    db.refresh(db_product)
    logging.info(f"Updated product: {product_id}")
    return db_product

@router.delete("/{product_id}")
def delete_product(product_id: int, db: Session = Depends(get_db)):
    db_product = db.query(ProductModel).filter(ProductModel.id == product_id).first()
    if not db_product:
        raise HTTPException(status_code=404, detail="Product not found")
    db.delete(db_product)
    db.commit()
    logging.info(f"Deleted product: {product_id}")
    return {"detail": "Product deleted"}

# Search Endpoint
@router.get("/search/", response_model=List[Product])
def search_products(
    q: str = Query(None, description="Search query for name, category, or price"),
    company_id: Optional[int] = Query(None, description="Filter by company ID"),
    skip: int = 0,
    limit: int = 10,
    db: Session = Depends(get_db)
):
    query = db.query(ProductModel)
    if q:
        # Search across name (product), name (category), price (product)
        query = query.join(ProductModel.category).filter(
            or_(
                ProductModel.name.ilike(f"%{q}%"),
                ProductModel.category.name.ilike(f"%{q}%"),  # Assuming category.name is searchable
                ProductModel.price == float(q) if q.isdigit() else False  # Simple price search
            )
        )
    if company_id:
        query = query.filter(ProductModel.company_id == company_id)
    products = query.offset(skip).limit(limit).all()
    return products