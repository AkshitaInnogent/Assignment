import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

// --------------------- MODELS ---------------------

class ClassRoom {
    int id;
    String name;
    ClassRoom(int id, String name) { this.id = id; this.name = name; }

    public String toString() { return id + " " + name; }
    public String toFileString() { return id + "," + name; }
}

class Address {
    int id, pincode, studentId;
    String city;
    Address(int id, int pincode, String city, int studentId) {
        this.id = id; this.pincode = pincode; this.city = city; this.studentId = studentId;
    }
    public String toString() { return id + " " + pincode + " " + city + " " + studentId; }
    public String toFileString() { return id + "," + pincode + "," + city + "," + studentId; }
}

class Student {
    int id, classId, marks, age, rank;
    String name, gender, result;
    Student(int id, String name, int classId, int marks, String gender, int age) {
        this.id = id; this.name = name; this.classId = classId;
        this.marks = marks; this.gender = gender; this.age = age;
        this.result = (marks < 50 ? "Failed" : "Passed");
    }
    public String toString() {
        return id + " " + name + " Class:" + classId + " Marks:" + marks +
                " Gender:" + gender + " Age:" + age + " Result:" + result + " Rank:" + rank;
    }
    public String toFileString() {
        return id + "," + name + "," + classId + "," + marks + "," + gender + "," + age + "," + result + "," + rank;
    }
}

// --------------------- CORE SYSTEM ---------------------

class StudentManagementSystem {
    List<ClassRoom> classes = new ArrayList<>();
    List<Student> students = new ArrayList<>();
    List<Address> addresses = new ArrayList<>();

    // ---------------- Business Rules ----------------
    void addClass(ClassRoom c) {
        classes.add(c);
        saveClassesToFile();
    }

    void addStudent(Student s) {
        if (s.age > 20) {
            System.out.println("Student age > 20, not inserted: " + s.name);
            return;
        }
        students.add(s);
        updateRanks();
        saveStudentsToFile();
    }

    void addAddress(Address a) {
        addresses.add(a);
        saveAddressesToFile();
    }

    void updateRanks() {
        students.sort((s1, s2) -> Integer.compare(s2.marks, s1.marks));
        int rank = 1;
        for (int i = 0; i < students.size(); i++) {
            if (i > 0 && students.get(i).marks == students.get(i-1).marks) {
                students.get(i).rank = students.get(i-1).rank;
            } else {
                students.get(i).rank = rank;
            }
            rank++;
        }
    }

    void deleteStudent(int studentId) {
        students.removeIf(s -> s.id == studentId);
        addresses.removeIf(a -> a.studentId == studentId);
        classes.removeIf(c -> students.stream().noneMatch(s -> s.classId == c.id));
        updateRanks();
        saveStudentsToFile();
        saveAddressesToFile();
        saveClassesToFile();
    }

    // ---------------- Filtering & Pagination ----------------
    List<Student> getFilteredStudents(Map<String, Object> filters, String sortBy, boolean ascending, int start, int end) {
        List<Student> result = new ArrayList<>(students);

        if (filters != null) {
            if (filters.containsKey("gender"))
                result = result.stream().filter(s -> s.gender.equals(filters.get("gender"))).toList();
            if (filters.containsKey("age"))
                result = result.stream().filter(s -> s.age == (int) filters.get("age")).toList();
            if (filters.containsKey("classId"))
                result = result.stream().filter(s -> s.classId == (int) filters.get("classId")).toList();
            if (filters.containsKey("city")) {
                String city = (String) filters.get("city");
                Set<Integer> ids = addresses.stream().filter(a -> a.city.equalsIgnoreCase(city)).map(a -> a.studentId).collect(Collectors.toSet());
                result = result.stream().filter(s -> ids.contains(s.id)).toList();
            }
            if (filters.containsKey("pincode")) {
                int pincode = (int) filters.get("pincode");
                Set<Integer> ids = addresses.stream().filter(a -> a.pincode == pincode).map(a -> a.studentId).collect(Collectors.toSet());
                result = result.stream().filter(s -> ids.contains(s.id)).toList();
            }
            if (filters.containsKey("result"))
                result = result.stream().filter(s -> s.result.equals(filters.get("result"))).toList();
        }

        if (sortBy != null) {
            Comparator<Student> comp = switch (sortBy) {
                case "name" -> Comparator.comparing(s -> s.name);
                case "marks" -> Comparator.comparingInt(s -> s.marks);
                case "rank" -> Comparator.comparingInt(s -> s.rank);
                default -> Comparator.comparingInt(s -> s.id);
            };
            if (!ascending) comp = comp.reversed();
            result = result.stream().sorted(comp).toList();
        }

        if (end > result.size()) end = result.size();
        if (start < 0) start = 0;
        if (start > end) return new ArrayList<>();

        return result.subList(start, end);
    }

