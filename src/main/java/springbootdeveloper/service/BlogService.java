package springbootdeveloper.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springbootdeveloper.domain.Article;
import springbootdeveloper.dto.AddArticleRequest;
import springbootdeveloper.dto.UpdateArticleRequest;
import springbootdeveloper.repository.BlogRepository;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class BlogService {

    private final BlogRepository blogRepository;

    public Article save(AddArticleRequest request, String username) {
        return blogRepository.save(request.toEntity(username));
    }

    public List<Article> findAll() {
        return blogRepository.findAll();
    }

    public Article findById(long id) {
        return blogRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found: " + id));
    }

    public void delete(long id) {
        Article article = blogRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found : " + id));

        authorizeArticleAuthor(article);
        blogRepository.delete(article);
    }

    @Transactional
    public Article update(long id, UpdateArticleRequest request) {
        Article article = blogRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found: " + id));

        authorizeArticleAuthor(article);
        article.update(request.getTitle(), request.getContent());

        return article;
    }

    //게시글을 작성한 유저인지 확인
    private static void authorizeArticleAuthor(Article article) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!article.getAuthor().equals(username)) {
            throw new IllegalArgumentException("not authorized");
        }
    }
}
