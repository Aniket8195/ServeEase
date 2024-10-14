package com.example.ServeEase.Repository;

import com.example.ServeEase.Model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepo extends JpaRepository<Category,Long> {
    boolean existsByCategoryName(String categoryName);
    void deleteByCategoryName(String categoryName);
}
