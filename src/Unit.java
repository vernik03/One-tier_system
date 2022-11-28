import java.util.List;
import java.util.Scanner;

public class Unit {
    String name;
    String id;
    List<Employee> employeers;

    Unit(String name, String id, List<Employee> c){
        this.employeers = c;

        this.name = name;
        this.id = id;
    }


    public void display() {
        System.out.println("Unit id: "+ id);
        System.out.println("Unit: "+ name+ " contains :");
        for(int i = 0; i<employeers.size();i++){
            employeers.get(i).display();
        }
    }

    public  void changeUnit(){
        display();
        System.out.println("Enter which param you want to change: ");
        int choice = 0;
        System.out.println("1. Unit id");
        System.out.println("2. Unit name");
        System.out.println("3. Change employee param");

        Scanner keyboard = new Scanner(System.in);
        Scanner keyboard2 = new Scanner(System.in);
        choice = keyboard.nextInt();
        switch (choice){
            case -1 : return;
            case 1: {
                System.out.println("Enter Unit id  :");
                String cId = keyboard.nextLine();
                id = cId;
            }
            case 2: {
                System.out.println("Enter Unit name  :");
                String name = keyboard.nextLine();
                name = name;
            }
            case 3 :{
                System.out.println("Enter with which employee you want  to work?  :");
                for(int  i =0; i< employeers.size();i++){
                    System.out.println(i+" employee");
                }
                choice = keyboard.nextInt();
                System.out.println("Enter which param you want to change: ");
                System.out.println("1. Employee name");
                System.out.println("2. Employee id");
                System.out.println("3. Unit id");
                System.out.println("4. Employee surname");
                System.out.println("5. Employee salary");
                int choice2 = 0;
                choice2 = keyboard.nextInt();
                switch (choice2){
                    case 1:{
                        System.out.println("Enter name :");
                        String tmp = keyboard2.nextLine();
                        employeers.get(choice).name =  tmp;
                        break;
                    }
                    case 2:{
                        System.out.println("Enter id :");
                        String tmp = keyboard.nextLine();
                        employeers.get(choice).id = tmp;
                        break;
                    }
                    case 3:{
                        System.out.println("Enter unit id :");
                        String tmp = keyboard.nextLine();
                        employeers.get(choice).unit = tmp;
                        break;
                    }
                    case 4:{
                        System.out.println("Enter surname :");
                        String tmp = keyboard.nextLine();
                        employeers.get(choice).surname = tmp;
                        break;
                    }
                    case 5:{
                        System.out.println("Enter salary :");
                        String tmp = keyboard.nextLine();
                        employeers.get(choice).salary = tmp;
                        break;
                    }
                    case 0: break;
                }
            }
        }
    }

    public void deleteEmployee(){
        System.out.println("Enter which employee you want to delete ?");
        for(int  i =0; i< employeers.size();i++){
            System.out.println(i+" employee");
        }
        Scanner keyboard = new Scanner(System.in);
        int choice = keyboard.nextInt();
        employeers.remove(choice);
    }
}
