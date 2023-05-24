package likelion.springbootgongpal.domain;

import jakarta.persistence.*;
import lombok.Data;

import javax.lang.model.type.ArrayType;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Member {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "member")
    private List<Order> OrderList = new ArrayList<>();

    public static Member createMember(String name, Address address){
        Member member = new Member();
        member.name = name;
        member.address = address;
        return member;
    }

}
