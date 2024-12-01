package ua.tc.marketplace.util.mapper;

import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.tc.marketplace.model.entity.Category;
import ua.tc.marketplace.service.AdService;
import ua.tc.marketplace.service.CategoryService;

/**
 * A resolver component responsible for counting advertisements in a specific category.
 * <p>
 * This class serves as a bridge between the application logic and the {@link CategoryService},
 * providing a method to count the total number of advertisements associated with a given category.
 * </p>
 *
 * <p>
 * It uses Spring's {@code @Component} annotation for dependency injection and can be discovered
 * as a Spring-managed bean.
 * </p>
 */
@Component
public class CategoryCountResolver {

    @Autowired
    private AdService adService;

    /**
     * Counts all advertisements in a given category.
     *
     * @param category the {@link Category} for which advertisements are counted
     * @return the total number of advertisements in the specified category
     */
    @Named("countAllCategoryAds")
    public Long countAllCategoryAds(Category category) {
        return adService.countAdsByCategory(category);
    }
}