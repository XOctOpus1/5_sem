//var 4 Denys Gordiichuk IPS-31

import java.io.*;
import java.net.*;
import java.util.*;
import java.rmi.*;
import java.rmi.server.*;

public class ClientRmiTask4 extends UnicastRemoteObject implements ClientRmiTask4Interface {
    public ClientRmiTask4() throws RemoteException {
        super();
    }

    public static void main(String[] args) {
        try {
            ClientRmiTask4 client = new ClientRmiTask4();
            ServerRmiTask4Interface server = (ServerRmiTask4Interface) Naming.lookup("rmi://localhost:1234/ServerRmiTask4");
            Scanner sc = new Scanner(System.in);
            System.out.println("Enter the number of applicants: ");
            int n = sc.nextInt();
            server.setN(n);
            Applicant[] applicants = new Applicant[n];
            for (int i = 0; i < n; i++) {
                System.out.println("Enter the id: ");
                int id = sc.nextInt();
                server.setId(id);
                System.out.println("Enter the surname: ");
                String surname = sc.next();
                server.setSurname(surname);
                System.out.println("Enter the first name: ");
                String firstName = sc.next();
                server.setFirstName(firstName);
                System.out.println("Enter the patronymic: ");
                String patronymic = sc.next();
                server.setPatronymic(patronymic);
                System.out.println("Enter the address: ");
                String address = sc.next();
                server.setAddress(address);
                System.out.println("Enter the telephone: ");
                String telephone = sc.next();
                server.setTelephone(telephone);
                System.out.println("Enter the grades: ");
                int grades = sc.nextInt();
                server.setGrades(grades);
                applicants[i] = new Applicant(id, surname, firstName, patronymic, address, telephone, grades);
            }
            System.out.println("Enter the sum of points: ");
            int sum = sc.nextInt();
            server.setSum(sum);
            System.out.println("Enter the number of applicants: ");
            int number = sc.nextInt();
            server.setNumber(number);
            System.out.println("List of applicants with unsatisfactory grades: ");
            for (int i = 0; i < n; i++) {
                if (server.getUnsatisfactoryGrades()) {
                    System.out.println(applicants[i]);
                }
            }
            System.out.println("List of applicants with a higher sum of points: "); 
            for (int i = 0; i < n; i++) {
                if (server.getHigherSum()) {
                    System.out.println(applicants[i]);
                }
            }
            System.out.println("List of applicants with the highest number of applicants: ");
            for (int i = 0; i < n; i++) {
                if (server.getHighestNumber()) {
                    System.out.println(applicants[i]);
                }
            }
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }
    }
}


class Applicant {
    private int id;
    private String surname;
    private String firstName;
    private String patronymic;
    private String address;
    private String telephone;
    private int grades;

    public Applicant(int id, String surname, String firstName, String patronymic, String address, String telephone, int grades) {
        this.id = id;
        this.surname = surname;
        this.firstName = firstName;
        this.patronymic = patronymic;
        this.address = address;
        this.telephone = telephone;
        this.grades = grades;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public int getGrades() {
        return grades;
    }

    public void setGrades(int grades) {
        this.grades = grades;
    }

    @Override
    public String toString() {
        return "Applicant{" +
                "id=" + id +
                ", surname='" + surname + '\'' +
                ", firstName='" + firstName + '\'' +
                ", patronymic='" + patronymic + '\'' +
                ", address='" + address + '\'' +
                ", telephone='" + telephone + '\'' +
                ", grades=" + grades +
                '}';
    }
}




