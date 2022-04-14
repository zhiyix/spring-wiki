package com.imetre.wiki.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.imetre.wiki.aspect.LogAspect;
import com.imetre.wiki.domain.EBookDocument;
import com.imetre.wiki.domain.EBookDocumentExample;
import com.imetre.wiki.mapper.EBookDocumentMapper;
import com.imetre.wiki.request.EBookDocumentQueryReq;
import com.imetre.wiki.request.EBookDocumentSaveReq;
import com.imetre.wiki.response.PageResp;
import com.imetre.wiki.utils.CopyUtil;
import com.imetre.wiki.utils.SnowFlake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class EBookDocumentService {
    private final static Logger LOG = LoggerFactory.getLogger(LogAspect.class);

    @Resource
    private EBookDocumentMapper bookDocumentMapper;

    @Resource
    private SnowFlake snowFlake;

    public List<EBookDocument> all() {
        EBookDocumentExample categoryExample = new EBookDocumentExample();
        categoryExample.setOrderByClause("sort asc");
        List<EBookDocument> categoryList = bookDocumentMapper.selectByExample(categoryExample);

        return categoryList;
    }

    public PageResp<EBookDocument> list(EBookDocumentQueryReq req) {
        EBookDocumentExample eBookDocumentExample = new EBookDocumentExample();
        EBookDocumentExample.Criteria criteria = eBookDocumentExample.createCriteria();
        if (!ObjectUtils.isEmpty(req.getName())) {
            criteria.andNameLike("%" + req.getName() + "%");
        }

        PageHelper.startPage(req.getPage(), req.getSize());
        List<EBookDocument> eBookDocuments = bookDocumentMapper.selectByExample(eBookDocumentExample);

        PageInfo<EBookDocument> pageInfo = new PageInfo<>(eBookDocuments);
        LOG.info("总行数：{}", pageInfo.getTotal());
        LOG.info("总页数：{}", pageInfo.getPages());

        PageResp<EBookDocument> pageResp = new PageResp();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(eBookDocuments);

        return pageResp;
    }

    /**
     * 保存
     */
    public void save(EBookDocumentSaveReq req) {
        EBookDocument bookDocument = CopyUtil.copy(req, EBookDocument.class);
        if (ObjectUtils.isEmpty(req.getId())) {
            // 新增
            bookDocument.setId(snowFlake.nextId());
            bookDocumentMapper.insert(bookDocument);
        } else {
            // 更新
            bookDocumentMapper.updateByPrimaryKey(bookDocument);
        }
    }

    public void delete(Long id) {
        bookDocumentMapper.deleteByPrimaryKey(id);
    }
}
