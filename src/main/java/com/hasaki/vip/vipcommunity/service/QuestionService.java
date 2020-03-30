package com.hasaki.vip.vipcommunity.service;

import com.hasaki.vip.vipcommunity.dto.PublishQuestionDTO;
import com.hasaki.vip.vipcommunity.dto.QuestionDTO;
import com.hasaki.vip.vipcommunity.dto.UserDTO;
import com.hasaki.vip.vipcommunity.enums.FollowType;
import com.hasaki.vip.vipcommunity.mapper.QuestionExtMapper;
import com.hasaki.vip.vipcommunity.mapper.QuestionMapper;
import com.hasaki.vip.vipcommunity.model.Follow;
import com.hasaki.vip.vipcommunity.model.Question;
import com.hasaki.vip.vipcommunity.model.User;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private QuestionExtMapper questionExtMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private FollowService followService;

    public Question createOrUpdate(PublishQuestionDTO publishQuestionDTO, User user){
        Question question_db = questionMapper.selectByPrimaryKey(publishQuestionDTO.getId());
        if(question_db == null){
            //create
            question_db = new Question();
            question_db.setTitle(publishQuestionDTO.getTitle());
            question_db.setDescription(publishQuestionDTO.getContent());
            question_db.setTag(StringUtils.join(publishQuestionDTO.getTags(),','));
            question_db.setCreator(user.getId());
            question_db.setGmtCreate(System.currentTimeMillis());
            question_db.setGmtModify(System.currentTimeMillis());
            question_db.setReplyCount(0);
            question_db.setLikeCount(0);
            question_db.setViewCount(0);
            questionExtMapper.insertAndUseId(question_db);
            return question_db;
        } else {
            //update
            Question question = new Question();
            question.setId(question_db.getId());
            question.setTitle(publishQuestionDTO.getTitle());
            question.setDescription(publishQuestionDTO.getContent());
            question.setTag(StringUtils.join(publishQuestionDTO.getTags(),','));
            question.setGmtModify(System.currentTimeMillis());
            questionMapper.updateByPrimaryKey(question);
            return questionMapper.selectByPrimaryKey(publishQuestionDTO.getId());
        }
    }


    public QuestionDTO getQuestionDTOById(Long questionId) {
        QuestionDTO questionDTO = new QuestionDTO();
        Question question = questionMapper.selectByPrimaryKey(questionId);
        questionDTO.setQuestion(question);
        if(question == null){
            return questionDTO;
        }
        UserDTO userDTO = userService.getUserDTOById(question.getCreator());
        questionDTO.setUser(userDTO);
        if(userDTO == null){
            return questionDTO;
        }
        Follow follow = new Follow();
        follow.setUserId(userDTO.getId());
        follow.setFollowType(FollowType.QUESTION.getType());
        follow.setFollowId(questionId);
        List<Follow> sameFollowLog = followService.getSameFollowLog(follow);
        questionDTO.setFollowed(sameFollowLog != null && sameFollowLog.size() > 0);
        return questionDTO;
    }
}
