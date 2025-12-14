package ru.yp.sprint5pw.service;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.yp.sprint5pw.controller.dto.PageParams;
import ru.yp.sprint5pw.model.Product;
import ru.yp.sprint5pw.repository.ProductRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Iterable<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getProducts(String search, String sort, PageParams pageParams) {

        Pageable pageRequest;

        if (SortType.NO.toString().equals(sort)) {
            pageRequest = PageRequest.of(pageParams.getPageNumber() - 1,
                    pageParams.getPageSize());
        } else {
            String sortCriteria = SortType.ALPHA.toString().equals(sort) ? "title" : "price";
            pageRequest = PageRequest.of(pageParams.getPageNumber() - 1,
                    pageParams.getPageSize(),
                    Sort.by(sortCriteria));
        }

        Page<Product> page = productRepository.findProductsWithCriterias(("%" + search + "%").toLowerCase(), pageRequest);

        if (page.getTotalPages() <= 1) {
            pageParams.setHasPrevious(false);
            pageParams.setHasNext(false);
        } else {
            if (pageParams.getPageNumber() == 1) {
                pageParams.setHasPrevious(false);
                pageParams.setHasNext(true);
            } else if (pageParams.getPageNumber() < page.getTotalPages()) {
                pageParams.setHasPrevious(true);
                pageParams.setHasNext(true);
            } else {
                pageParams.setHasPrevious(true);
                pageParams.setHasNext(false);
            }
        }

        return page.getContent();
    }
}
