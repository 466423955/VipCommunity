package com.hasaki.vip.vipcommunity.service;

import com.hasaki.vip.vipcommunity.enums.FollowType;
import com.hasaki.vip.vipcommunity.mapper.FollowMapper;
import com.hasaki.vip.vipcommunity.model.Follow;
import com.hasaki.vip.vipcommunity.model.FollowExample;
import com.hasaki.vip.vipcommunity.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FollowService {
    @Autowired
    private FollowMapper followMapper;
    @Autowired
    private QuestionService questionService;

    public Object isFollowObjectExist(Follow follow) {
        if (follow.getFollowType() == FollowType.QUESTION.getType()) {
            Question question = questionService.selectByPrimaryKey(follow.getFollowId());
            return question;
        }
        return null;
    }

    public List<Follow> getSameFollowLog(Follow follow) {
        FollowExample followExample = new FollowExample();
        followExample.createCriteria().andFollowIdEqualTo(follow.getFollowId()).andFollowTypeEqualTo(follow.getFollowType()).andUserIdEqualTo(follow.getUserId());
        return followMapper.selectByExample(followExample);
    }

    @Transactional
    public void executeFollow(Follow follow) {
        if (follow.getFollowType() == FollowType.QUESTION.getType()) {
            this.questionService.followCountUpdate(follow.getFollowId(), 1);
        }
        followMapper.insert(follow);
    }

    @Transactional
    public void executeUnFollow(List<Follow> follows) {
        for (Follow follow : follows) {
            if (follow.getFollowType() == FollowType.QUESTION.getType()) {
                this.questionService.followCountUpdate(follow.getFollowId(), -1);
            }
            followMapper.deleteByPrimaryKey(follow.getId());
        }
    }
}
