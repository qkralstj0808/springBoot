package likelion.springbootgongpal.domain;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.*;
import org.hibernate.annotations.Comment;

import java.util.ArrayList;
import java.util.List;

import static lombok.AccessLevel.PROTECTED;

@Entity // 엔티티 클래스를 정의
@Getter // 필드에 접근 후 값 설정
@Setter // 필드에 접근 후 값 설정
@NoArgsConstructor // 인자 없는 생성자를 자동으로 생성
public class Item {
    @Id @GeneratedValue
    private Long id; // 고유 식별자

    @OneToMany(mappedBy = "item") // 일대 다 관계 매핑
    private List<OrderItem> orderItem = new ArrayList<>();

    private String brand;
    private String name;
    private Integer price;
    private Integer stock;

    /**
     * 비즈니스 로직
     */
    @Comment("재고 추가")
    public void addStock(int quantity) {
        this.stock += quantity;
    } // quantity 만큼 재고 증가

    @Comment("재고 감소")
    public void removeStock(int stockQuantity) {
        int restStock = this.stock - stockQuantity; // stockQuantity만큼 재고 감소
        if (restStock < 0) {
            throw new IllegalStateException("need more stock");
        }
        this.stock = restStock;
    }
}