class Product:
    def __init__(self, name, stock, price, location, tags):
        self.name = name
        self.stock = stock
        self.price = price
        self.location = location
        self.tags = tags
    
    def value(self):
        return self.stock * self.price
    
    def describe(self):
        return f"Name: {self.name:<15}| Stock: {self.stock:<15}| Price: Rs.{self.price:<15}| Location: {self.location:<15}| Tags: {', '.join(self.tags):<30}"

class FoodProduct(Product):
    def __init__(self, name, stock, price, location, tags, expiry_date):
        super().__init__(name, stock, price, location, tags)
        self.expiry_date = expiry_date
    def describe(self):
        return super().describe() + f"| Expiry Date: {self.expiry_date}"

class ElectronicProduct(Product):
    def __init__(self, name, stock, price, location, tags, warranty_period):
        super().__init__(name, stock, price, location, tags)
        self.warranty_period = warranty_period  
    def describe(self):
        return super().describe() + f"| Warranty Period: {self.warranty_period} months"

  