    void printFilteredStudents(Map<String, Object> filters, String sortBy, boolean ascending, int start, int end) {
        List<Student> list = getFilteredStudents(filters, sortBy, ascending, start, end);
        list.forEach(System.out::println);
    }

    // ---------------- Specific Finders ----------------
    void findByPincode(int pincode, String gender, int classId) {
        Map<String, Object> filters = new HashMap<>();
        filters.put("pincode", pincode);
        if (!gender.equals("N")) filters.put("gender", gender);
        if (classId != -1) filters.put("classId", classId);
        printFilteredStudents(filters, null, true, 0, Integer.MAX_VALUE);
    }

    void findByCity(String city, String gender, int classId) {
        Map<String, Object> filters = new HashMap<>();
        filters.put("city", city);
        if (!gender.equals("N")) filters.put("gender", gender);
        if (classId != -1) filters.put("classId", classId);
        printFilteredStudents(filters, null, true, 0, Integer.MAX_VALUE);
    }

    void findByClass(int classId, String gender, int age) {
        Map<String, Object> filters = new HashMap<>();
        filters.put("classId", classId);
        if (!gender.equals("N")) filters.put("gender", gender);
        if (age != -1) filters.put("age", age);
        printFilteredStudents(filters, null, true, 0, Integer.MAX_VALUE);
    }

    void printPassedStudents() {
        Map<String, Object> filters = new HashMap<>();
        filters.put("result", "Passed");
        printFilteredStudents(filters, null, true, 0, Integer.MAX_VALUE);
    }

    void printFailedStudents() {
        Map<String, Object> filters = new HashMap<>();
        filters.put("result", "Failed");
        printFilteredStudents(filters, null, true, 0, Integer.MAX_VALUE);
    }

    // ---------------- File System ----------------
    void saveStudentsToFile() {
        try (PrintWriter pw = new PrintWriter("students.txt")) {
            for (Student s : students) pw.println(s.toFileString());
        } catch (IOException e) {
            System.err.println("Error saving students: " + e.getMessage());
        }
    }

    void saveClassesToFile() {
        try (PrintWriter pw = new PrintWriter("classes.txt")) {
            for (ClassRoom c : classes) pw.println(c.toFileString());
        } catch (IOException e) {
            System.err.println("Error saving classes: " + e.getMessage());
        }
    }

    void saveAddressesToFile() {
        try (PrintWriter pw = new PrintWriter("addresses.txt")) {
            for (Address a : addresses) pw.println(a.toFileString());
        } catch (IOException e) {
            System.err.println("Error saving addresses: " + e.getMessage());
        }
    }

