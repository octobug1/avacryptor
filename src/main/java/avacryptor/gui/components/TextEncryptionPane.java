
package avacryptor.gui.components;

import avacryptor.gui.Theme;
import avacryptor.services.EncryptionService;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class TextEncryptionPane {

    private final PasswordField passwordField;

    private final EncryptionService encryptionService =
            new EncryptionService();

    private final TextArea inputArea =
            new TextArea();

    private final TextArea outputArea =
            new TextArea();

    private final Label statusLabel =
            new Label("Ready");


    public TextEncryptionPane(
            PasswordField passwordField
    ) {
        this.passwordField = passwordField;
    }


    public VBox getView() {

        Label title =
                new Label("Text Encryption");

        title.setStyle(Theme.TITLE);
        title.setStyle(
                Theme.TITLE +
                "-fx-font-size: 20px;" +
                "-fx-font-weight: bold;"
        );


        Label description =
                new Label(
                        "Encrypt or decrypt text using your password."
                );

        description.setStyle(
                Theme.SUBTITLE
        );


        Label inputLabel =
                new Label("Input");

        inputLabel.setStyle(
                Theme.LABEL
        );


        inputArea.setPromptText(
                "Enter text to encrypt or encrypted text to decrypt..."
        );

        inputArea.setWrapText(true);
        inputArea.setPrefRowCount(6);
        inputArea.setStyle(
                Theme.INPUT
        );


        Label outputLabel =
                new Label("Output");

        outputLabel.setStyle(
                Theme.LABEL
        );


        outputArea.setPromptText(
                "Result will appear here..."
        );

        outputArea.setEditable(false);
        outputArea.setWrapText(true);
        outputArea.setPrefRowCount(6);
        outputArea.setStyle(
                Theme.INPUT
        );


        Button encryptButton =
                new Button("Encrypt");

        encryptButton.setStyle(
                Theme.PRIMARY_BUTTON
        );


        Button decryptButton =
                new Button("Decrypt");

        decryptButton.setStyle(
                Theme.PRIMARY_BUTTON
        );


        Button copyButton =
                new Button("Copy Output");

        copyButton.setStyle(
                Theme.SECONDARY_BUTTON
        );


        Button clearButton =
                new Button("Clear");

        clearButton.setStyle(
                Theme.SECONDARY_BUTTON
        );


        encryptButton.setOnAction(
                event -> encrypt()
        );


        decryptButton.setOnAction(
                event -> decrypt()
        );


        copyButton.setOnAction(
                event -> copyOutput()
        );


        clearButton.setOnAction(
                event -> clear()
        );


        HBox buttons =
                new HBox(
                        10,
                        encryptButton,
                        decryptButton,
                        copyButton,
                        clearButton
                );


        statusLabel.setStyle(
                Theme.SUBTITLE
        );


        VBox content =
                new VBox(
                        8,
                        title,
                        description,

                        inputLabel,
                        inputArea,

                        buttons,

                        outputLabel,
                        outputArea,

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


    private void encrypt() {

        String message =
                inputArea.getText();

        String password =
                passwordField.getText();


        if (message.isBlank()) {

            setError(
                    "Enter some text first."
            );

            return;
        }


        if (password.isBlank()) {

            setError(
                    "Enter a password first."
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


            setSuccess(
                    "Text encrypted successfully."
            );


        } catch (Exception e) {

            setError(
                    "Encryption failed."
            );
        }
    }


    private void decrypt() {

        String encryptedData =
                inputArea.getText();

        String password =
                passwordField.getText();


        if (encryptedData.isBlank()) {

            setError(
                    "Enter encrypted text first."
            );

            return;
        }


        if (password.isBlank()) {

            setError(
                    "Enter a password first."
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


            setSuccess(
                    "Text decrypted successfully."
            );


        } catch (Exception e) {

            setError(
                    "Decryption failed. Check your password."
            );
        }
    }


    private void copyOutput() {

        if (outputArea.getText().isBlank()) {

            setError(
                    "There is nothing to copy."
            );

            return;
        }


        javafx.scene.input.Clipboard clipboard =
                javafx.scene.input.Clipboard
                        .getSystemClipboard();


        javafx.scene.input.ClipboardContent content =
                new javafx.scene.input.ClipboardContent();


        content.putString(
                outputArea.getText()
        );


        clipboard.setContent(
                content
        );


        setSuccess(
                "Output copied to clipboard."
        );
    }


    private void clear() {

        inputArea.clear();
        outputArea.clear();

        statusLabel.setText(
                "Ready"
        );

        statusLabel.setStyle(
                Theme.SUBTITLE
        );
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
