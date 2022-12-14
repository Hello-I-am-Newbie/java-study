package com.onlinejava.project.bookstore.adapters.file;

import com.onlinejava.project.bookstore.Main;
import com.onlinejava.project.bookstore.application.domain.entity.Purchase;
import com.onlinejava.project.bookstore.application.ports.output.PurchaseRepository;
import com.onlinejava.project.bookstore.core.factory.Bean;

import java.util.List;

@Bean
public class FilePurchaseRepository extends FileRepository<Purchase> implements PurchaseRepository {
    @Override
    public List<Purchase> findAll() {
        return this.list;
    }

    @Override
    public void save() {
        saveEntityToCSVFile("purchaselist.csv", Purchase.class, Main.HAS_HEADER);
    }

    @Override
    public void add(Purchase purchase) {
        this.list.add(purchase);
    }

    @Override
    public void initData() {
        this.list = getEntityListFromLines("purchaselist.csv", Purchase.class);
    }
}