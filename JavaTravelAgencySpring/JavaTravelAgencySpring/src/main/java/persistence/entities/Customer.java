package persistence.entities;

import javax.persistence.*;
import java.util.Date;

@NamedQueries({
        @NamedQuery(name = "countEmail", query = "select count(email) from Customer where email = :email"),
        @NamedQuery(name = "findCustomerByEmail", query = "select customer from Customer customer where customer.email = :email"),
        @NamedQuery(name = "findCustomerByUsername", query = "select customer from Customer customer inner join customer.account account where account.userName = :userName"),
        @NamedQuery(name = "findCustomerByUserNameAndPassword", query = "select customer from Customer customer inner join customer.account account where account.userName = :userName and account.password = :password"),
        @NamedQuery(name = "deleteCustomerById", query = "delete from Customer where id = :id"),
})
@Entity
@Table(name = "customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "address")
    private String address;

    @Column(name = "date_of_birth")
    private Date birthDate;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "email")
    private String email;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "accounts_id")
    private Account account;

    public Customer(String name, String surname, String address, Date birthDate, String phoneNumber, String email, Account account) {
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.birthDate = birthDate;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.account = account;
    }

    public Customer() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @Override
    public String toString() {
        return "Customer: " + name + ", '" + surname + ", birthDate: " + birthDate + ", address: " + address
                + ", phone number: " + phoneNumber + ", email: " + email;
    }
}
