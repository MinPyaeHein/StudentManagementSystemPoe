package Utils;

import Model.Student;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class ImgUtil {

    public static void displayProfileImage(int id, String folderName, ImageView imageView){
        try {
            String imagePath = System.getProperty("user.dir") +  folderName + id + ".jpg";
            Image image = new Image("file:" + imagePath);
            imageView.setImage(image);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void saveImageWithId(int id, File selectedImageFile,String folderName) throws IOException {
        if (selectedImageFile != null) {
            try {
                Path targetDirectory = Path.of(System.getProperty("user.dir"));
                System.out.println("Target directory: " + targetDirectory.toString());
                Path targetFile = targetDirectory.resolve(folderName+ id + ".jpg");
                Files.createDirectories(targetFile.getParent());
                Files.copy(selectedImageFile.toPath(), targetFile, StandardCopyOption.REPLACE_EXISTING);
                System.out.println("Image saved at: " + targetFile.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public static void deleteImageWithId(Object id, File selectedImageFile, String folderName) throws IOException {
        if (selectedImageFile != null) {
            try {
                System.out.println("Selected image file: " + selectedImageFile.getAbsolutePath());
                Path targetDirectory = Path.of(System.getProperty("user.dir"));
                System.out.println("Target directory: " + targetDirectory.toString());
                Path imagePath = targetDirectory.resolve(folderName + id + ".jpg");
                System.out.println("Selected image path: " + imagePath.toString());
                if (Files.exists(imagePath)) {
                    Files.delete(imagePath);
                    System.out.println("Image deleted: " + imagePath.toString());
                } else {
                    System.out.println("Image file not found: " + imagePath.toString());
                }
            } catch (IOException e) {
                System.out.println("Error deleting the image: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            System.out.println("No file selected to delete.");
        }
    }





}
