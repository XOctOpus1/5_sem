//var 4 Denys Gordiichuk IPS-31

import java.io.*;
import java.net.*;
import java.util.*;

public class ServerSocketTask4 {
    public static void main(String[] args) {
        try {
            ServerSocket ss = new ServerSocket(1234);
            Socket s = ss.accept();
            DataInputStream dis = new DataInputStream(s.getInputStream());
            DataOutputStream dos = new DataOutputStream(s.getOutputStream());
            int n = dis.readInt();
            Applicant[] applicants = new Applicant[n];
            for (int i = 0; i < n; i++) {
                int id = dis.readInt();
                String surname = dis.readUTF();
                String firstName = dis.readUTF();
                String patronymic = dis.readUTF();
                String address = dis.readUTF();
                String telephone = dis.readUTF();
                int grades = dis.readInt();
                applicants[i] = new Applicant(id, surname, firstName, patronymic, address, telephone, grades);
            }
            int sum = dis.readInt();
            int number = dis.readInt();
            for (int i = 0; i < n; i++) {
                dos.writeBoolean(applicants[i].getGrades() < 4);
            }
            for (int i = 0; i < n; i++) {
                dos.writeBoolean(applicants[i].getGrades() > sum);
            }
            Arrays.sort(applicants, new Comparator<Applicant>() {
                @Override
                public int compare(Applicant o1, Applicant o2) {
                    return o2.getGrades() - o1.getGrades();
                }
            });
            for (int i = 0; i < n; i++) {
                dos.writeBoolean(i < number);
            }
            s.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}