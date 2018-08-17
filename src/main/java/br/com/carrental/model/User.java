package br.com.carrental.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, insertable = false)
    private Long id;

    @Column(name = "id_document", nullable = false,
            length = 11, columnDefinition = "char(11)", unique = true)
    private String idDocument;

    @Column(name = "complete_name", nullable = false,
            length = 250, columnDefinition = "varchar(250)")
    private String name;

    @Column(length = 250, columnDefinition = "varchar(250)",
            unique = true)
    private String email;

    @Column(length = 250, columnDefinition = "varchar(250)")
    private String address;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Column(name = "birth_date")
    private Date birthDate;

    public User() {
        super();
    }

    public User(String idDocument, String name, String email, String address, Date birthDate) {
        this.idDocument = idDocument;
        this.name = name;
        this.email = email;
        this.address = address;
        this.birthDate = birthDate;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public Long getId() {
        return id;
    }

    public String getIdDocument() {
        return idDocument;
    }

    public String getAddress() {
        return address;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setIdDocument(String idDocument) {
        this.idDocument = idDocument;
    }
}
