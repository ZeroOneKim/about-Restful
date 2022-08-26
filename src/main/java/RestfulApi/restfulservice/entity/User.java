package RestfulApi.restfulservice.entity;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@AllArgsConstructor
//@JsonFilter("UserInfo")
@NoArgsConstructor
//JsonIgnoreProperties(value={"password", "ssn"})
public class User {
    private Integer id;

    @Size(min=2, message="Name은 두글자 이상 입력하세요") //Validation 유효성 즉 2글자이상을 받아들인다.
    private String name;
    @Past
    private Date JoinDate;

    private String password;
    private String ssn;
}
