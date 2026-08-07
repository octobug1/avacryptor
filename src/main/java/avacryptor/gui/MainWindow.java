package avacryptor.gui;

import avacryptor.crypto.KeyGenerator;
import avacryptor.gui.components.FileEncryptionPane;
import avacryptor.gui.components.TextEncryptionPane;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class MainWindow {


public Parent getRoot() {


    // =========================
    // PASSWORD
    // =========================

    Label passwordTitle =
            new Label(
                    "Encryption Password"
            );


    passwordTitle.setStyle(
            Theme.LABEL +
            "-fx-font-size: 14px;" +
            "-fx-font-weight: bold;"
    );


    PasswordField passwordField =
            new PasswordField();


    passwordField.setPromptText(
            "Enter your encryption password..."
    );


    passwordField.setPrefHeight(40);


    passwordField.setStyle(
            Theme.INPUT
    );


    HBox.setHgrow(
            passwordField,
            Priority.ALWAYS
    );


    Button generateButton =
            new Button(
                    "Generate Password"
            );


    generateButton.setStyle(
            Theme.PRIMARY_BUTTON
    );


    Button copyButton =
            new Button(
                    "Copy"
            );


    copyButton.setStyle(
            Theme.SECONDARY_BUTTON
    );


    generateButton.setOnAction(
            event -> {

                String generatedPassword =
                        KeyGenerator.generatePassword();


                passwordField.setText(
                        generatedPassword
                );
            }
    );


    copyButton.setOnAction(
            event -> {

                if(passwordField.getText().isBlank()) {
                    return;
                }


                javafx.scene.input.Clipboard clipboard =
                        javafx.scene.input.Clipboard
                                .getSystemClipboard();


                javafx.scene.input.ClipboardContent content =
                        new javafx.scene.input.ClipboardContent();


                content.putString(
                        passwordField.getText()
                );


                clipboard.setContent(
                        content
                );
            }
    );


    HBox passwordBox =
            new HBox(
                    10,
                    passwordField,
                    generateButton,
                    copyButton
            );


    VBox passwordCard =
            new VBox(
                    10,
                    passwordTitle,
                    passwordBox
            );


    passwordCard.setPadding(
            new Insets(18)
    );


    passwordCard.setStyle(
            Theme.CARD
    );



    // =========================
    // HEADER
    // =========================


    Image logoImage = null;
    try {
        logoImage = new Image(
                getClass()
                        .getResourceAsStream(
                                "/images/ava-logo-fixed.png"
                        )
        );
    } catch (Exception ignored) {
        logoImage = new Image(
                getClass()
                        .getResourceAsStream(
                                "/images/ava-logo.png"
                        )
        );
    }


    ImageView logo =
            new ImageView(
                    logoImage
            );


    logo.setFitWidth(42);
    logo.setFitHeight(42);
    logo.setPreserveRatio(true);



    Label title =
            new Label(
                    "AvaCryptor"
            );


    title.setFont(
            Font.font(
                    "System",
                    FontWeight.BOLD,
                    34
            )
    );


    title.setStyle(
            Theme.TITLE
    );



    HBox titleRow =
            new HBox(
                    12,
                    logo,
                    title
            );


    titleRow.setAlignment(
            Pos.CENTER
    );



    Label subtitle =
            new Label(
                    "Secure AES encryption for text and files"
            );


    subtitle.setStyle(
            Theme.SUBTITLE +
            "-fx-font-size: 14px;"
    );



    VBox header =
            new VBox(
                    6,
                    titleRow,
                    subtitle
            );


    header.setAlignment(
            Pos.CENTER
    );



    // =========================
    // COMPONENTS
    // =========================


    TextEncryptionPane textPane =
            new TextEncryptionPane(
                    passwordField
            );


    FileEncryptionPane filePane =
            new FileEncryptionPane(
                    passwordField
            );



    // =========================
    // MAIN CONTENT
    // =========================


    VBox content =
            new VBox(
                    18,
                    passwordCard,
                    textPane.getView(),
                    filePane.getView()
            );


    content.setMaxWidth(
            850
    );



    // =========================
    // ROOT
    // =========================


    VBox root =
            new VBox(
                    25,
                    header,
                    content
            );


    root.setPadding(
            new Insets(30)
    );


    root.setAlignment(
            Pos.TOP_CENTER
    );


    root.setStyle(
            Theme.BACKGROUND
    );



    // =========================
    // SCROLL
    // =========================


    ScrollPane scroll =
            new ScrollPane(
                    root
            );


    scroll.setFitToWidth(
            true
    );


    scroll.setStyle(
            "-fx-background: #0b1120;" +
            "-fx-background-color: #0b1120;"
    );



    return scroll;
}


}
