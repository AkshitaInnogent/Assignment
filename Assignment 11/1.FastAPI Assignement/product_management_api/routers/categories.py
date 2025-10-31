from fastapi import APIRouter, Depends, HTTPException, status
from sqlalchemy.orm import Session
from typing import List
from product_management_api.database import get_db
# from product_management_api.models import Category as CategoryModel
from product_management_api.schemas import CategoryCreate, Category
from product_management_api.models.category import Category as CategoryModel
import logging

# Configure logging
logging.basicConfig(
    filename="logs/api.log",
    level=logging.INFO,
    format="%(asctime)s - %(levelname)s - %(message)s"
)

router = APIRouter(
    prefix="/categories",
    tags=["categories"]
)

@router.post("/", response_model=Category, status_code=status.HTTP_201_CREATED)
def create_category(category: CategoryCreate, db: Session = Depends(get_db)):
    """
    Create a new category. Prevents duplicates by name.
    """
    db_category = db.query(CategoryModel).filter(CategoryModel.name == category.name).first()
    if db_category:
        raise HTTPException(
            status_code=400,
            detail="Category with this name already exists"
        )
    
    new_category = CategoryModel(**category.dict())
    db.add(new_category)
    db.commit()
    db.refresh(new_category)
    
    logging.info(f"Created category: ID={new_category.id}, Name='{new_category.name}'")
    return new_category


@router.get("/", response_model=List[Category])
def read_categories(skip: int = 0, limit: int = 10, db: Session = Depends(get_db)):
    """
    Retrieve a paginated list of categories.
    """
    categories = db.query(CategoryModel).offset(skip).limit(limit).all()
    logging.info(f"Retrieved categories: skip={skip}, limit={limit}, count={len(categories)}")
    return categories


@router.get("/{category_id}", response_model=Category)
def read_category(category_id: int, db: Session = Depends(get_db)):
    """
    Get a single category by ID.
    """
    category = db.query(CategoryModel).filter(CategoryModel.id == category_id).first()
    if not category:
        raise HTTPException(
            status_code=404,
            detail="Category not found"
        )
    
    logging.info(f"Retrieved category: ID={category_id}")
    return category


@router.put("/{category_id}", response_model=Category)
def update_category(category_id: int, category: CategoryCreate, db: Session = Depends(get_db)):
    """
    Update an existing category.
    """
    db_category = db.query(CategoryModel).filter(CategoryModel.id == category_id).first()
    if not db_category:
        raise HTTPException(
            status_code=404,
            detail="Category not found"
        )
    
    # Check for name conflict with other categories
    existing = db.query(CategoryModel).filter(
        CategoryModel.name == category.name,
        CategoryModel.id != category_id
    ).first()
    if existing:
        raise HTTPException(
            status_code=400,
            detail="Another category with this name already exists"
        )
    
    for key, value in category.dict().items():
        setattr(db_category, key, value)
    
    db.commit()
    db.refresh(db_category)
    
    logging.info(f"Updated category: ID={category_id}, New Name='{category.name}'")
    return db_category


@router.delete("/{category_id}", status_code=status.HTTP_200_OK)
def delete_category(category_id: int, db: Session = Depends(get_db)):
    """
    Delete a category by ID.
    Note: This will fail if products are still linked (due to foreign key constraint).
    """
    db_category = db.query(CategoryModel).filter(CategoryModel.id == category_id).first()
    if not db_category:
        raise HTTPException(
            status_code=404,
            detail="Category not found"
        )
    
    db.delete(db_category)
    db.commit()
    
    logging.info(f"Deleted category: ID={category_id}")
    return {"detail": "Category deleted successfully"}