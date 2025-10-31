from pydantic import BaseModel
from typing import Optional, List

# Company Schemas
class CompanyBase(BaseModel):
    name: str
    description: Optional[str] = None

class CompanyCreate(CompanyBase):
    pass
from pydantic import BaseModel
from typing import Optional, List

# ----------------------------
# Company Schemas
# ----------------------------
class CompanyBase(BaseModel):
    name: str
    description: Optional[str] = None

class CompanyCreate(CompanyBase):
    pass

class Company(CompanyBase):
    id: int
    class Config:
        from_attributes = True


# ----------------------------
# Category Schemas
# ----------------------------
class CategoryBase(BaseModel):
    name: str

class CategoryCreate(CategoryBase):
    pass

class Category(CategoryBase):
    id: int
    class Config:
        from_attributes = True


# ----------------------------
# Product Schemas
# ----------------------------
class ProductBase(BaseModel):
    name: str
    price: float
    company_id: int
    category_id: int

class ProductCreate(ProductBase):
    pass

# Nested relation types for response
class Product(ProductBase):
    id: int
    category: Optional[Category] = None
    company: Optional[Company] = None

    class Config:
        from_attributes = True

class Company(CompanyBase):
    id: int
    class Config:
        from_attributes = True  # For ORM compatibility

# Category Schemas
class CategoryBase(BaseModel):
    name: str

class CategoryCreate(CategoryBase):
    pass

class Category(CategoryBase):
    id: int
    class Config:
        from_attributes = True

# Product Schemas
class ProductBase(BaseModel):
    name: str
    price: float
    company_id: int
    category_id: int

class ProductCreate(ProductBase):
    pass

class Product(ProductBase):
    id: int
    class Config:
        from_attributes = True