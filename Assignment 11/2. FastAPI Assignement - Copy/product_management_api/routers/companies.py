from fastapi import APIRouter, HTTPException
from typing import List
import logging
from product_management_api.schemas import CompanyCreate, Company
from product_management_api.database import prisma  # ✅ shared instance

router = APIRouter(prefix="/companies", tags=["companies"])

logging.basicConfig(filename="logs/api.log", level=logging.INFO)

@router.post("/", response_model=Company)
async def create_company(company: CompanyCreate):
    existing = await prisma.company.find_first(where={"name": company.name})
    if existing:
        raise HTTPException(status_code=400, detail="Company already exists")

    new_company = await prisma.company.create(data=company.model_dump())
    logging.info(f"Created company ID={new_company.id}")
    return new_company

@router.get("/", response_model=List[Company])
async def read_companies(skip: int = 0, limit: int = 10):
    return await prisma.company.find_many(skip=skip, take=limit)

@router.get("/{company_id}", response_model=Company)
async def read_company(company_id: int):
    company = await prisma.company.find_unique(where={"id": company_id})
    if not company:
        raise HTTPException(status_code=404, detail="Company not found")
    return company

@router.put("/{company_id}", response_model=Company)
async def update_company(company_id: int, company: CompanyCreate):
    existing = await prisma.company.find_unique(where={"id": company_id})
    if not existing:
        raise HTTPException(status_code=404, detail="Company not found")

    updated = await prisma.company.update(where={"id": company_id}, data=company.model_dump())
    logging.info(f"Updated company ID={company_id}")
    return updated

@router.delete("/{company_id}")
async def delete_company(company_id: int):
    existing = await prisma.company.find_unique(where={"id": company_id})
    if not existing:
        raise HTTPException(status_code=404, detail="Company not found")

    await prisma.company.delete(where={"id": company_id})
    logging.info(f"Deleted company ID={company_id}")
    return {"detail": "Company deleted successfully"}
