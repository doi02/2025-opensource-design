package doi.game_review_community.rawg.dto;

import lombok.Data;
import java.util.List;

@Data
public class RawgGamesResponseDto {
    private long count;
    private String next;
    private String previous;
    private List<RawgGameDto> results;
}
