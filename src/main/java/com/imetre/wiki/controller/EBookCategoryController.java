package com.imetre.wiki.controller;

import com.imetre.wiki.domain.EBookCategory;
import com.imetre.wiki.request.EBookCategoryQueryReq;
import com.imetre.wiki.request.EBookCategorySaveReq;
import com.imetre.wiki.response.PageResp;
import com.imetre.wiki.service.EBookCategoryService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/ebook/category")
public class EBookCategoryController {

    @Resource
    private EBookCategoryService bookCategoryService;

    @GetMapping("/all")
    public List<EBookCategory> all() {
        List<EBookCategory> list = bookCategoryService.all();
        return list;
    }

    @GetMapping("/list")
    public PageResp<EBookCategory> list(@Valid EBookCategoryQueryReq req) {
        PageResp<EBookCategory> list = bookCategoryService.list(req);
        return list;
    }

    @PostMapping("/save")
    public void save(@RequestBody EBookCategorySaveReq req) {
        bookCategoryService.save(req);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id) {
        bookCategoryService.delete(id);
    }
}
