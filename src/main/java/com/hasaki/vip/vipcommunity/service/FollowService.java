package com.hasaki.vip.vipcommunity.service;

import com.hasaki.vip.vipcommunity.enums.FollowType;
import com.hasaki.vip.vipcommunity.mapper.FollowMapper;
import com.hasaki.vip.vipcommunity.mapper.QuestionMapper;
import com.hasaki.vip.vipcommunity.model.Follow;
import com.hasaki.vip.vipcommunity.model.FollowExample;
import com.hasaki.vip.vipcommunity.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FollowService {
    @Autowired
    private FollowMapper followMapper;
    @Autowired
    private QuestionMapper questionMapper;

    public boolean isFollowObjectExist(Follow follow){
        if(follow.getFollowType() == FollowType.QUESTION.getType()){
            Question question = questionMapper.selectByPrimaryKey(follow.getFollowId());
            return question != null;
        }
        return false;
    }

    public List<Follow> getSameFollowLog(Follow follow){
        FollowExample followExample = new FollowExample();
        followExample.createCriteria().andFollowIdEqualTo(follow.getFollowId()).andFollowTypeEqualTo(follow.getFollowType()).andUserIdEqualTo(follow.getUserId());
        return followMapper.selectByExample(followExample);
    }

    public void insert(Follow follow){
        followMapper.insert(follow);
    }

    public void deleteById(Long followId) {
        followMapper.deleteByPrimaryKey(followId);
    }
}
