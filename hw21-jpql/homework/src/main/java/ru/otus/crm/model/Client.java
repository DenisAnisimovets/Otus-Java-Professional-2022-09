package ru.otus.crm.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "client")
public class Client implements Cloneable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private Address address;

    @OneToMany(mappedBy = "client", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Phone> phones;


    public Client(String name) {
        this.name = name;
    }

    public Client(Long id, String name) {
        this.id = id;
        this.name = name;
        this.phones = new ArrayList<>();
        this.address = null;
    }

    public Client(Long id, String name, Address address, List<Phone> phones) {
        this.id = id;
        this.name = name;
        this.address = new Address(address);
        this.phones = phones;
        this.phones.forEach(phone -> phone.setClient(this));
    }

    public Client() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private List<Phone> copyPhones(List<Phone> phones) {
        if(phones == null) {
            return phones;
        }
        return phones.stream().peek(phone -> phone.setClient(this)).toList();
    }

    @Override
    public Client clone() {
        List<Phone> newPhones = new ArrayList<>();
        Client newClient = new Client(this.id, this.name, this.getAddress(), newPhones);
        phones.forEach(phone -> {
            Phone newPhone = new Phone(phone.getId(), phone.getNumber());
            newPhone.setClient(newClient);
            newPhones.add(newPhone);
        });

        return newClient;
    }

    public Address getAddress() {
        return address;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
