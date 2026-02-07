package ru.yp.sprint7pw.service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.yp.sprint7pw.controller.dto.Page;
import ru.yp.sprint7pw.controller.dto.PageParams;
import ru.yp.sprint7pw.model.Product;
import ru.yp.sprint7pw.repository.ProductRepository;

import java.util.NoSuchElementException;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Flux<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    @Cacheable(value = "page", key = "#name")
    public Mono<Page> getProductsCacheable(String name, String search, String sort, Integer pageNumber, Integer pageSize) {
        return getProducts(search, sort, pageNumber, pageSize);
    }

    @Override
    public Mono<Page> getProducts(String search, String sort, Integer pageNumber, Integer pageSize) {

        search = ("%" + search + "%").toLowerCase();
        int offset = (pageNumber - 1) * pageSize;
        int limit = pageSize;

        Flux<Product> products = switch (ServiceConstants.SortType.valueOf(sort)) {
            case NO -> productRepository.findProductsByCriterias(search, offset, limit);
            case ALPHA -> productRepository.findProductsByCriteriasOrderByTitle(search, offset, limit);
            case PRICE -> productRepository.findProductsByCriteriasOrderByPrice(search, offset, limit);
        };

        return products.collectList()
                .zipWith(productRepository.countProductsByCriterias(search))
                .map(tuple -> {

                            Page page = new Page();
                            page.setProducts(tuple.getT1());

                            PageParams pageParams = new PageParams();
                            pageParams.setPageNumber(pageNumber);
                            pageParams.setPageSize(pageSize);

                            Integer productsCount = tuple.getT2();
                            int totalPages = 0;

                            if (productsCount <= pageSize) totalPages = 1;
                            else {
                                totalPages = productsCount / pageSize;
                                totalPages = (productsCount % pageSize == 0) ? totalPages : totalPages + 1;
                            }

                            if (totalPages <= 1) {
                                pageParams.setHasPrevious(false);
                                pageParams.setHasNext(false);
                            } else {
                                if (pageNumber == 1) {
                                    pageParams.setHasPrevious(false);
                                    pageParams.setHasNext(true);
                                } else if (pageNumber < totalPages) {
                                    pageParams.setHasPrevious(true);
                                    pageParams.setHasNext(true);
                                } else {
                                    pageParams.setHasPrevious(true);
                                    pageParams.setHasNext(false);
                                }
                            }

                            page.setPageParams(pageParams);
                            return page;
                        }
                );
    }

    @Cacheable(value = "product", key = "#name")
    @Override
    public Mono<Product> getProductCacheable(String name, Integer itemId) {
        return getProduct(itemId);
    }

    @Override
    public Mono<Product> getProduct(Integer itemId) {
        return productRepository.findById(itemId)
                .switchIfEmpty(Mono.error(new NoSuchElementException("Product not found")));
    }

    @Override
    public Mono<Product> create(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Mono<Product> update(Product product) {
        return productRepository.save(product);
    }

    @Override
    @CacheEvict(value = {"page", "product"}, allEntries = true)
    public Mono<Void> clearCaches() {
        return Mono.empty();
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = "page", allEntries = true),
            @CacheEvict(cacheNames="product", key="#name")
    })
    public Mono<Void> clearCaches(String name) {
        return Mono.empty();
    }
}
