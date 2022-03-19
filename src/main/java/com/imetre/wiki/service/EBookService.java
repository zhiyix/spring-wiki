package com.imetre.wiki.service;

import com.imetre.wiki.domain.EBook;
import com.imetre.wiki.domain.EBookExample;
import com.imetre.wiki.mapper.EBookMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class EBookService {
    @Resource
    private EBookMapper eBookMapper;

    public List<EBook> list() {
        EBookExample eBookExample = new EBookExample();
        EBookExample.Criteria criteria = eBookExample.createCriteria();

        List<EBook> eBookList = eBookMapper.selectByExample(eBookExample);

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
