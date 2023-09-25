package springbootdeveloper.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import springbootdeveloper.domain.Article;
import springbootdeveloper.dto.ArticleListViewResponse;
import springbootdeveloper.dto.ArticleViewResponse;
import springbootdeveloper.repository.BlogRepository;
import springbootdeveloper.service.BlogService;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class BlogViewController {

    private final BlogService blogService;
    private final BlogRepository blogRepository;

    @GetMapping("/articles")
    public String getArticles(Model model, @PageableDefault(size = 5) Pageable pageable) {
        Page<Article> page = blogRepository.findAll(pageable);
        Page<ArticleListViewResponse> articles = page.map(ArticleListViewResponse::new);
        int startPage = Math.max(1, articles.getPageable().getPageNumber() - 4);
        int endPage = Math.max(articles.getTotalPages(), articles.getPageable().getPageNumber() - 4);
        model.addAttribute("startPage",startPage);
        model.addAttribute("endPage",endPage);
        model.addAttribute("articles", articles);

        return "articleList";
    }

    @GetMapping("/articles/{id}")
    public String getArticle(@PathVariable Long id, Model model) {
        Article article = blogService.findById(id);
        model.addAttribute("article", new ArticleViewResponse(article));

        return "article";
    }

    @GetMapping("/new-article")
    public String newArticle(@RequestParam(required = false) Long id, Model model) {
        if (id == null) {
            model.addAttribute("article", new ArticleViewResponse());
        } else {
            Article article = blogService.findById(id);
            model.addAttribute("article", new ArticleViewResponse(article));
        }
        return "newArticle";
    }
}
