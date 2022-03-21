package com.imetre.wiki.controller;

import com.imetre.wiki.domain.EBook;
import com.imetre.wiki.request.EBookQueryReq;
import com.imetre.wiki.request.EBookSaveReq;
import com.imetre.wiki.response.PageResp;
import com.imetre.wiki.service.EBookService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/ebook")
public class EBookController {

    @Resource
    private EBookService eBookService;

    @GetMapping("/list")
    public PageResp<EBook> list(EBookQueryReq req) {
        PageResp<EBook> list = eBookService.list(req);
        return list;
    }

    @PostMapping("/save")
    public void save(@RequestBody EBookSaveReq req) {
        eBookService.save(req);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id) {
        eBookService.delete(id);
    }
}
