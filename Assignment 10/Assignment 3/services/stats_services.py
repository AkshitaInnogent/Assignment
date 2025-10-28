import numpy as np
from models.product import Product, FoodProduct, ElectronicProduct

def stats(inventory):

    def avg_price(inventory):
        prices = np.array([product.price for product in inventory])
        print(f"The average price of items: Rs.{np.mean(prices)}")

    def most_expensive(inventory):
        prices = np.array([product.price for product in inventory])
        print(f"Most expensive item price: Rs.{np.max(prices)}")

    def total_count(inventory):
        stocks = np.array([product.stock for product in inventory])
        print(f"Total count of all items in stock: {np.sum(stocks)}")

    def total_value(inventory):
        prices = np.array([product.price for product in inventory])
        stocks = np.array([product.stock for product in inventory])
        values = prices * stocks
        print("Total value of each product in stock:")
        for product, val in zip(inventory, values):
            print(f"Total value of {product.name} in stock: Rs.{val}")

    def clearance_items_avg_and_total_value(inventory):
        clearance_items = [product for product in inventory if "clearance" in product.tags]
        if not clearance_items:
            print("No clearance items found.")
            return
        c_prices = np.array([product.price for product in clearance_items])
        c_stocks = np.array([product.stock for product in clearance_items])
        values = c_prices * c_stocks
        print("Clearance Items Statistics:")
        print(f"The average price of clearance items: Rs.{np.mean(c_prices)}")
        print(f"Total value of all clearance products in stock: Rs.{np.sum(values)}")

    while True:
        print("\nEnter one of the following options:")
        print("1. Average Price of Items")
        print("2. Most Expensive Item Price")
        print("3. Total Count of All Items in Stock")
        print("4. Total Value of Each Product in Stock")
        print("5. Average price and Total value of Clearance Items")
        print("6. Back to Main Menu")

        try:
            x = int(input("Your choice: "))
        except ValueError:
            print("Invalid input. Please enter a number.")
            continue

        match x:
            case 1:
                avg_price(inventory)
            case 2:
                most_expensive(inventory)
            case 3:
                total_count(inventory)
            case 4:
                total_value(inventory)
            case 5:
                clearance_items_avg_and_total_value(inventory)
            case 6:
                break
            case _:
                print("Invalid choice. Please try again.")
