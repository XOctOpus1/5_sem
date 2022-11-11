// Denys Gordiichuk var 3


import java.io.IOException;
import java.util.List;
import java.util.Scanner;




public class Main {
    public static void main(String[] args) throws IOException {
        DAO.connect();
        Scanner scanner = new Scanner(System.in);
        String str = "";
        while (!str.equals("exit")) {
            System.out.println("Enter command:");
            str = scanner.nextLine();
            String[] command = str.split(" ");
            switch (command[0]) {
                case "create":
                    DAO.create(command[1]);
                    break;
                case "delete":
                    DAO.delete(Integer.parseInt(command[1]));
                    break;
                case "hire":
                    DAO.hire(Integer.parseInt(command[1]), command[2], command[3], command[4], command[5]);
                    break;
                case "dismiss":
                    DAO.dismiss(Integer.parseInt(command[1]));
                    break;
                case "edit":
                    DAO.edit(Integer.parseInt(command[1]), command[2], command[3], command[4], command[5]);
                    break;
                case "transfer":
                    DAO.transfer(Integer.parseInt(command[1]), Integer.parseInt(command[2]));
                    break;
                case "count":
                    System.out.println(DAO.count(Integer.parseInt(command[1])));
                    break;
                case "getEmployees":
                    List<String> list = DAO.getEmployees(Integer.parseInt(command[1]));
                    for (String s : list) {
                        System.out.println(s);
                    }
                    break;
                case "getUnits":
                    List<String> list1 = DAO.getUnits();
                    for (String s : list1) {
                        System.out.println(s);
                    }
                    break;
            }
        }
        DAO.disconnect();
    }
}