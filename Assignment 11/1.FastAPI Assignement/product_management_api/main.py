from fastapi import FastAPI
from product_management_api.database import Base, engine
from product_management_api.routers import companies, categories, products
print("Starting Product Management API...")

app = FastAPI()

app.include_router(companies.router)
app.include_router(categories.router)
app.include_router(products.router)
print("Routers included successfully.")

@app.get("/")
def root():
    return {"FastAPI is running!"}
print("Defining root endpoint.")
Base.metadata.create_all(bind=engine)

