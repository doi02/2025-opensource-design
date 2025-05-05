package doi.game_review_community.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;
import jakarta.validation.constraints.*;
import lombok.NoArgsConstructor;

//리뷰정보 DTO
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewDto {

    private Long id;

    private Long gameId;

    private String authorUsername;

    @NotBlank
    private String content;

    @DecimalMin("0.0") @DecimalMax("5.0")
    private double graphicScore;

    @DecimalMin("0.0") @DecimalMax("5.0")
    private double storyScore;

    @DecimalMin("0.0") @DecimalMax("5.0")
    private double gameplayScore;

    @DecimalMin("0.0") @DecimalMax("5.0")
    private double soundScore;

    private double totalScore;

    private int likeCount;

    private int dislikeCount;

    private LocalDateTime createdDate;

    private LocalDateTime modifiedDate;
}