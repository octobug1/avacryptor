package avacryptor.gui;

import avacryptor.services.EncryptionService;
import avacryptor.crypto.KeyGenerator;

import avacryptor.services.FileEncryptionService;

import javafx.stage.FileChooser;

import java.io.File;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;

public class MainWindow {

    private final TextArea inputArea = new TextArea();
    private final TextArea outputArea = new TextArea();
    private final PasswordField passwordField = new PasswordField();
    private final TextField visiblePasswordField = new TextField();
    private Label statusLabel;

    private final EncryptionService encryptionService =
            new EncryptionService();

private File selectedFile;

private final FileEncryptionService fileService =
        new FileEncryptionService();

    public Parent getRoot() {

        Label fileLabel = new Label("No file selected");

Button chooseFileButton = new Button("Choose File");

chooseFileButton.setOnAction(e -> {

    FileChooser chooser = new FileChooser();

    selectedFile = chooser.showOpenDialog(null);

    if(selectedFile != null) {

        fileLabel.setText(
                selectedFile.getName()
        );

    }

});



Button encryptFileButton =
        new Button("Encrypt File");


encryptFileButton.setOnAction(e -> {

    try {

        if(selectedFile == null) {
            statusLabel.setText(
                    "Select a file first"
            );
            return;
        }


        File output = createUniqueFile(
        selectedFile.getParentFile(),
        selectedFile.getName() + ".ava"
);


        fileService.encryptFile(
                selectedFile,
                output,
                passwordField.getText()
        );


        statusLabel.setText(
                "File encrypted successfully"
        );


    } catch(Exception ex) {

        statusLabel.setText(
                "Encryption failed"
        );

    }

});




Button decryptFileButton =
        new Button("Decrypt File");


decryptFileButton.setOnAction(e -> {

    try {

        if(selectedFile == null) {
            statusLabel.setText(
                    "Select a file first"
            );
            return;
        }


        File output =
                createDecryptedFile(
                        selectedFile
                );


        fileService.decryptFile(
                selectedFile,
                output,
                passwordField.getText()
        );


        statusLabel.setText(
                "File decrypted: "
                + output.getName()
        );


    } catch(Exception ex) {

        statusLabel.setText(
                "Decryption failed"
        );

    }

});

        // =========================
        // TITLE
        // =========================

        Label title = new Label("AvaCryptor");
        title.setFont(Font.font(28));

        Label subtitle = new Label(
                "Secure AES Encryption & Decryption"
        );

        subtitle.setStyle("-fx-text-fill: #777777;");

        VBox header = new VBox(
                5,
                title,
                subtitle
        );

        header.setAlignment(Pos.CENTER);


        // =========================
        // PASSWORD
        // =========================

        Label passwordLabel =
                new Label("Encryption Password");

       passwordField.setPromptText(
        "Enter a password..."
);

visiblePasswordField.setPromptText(
        "Enter a password..."
);

visiblePasswordField.setVisible(false);

        passwordField.setPrefHeight(40);

        Button generateKeyButton =
        new Button("Generate Password");



Button copyPasswordButton =
        new Button("Copy");
        generateKeyButton.setOnAction(event -> {

    String generatedPassword =
            KeyGenerator.generatePassword();

    passwordField.setText(
            generatedPassword
    );

    visiblePasswordField.setText(
            generatedPassword
    );
});





copyPasswordButton.setOnAction(event -> {

    javafx.scene.input.Clipboard clipboard =
            javafx.scene.input.Clipboard
                    .getSystemClipboard();


    javafx.scene.input.ClipboardContent content =
            new javafx.scene.input.ClipboardContent();


    content.putString(
            passwordField.isVisible()
                    ? passwordField.getText()
                    : visiblePasswordField.getText()
    );


    clipboard.setContent(content);
});

        generateKeyButton.setPrefHeight(40);

        
        HBox passwordBox =
        new HBox(
                10,
                passwordField,
                visiblePasswordField,
                generateKeyButton,
                
                copyPasswordButton
        );

        HBox.setHgrow(
                passwordField,
                Priority.ALWAYS
        );

        VBox passwordSection =
                new VBox(
                        8,
                        passwordLabel,
                        passwordBox
                );


        // =========================
        // INPUT
        // =========================

        Label inputLabel =
                new Label("Input");

        inputArea.setPromptText(
                "Enter text to encrypt or decrypt..."
        );

        inputArea.setWrapText(true);

        inputArea.setPrefRowCount(8);

        VBox inputSection =
                new VBox(
                        8,
                        inputLabel,
                        inputArea
                );


        // =========================
        // OUTPUT
        // =========================

        Label outputLabel =
                new Label("Output");

        outputArea.setEditable(false);

        outputArea.setWrapText(true);

        outputArea.setPrefRowCount(8);

        outputArea.setPromptText(
                "Encrypted or decrypted text will appear here..."
        );

        Button copyButton =
                new Button("Copy");

        copyButton.setOnAction(event -> {

            if (!outputArea.getText().isEmpty()) {

                javafx.scene.input.Clipboard clipboard =
                        javafx.scene.input.Clipboard
                                .getSystemClipboard();

                javafx.scene.input.ClipboardContent content =
                        new javafx.scene.input.ClipboardContent();

                content.putString(
                        outputArea.getText()
                );

                clipboard.setContent(content);
            }
        });

        VBox outputSection =
                new VBox(
                        8,
                        outputLabel,
                        outputArea,
                        copyButton
                );


        // =========================
        // ACTION BUTTONS
        // =========================

        statusLabel = new Label("Ready");
statusLabel.getStyleClass().add("status-label");

        Button encryptButton =
                new Button("Encrypt");

        Button decryptButton =
                new Button("Decrypt");

        Button clearButton =
                new Button("Clear");

        encryptButton.setPrefWidth(120);
        decryptButton.setPrefWidth(120);
        clearButton.setPrefWidth(120);

        encryptButton.setOnAction(
                event -> encrypt()
        );

        decryptButton.setOnAction(
                event -> decrypt()
        );

        clearButton.setOnAction(
                event -> clearFields()
        );

        HBox actionButtons =
                new HBox(
                        10,
                        encryptButton,
                        decryptButton,
                        clearButton
                );

        actionButtons.setAlignment(
                Pos.CENTER
        );


        // =========================
        // MAIN LAYOUT
        // =========================

        HBox fileButtons =
        new HBox(
                10,
                chooseFileButton,
                encryptFileButton,
                decryptFileButton
        );

fileButtons.setAlignment(Pos.CENTER);


VBox fileSection =
        new VBox(
                8,
                fileLabel,
                fileButtons
        );


VBox root =
        new VBox(
                20,
                header,
                passwordSection,
                inputSection,
                outputSection,
                fileSection,
                actionButtons,
                statusLabel
        );

        root.setPadding(
                new Insets(30)
        );

        root.setAlignment(
                Pos.TOP_CENTER
        );

        


        // =========================
        // BASIC STYLING
        // =========================

        root.setStyle(
                "-fx-background-color: #f5f5f5;"
        );

        inputArea.setStyle(
                "-fx-control-inner-background: white;"
        );

        outputArea.setStyle(
                "-fx-control-inner-background: white;"
        );


        return root;
    }


