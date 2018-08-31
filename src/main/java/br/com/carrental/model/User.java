package br.com.carrental.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * DatabaseEntity class of User
 *
 * @author Micael
 * */

@Entity
public class User implements DatabaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, insertable = false)
    private Long id;

    @Column(name = "id_document", nullable = false,
            length = 11, columnDefinition = "char(11)", unique = true)
    private String idDocument;

    @Column(name = "complete_name", nullable = false,
            length = 50, columnDefinition = "varchar(50)")
    private String name;

    @Column(length = 100, columnDefinition = "varchar(100)",
            unique = true)
    private String email;

    @Column(length = 100, columnDefinition = "varchar(100)")
    private String address;

    @Temporal(TemporalType.DATE)
    @Column(name = "birth_date")
    private Date birthDate;

    public User() {
        super();
    }

    public User(final String idDocument, final String name, String email, String address, Date birthDate) {
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

    public void setName(String name) {
        this.name = name;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
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
