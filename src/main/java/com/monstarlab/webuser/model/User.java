package com.monstarlab.webuser.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor

@Table(name = "tbl_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)

    @NotEmpty(message = "username không được bỏ trống")
    @Size(min=4, message = "Độ dài username phải lớn hơn 4 kí tự")
    private String username;

    @Email(message = "{REGEX.VALIDATE.EMAIL}") // REGEX.VALIDATE.EMAIL in application.properties
    @NotEmpty(message = "Email không để trống")
    @Column(unique = true)
    private String email;

    @Past(message = "Ngày sinh phải là quá khứ")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;

//    @Enumerated(EnumType.STRING)
//    private Status status;
}
