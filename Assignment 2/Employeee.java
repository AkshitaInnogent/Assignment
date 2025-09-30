import java.util.*;

class Employee implements Comparable<Employee> {
    int id;
    String name;
    String department;
    double salary;

    Employee(int id, String name, String department, double salary) {
        this.id = id;
        this.name = name;
        this.department = department;
        this.salary = salary;
    }

    @Override
    public String toString() {
        return id + " | " + name + " | " + department + " | " + salary;
    }

    @Override
    public int compareTo(Employee other) {
        return Double.compare(other.salary, this.salary); // Descending salary
    }

    static class DepartmentNameSalaryComparator implements Comparator<Employee> {
        @Override
        public int compare(Employee e1, Employee e2) {
            int cmp = e1.department.compareTo(e2.department);
            if (cmp != 0) return cmp;

            cmp = e1.name.compareTo(e2.name);
            if (cmp != 0) return cmp;

            return Double.compare(e1.salary, e2.salary);
        }
    }
}

public class Employeee {
    public static void main(String[] args) {
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(1, "Aanya", "Sales", 32000));
        employees.add(new Employee(2, "Shravak", "IT", 32000));
        employees.add(new Employee(3, "Aanchal", "Teaching", 20000));
        employees.add(new Employee(4, "Akshita", "Hr", 225000));

        // Sort by Department → Name → Salary
        employees.sort(new Employee.DepartmentNameSalaryComparator());
        System.out.println("Sorted by Department, then Name, then Salary:");
        for (Employee e : employees) {
            System.out.println(e);
        }

        // Sort by Salary (Descending)
        Collections.sort(employees);
        System.out.println("\nSorted by Salary (Descending):");
        for (Employee e : employees) {
            System.out.println(e);
        }
    }
}
