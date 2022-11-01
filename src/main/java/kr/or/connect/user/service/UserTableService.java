package kr.or.connect.user.service;

import kr.or.connect.user.dao.UserTableDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserTableService {
    private final UserTableDao userTableDao;

    @Transactional
    public int getUserId(String username) {
         return userTableDao.selectUserId(username);
    }
}
