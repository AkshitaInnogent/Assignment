from fastapi import FastAPI
from product_management_api.routers import companies, categories, products
from product_management_api.database import prisma

app = FastAPI(title="Product Management API")

@app.on_event("startup")
async def startup_event():
    print("🚀 Starting Product Management API...")
    await prisma.connect()
    print("✅ Connected to Prisma database")

@app.on_event("shutdown")
async def shutdown_event():
    await prisma.disconnect()
    print("🔌 Disconnected from Prisma")

# Include routers
app.include_router(companies.router)
app.include_router(categories.router)
app.include_router(products.router)

print("✅ Routers included successfully.")
