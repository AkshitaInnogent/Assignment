from models.product import Product, FoodProduct, ElectronicProduct
import numpy as np


LOW_STOCK = 5

def list_products(inventory):#works
    for product in inventory:
        print(product.describe())

def add_product(inventory):

    print("Choose product type to add:")
    print("1. General Product")
    print("2. Food Product")
    print("3. Electronic Product")
    choice = int(input("Your choice: "))

    name = input("Enter product name: ")
    stock = int(input("Enter stock quantity: "))
    price = float(input("Enter product price: "))
    location = input("Enter product location: ")
    tags = set(input("Enter product tags (comma separated): ").split(","))

    if choice == 1:
        product = Product(name, stock, price, location, tags)
    elif choice == 2:
        expiry_date = input("Enter expiry date (YYYY-MM-DD):")
        product = FoodProduct(name, stock, price, location, tags, expiry_date)
    elif choice == 3:
        warranty_period = int(input("Enter warranty period (in months):"))
        product = ElectronicProduct(name, stock, price, location, tags, warranty_period)
    else:
        print("Invalid choice. Product not added.")
        return
    
    inventory.append(product)
    print("Product added successfully")


def update_stock(inventory):#works
    name = input("Enter product name to update stock:")
    for product in inventory:
        if product.name.lower() == name.lower():
            new_stock = int(input("Enter new stock quantity:"))
            product.stock = new_stock
            print("Stock updated successfully")
            break
        else:
            if inventory[-1] == product:
                print("Product not found in inventory")
    

def delete_product(inventory):#works
    name = input("Enter product name u want to delete: ")
    for product in inventory:
        if product.name.lower() == name.lower():
            inventory.remove(product)
            print("product deleted successfully")
        else:
            if inventory[-1] == product:
                print("Product not found in inventory")

def total_value(inventory):#works
    total_value = 0
    for product in inventory:
        total_value += product.value()
    print(total_value)

def show_low_stock(inventory):#works
    print("Products with low stocks(less than 5) are:")
    for product in inventory:
        if(product.stock<LOW_STOCK):
            print(f"Name : {product.name:<15}  Stock : {product.stock}")
    
def show_discounted_products(inventory): #works
    print("Products on clearance sale are:")
    for product in inventory:
        if "clearance" in product.tags:
            print(f"Name : {product.name:<12}  Price : Rs.{product.price:<3}  Discounted Price: Rs.{product.price*0.5 :<8} Discount: 50%")

    
            




    
    