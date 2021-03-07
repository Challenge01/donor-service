//package com.duan.donor.common.util;
//
//
//import java.io.File;
//import java.io.IOException;
//import java.util.UUID;
//
///**
// * 上传文件工具类（图片）
// */
//public class HttpUtil {
//
//
//    /**
//     * 头像上传
//     * @param headImage 头像传出文件
//     * @param request HTTP请求
//     * @param dirs 保存目录
//     * @throws IOException IO异常
//     * @return 文件名称
//     */
//    public static String UploadImg (MultipartFile headImage, HttpServletRequest request, String dirs) throws IOException {
//        //图片的保存路径
//        String filePath = dirs;
//        //设置头像名称
//        String fileName = UUID.randomUUID().toString().replaceAll("-", "") + headImage.getOriginalFilename();
//        System.out.println("上传后的文件名称 ----------------->" + fileName);
//        //创建文件
//        File targetFile = new File(filePath, fileName);
//        //复制文件
//        headImage.transferTo(targetFile);
//        //返回带路径的头像名称
//        fileName = "/"+dirs+"/"+fileName;
//        return fileName;
//    }
//}