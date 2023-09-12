package springbootdeveloper.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import springbootdeveloper.domain.Article;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AddArticleRequest {

    private String title;
    private String content;

    public Article toEntity(String author, LocalDateTime createdAt, LocalDateTime updateAt) {
        return Article.builder()
                .title(title)
                .content(content)
                .author(author)
                .createdAt(createdAt)
                .updatedAt(updateAt)
                .build();
    }
}
