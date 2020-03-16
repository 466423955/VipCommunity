package com.hasaki.vip.vipcommunity.service;

import com.hasaki.vip.vipcommunity.dto.UserDTO;
import com.hasaki.vip.vipcommunity.mapper.UserMapper;
import com.hasaki.vip.vipcommunity.model.User;
import com.hasaki.vip.vipcommunity.model.UserExample;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Create by hanzp on 2020-03-15
 */
@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public List<User> getUserByAccountPassword(String email, String password) {
        UserExample userExample = new UserExample();
        userExample.createCriteria()
                .andEmailEqualTo(email)
                .andPasswordEqualTo(password);
        return userMapper.selectByExample(userExample);
    }

    public void updateUserToken(User user) {
        User updateUser = new User();
        updateUser.setToken(user.getToken());
        UserExample dbUserExample = new UserExample();
        dbUserExample.createCriteria().andIdEqualTo(user.getId());
        userMapper.updateByExampleSelective(updateUser, dbUserExample);
    }

    public User findByToken(String token) {
        UserExample userExample = new UserExample();
        userExample.createCriteria()
                .andTokenEqualTo(token);
        List<User> dbUsers = userMapper.selectByExample(userExample);
        if (dbUsers.size() == 0) {
            return null;
        }
        return dbUsers.get(0);
    }

    public UserDTO getUserDTOByUser(User user){
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(user, userDTO);
        return userDTO;
    }
}
