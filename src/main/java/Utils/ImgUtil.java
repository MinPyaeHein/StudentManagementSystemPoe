package Utils;

import Model.Student;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class ImgUtil {
    public static File selectedImageFile;
    public static void displayProfileImage(int id, String folderName, ImageView imageView){
        try {
            String imagePath = System.getProperty("user.dir") +  folderName + id + ".jpg";
            Image image = new Image("file:" + imagePath);
            imageView.setImage(image);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static String saveImageWithId(int id, File selectedImageFile, String folderName) throws IOException {
        System.out.println("Arrive Save student with Id" + id);

        if (selectedImageFile != null) {
            try {
                // Get the project root directory
                Path targetDirectory = Path.of(System.getProperty("user.dir"), folderName);

                // Ensure the directory exists
                Files.createDirectories(targetDirectory);

                // Create the target file path with ID as filename
                String newFileName = id + ".jpg"; // Image saved as "ID.jpg"
                Path targetFile = targetDirectory.resolve(newFileName);

                // Copy and replace the existing file
                Files.copy(selectedImageFile.toPath(), targetFile, StandardCopyOption.REPLACE_EXISTING);

                System.out.println("Image saved at: " + targetFile.toString());

                // Return only the file name (not the full path)
                return newFileName;
            } catch (IOException e) {
                e.printStackTrace();
                throw e;
            }
        }
        return null; // If no image was selected
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

    public static void openFileChooser(Button selectImageButton, ImageView imageView) {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png", "*.jpeg");
        fileChooser.getExtensionFilters().add(extFilter);
        selectedImageFile = fileChooser.showOpenDialog(selectImageButton.getScene().getWindow());
        if (selectedImageFile != null) {
            Image image = new Image(selectedImageFile.toURI().toString());
            imageView.setImage(image);
        }
    }
}