    void loadData() {
        students.clear(); classes.clear(); addresses.clear();

        try (BufferedReader br = new BufferedReader(new FileReader("students.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] arr = line.split(",");
                Student s = new Student(Integer.parseInt(arr[0]), arr[1], Integer.parseInt(arr[2]),
                        Integer.parseInt(arr[3]), arr[4], Integer.parseInt(arr[5]));
                s.result = arr[6];
                s.rank = Integer.parseInt(arr[7]);
                students.add(s);
            }
        } catch (FileNotFoundException e) {
            System.out.println("students.txt not found, starting fresh.");
        } catch (Exception e) {
            e.printStackTrace();
        }

        try (BufferedReader br = new BufferedReader(new FileReader("classes.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] arr = line.split(",");
                classes.add(new ClassRoom(Integer.parseInt(arr[0]), arr[1]));
            }
        } catch (FileNotFoundException e) {
            System.out.println("classes.txt not found, starting fresh.");
        } catch (Exception e) {
            e.printStackTrace();
        }

        try (BufferedReader br = new BufferedReader(new FileReader("addresses.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] arr = line.split(",");
                addresses.add(new Address(Integer.parseInt(arr[0]), Integer.parseInt(arr[1]), arr[2], Integer.parseInt(arr[3])));
            }
        } catch (FileNotFoundException e) {
            System.out.println("addresses.txt not found, starting fresh.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

// --------------------- MAIN ---------------------

public class Main {
    public static void main(String[] args) {
        StudentManagementSystem sms = new StudentManagementSystem();

        // Add Classes
        sms.addClass(new ClassRoom(1, "A"));
        sms.addClass(new ClassRoom(2, "B"));
        sms.addClass(new ClassRoom(3, "C"));
        sms.addClass(new ClassRoom(4, "D"));

        // Add Students
        sms.addStudent(new Student(1, "stud1", 1, 88, "F", 10));
        sms.addStudent(new Student(2, "stud2", 1, 70, "F", 11));
        sms.addStudent(new Student(3, "stud3", 2, 88, "M", 22)); // rejected (age > 20)
        sms.addStudent(new Student(4, "stud4", 2, 55, "M", 19));
        sms.addStudent(new Student(5, "stud5", 1, 30, "F", 15));
        sms.addStudent(new Student(6, "stud6", 3, 30, "F", 13));
        sms.addStudent(new Student(7, "stud7", 3, 10, "F", 12));
        sms.addStudent(new Student(8, "stud8", 3, 0, "M", 11));

        // Add Addresses
        sms.addAddress(new Address(1, 452002, "indore", 1));
        sms.addAddress(new Address(2, 422002, "delhi", 1));
        sms.addAddress(new Address(3, 442002, "indore", 2));
        sms.addAddress(new Address(4, 462002, "delhi", 4));
        sms.addAddress(new Address(5, 472002, "indore", 4));
        sms.addAddress(new Address(6, 452002, "indore", 5));
        sms.addAddress(new Address(7, 452002, "delhi", 5));
        sms.addAddress(new Address(8, 482002, "mumbai", 6));
        sms.addAddress(new Address(9, 482002, "bhopal", 7));
        sms.addAddress(new Address(10, 482002, "indore", 8));

        System.out.println("=== All Students (after Age Rule) ===");
        sms.printFilteredStudents(null, "marks", false, 0, Integer.MAX_VALUE);

        System.out.println("\n=== Find by Pincode 482002 ===");
        sms.findByPincode(482002, "N", -1);

        System.out.println("\n=== Find by City Indore ===");
        sms.findByCity("indore", "N", -1);

        System.out.println("\n=== Find by Class 1 ===");
        sms.findByClass(1, "N", -1);

        System.out.println("\n=== Passed Students ===");
        sms.printPassedStudents();

        System.out.println("\n=== Failed Students ===");
        sms.printFailedStudents();

        System.out.println("\n=== Pagination Example (Female students, records 0–2 ordered by marks) ===");
        Map<String, Object> filters = new HashMap<>();
        filters.put("gender", "F");
        sms.printFilteredStudents(filters, "marks", false, 0, 2);

        System.out.println("\n=== Delete Student 2 ===");
        sms.deleteStudent(2);
        sms.printFilteredStudents(null, "id", true, 0, Integer.MAX_VALUE);
    }
}
