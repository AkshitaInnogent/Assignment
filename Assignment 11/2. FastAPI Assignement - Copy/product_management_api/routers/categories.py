from fastapi import APIRouter, HTTPException, status
from typing import List
import logging
from product_management_api.schemas import CategoryCreate, Category
from product_management_api.database import prisma  # ✅ shared Prisma

router = APIRouter(prefix="/categories", tags=["categories"])

logging.basicConfig(
    filename="logs/api.log",
    level=logging.INFO,
    format="%(asctime)s - %(levelname)s - %(message)s"
)

@router.post("/", response_model=Category, status_code=status.HTTP_201_CREATED)
async def create_category(category: CategoryCreate):
    existing = await prisma.category.find_first(where={"name": category.name})
    if existing:
        raise HTTPException(status_code=400, detail="Category with this name already exists")

    new_category = await prisma.category.create(data=category.model_dump())
    logging.info(f"Created category: ID={new_category.id}, Name={new_category.name}")
    return new_category

@router.get("/", response_model=List[Category])
async def read_categories(skip: int = 0, limit: int = 10):
    categories = await prisma.category.find_many(skip=skip, take=limit)
    logging.info(f"Fetched {len(categories)} categories.")
    return categories

@router.get("/{category_id}", response_model=Category)
async def read_category(category_id: int):
    category = await prisma.category.find_unique(where={"id": category_id})
    if not category:
        raise HTTPException(status_code=404, detail="Category not found")
    return category

@router.put("/{category_id}", response_model=Category)
async def update_category(category_id: int, category: CategoryCreate):
    existing = await prisma.category.find_unique(where={"id": category_id})
    if not existing:
        raise HTTPException(status_code=404, detail="Category not found")

    name_conflict = await prisma.category.find_first(
        where={
            "AND": [
                {"name": category.name},
                {"NOT": {"id": category_id}}
            ]
        }
    )
    if name_conflict:
        raise HTTPException(status_code=400, detail="Another category with this name already exists")

    updated = await prisma.category.update(where={"id": category_id}, data=category.model_dump())
    logging.info(f"Updated category ID={category_id}")
    return updated

@router.delete("/{category_id}")
async def delete_category(category_id: int):
    existing = await prisma.category.find_unique(where={"id": category_id})
    if not existing:
        raise HTTPException(status_code=404, detail="Category not found")

    await prisma.category.delete(where={"id": category_id})
    logging.info(f"Deleted category ID={category_id}")
    return {"detail": "Category deleted successfully"}
