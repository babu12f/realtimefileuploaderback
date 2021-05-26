package com.nrbswift.spring4web.controllers;

import com.google.gson.Gson;
import com.nrbswift.spring4web.dao.Picture;
import com.nrbswift.spring4web.dao.PictureDao;
import com.nrbswift.spring4web.mq.MessageSender;
import com.nrbswift.spring4web.mq.MqMessageObject;
import com.nrbswift.spring4web.utils.WebContextHolderUtils;
import com.nrbswift.spring4web.websocket.MyChatEndpoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.List;

import org.apache.commons.io.FileUtils;

@Controller
public class FileUploadController {
    @Autowired
    MessageSender messageSender;

    @Autowired
    MyChatEndpoint myChatEndpoint;

    @Autowired
    private PictureDao pictureDao;

    @CrossOrigin
    @ResponseBody
    @RequestMapping(value = "/file",  method = RequestMethod.POST)
    public String postUploadForm(@RequestParam("mypic") MultipartFile[] files, RedirectAttributes redirectAttributes) {

//        String UPLOADED_FOLDER = WebContextHolderUtils.get().getServletContext().getRealPath("/") + "upload\\";
//        String UPLOADED_FOLDER = WebContextHolderUtils.get().getServletContext().getRealPath("/") + "WEB-INF\\classes\\asset\\img\\";
        String UPLOADED_FOLDER = "c:\\upload\\";

        List<Picture> list = new ArrayList<>();

        for (MultipartFile file: files) {

            if (file.isEmpty()) {
                continue;
            }

            try {
                byte[] bytes = file.getBytes();
                if (!(new File(UPLOADED_FOLDER).exists())) {
                    new File(UPLOADED_FOLDER).mkdir();
                }
                String fileName = UUID.randomUUID() + file.getOriginalFilename();
                Path path = Paths.get(UPLOADED_FOLDER + fileName);
                Files.write(path, bytes);

                Picture picture = new Picture(fileName, new Date());
                pictureDao.insertPicture(picture);
                list.add(picture);

            } catch (IOException e) {
                e.printStackTrace();
            }

            Gson gson = new Gson();
            String picJson = gson.toJson(list);

            MqMessageObject mqMessageObject = new MqMessageObject();
            mqMessageObject.setFileList(picJson);
            mqMessageObject.setUserSessionId(WebContextHolderUtils.get().getSession().getId());

            messageSender.sendObjectMessage(mqMessageObject);
        }

        return "file uploaded successfully";
    }

    @RequestMapping(value = "/file",  method = RequestMethod.GET)
    public String showFileUploadForm(Model model) throws IOException {
        String UPLOADED_FOLDER = WebContextHolderUtils.get().getServletContext().getRealPath("/") + "WEB-INF\\classes\\asset\\img\\";

        Path source = Paths.get(UPLOADED_FOLDER + "ac249aa1-e034-45fb-9f14-f521bfebcc7c20180906_113853.jpg");
        Path target = Paths.get(UPLOADED_FOLDER + "1d28393d-168d-4142-b2a3-62cf660da52dabbu_nid_thumb.jpg");
        System.out.println(WebContextHolderUtils.get().getSession().getId());
        //myChatEndpoint.sendMessageToUser(WebContextHolderUtils.get().getSession().getId());


       /* try {
            InputStream is = new FileInputStream(Paths.get("c:\\upload\\" + "ac249aa1-e034-45fb-9f14-f521bfebcc7c20180906_113853.jpg").toFile());

            BufferedImage originalImage = ImageIO.read(is);
            Image newResizedImage = originalImage.getScaledInstance(200, 200, Image.SCALE_SMOOTH);

            // get file extension
            String s = target.getFileName().toString();
            String fileExtension = s.substring(s.lastIndexOf(".") + 1);

            BufferedImage bi = new BufferedImage(newResizedImage.getWidth(null), newResizedImage.getHeight(null), BufferedImage.TYPE_INT_RGB);

            Graphics2D graphics2D = bi.createGraphics();
            graphics2D.drawImage(newResizedImage, 0, 0, null);
            graphics2D.dispose();

            // we want image in png format
            ImageIO.write(bi, fileExtension, target.toFile());

            File from = new File("c:\\upload\\" + "ac249aa1-e034-45fb-9f14-f521bfebcc7c20180906_113853.jpg");
            File to = new File(UPLOADED_FOLDER + "ac249aa1-e034-45fb-9f14-f521bfebcc7c20180906_113853.jpg");
            FileUtils.copyFile(from, to);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        return "fileForm";
    }

    @CrossOrigin
    @RequestMapping(value = "/getfiles",  method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody List<Picture> getAllFiles(Model model) {
        List<Picture>  pictureList = pictureDao.getAll();

         return pictureList;
    }



//    @RequestMapping(value = "/file",  method = RequestMethod.POST)
//    public String postUploadForm(@RequestParam("mypic") MultipartFile file, RedirectAttributes redirectAttributes) {
//
//        String UPLOADED_FOLDER = WebContextHolderUtils.get().getServletContext().getRealPath("/") + "upload\\";
//
//        if (file.isEmpty()) {
//            redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
//            return "redirect:uploadStatus";
//        }
//
//        try {
//            byte[] bytes = file.getBytes();
//            if (!(new File(UPLOADED_FOLDER).exists())) {
//                new File(UPLOADED_FOLDER).mkdir();
//            }
//            Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
//            Files.write(path, bytes);
//
//            redirectAttributes.addFlashAttribute("message",
//                    "You successfully uploaded '" + file.getOriginalFilename() + "'");
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return "redirect:/file";
//    }
}
