import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {
    private String name;
    private String email;
    private String phone;
    private String telegram;
    private String vk;

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", telegram=" + telegram + '\'' +
                ", vk='" + vk + '\'' +
                '}';
    }
}