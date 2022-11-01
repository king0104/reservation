package kr.or.connect.fileinfo.service;

import kr.or.connect.fileinfo.dto.FileInfo;
import kr.or.connect.fileinfo.dto.FileInfoRegister;
import kr.or.connect.fileinfo.dao.FileInfoDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FileInfoService {

    private final FileInfoDao fileInfoDao;

    @Transactional
    public int save(FileInfoRegister fileInfoRegister) {
        return fileInfoDao.insert(fileInfoRegister);
    }

    @Transactional
    public FileInfo get(int fileId) {
        return fileInfoDao.select(fileId);
    }

}
