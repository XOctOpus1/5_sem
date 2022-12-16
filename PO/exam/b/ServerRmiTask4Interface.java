import java.rmi.*;

public interface ServerRmiTask4Interface extends Remote {
    public void setN(int n) throws RemoteException;
    public void setId(int id) throws RemoteException;
    public void setSurname(String surname) throws RemoteException;
    public void setFirstName(String firstName) throws RemoteException;
    public void setPatronymic(String patronymic) throws RemoteException;
    public void setAddress(String address) throws RemoteException;
    public void setTelephone(String telephone) throws RemoteException;
    public void setGrades(int grades) throws RemoteException;
    public void setSum(int sum) throws RemoteException;
    public void setNumber(int number) throws RemoteException;
    public boolean getUnsatisfactoryGrades() throws RemoteException;
    public boolean getHigherSum() throws RemoteException;
    public boolean getHighestNumber() throws RemoteException;
}