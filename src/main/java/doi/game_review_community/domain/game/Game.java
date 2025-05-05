package doi.game_review_community.domain.game;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "games")
@Getter @Setter @ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @ElementCollection(targetClass = Platform.class, fetch = FetchType.EAGER)
    @CollectionTable(
            name = "game_platforms",
            joinColumns = @JoinColumn(name = "game_id")
    )
    @Enumerated(EnumType.STRING)
    private List<Platform> platforms;

    private LocalDate releaseDate;

    @Column(length = 100)
    private String developer;

    @Column(length = 100)
    private String publisher;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String imageUrl;

    @Builder
    public Game(String name,
                List<Platform> platforms,
                LocalDate releaseDate,
                String developer,
                String publisher,
                String description,
                String imageUrl) {
        this.name = name;
        this.platforms = platforms;
        this.releaseDate = releaseDate;
        this.developer = developer;
        this.publisher = publisher;
        this.description = description;
        this.imageUrl = imageUrl;
    }
}
