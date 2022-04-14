package com.imetre.wiki.controller;

import com.imetre.wiki.domain.EBookDocument;
import com.imetre.wiki.request.EBookDocumentQueryReq;
import com.imetre.wiki.request.EBookDocumentSaveReq;
import com.imetre.wiki.response.PageResp;
import com.imetre.wiki.service.EBookDocumentService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/ebook/document")
public class EBookDocumentController {

    @Resource
    private EBookDocumentService bookDocumentService;

    @GetMapping("/all")
    public List<EBookDocument> all() {
        List<EBookDocument> list = bookDocumentService.all();
        return list;
    }

    @GetMapping("/list")
    public PageResp<EBookDocument> list(@Valid EBookDocumentQueryReq req) {
        PageResp<EBookDocument> list = bookDocumentService.list(req);
        return list;
    }

    @PostMapping("/save")
    public void save(@RequestBody EBookDocumentSaveReq req) {
        bookDocumentService.save(req);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id) {
        bookDocumentService.delete(id);
    }
}
