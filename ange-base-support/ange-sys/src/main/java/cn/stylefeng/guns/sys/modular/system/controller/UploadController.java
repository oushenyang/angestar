package cn.stylefeng.guns.sys.modular.system.controller;

import cn.hutool.core.io.resource.ResourceUtil;
import cn.stylefeng.guns.sys.core.util.UeditorUtil;
import cn.stylefeng.guns.sys.modular.system.model.UeditorFileResult;
import cn.stylefeng.guns.sys.modular.system.model.UploadResult;
import cn.stylefeng.guns.sys.modular.system.service.FileInfoService;
import cn.stylefeng.roses.kernel.model.exception.ServiceException;
import cn.stylefeng.roses.kernel.model.response.ResponseData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

import static cn.stylefeng.guns.sys.core.exception.enums.BizExceptionEnum.UE_CONFIG_ERROR;

/**
 * UEditor相关文件操作
 *
 * @author fengshuonan
 * @Date 2019-08-27 10:02
 */
@Controller
@RequestMapping("/upload")
@Slf4j
public class UploadController {

    @Value("${uploadFile.file-path}")
    public String imgPath;

    @Autowired
    private FileInfoService fileInfoService;

    /**
     * layui上传组件
     * @param file 文件
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, path = "/uploadFile")
    @ResponseBody
    public ResponseData uploadFile(@RequestPart("file") MultipartFile file) {
        UploadResult uploadResult = this.fileInfoService.uploadFile(file,imgPath);
        String fileId = uploadResult.getFileId();
        HashMap<String, Object> map = new HashMap<>();
        map.put("fileId", fileId);
        return ResponseData.success(0, "上传成功", map);
    }
}
