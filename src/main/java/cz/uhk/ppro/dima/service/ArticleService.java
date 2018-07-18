package cz.uhk.ppro.dima.service;

import cz.uhk.ppro.dima.model.Article;
import cz.uhk.ppro.dima.model.Category;
import cz.uhk.ppro.dima.model.Comment;
import cz.uhk.ppro.dima.model.User;
import cz.uhk.ppro.dima.repository.ArticleRepository;
import cz.uhk.ppro.dima.repository.CategoryRepository;
import cz.uhk.ppro.dima.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
public class ArticleService {
    private CategoryRepository categoryRepo;
    private ArticleRepository articleRepo;
    private CommentRepository commentRepo;

    @Autowired
    public ArticleService(CategoryRepository categoryRepo, ArticleRepository articleRepo, CommentRepository commentRepo) {
        this.categoryRepo = categoryRepo;
        this.articleRepo = articleRepo;
        this.commentRepo = commentRepo;
    }

    @Transactional
    public void saveArticle(Article article, User user) {
        article.setTimestamp(new Timestamp(System.currentTimeMillis()));
        article.setUser(user);
        articleRepo.save(article);
    }

    @Transactional
    public List<Category> findAllCategories() {
        return categoryRepo.findAll();
    }

    @Transactional
    public Category findCategoryByName(String name){
        return categoryRepo.findByName(name);
    }

    @Transactional()
    public List<Article> findArticles() throws DataAccessException {
        return articleRepo.findAll();
    }
    @Transactional
    public Optional<Article> findById(int id) {
        return articleRepo.findById(id);
    }

    @Transactional
    public void saveComment(Comment comment, User user, int articleId) {
        comment.setAuthor(user);
        comment.setArticle(articleRepo.findById(articleId).get());
        comment.setPostDate(new Timestamp(System.currentTimeMillis()));
        commentRepo.save(comment);
    }
}
