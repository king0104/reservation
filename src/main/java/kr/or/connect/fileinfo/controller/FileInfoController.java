package kr.or.connect.fileinfo.controller;

import kr.or.connect.fileinfo.dto.FileInfo;
import kr.or.connect.fileinfo.service.FileInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

@Controller
@RequiredArgsConstructor
public class FileInfoController {

    private final FileInfoService fileInfoService;

    @GetMapping("/api/file/{fileId}")
    public void download(HttpServletResponse response, @PathVariable("fileId") int fileId) throws UnsupportedEncodingException {
        FileInfo fileInfo = fileInfoService.get(fileId);
        String fileName = fileInfo.getFileName();
        String saveFileName = fileInfo.getSaveFileName();
        String contentType = fileInfo.getContentType();
        File file = new File(saveFileName);
        long fileLength = file.length();

        response.setHeader("Content-Disposition", "attachment; filename=\""+ fileName +"\";");
        response.setHeader("Content-Transfer-Encoding", "binary");
        response.setHeader("Content-Type", contentType);
        response.setHeader("Content-Length", "" + fileLength);
        response.setHeader("Pragma", "no-cache;");
        response.setHeader("Expires", "-1;");

        try (
                FileInputStream fis = new FileInputStream(saveFileName);
                OutputStream out = response.getOutputStream();
        ) {
            int readCount = 0;
            byte[] buffer = new byte[1024];
            while ((readCount = fis.read(buffer)) != -1) { // 1. 버퍼로 읽어들이기(버퍼로 읽어들인 바이트 수 = readcount)(읽어들인 것이 없으면, -1)
                out.write(buffer, 0, readCount); // 2. 외부에서 버퍼로 읽어들인 만큼, 버퍼에서 외부로 쓰기
                System.out.println(readCount);
            }
        } catch (Exception ex) {
            throw new RuntimeException("file Save Error");
        }


    }


}
