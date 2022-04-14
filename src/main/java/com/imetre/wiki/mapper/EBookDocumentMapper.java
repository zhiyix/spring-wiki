package com.imetre.wiki.mapper;

import com.imetre.wiki.domain.EBookDocument;
import com.imetre.wiki.domain.EBookDocumentExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface EBookDocumentMapper {
    long countByExample(EBookDocumentExample example);

    int deleteByExample(EBookDocumentExample example);

    int deleteByPrimaryKey(Long id);

    int insert(EBookDocument record);

    int insertSelective(EBookDocument record);

    List<EBookDocument> selectByExample(EBookDocumentExample example);

    EBookDocument selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") EBookDocument record, @Param("example") EBookDocumentExample example);

    int updateByExample(@Param("record") EBookDocument record, @Param("example") EBookDocumentExample example);

    int updateByPrimaryKeySelective(EBookDocument record);

    int updateByPrimaryKey(EBookDocument record);
}