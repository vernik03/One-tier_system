import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static List<Unit> units;
    public static  String filepath = "src/HRDepartment.xml";
    public static void main(String[] args) throws IOException, SQLException, ClassNotFoundException, ParserConfigurationException, SAXException {

        readXmlFile();
        makeDBFromXml();

    }

    public static void addUnit() throws ClassNotFoundException, SQLException {
        String jdbcURL = "jdbc:mysql://localhost:3306/";
        String username = "root";
        String password = "admin";
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection(jdbcURL,username,password);
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter Unit name");
        String val = scanner.nextLine();
        System.out.println("Enter Unit id");
        String val2 = scanner.nextLine();
        String sql = "INSERT INTO hr_department.units(id, name) VALUES ('"+val2+"','"+ val+"'); ";
        // String sql = "insert into units ('id', 'name') " + "c";
        PreparedStatement preparedStmt = connection.prepareStatement(sql);
        preparedStmt.execute();
    }
    public static void addEmployee() throws ClassNotFoundException, SQLException {
        String jdbcURL = "jdbc:mysql://localhost:3306/";
        String username = "root";
        String password = "admin";
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection(jdbcURL,username,password);
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter Unit name");
        String val = scanner.nextLine();
        String sql = "select * from hr_department.units;";
        PreparedStatement p = connection.prepareStatement(sql);
        ResultSet  rs = p.executeQuery();
        boolean tmp = false;
        while(rs.next()){
            String name = rs.getString("name");
            if (name.equals(val)){
                tmp = true;
                break;
            }
        }
        if(tmp){
            System.out.println("Enter Employee id");
            String v2 = scanner.nextLine();
            System.out.println("Enter Unit id");
            String val1 = scanner.nextLine();
            System.out.println("Enter Employee name");
            int val2 = scanner.nextInt();
            System.out.println("Enter Employee surname");
            int val3 = scanner.nextInt();
            System.out.println("Enter Employee salary");
            int val4 = scanner.nextInt();
            String sql2 = "INSERT INTO hr_department.employees(id, unitId, name, surname, salary) VALUES ('"+v2+"','"+ val1+"', '"+val2+"','"+val3+"','"+val4+"'); ";
            PreparedStatement preparedStmt = connection.prepareStatement(sql2);
            preparedStmt.execute();
        }
    }
    public static void deleteEmployee()throws ClassNotFoundException, SQLException{
        String jdbcURL = "jdbc:mysql://localhost:3306/";
        String username = "root";
        String password = "admin";
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection(jdbcURL,username,password);
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter Employee name");
        String val = scanner.nextLine();
        String sql = "delete from hr_department.employees where employee_namE = '"+val+"';";
        PreparedStatement preparedStmt = connection.prepareStatement(sql);
        preparedStmt.execute();
    }
    public static void deleteUnit()throws ClassNotFoundException, SQLException{
        String jdbcURL = "jdbc:mysql://localhost:3306/";
        String username = "root";
        String password = "admin";
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection(jdbcURL,username,password);
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter Unit name");
        String val = scanner.nextLine();
        String sql = "delete from hr_department.employees where unitId = '"+val+"';";
        PreparedStatement preparedStmt = connection.prepareStatement(sql);
        preparedStmt.execute();
        String sql2 = "delete from hr_department.units where name = '"+val+"';";
        PreparedStatement preparedStmt2 = connection.prepareStatement(sql2);
        preparedStmt2.execute();
    }
    private static Employee getEmployee(Node node) {
        Element element = (Element) node;
        Employee lang = new Employee(getTagValue("Name", element),getTagValue("employeeId", element),
                getTagValue("unitId", element),getTagValue("Surname", element),getTagValue("Salary",element));
        return lang;
    }

    private static Unit getUnit(Node node, List<Employee> employeers) {
        Element element = (Element) node;
        Unit  lang = new Unit(getTagValue("title", element),getTagValue("id", element),
                (List<Employee>) employeers);

        return lang;
    }

    private static String getTagValue(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = (Node) nodeList.item(0);
        return node.getNodeValue();
    }

    private static void readXmlFile() throws ParserConfigurationException, IOException, SAXException {
        File xmlFile = new File(filepath);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        builder = factory.newDocumentBuilder();
        Document document = builder.parse(xmlFile);
        document.getDocumentElement().normalize();
        System.out.println("Корневой элемент: " + document.getDocumentElement().getNodeName());

        NodeList nodeList = document.getElementsByTagName("Unit");
        NodeList nodeList1 = document.getElementsByTagName("employee");

        List<Unit> langList = new ArrayList<Unit>();

        List<Employee> langList1 = new ArrayList<Employee>();
        for(int j = 0; j<nodeList1.getLength();j++){
            langList1.add(getEmployee(nodeList1.item(j)));
        }
        List<List<Employee>> lists = new ArrayList<>();

        int countOfCities = 0;
        int temp = 0;
        for (int i = 0; i < nodeList.getLength(); i++) {
            countOfCities = (nodeList.item(i).getChildNodes().getLength()-5)/2;
            if(lists.size() == 0){
                lists.add(i,langList1.subList(0,countOfCities));
            }else{
                lists.add(i,langList1.subList(temp,temp+countOfCities));
            }
            temp = temp+countOfCities;
            langList.add(getUnit(nodeList.item(i),lists.get(i)));
        }
        units = langList;
        for (Unit lang : langList) {
            lang.display();
            System.out.println(" ");
        }
    }
    private static void writeXmlFile(){
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try {
            builder = factory.newDocumentBuilder();


            Document doc = builder.newDocument();

            Element rootElement = doc.createElement( "HRDepartment");

            doc.appendChild(rootElement);

            for(int i = 0; i<units.size();i++){
                rootElement.appendChild(getUnitToFile(doc,units.get(i)));
            }


            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();

            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc);


            //  StreamResult console = new StreamResult(System.out);
            StreamResult file = new StreamResult(new File(filepath));

            //  transformer.transform(source, console);
            transformer.transform(source, file);
            //    System.out.println("Создание XML файла закончено");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Node getUnitToFile(Document doc, Unit c){
        Element unit = doc.createElement("Unit");
        Node item = null;
        item = doc.createElement("title");
        item.appendChild(doc.createTextNode(c.name));
        unit.appendChild(item);
        Node item2 = null;
        item2 = doc.createElement("id");
        item2.appendChild(doc.createTextNode(c.id));
        unit.appendChild(item2);

        for(int i = 0;i<c.employeers.size();i++){
            unit.appendChild(getCities(doc, unit,c.employeers.get(i)));
        }
        return unit;
    }


    private static Node getCities(Document doc, Element element, Employee c) {

        Node item = null;
        item = doc.createElement("employee");


        Node item2 = null;
        item2 = doc.createElement("unitId");
        item2.appendChild(doc.createTextNode(c.id));
        item.appendChild(item2);

        Node item3 = null;
        item3 = doc.createElement("unitId");
        item3.appendChild(doc.createTextNode(c.unit));
        item.appendChild(item3);

        Node item4 = null;
        item4 = doc.createElement("Name");
        item4.appendChild(doc.createTextNode(c.name));
        item.appendChild(item4);


        Node item5 = null;
        item5 = doc.createElement("Surname");
        item5.appendChild(doc.createTextNode(c.surname));
        item.appendChild(item5);

        Node item6 = null;
        item6 = doc.createElement("Salary");
        item6.appendChild(doc.createTextNode(c.salary));
        item.appendChild(item6);
        return item;
    }

    private static Unit makeNewUnitXML(){
        List<Employee> employeers = new ArrayList<>();
        String unitName = "";
        String unitId = "";
        Scanner keyboard = new Scanner(System.in);
        System.out.println("Enter unit Name : ");
        unitName = keyboard.nextLine();
        System.out.println("Enter unit id : ");
        unitId = keyboard.nextLine();
        System.out.println("Do you want add employeers to " + unitName +"y/n ?");
        String tmp = keyboard.nextLine();
        if(tmp.equals("y")){
            System.out.println("Enter count of Cities ?");
            int n = keyboard.nextInt();
            for(int i = 0; i< n;i++){
                System.out.println("Enter employee name ?");
                String name = keyboard.nextLine();
                System.out.println("Enter employee id ?");
                String id = keyboard.nextLine();
                System.out.println("Enter employee ref?");
                String ref = keyboard.nextLine();
                System.out.println("Enter employee count of people ?");
                String count = keyboard.nextLine();
                System.out.println("Enter employee is stoliza ?");
                String Salary = keyboard.nextLine();
                employeers.add(i,new Employee(name,id,ref,count,Salary));
            }

        } else{
            return new Unit(unitName, unitId, employeers);
        }
        Unit unit = new Unit(unitName, unitId, employeers);

        return unit;
    }

    private static void deleteUnitXML(){
        System.out.println("Enter which unit delete :");
        for(int  i =0; i< units.size();i++){
            System.out.println(i+" unit");
        }
        Scanner keyboard = new Scanner(System.in);
        int choice = keyboard.nextInt();
        units.remove(choice);
    }

    private static void makeDBFromXml() throws ClassNotFoundException, SQLException {
        String jdbcURL = "jdbc:mysql://localhost:3306/";
        String username = "root";
        String password = "admin";
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection(jdbcURL,username,password);
        for(int i = 0;i<units.size();i++){

            String val = units.get(i).name;
            String v2 = units.get(i).id;
            String sql = "INSERT INTO hr_department.units(id, name) VALUES ('"+v2+"','"+ val+"'); ";
            PreparedStatement preparedStmt = connection.prepareStatement(sql);
            preparedStmt.execute();
            for(int j = 0; j<units.get(i).employeers.size();j++){

                String val1 = units.get(i).employeers.get(j).name;

                String val2 = units.get(i).employeers.get(j).id;

                String val3 = units.get(i).employeers.get(j).surname;

                String val4 = units.get(i).employeers.get(j).salary;
                String sql2 = "INSERT INTO hr_department.employees(id, unitId, name, surname, salary) VALUES ('"+val2+"','"+ v2+"', '"+val1+"','"+val3+"','"+val4+"'); ";
                PreparedStatement preparedStmt2 = connection.prepareStatement(sql2);
                preparedStmt2.execute();
            }
        }

    }
}
