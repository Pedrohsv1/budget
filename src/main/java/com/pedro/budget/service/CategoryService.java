package com.pedro.budget.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.pedro.budget.dto.CategoryRequest;
import com.pedro.budget.dto.CategoryResponse;
import com.pedro.budget.entity.Category;
import com.pedro.budget.entity.CategoryType;
import com.pedro.budget.entity.User;
import com.pedro.budget.mapper.CategoryMapper;
import com.pedro.budget.repository.CategoryRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public CategoryResponse createCategory(CategoryRequest request, User user) {
        if (categoryRepository.existsByTitleAndUserId(request.getTitle(), user.getId())) {
            throw new IllegalArgumentException("Category title must be unique");
        }

        return categoryMapper.toResponse(categoryRepository.save(categoryMapper.toEntity(request, user)));
    }

    public void deleteCategory(UUID id, User user) {
        Category category = categoryRepository.findByIdAndUserId(id, user.getId())
                .orElseThrow(() -> new EntityNotFoundException("Cannot find category"));

        categoryRepository.delete(category);
    }

    public List<CategoryResponse> listCategories(User user) {
        List<Category> categories = categoryRepository.findAllByUser(user);

        return categoryMapper.toResponseList(categories);
    }

    public Void createDefaultCategories(User user) {
        List<Category> categories = new ArrayList<>();

        // Food
        categories.add(new Category("Food and drinks", CategoryType.FOOD, user));
        categories.add(new Category("Food delivery", CategoryType.FOOD, user));
        categories.add(new Category("Restaurants, bars and cafés", CategoryType.FOOD, user));
        categories.add(new Category("Groceries", CategoryType.FOOD, user));

        // Shopping
        categories.add(new Category("Sporting goods", CategoryType.SHOPPING, user));
        categories.add(new Category("Kids and baby", CategoryType.SHOPPING, user));
        categories.add(new Category("Cashback", CategoryType.SHOPPING, user));
        categories.add(new Category("Shopping", CategoryType.SHOPPING, user));
        categories.add(new Category("Online shopping", CategoryType.SHOPPING, user));
        categories.add(new Category("Electronics", CategoryType.SHOPPING, user));
        categories.add(new Category("Bookstores", CategoryType.SHOPPING, user));
        categories.add(new Category("Stationery", CategoryType.SHOPPING, user));
        categories.add(new Category("Pet shops and vets", CategoryType.SHOPPING, user));
        categories.add(new Category("Clothing", CategoryType.SHOPPING, user));

        // Health and care
        categories.add(new Category("Gyms and fitness centers", CategoryType.HEALTH_AND_CARE, user));
        categories.add(new Category("Wellness", CategoryType.HEALTH_AND_CARE, user));
        categories.add(new Category("Dentist", CategoryType.HEALTH_AND_CARE, user));
        categories.add(new Category("Pharmacy", CategoryType.HEALTH_AND_CARE, user));
        categories.add(new Category("Hospitals, clinics and labs", CategoryType.HEALTH_AND_CARE, user));
        categories.add(new Category("Eyewear and opticians", CategoryType.HEALTH_AND_CARE, user));
        categories.add(new Category("Sports and activities", CategoryType.HEALTH_AND_CARE, user));
        categories.add(new Category("Healthcare", CategoryType.HEALTH_AND_CARE, user));
        categories.add(new Category("Health and care", CategoryType.HEALTH_AND_CARE, user));

        // Digital services
        categories.add(new Category("Gaming", CategoryType.DIGITAL_SERVICES, user));
        categories.add(new Category("Digital services", CategoryType.DIGITAL_SERVICES, user));
        categories.add(new Category("Music streaming", CategoryType.DIGITAL_SERVICES, user));
        categories.add(new Category("Video streaming", CategoryType.DIGITAL_SERVICES, user));

        // Transport
        categories.add(new Category("Bike rentals", CategoryType.TRANSPORT, user));
        categories.add(new Category("Car rentals", CategoryType.TRANSPORT, user));
        categories.add(new Category("Parking", CategoryType.TRANSPORT, user));
        categories.add(new Category("Vehicle maintenance", CategoryType.TRANSPORT, user));
        categories.add(new Category("Traffic tickets", CategoryType.TRANSPORT, user));
        categories.add(new Category("Tolls and in-vehicle payments", CategoryType.TRANSPORT, user));
        categories.add(new Category("Gas stations", CategoryType.TRANSPORT, user));
        categories.add(new Category("Auto services", CategoryType.TRANSPORT, user));
        categories.add(new Category("Vehicle taxes and fees", CategoryType.TRANSPORT, user));
        categories.add(new Category("Taxis and ride-hailing", CategoryType.TRANSPORT, user));
        categories.add(new Category("Transport", CategoryType.TRANSPORT, user));
        categories.add(new Category("Public transit", CategoryType.TRANSPORT, user));

        // Housing
        categories.add(new Category("Water", CategoryType.HOUSING, user));
        categories.add(new Category("Rent", CategoryType.HOUSING, user));
        categories.add(new Category("Mobile phone", CategoryType.HOUSING, user));
        categories.add(new Category("Electricity", CategoryType.HOUSING, user));
        categories.add(new Category("Gas", CategoryType.HOUSING, user));
        categories.add(new Category("Property taxes", CategoryType.HOUSING, user));
        categories.add(new Category("Internet", CategoryType.HOUSING, user));
        categories.add(new Category("Housing", CategoryType.HOUSING, user));
        categories.add(new Category("Utilities", CategoryType.HOUSING, user));
        categories.add(new Category("Telecommunications", CategoryType.HOUSING, user));
        categories.add(new Category("TV", CategoryType.HOUSING, user));
        categories.add(new Category("Household items", CategoryType.HOUSING, user));

        // Leisure and entertainment
        categories.add(new Category("Airports and airlines", CategoryType.LEISURE_AND_ENTERTAINMENT, user));
        categories.add(new Category("Tickets", CategoryType.LEISURE_AND_ENTERTAINMENT, user));
        categories.add(new Category("Movies, theater and concerts", CategoryType.LEISURE_AND_ENTERTAINMENT, user));
        categories.add(new Category("Stadiums and arenas", CategoryType.LEISURE_AND_ENTERTAINMENT, user));
        categories.add(new Category("Lodging", CategoryType.LEISURE_AND_ENTERTAINMENT, user));
        categories.add(new Category("Leisure and entertainment", CategoryType.LEISURE_AND_ENTERTAINMENT, user));
        categories.add(new Category("Museums and attractions", CategoryType.LEISURE_AND_ENTERTAINMENT, user));
        categories.add(new Category("Bus tickets", CategoryType.LEISURE_AND_ENTERTAINMENT, user));
        categories.add(new Category("Frequent flyer programs", CategoryType.LEISURE_AND_ENTERTAINMENT, user));
        categories.add(new Category("Travel", CategoryType.LEISURE_AND_ENTERTAINMENT, user));

        // Finances
        categories.add(new Category("Margin adjustment", CategoryType.FINANCES, user));
        categories.add(new Category("Late payment and overdraft fees", CategoryType.FINANCES, user));
        categories.add(new Category("Student loans", CategoryType.FINANCES, user));
        categories.add(new Category("Loans", CategoryType.FINANCES, user));
        categories.add(new Category("Loans and financing", CategoryType.FINANCES, user));
        categories.add(new Category("Financing", CategoryType.FINANCES, user));
        categories.add(new Category("Auto loans", CategoryType.FINANCES, user));
        categories.add(new Category("Mortgage", CategoryType.FINANCES, user));
        categories.add(new Category("Multi-asset funds", CategoryType.FINANCES, user));
        categories.add(new Category("Automatic investments", CategoryType.FINANCES, user));
        categories.add(new Category("Investments", CategoryType.FINANCES, user));
        categories.add(new Category("Interest charges", CategoryType.FINANCES, user));
        categories.add(new Category("Interest and dividend income", CategoryType.FINANCES, user));
        categories.add(new Category("Legal obligations", CategoryType.FINANCES, user));
        categories.add(new Category("Credit card payment", CategoryType.FINANCES, user));
        categories.add(new Category("Credit card bill installments", CategoryType.FINANCES, user));
        categories.add(new Category("Pension", CategoryType.FINANCES, user));
        categories.add(new Category("Child support and alimony", CategoryType.FINANCES, user));
        categories.add(new Category("Fixed income", CategoryType.FINANCES, user));
        categories.add(new Category("Equities", CategoryType.FINANCES, user));
        categories.add(new Category("Blocked balance", CategoryType.FINANCES, user));
        categories.add(new Category("Transfer - Boleto", CategoryType.FINANCES, user));
        categories.add(new Category("Transfer - Currency exchange", CategoryType.FINANCES, user));
        categories.add(new Category("Transfer - Check", CategoryType.FINANCES, user));
        categories.add(new Category("Transfer - Cash", CategoryType.FINANCES, user));
        categories.add(new Category("Transfer - Same bank", CategoryType.FINANCES, user));
        categories.add(new Category("Transfer - PIX", CategoryType.FINANCES, user));
        categories.add(new Category("Transfer - TED", CategoryType.FINANCES, user));
        categories.add(new Category("Transfer between own accounts", CategoryType.FINANCES, user));
        categories.add(new Category("Transfer between own accounts - Cash", CategoryType.FINANCES, user));
        categories.add(new Category("Transfer between own accounts - PIX", CategoryType.FINANCES, user));
        categories.add(new Category("Transfer between own accounts - TED", CategoryType.FINANCES, user));
        categories.add(new Category("Third-party transfer - Boleto", CategoryType.FINANCES, user));
        categories.add(new Category("Third-party transfer - Debit", CategoryType.FINANCES, user));
        categories.add(new Category("Third-party transfer - DOC", CategoryType.FINANCES, user));
        categories.add(new Category("Third-party transfer - PIX", CategoryType.FINANCES, user));
        categories.add(new Category("Third-party transfer - TED", CategoryType.FINANCES, user));
        categories.add(new Category("Transfers", CategoryType.FINANCES, user));
        categories.add(new Category("Third-party transfers", CategoryType.FINANCES, user));
        categories.add(new Category("Transfer - DOC", CategoryType.FINANCES, user));

        // Income
        categories.add(new Category("Retirement", CategoryType.INCOME, user));
        categories.add(new Category("Business income", CategoryType.INCOME, user));
        categories.add(new Category("Government benefits", CategoryType.INCOME, user));
        categories.add(new Category("Income", CategoryType.INCOME, user));
        categories.add(new Category("One-time income", CategoryType.INCOME, user));
        categories.add(new Category("Salary", CategoryType.INCOME, user));

        // Education
        categories.add(new Category("Daycare", CategoryType.EDUCATION, user));
        categories.add(new Category("Online courses", CategoryType.EDUCATION, user));
        categories.add(new Category("Education", CategoryType.EDUCATION, user));
        categories.add(new Category("School", CategoryType.EDUCATION, user));
        categories.add(new Category("University", CategoryType.EDUCATION, user));

        // Taxes and fees
        categories.add(new Category("Income tax", CategoryType.TAXES_AND_FEES, user));
        categories.add(new Category("Investment taxes", CategoryType.TAXES_AND_FEES, user));
        categories.add(new Category("Taxes", CategoryType.TAXES_AND_FEES, user));
        categories.add(new Category("Financial transaction taxes", CategoryType.TAXES_AND_FEES, user));
        categories.add(new Category("Bank fees", CategoryType.TAXES_AND_FEES, user));
        categories.add(new Category("Credit card fees", CategoryType.TAXES_AND_FEES, user));
        categories.add(new Category("Checking account fees", CategoryType.TAXES_AND_FEES, user));
        categories.add(new Category("Transfer and ATM fees", CategoryType.TAXES_AND_FEES, user));

        // Insurance
        categories.add(new Category("Auto insurance", CategoryType.INSURANCE, user));
        categories.add(new Category("Life insurance", CategoryType.INSURANCE, user));
        categories.add(new Category("Home insurance", CategoryType.INSURANCE, user));
        categories.add(new Category("Health insurance", CategoryType.INSURANCE, user));
        categories.add(new Category("Insurance", CategoryType.INSURANCE, user));

        // Betting and games
        categories.add(new Category("Betting", CategoryType.BETTING_AND_GAMES, user));
        categories.add(new Category("Online betting", CategoryType.BETTING_AND_GAMES, user));
        categories.add(new Category("Lottery", CategoryType.BETTING_AND_GAMES, user));

        // Donations
        categories.add(new Category("Donations", CategoryType.DONATIONS, user));

        // Others
        categories.add(new Category("Refunds", CategoryType.OTHERS, user));
        categories.add(new Category("Others", CategoryType.OTHERS, user));
        categories.add(new Category("Services", CategoryType.OTHERS, user));

        categoryRepository.saveAll(categories);

        return null;
    }
}