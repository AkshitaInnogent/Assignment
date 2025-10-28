from services.inventory_services import list_products, add_product, update_stock, delete_product, total_value, show_low_stock, show_discounted_products
def main():
    while True:
        print("Enter one of the following options:")
        print("1. List Products")
        print("2. Add Product")
        print("3. Update Stock")
        print("4. Delete Product")
        print("5. Total Inventory Value")
        print("6 . Show Low Stock Products")
        print("7. Show Discounted Products")
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

if __name__ == "__main__":
    main()