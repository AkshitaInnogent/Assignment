from fastapi import APIRouter, Depends, HTTPException
from sqlalchemy.orm import Session
from product_management_api.database import get_db
from product_management_api.models.company import Company as CompanyModel
from product_management_api.schemas import CompanyCreate, Company
import logging
from typing import List


router = APIRouter(prefix="/companies", tags=["companies"])
logging.basicConfig(filename="logs/api.log", level=logging.INFO)

@router.post("/", response_model=Company)
def create_company(company: CompanyCreate, db: Session = Depends(get_db)):
    db_company = db.query(CompanyModel).filter(CompanyModel.name == company.name).first()
    if db_company:
        raise HTTPException(status_code=400, detail="Company already exists")
    new_company = CompanyModel(**company.dict())
    db.add(new_company)
    db.commit()
    db.refresh(new_company)
    logging.info(f"Created company: {new_company.id}")
    return new_company

@router.get("/", response_model=List[Company])
def read_companies(skip: int = 0, limit: int = 10, db: Session = Depends(get_db)):
    companies = db.query(CompanyModel).offset(skip).limit(limit).all()
    return companies

@router.get("/{company_id}", response_model=Company)
def read_company(company_id: int, db: Session = Depends(get_db)):
    company = db.query(CompanyModel).filter(CompanyModel.id == company_id).first()
    if not company:
        raise HTTPException(status_code=404, detail="Company not found")
    return company

@router.put("/{company_id}", response_model=Company)
def update_company(company_id: int, company: CompanyCreate, db: Session = Depends(get_db)):
    db_company = db.query(CompanyModel).filter(CompanyModel.id == company_id).first()
    if not db_company:
        raise HTTPException(status_code=404, detail="Company not found")
    for key, value in company.dict().items():
        setattr(db_company, key, value)
    db.commit()
    db.refresh(db_company)
    logging.info(f"Updated company: {company_id}")
    return db_company

@router.delete("/{company_id}")
def delete_company(company_id: int, db: Session = Depends(get_db)):
    db_company = db.query(CompanyModel).filter(CompanyModel.id == company_id).first()
    if not db_company:
        raise HTTPException(status_code=404, detail="Company not found")
    db.delete(db_company)
    db.commit()
    logging.info(f"Deleted company: {company_id}")
    return {"detail": "Company deleted"}