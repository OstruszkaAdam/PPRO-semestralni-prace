package cz.uhk.ppro.dima.repository.jpa;

import cz.uhk.ppro.dima.model.Article;
import cz.uhk.ppro.dima.repository.SearchRepository;
import org.apache.lucene.search.Query;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional
public class JpaSearchRepositoryImpl implements SearchRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public List search(String hledanyVyraz) {

        FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
        QueryBuilder queryBuilder = fullTextEntityManager.getSearchFactory()
                .buildQueryBuilder().forEntity(Article.class).get();

        Query query = queryBuilder
                .phrase()
                .withSlop(4) // slop factor = this works like a within or near operator = maximalni mezera (pocet slov) mezi vyhledavanymi slovy
                .onField("name") //vyhledavani v nazvech clanku
                .andField("text") // vyhledavani v poli text
                .sentence(hledanyVyraz)
                .createQuery();

        FullTextQuery jpaQuery = fullTextEntityManager.createFullTextQuery(query, Article.class);

        // execute search
        List results = jpaQuery.getResultList();

        return results;

    }

}
