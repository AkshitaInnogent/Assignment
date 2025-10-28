from models.product import Product, FoodProduct, ElectronicProduct
inventory = [

    Product("Soap", 2, 40, "shelf-2", {"personal care"}),
    Product("Detergent", 20, 120, "shelf-5", {"household", "clearance"}),
    Product("Notebook", 50, 25, "shelf-9", {"stationery"}),

    FoodProduct("Rice", 2, 80, "shelf-1", {"grocery", "staple"}, "2025-12-31"),
    FoodProduct("Biscuits", 60, 15, "shelf-4", {"grocery", "snacks"}, "2024-11-15"),

    ElectronicProduct("Washing Machine", 2, 25000, "shelf-8", {"home appliance"}, "2 years"),
    ElectronicProduct("Iron", 25, 1500, "shelf-7", {"home appliance", "clearance"}, "1 year"),
    ElectronicProduct("LED TV", 5, 40000, "shelf-10", {"home appliance"}, "3 years")
]


LOW_STOCK = 5

def list_products():#works
    for product in inventory:
        print(product.describe())

def add_product():

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


def update_stock():#works
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
    

def delete_product():#works
    name = input("Enter product name u want to delete: ")
    for product in inventory:
        if product.name.lower() == name.lower():
            inventory.remove(product)
            print("product deleted successfully")
        else:
            if inventory[-1] == product:
                print("Product not found in inventory")

def total_value():#works
    total_value = 0
    for product in inventory:
        total_value += product.value()
    print(total_value)

def show_low_stock():#works
    print("Products with low stocks(less than 5) are:")
    for product in inventory:
        if(product.stock<LOW_STOCK):
            print(f"Name : {product.name:<15}  Stock : {product.stock}")
    
def show_discounted_products(): #works
    print("Products on clearance sale are:")
    for product in inventory:
        if "clearance" in product.tags:
            print(f"Name : {product.name:<12}  Price : Rs.{product.price:<3}  Discounted Price: Rs.{product.price*0.5 :<8} Discount: 50%")
