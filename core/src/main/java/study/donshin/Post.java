package study.donshin;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter
public class Post {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String title;

    @Column(nullable = false, length = 180)
    private String uploadedFileName;
}
