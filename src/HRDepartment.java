public class HRDepartment {
    Unit[] units;
    HRDepartment(Unit[] c){
        this.units = c;
    }
    void displayHRDepartment(){
        System.out.println("HRDepartment contains : ");
        for(int i = 0;i<units.length;i++){
            units[i].display();
        }
    }
}
