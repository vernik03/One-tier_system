public class Employee {
    String name;
    String id;
    String ref;
    String surname;
    String salary;
    Employee(String str, String i, String r, String c, String is){
        this.name = str;
        this.id = i;
        this.ref = r;
        this.surname = c;
        this.salary = is;
    }
    public void display() {
        System.out.println("Employee :"+ name);
        System.out.println("Employee id :"+ id);
        System.out.println("Employee ref: "+ ref);
        System.out.println("Employee count Of people: "+ surname);
        System.out.println("Employee  stoliza : "+ salary);
    }
}
