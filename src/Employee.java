public class Employee {
    String name;
    String id;
    String unit;
    String surname;
    String salary;
    Employee(String str, String i, String r, String c, String is){
        this.name = str;
        this.id = i;
        this.unit = r;
        this.surname = c;
        this.salary = is;
    }
    public void display() {
        System.out.println("Employee :"+ name + " " + surname);
        System.out.println("Employee id :"+ id);
        System.out.println("Unit id: "+ unit);
        System.out.println("Employee  stoliza : "+ salary);
    }
}
