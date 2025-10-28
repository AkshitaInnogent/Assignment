from services.inventory_services import list_products, add_product, update_stock, delete_product, total_value, show_low_stock, show_discounted_products
from models.product import Product, FoodProduct, ElectronicProduct
from services.stats_services import stats

def main():
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
    while True:
        print("Enter one of the following options:")
        print("1. List Products")
        print("2. Add Product")
        print("3. Update Stock")
        print("4. Delete Product")
        print("5. Total Inventory Value")
        print("6 . Show Low Stock Products")
        print("7. Show Discounted Products")
        print("8. Statistics Report")
        print("9. Exit")

        x = int(input("Your choice: "))
        match x:
            case 1:
                list_products(inventory)
            case 2:
                add_product(inventory)
            case 3:
                update_stock(inventory)
            case 4:
                delete_product(inventory)
            case 5:
                total_value(inventory)
            case 6:
                show_low_stock(inventory)
            case 7: 
                show_discounted_products(inventory)
            case 8:
                stats(inventory)
            case 9:
                print("Exiting the program.")
                break
        print("\n")

if __name__ == "__main__":
    main()