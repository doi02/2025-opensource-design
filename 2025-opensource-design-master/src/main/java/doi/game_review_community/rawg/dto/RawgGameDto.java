// 2. RawgGameDto.java
package doi.game_review_community.rawg.dto;

import doi.game_review_community.rawg.dto.Platform.PlatformWrapperDto;
import doi.game_review_community.rawg.dto.developer.DeveloperWrapperDto;
import doi.game_review_community.rawg.dto.publisher.PublisherWrapperDto;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;
import java.util.List;

@Data
public class RawgGameDto {
    private Long id;
    private String name;

    @JsonProperty("released")
    private LocalDate releaseDate;

    @JsonProperty("background_image")
    private String imageUrl;

    private List<PlatformWrapperDto> platforms;
    private List<DeveloperWrapperDto> developers;
    private List<PublisherWrapperDto> publishers;

    private String description;
}
