package com.nrbswift.spring4web.fileprocess;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nrbswift.spring4web.dao.Picture;
import com.nrbswift.spring4web.mq.MqMessageObject;
import com.nrbswift.spring4web.websocket.MyChatEndpoint;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Component
public class FileProcessor {
    @Autowired
    ServletContext servletContext;

    @Autowired
    MyChatEndpoint myChatEndpoint;

    public void processFileAndSendSocket(MqMessageObject mqMessageObject) {
        List<Picture> pictureList = getPictureList(mqMessageObject);
        String userSessionId = mqMessageObject.getUserSessionId();

        createThumbnailAndMoveFile(pictureList);
        System.out.println(userSessionId);
        myChatEndpoint.sendMessageToUser(userSessionId, "Your File Uploaded Successfully");
    }

    private void createThumbnailAndMoveFile(List<Picture> pictureList) {
        pictureList.forEach((Picture picture) -> {
            processSingleImage(picture.getImagePath());
        });
        //processSingleImage("Screenshot_1.png");
    }

    private void processSingleImage(String imageName) {
        String UPLOADED_FOLDER = servletContext.getRealPath("/") + "WEB-INF\\classes\\asset\\img\\";
        String TEMP_FOLDER = "c:\\upload\\";

        Path source = Paths.get(UPLOADED_FOLDER + "ac249aa1-e034-45fb-9f14-f521bfebcc7c20180906_113853.jpg");
        Path target = Paths.get(UPLOADED_FOLDER + "thumb_200_200_" + imageName);

        try {
            InputStream is = new FileInputStream(Paths.get(TEMP_FOLDER + imageName).toFile());

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

            //Move original file
            File from = new File(TEMP_FOLDER + imageName);
            File to = new File(UPLOADED_FOLDER + imageName);
            FileUtils.copyFile(from, to);
            is.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private List<Picture> getPictureList(MqMessageObject mqMessageObject) {
        Gson gson = new Gson();

        return gson.fromJson(mqMessageObject.getFileList(), new TypeToken<List<Picture>>(){}.getType());
    }
}
