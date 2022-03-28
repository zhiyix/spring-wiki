package com.imetre.wiki.mapper;

import com.imetre.wiki.domain.EBookCategory;
import com.imetre.wiki.domain.EBookCategoryExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface EBookCategoryMapper {
    long countByExample(EBookCategoryExample example);

    int deleteByExample(EBookCategoryExample example);

    int deleteByPrimaryKey(Long id);

    int insert(EBookCategory record);

    int insertSelective(EBookCategory record);

    List<EBookCategory> selectByExample(EBookCategoryExample example);

    EBookCategory selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") EBookCategory record, @Param("example") EBookCategoryExample example);

    int updateByExample(@Param("record") EBookCategory record, @Param("example") EBookCategoryExample example);

    int updateByPrimaryKeySelective(EBookCategory record);

    int updateByPrimaryKey(EBookCategory record);
}