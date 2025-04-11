package spring.spring_async.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long postOwnerId;

    private String body;

    public Comment(Long postOwnerId, String body) {
        this.postOwnerId = postOwnerId;
        this.body = body;
    }
}
