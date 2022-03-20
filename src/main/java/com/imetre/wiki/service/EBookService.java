package com.imetre.wiki.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.imetre.wiki.aspect.LogAspect;
import com.imetre.wiki.domain.EBook;
import com.imetre.wiki.domain.EBookExample;
import com.imetre.wiki.mapper.EBookMapper;
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

    public List<EBook> list() {
        EBookExample eBookExample = new EBookExample();
        EBookExample.Criteria criteria = eBookExample.createCriteria();

        PageHelper.startPage(1, 3);
        List<EBook> eBookList = eBookMapper.selectByExample(eBookExample);

        PageInfo<EBook> pageInfo = new PageInfo<>(eBookList);
        LOG.info("总页数: {}", pageInfo.getPages());
        LOG.info("总行数: {}", pageInfo.getTotal());
        return eBookList;
    }

    /**
     * 保存
     *
    public void save(EbookSaveReq req) {
        Ebook ebook = CopyUtil.copy(req, Ebook.class);
        if (ObjectUtils.isEmpty(req.getId())) {
            // 新增
            ebook.setId(snowFlake.nextId());
            eBookMapper.insert(ebook);
        } else {
            // 更新
            eBookMapper.updateByPrimaryKey(ebook);
        }
    }*/

    public void delete(Long id) {
        eBookMapper.deleteByPrimaryKey(id);
    }
}
