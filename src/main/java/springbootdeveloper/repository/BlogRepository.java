package springbootdeveloper.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import springbootdeveloper.domain.Article;

public interface BlogRepository extends JpaRepository<Article, Long> {

    // 제목과 내용을 기반으로 찾고 내림차순 정렬(최신 순으로 보기)
    Page<Article> findByTitleContainingOrContentContainingOrderByIdDesc(String title, String content, Pageable pageable);
}
