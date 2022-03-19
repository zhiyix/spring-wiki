package com.imetre.wiki.controller;

import com.imetre.wiki.domain.EBook;
import com.imetre.wiki.service.EBookService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/ebook")
public class EBookController {

    @Resource
    private EBookService eBookService;

    @GetMapping("/list")
    public List<EBook> list() {
        List<EBook> list = eBookService.list();
        return list;
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id) {
        eBookService.delete(id);
    }
}
