package br.com.carrental.service.dto;

import br.com.carrental.service.dto.util.CalendarDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Calendar;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Data Transfer Object of User. Class that will receive the elements of a JSON request and transport it.
 *
 * @author Micael
 */
@ApiModel
public class UserDTO implements DTO {

    @NotNull
    @NotBlank
    @Size(max = 11, message = "Id Document must have under 11 characters")
    @ApiModelProperty(example = "12345678901", position = 0)
    private String idDocument;

    @Size(min = 2, max = 50)
    @NotNull
    @ApiModelProperty(example = "Nome Legal", position = 1)
    private String name;

    @Email
    @ApiModelProperty(example = "email@legal.com", position = 2)
    private String email;

    @Size(max = 100)
    @ApiModelProperty(example = "Rua Legal, 123 - Estado Legal", position = 3)
    private String address;

    @JsonDeserialize(using = CalendarDeserializer.class)
    @ApiModelProperty(example = "14/01/1998", position = 4)
    private Calendar birthDate;

    public UserDTO() {
    }

    public UserDTO(String idDocument, String name, String email, String address, Calendar birthDate) {
        this.idDocument = idDocument;
        this.name = name;
        this.email = email;
        this.address = address;
        this.birthDate = birthDate;
    }

    public String getIdDocument() {
        return idDocument;
    }

    public void setIdDocument(String idDocument) {
        this.idDocument = idDocument;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Calendar getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Calendar birthDate) {
        this.birthDate = birthDate;
    }
}
