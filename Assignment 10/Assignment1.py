inventory = [
    {"name": "Rice", "stock": 1, "price": 80, "location": "shelf-1", "tags": {"grocery", "staple"}},
    {"name": "Wheat Flour", "stock": 1, "price": 80, "location": "shelf-1", "tags": {"grocery", "staple"}},
    {"name": "Soap", "stock": 1, "price": 80, "location": "shelf-2", "tags": {"grocery", "clearance"}},
    {"name": "Shampoo", "stock": 1, "price": 80, "location": "shelf-2", "tags": {"personal care","clearance"}},
    # {"name": "Toothpaste", "stock": 35, "price": 55, "location": "shelf-3", "tags": {"personal care"}},
    # {"name": "Biscuits", "stock": 60, "price": 15, "location": "shelf-4", "tags": {"grocery", "snacks"}},
    # {"name": "Detergent", "stock": 25, "price": 120, "location": "shelf-5", "tags": {"household","clearance"}},
    # {"name": "Oil", "stock": 45, "price": 150, "location": "shelf-6", "tags": {"grocery", "cooking"}},
    # {"name": "Tea", "stock": 55, "price": 210, "location": "shelf-7", "tags": {"grocery", "beverage","clearance"}},
    # {"name": "Sugar", "stock": 70, "price": 48, "location": "shelf-1", "tags": {"grocery", "staple"}}
]

LOW_STOCK = 5

def list_products():
    for product in inventory:
        
        print(f"Name: {product['name']:<12} | Stock: {product['stock']:<3} | Price: Rs.{product['price']:<6} | Location: {product['location']:<8} | Tags: {', '.join(product['tags'])}")


def add_product():
    name = input("Enter product name: ")
    stock = int(input("Enter stock quantity: "))
    price = float(input("Enter product price: "))
    location = input("Enter product location: ")
    tags = set(input("Enter product tags (comma separated): ").split(","))

    new_product = {
        "name" : name,
        "stock" : stock,
        "price" : price,
        "location" : location,
        "tags" : {tag.strip() for tag in tags}
    }
    inventory.append(new_product)
    print("Product added successfully")


def update_stock():
    name = input("Enter product name to update stock:")
    for product in inventory:
        if product["name"].lower() == name.lower():
            new_stock = int(input("Enter new stock quantity:"))
            product["stock"] = new_stock
            print("Stock updated successfully")
        else:
            if inventory[-1] == product:
                print("Product not found in inventory")
    

def delete_product():
    name = input("Enter product name u want to delete: ")
    for product in inventory:
        if product["name"].lower() == name.lower():
            inventory.remove(product)
            print("product deleted successfully")
        else:
            if inventory[-1] == product:
                print("Product not found in inventory")

def total_value():
    total_value = 0
    for product in inventory:
        total_value += product["stock"]*product["price"]
    print(total_value)

def show_low_stock():
    print("Products with low stocks(less than 5) are:")
    for product in inventory:
        if(product["stock"]<LOW_STOCK):
            print(f"Name : {product['name']:<12}  Stock : {product['stock']}")
    
def show_discounted_products():
    print("Products on clearance sale are:")
    for product in inventory:
        if "clearance" in product["tags"]:
            print(f"Name : {product['name']:<12}  Price : Rs.{product['price']:<3}  Discounted Price: Rs.{product['price']*0.5 :<8} Discount: 50%")

def main_menu():
    while True:
        print("Enter one of the following options:")
        print("1. List Products")
        print("\n")
        print("2. Add Product")
        print("\n")
        print("3. Update Stock")
        print("\n")
        print("4. Delete Product")
        print("\n")
        print("5. Total Inventory Value")
        print("\n")
        print("6 . Show Low Stock Products")
        print("\n")
        print("7. Show Discounted Products")
        print("\n")
        print("8. Exit")

        x = int(input("Your choice: "))
        match x:
            case 1:
                list_products()
            case 2:
                add_product()
            case 3:
                update_stock()
            case 4:
                delete_product()
            case 5:
                total_value()
            case 6:
                show_low_stock()
            case 7: 
                show_discounted_products()
            case 8:
                print("Exiting the program.")
                break
        print("\n")

def main():
    print("Welcome to the Inventory Management System")
    main_menu()

if __name__ == "__main__":
    main()