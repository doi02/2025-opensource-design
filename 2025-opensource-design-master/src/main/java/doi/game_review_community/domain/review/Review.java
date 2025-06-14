package doi.game_review_community.domain.review;

import doi.game_review_community.domain.game.Game;
import doi.game_review_community.domain.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "reviews",
        uniqueConstraints = @UniqueConstraint(
            columnNames = {"game_id", "author_id"}
        ))
@Getter @Setter @ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_id", nullable = false)
    private Game game;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User author;

    @Column(length = 200, nullable = false)
    private String content;

    @Column(nullable = false)
    private double graphicScore;

    @Column(nullable = false)
    private double storyScore;

    @Column(nullable = false)
    private double gameplayScore;

    @Column(nullable = false)
    private double soundScore;

    @Column(nullable = false)
    private double totalScore;

    @Column(nullable = false)
    private int likeCount = 0;

    @Column(nullable = false)
    private int dislikeCount = 0;

    @Transient
    private Set<Long> likedUserIds = new HashSet<>();

    @Transient
    private Set<Long> dislikedUserIds = new HashSet<>();

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdDate;

    @Column(nullable = false)
    private LocalDateTime modifiedDate;

    public Review(Game game, User author, String content,
                  double graphicScore, double storyScore,
                  double gameplayScore, double soundScore) {
        this.game = game;
        this.author = author;
        this.content = content;
        this.graphicScore = graphicScore;
        this.storyScore = storyScore;
        this.gameplayScore = gameplayScore;
        this.soundScore = soundScore;
        calculateTotalScore();
    }

    @PrePersist
    public void prePersist() {
        this.createdDate = LocalDateTime.now();
        this.modifiedDate = this.createdDate;
        calculateTotalScore();
    }

    @PreUpdate
    public void preUpdate() {
        this.modifiedDate = LocalDateTime.now();
        calculateTotalScore();
    }

    private void calculateTotalScore() {
        this.totalScore = (graphicScore + storyScore + gameplayScore + soundScore) * 5;
    }

    public void toggleLike(Long userId) {
        if (likedUserIds.remove(userId)) {
            // 이미 like였다면 like set에서 리무브
        } else {
            likedUserIds.add(userId);
            dislikedUserIds.remove(userId); //dislike였다면 dislike를 제거
        }
        updateCounts();
    }

    public void toggleDislike(Long userId) {
        if (dislikedUserIds.remove(userId)) {
            // 이미 dislike라면 dislike set에서 리무브
        } else {
            dislikedUserIds.add(userId);
            likedUserIds.remove(userId);//like였으면 like를 제거
        }
        updateCounts();
    }

    private void updateCounts() {
        this.likeCount = likedUserIds.size();
        this.dislikeCount = dislikedUserIds.size();
    }
}