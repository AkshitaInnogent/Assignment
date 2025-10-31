from fastapi import APIRouter, HTTPException, Query
from typing import List, Optional
import logging
from product_management_api.schemas import ProductCreate, Product
from product_management_api.database import prisma  # ✅ shared Prisma client

router = APIRouter(prefix="/products", tags=["products"])

logging.basicConfig(
    filename="logs/api.log",
    level=logging.INFO,
    format="%(asctime)s - %(levelname)s - %(message)s"
)

@router.post("/", response_model=Product)
async def create_product(product: ProductCreate):
    existing = await prisma.product.find_first(where={"name": product.name})
    if existing:
        raise HTTPException(status_code=400, detail="Product already exists")

    new_product = await prisma.product.create(data=product.model_dump())
    logging.info(f"Created product ID={new_product.id}")
    return new_product

@router.get("/", response_model=List[Product])
async def read_products(skip: int = 0, limit: int = 10):
    return await prisma.product.find_many(
        skip=skip,
        take=limit,
        include={"category": True, "company": True}
    )

@router.get("/{product_id}", response_model=Product)
async def read_product(product_id: int):
    product = await prisma.product.find_unique(
        where={"id": product_id},
        include={"category": True, "company": True}
    )
    if not product:
        raise HTTPException(status_code=404, detail="Product not found")
    return product

@router.put("/{product_id}", response_model=Product)
async def update_product(product_id: int, product: ProductCreate):
    existing = await prisma.product.find_unique(where={"id": product_id})
    if not existing:
        raise HTTPException(status_code=404, detail="Product not found")

    updated = await prisma.product.update(
        where={"id": product_id},
        data=product.model_dump()
    )
    logging.info(f"Updated product ID={product_id}")
    return updated

@router.delete("/{product_id}")
async def delete_product(product_id: int):
    existing = await prisma.product.find_unique(where={"id": product_id})
    if not existing:
        raise HTTPException(status_code=404, detail="Product not found")

    await prisma.product.delete(where={"id": product_id})
    logging.info(f"Deleted product ID={product_id}")
    return {"detail": "Product deleted successfully"}

@router.get("/search/", response_model=List[Product])
async def search_products(
    q: Optional[str] = Query(None, description="Search by name or price"),
    company_id: Optional[int] = Query(None, description="Filter by company ID"),
    skip: int = 0,
    limit: int = 10
):
    filters = {}

    if q:
        filters["OR"] = [
            {"name": {"contains": q, "mode": "insensitive"}},
            {"price": float(q)} if q.replace(".", "", 1).isdigit() else {}
        ]

    if company_id:
        filters["company_id"] = company_id

    products = await prisma.product.find_many(
        where=filters,
        skip=skip,
        take=limit,
        include={"category": True, "company": True}
    )
    return products