    // =========================
    // ENCRYPT
    // =========================

    private void encrypt() {

        String message =
                inputArea.getText();

        String password =
                passwordField.getText();


        if (message.isBlank()) {

            showError(
                    "Please enter some text to encrypt."
            );

            return;
        }


        if (password.isBlank()) {

            showError(
                    "Please enter a password."
            );

            return;
        }


        try {

            String encrypted =
                    encryptionService.encrypt(
                            message,
                            password
                    );

            outputArea.setText(
                    encrypted
            );

        } catch (Exception e) {

            showError(
                    "Encryption failed: "
                    + e.getMessage()
            );
        }
    }


    // =========================
    // DECRYPT
    // =========================

    private void decrypt() {

        String encryptedData =
                inputArea.getText();

        String password =
                passwordField.getText();


        if (encryptedData.isBlank()) {

            showError(
                    "Please enter encrypted text."
            );

            return;
        }


        if (password.isBlank()) {

            showError(
                    "Please enter the password."
            );

            return;
        }


        try {

            String decrypted =
                    encryptionService.decrypt(
                            encryptedData,
                            password
                    );

            outputArea.setText(
                    decrypted
            );

        } catch (Exception e) {

            showError(
                    "Decryption failed. "
                    + "Check your password and encrypted text."
            );
        }
    }


    // =========================
    // CLEAR
    // =========================

    private void clearFields() {

        inputArea.clear();

        outputArea.clear();

        passwordField.clear();
    }


    // =========================
    // ERROR DIALOG
    // =========================

    private void showError(
            String message
    ) {

        Alert alert =
                new Alert(
                        Alert.AlertType.ERROR
                );

        alert.setTitle(
                "AvaCryptor"
        );

        alert.setHeaderText(
                "Something went wrong"
        );

        alert.setContentText(
                message
        );

        alert.showAndWait();
    }

    private File createDecryptedFile(
        File encryptedFile
) {

    String originalName =
            encryptedFile.getName();


    if(originalName.endsWith(".ava")) {

        originalName =
                originalName.substring(
                        0,
                        originalName.length() - 4
                );

    } else {

        originalName =
                originalName + "_decrypted";

    }


    File output =
            new File(
                    encryptedFile.getParent(),
                    originalName
            );


    int counter = 1;


    while(output.exists()) {

        String newName =
                originalName.replace(
                        ".",
                        "_" + counter + "."
                );


        output =
                new File(
                        encryptedFile.getParent(),
                        newName
                );


        counter++;

    }


    return output;
}

private File createUniqueFile(File directory, String fileName) {

    File file = new File(directory, fileName);

    if (!file.exists()) {
        return file;
    }

    String name = fileName;
    String extension = "";

    int dot = fileName.lastIndexOf(".");

    if (dot > 0) {
        name = fileName.substring(0, dot);
        extension = fileName.substring(dot);
    }

    int counter = 1;

    while (file.exists()) {

        file = new File(
                directory,
                name + "_" + counter + extension
        );

        counter++;
    }

    return file;
}
}

