package likelion.springbootgongpal.repository;

import likelion.springbootgongpal.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
