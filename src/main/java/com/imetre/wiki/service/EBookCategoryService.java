package com.imetre.wiki.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.imetre.wiki.aspect.LogAspect;
import com.imetre.wiki.domain.EBookCategory;
import com.imetre.wiki.domain.EBookCategoryExample;
import com.imetre.wiki.mapper.EBookCategoryMapper;
import com.imetre.wiki.request.EBookCategoryQueryReq;
import com.imetre.wiki.request.EBookCategorySaveReq;
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
public class EBookCategoryService {
    private final static Logger LOG = LoggerFactory.getLogger(LogAspect.class);

    @Resource
    private EBookCategoryMapper bookCategoryMapper;

    @Resource
    private SnowFlake snowFlake;

    public List<EBookCategory> all() {
        EBookCategoryExample categoryExample = new EBookCategoryExample();
        categoryExample.setOrderByClause("sort asc");
        List<EBookCategory> categoryList = bookCategoryMapper.selectByExample(categoryExample);

        return categoryList;
    }

    public PageResp<EBookCategory> list(EBookCategoryQueryReq req) {
        EBookCategoryExample eBookCategoryExample = new EBookCategoryExample();
        EBookCategoryExample.Criteria criteria = eBookCategoryExample.createCriteria();
        if (!ObjectUtils.isEmpty(req.getName())) {
            criteria.andNameLike("%" + req.getName() + "%");
        }

        PageHelper.startPage(req.getPage(), req.getSize());
        List<EBookCategory> eBookCategorys = bookCategoryMapper.selectByExample(eBookCategoryExample);

        PageInfo<EBookCategory> pageInfo = new PageInfo<>(eBookCategorys);
        LOG.info("总行数：{}", pageInfo.getTotal());
        LOG.info("总页数：{}", pageInfo.getPages());

        PageResp<EBookCategory> pageResp = new PageResp();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(eBookCategorys);

        return pageResp;
    }

    /**
     * 保存
     */
    public void save(EBookCategorySaveReq req) {
        EBookCategory bookCategory = CopyUtil.copy(req, EBookCategory.class);
        if (ObjectUtils.isEmpty(req.getId())) {
            // 新增
            bookCategory.setId(snowFlake.nextId());
            bookCategoryMapper.insert(bookCategory);
        } else {
            // 更新
            bookCategoryMapper.updateByPrimaryKey(bookCategory);
        }
    }

    public void delete(Long id) {
        bookCategoryMapper.deleteByPrimaryKey(id);
    }
}
