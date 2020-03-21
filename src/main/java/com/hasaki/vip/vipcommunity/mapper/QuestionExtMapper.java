package com.hasaki.vip.vipcommunity.mapper;

import com.hasaki.vip.vipcommunity.model.Question;
import com.hasaki.vip.vipcommunity.model.QuestionExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface QuestionExtMapper {
    int insertAndUseId(Question record);
}