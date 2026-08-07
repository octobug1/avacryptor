package avacryptor.gui.components;

import avacryptor.gui.Theme;
import avacryptor.services.FileEncryptionService;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;

import java.io.File;

public class FileEncryptionPane {

    private final PasswordField passwordField;

    private final FileEncryptionService fileService =
            new FileEncryptionService();

    private File selectedFile;


    private final Label fileLabel =
            new Label("No file selected");


    private final Label statusLabel =
            new Label("Ready");


    public FileEncryptionPane(
            PasswordField passwordField
    ) {
        this.passwordField = passwordField;
    }


    public VBox getView() {

        Label title =
                new Label("File Encryption");

        title.setStyle(
                Theme.TITLE +
                "-fx-font-size: 20px;" +
                "-fx-font-weight: bold;"
        );


        Label description =
                new Label(
                        "Encrypt or decrypt PDF, TXT and other files."
                );

        description.setStyle(
                Theme.SUBTITLE
        );


        Button chooseFileButton =
                new Button("Choose File");

        chooseFileButton.setStyle(
                Theme.SECONDARY_BUTTON
        );


        chooseFileButton.setOnAction(
                event -> chooseFile()
        );


        fileLabel.setStyle(
                Theme.FILE_LABEL
        );


        Button encryptButton =
                new Button("Encrypt File");

        encryptButton.setStyle(
                Theme.PRIMARY_BUTTON
        );


        Button decryptButton =
                new Button("Decrypt File");

        decryptButton.setStyle(
                Theme.PRIMARY_BUTTON
        );


        encryptButton.setOnAction(
                event -> encryptFile()
        );


        decryptButton.setOnAction(
                event -> decryptFile()
        );


        HBox fileButtons =
                new HBox(
                        10,
                        chooseFileButton,
                        encryptButton,
                        decryptButton
                );


        statusLabel.setStyle(
                Theme.SUBTITLE
        );


        VBox content =
                new VBox(
                        10,
                        title,
                        description,

                        fileButtons,

                        fileLabel,

                        statusLabel
                );


        content.setPadding(
                new Insets(20)
        );


        content.setStyle(
                Theme.CARD
        );


        return content;
    }


    private void chooseFile() {

        FileChooser chooser =
                new FileChooser();


        chooser.setTitle(
                "Choose a file"
        );


        File file =
                chooser.showOpenDialog(null);


        if (file != null) {

            selectedFile = file;


            fileLabel.setText(
                    "Selected: "
                    + file.getName()
            );


            statusLabel.setText(
                    "File ready."
            );

            statusLabel.setStyle(
                    Theme.SUCCESS
            );
        }
    }


    private void encryptFile() {

        if (selectedFile == null) {

            setError(
                    "Choose a file first."
            );

            return;
        }


        if (passwordField.getText().isBlank()) {

            setError(
                    "Enter a password first."
            );

            return;
        }


        try {

            File output =
                    createUniqueEncryptedFile(
                            selectedFile
                    );


            fileService.encryptFile(
                    selectedFile,
                    output,
                    passwordField.getText()
            );


            setSuccess(
                    "Encrypted file created: "
                    + output.getName()
            );


        } catch (Exception e) {

            setError(
                    "File encryption failed."
            );
        }
    }


    private void decryptFile() {

        if (selectedFile == null) {

            setError(
                    "Choose an encrypted file first."
            );

            return;
        }


        if (passwordField.getText().isBlank()) {

            setError(
                    "Enter a password first."
            );

            return;
        }


        try {

            File output =
                    createUniqueDecryptedFile(
                            selectedFile
                    );


            fileService.decryptFile(
                    selectedFile,
                    output,
                    passwordField.getText()
            );


            setSuccess(
                    "Decrypted file created: "
                    + output.getName()
            );


        } catch (Exception e) {

            setError(
                    "Decryption failed. Check the password."
            );
        }
    }


    private File createUniqueEncryptedFile(
            File original
    ) {

        String fileName =
                original.getName()
                + ".ava";


        return createUniqueFile(
                original.getParentFile(),
                fileName
        );
    }


    private File createUniqueDecryptedFile(
            File encrypted
    ) {

        String name =
                encrypted.getName();


        if (name.endsWith(".ava")) {

            name =
                    name.substring(
                            0,
                            name.length() - 4
                    );
        }


        return createUniqueFile(
                encrypted.getParentFile(),
                name
        );
    }


    private File createUniqueFile(
            File directory,
            String fileName
    ) {

        File file =
                new File(
                        directory,
                        fileName
                );


        if (!file.exists()) {

            return file;
        }


        String name =
                fileName;

        String extension =
                "";


        int dot =
                fileName.lastIndexOf(".");


        if (dot > 0) {

            name =
                    fileName.substring(
                            0,
                            dot
                    );

            extension =
                    fileName.substring(
                            dot
                    );
        }


        int counter = 1;


        while (file.exists()) {

            file =
                    new File(
                            directory,

                            name
                            + "_"
                            + counter
                            + extension
                    );


            counter++;
        }


        return file;
    }


    private void setSuccess(
            String message
    ) {

        statusLabel.setText(
                message
        );

        statusLabel.setStyle(
                Theme.SUCCESS
        );
    }


    private void setError(
            String message
    ) {

        statusLabel.setText(
                message
        );

        statusLabel.setStyle(
                Theme.ERROR
        );
    }
}

