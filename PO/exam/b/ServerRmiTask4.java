//var 4 Denys Gordiichuk IPS-31

import java.io.*;
import java.net.*;
import java.util.*;
import java.rmi.*;
import java.rmi.server.*;

public class ServerRmiTask4 extends UnicastRemoteObject implements ServerRmiTask4Interface {
    private int n;
    private int id;
    private String surname;
    private String firstName;
    private String patronymic;
    private String address;
    private String telephone;
    private int grades;
    private int sum;
    private int number;

    public ServerRmiTask4() throws RemoteException {
        super();
    }

    public void setN(int n) {
        this.n = n;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public void setGrades(int grades) {
        this.grades = grades;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public boolean getUnsatisfactoryGrades() {
        return grades < 60;
    }

    public boolean higherSumOfPoints() {
        return grades > sum;
    }

    public boolean getHighestNumber() {
        return grades > number;
    }
}

