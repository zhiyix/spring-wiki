package com.imetre.wiki.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.imetre.wiki.aspect.LogAspect;
import com.imetre.wiki.domain.EBook;
import com.imetre.wiki.domain.EBookExample;
import com.imetre.wiki.mapper.EBookMapper;
import com.imetre.wiki.request.EBookQueryReq;
import com.imetre.wiki.request.EBookSaveReq;
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
public class EBookService {
    private final static Logger LOG = LoggerFactory.getLogger(LogAspect.class);

    @Resource
    private EBookMapper eBookMapper;

    @Resource
    private SnowFlake snowFlake;

    public PageResp<EBook> list(EBookQueryReq req) {
        EBookExample eBookExample = new EBookExample();
        EBookExample.Criteria criteria = eBookExample.createCriteria();
        if (!ObjectUtils.isEmpty(req.getName())) {
            criteria.andNameLike("%" + req.getName() + "%");
        }
        if (!ObjectUtils.isEmpty(req.getCategoryId2())) {
            criteria.andCategory2IdEqualTo(req.getCategoryId2());
        }
        PageHelper.startPage(req.getPage(), req.getSize());
        List<EBook> eBooks = eBookMapper.selectByExample(eBookExample);

        PageInfo<EBook> pageInfo = new PageInfo<>(eBooks);
        LOG.info("总行数：{}", pageInfo.getTotal());
        LOG.info("总页数：{}", pageInfo.getPages());

        // List<EBookResp> respList = new ArrayList<>();
        // for (EBook EBook : eBooks) {
        //     // EBookResp EBookResp = new EBookResp();
        //     // BeanUtils.copyProperties(EBook, EBookResp);
        //     // 对象复制
        //     EBookResp EBookResp = CopyUtil.copy(EBook, EBookResp.class);
        //
        //     respList.add(EBookResp);
        // }

        // 列表复制
//        List<PageResp> list = CopyUtil.copyList(eBooks, PageResp.class);

        PageResp<EBook> pageResp = new PageResp();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(eBooks);

        return pageResp;
    }

    /**
     * 保存
     */
    public void save(EBookSaveReq req) {
        EBook ebook = CopyUtil.copy(req, EBook.class);
        if (ObjectUtils.isEmpty(req.getId())) {
            // 新增
            ebook.setId(snowFlake.nextId());
            eBookMapper.insert(ebook);
        } else {
            // 更新
            eBookMapper.updateByPrimaryKey(ebook);
        }
    }

    public void delete(Long id) {
        eBookMapper.deleteByPrimaryKey(id);
    }
}
