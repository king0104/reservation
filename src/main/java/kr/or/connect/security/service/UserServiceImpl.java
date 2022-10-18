package kr.or.connect.security.service;

import kr.or.connect.security.dao.UserDao;
import kr.or.connect.security.dao.UserRoleDao;
import kr.or.connect.security.dto.User;
import kr.or.connect.security.dto.UserEntity;
import kr.or.connect.security.dto.UserRole;
import kr.or.connect.security.dto.UserRoleEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserDbService {
    private final UserDao userDao;
    private final UserRoleDao userRoleDao;

    @Override
    @Transactional
    public UserEntity getUser(String loginUserId) {
        User user = userDao.getUserByEmail(loginUserId);
        return new UserEntity(user.getEmail(), user.getPassword());
    }

    @Override
    @Transactional
    public List<UserRoleEntity> getUserRoleList(String loginUserId) {
        List<UserRole> userRoleList = userRoleDao.getRolesByEmail(loginUserId);
        List<UserRoleEntity> list = new ArrayList<>();

        for(UserRole userRole : userRoleList) {
            list.add(new UserRoleEntity(loginUserId, userRole.getRoleName()));
        }
        return list;
    }

    @Transactional
    public void addUser(User user, boolean admin) {
        userDao.addUser(user);

        User selectedUser = userDao.getUserByEmail(user.getEmail());
        Long userId = selectedUser.getId();
        if (admin) {
            userRoleDao.addAdminRole(userId);
        }
        userRoleDao.addUserRole(userId);
    }

    public User getUserByEmail(String email) {
        return userDao.getUserByEmail(email);
    }
}
