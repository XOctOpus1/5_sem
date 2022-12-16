//var 4 Denys Gordiichuk IPS-31


import java.io.*;
import java.net.*;
import java.util.*;

public class ClientSocketTask4 {
    public static void main(String[] args) {
        try {
            Socket s = new Socket("localhost", 1234);
            DataInputStream dis = new DataInputStream(s.getInputStream());
            DataOutputStream dos = new DataOutputStream(s.getOutputStream());
            Scanner sc = new Scanner(System.in);
            System.out.println("Enter the number of applicants: ");
            int n = sc.nextInt();
            dos.writeInt(n);
            Applicant[] applicants = new Applicant[n];
            for (int i = 0; i < n; i++) {
                System.out.println("Enter the id: ");
                int id = sc.nextInt();
                dos.writeInt(id);
                System.out.println("Enter the surname: ");
                String surname = sc.next();
                dos.writeUTF(surname);
                System.out.println("Enter the first name: ");
                String firstName = sc.next();
                dos.writeUTF(firstName);
                System.out.println("Enter the patronymic: ");
                String patronymic = sc.next();
                dos.writeUTF(patronymic);
                System.out.println("Enter the address: ");
                String address = sc.next();
                dos.writeUTF(address);
                System.out.println("Enter the telephone: ");
                String telephone = sc.next();
                dos.writeUTF(telephone);
                System.out.println("Enter the grades: ");
                int grades = sc.nextInt();
                dos.writeInt(grades);
                applicants[i] = new Applicant(id, surname, firstName, patronymic, address, telephone, grades);
            }
            System.out.println("Enter the sum of points: ");
            int sum = sc.nextInt();
            dos.writeInt(sum);
            System.out.println("Enter the number of applicants: ");
            int number = sc.nextInt();
            dos.writeInt(number);
            System.out.println("List of applicants with unsatisfactory grades: ");
            for (int i = 0; i < n; i++) {
                if (dis.readBoolean()) {
                    System.out.println(applicants[i]);
                }
            }
            System.out.println("List of entrants whose sum of points is higher than specified: ");
            for (int i = 0; i < n; i++) {
                if (dis.readBoolean()) {
                    System.out.println(applicants[i]);
                }
            }
            System.out.println("Select the given number n of applicants with the highest number the sum of points: ");
            for (int i = 0; i < n; i++) {
                if (dis.readBoolean()) {
                    System.out.println(applicants[i]);
                }
            }
            s.close();
        } catch (IOException e) {
            System.out.println(e);
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
