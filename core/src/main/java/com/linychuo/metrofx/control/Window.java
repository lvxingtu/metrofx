package com.linychuo.metrofx.control;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Created by ivan on 14-8-14.
 */
public class Window implements MetroConfig {

  private StackPane content;
  private double initX;
  private double initY;
  private Stage primaryStage;

  public Window(Stage primaryStage, String title, int width, int height) {
    this.primaryStage = primaryStage;

    VBox mainBody = new VBox();
    mainBody.setSpacing(0);
    mainBody.setStyle("-fx-background-color:#6badf6");
    Scene scene = new Scene(mainBody, width, height);
    primaryStage.initStyle(StageStyle.UNDECORATED);
    primaryStage.setScene(scene);
    primaryStage.setTitle(title);

    initialBody();
    mainBody.getChildren().addAll(buildCaption(), content);
  }


  private Pane buildCaption() {
    AnchorPane caption = new AnchorPane();
    caption.setPrefHeight(CAPTION_HEIGHT);
    caption.setOnMousePressed(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent me) {
        initX = me.getScreenX() - primaryStage.getX();
        initY = me.getScreenY() - primaryStage.getY();
      }
    });
    caption.setOnMouseDragged(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        primaryStage.setX(event.getScreenX() - initX);
        primaryStage.setY(event.getScreenY() - initY);
      }
    });

    Label captionTitle = new Label(primaryStage.getTitle());
    AnchorPane.setTopAnchor(captionTitle, 5.0);
    AnchorPane.setLeftAnchor(captionTitle, MARGIN);
    caption.getChildren().addAll(captionTitle);

    Button closeBtn = new Button("X");
    closeBtn.setStyle(
        "-fx-text-fill:#fff; -fx-background-radius: 0; -fx-pref-width:48;-fx-pref-height:24;-fx-min-height:24; -fx-background-color:#c75050;-fx-cursor:hand;");
    AnchorPane.setRightAnchor(closeBtn, MARGIN);
    closeBtn.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        primaryStage.close();
      }
    });
    caption.getChildren().addAll(closeBtn);

    return caption;
  }

  private void initialBody() {
    content = new StackPane();
    VBox.setMargin(content, new Insets(0, MARGIN, MARGIN, MARGIN));
    Rectangle
        bg =
        new Rectangle(primaryStage.getScene().getWidth() - MARGIN * 2,
                      primaryStage.getScene().getHeight() - CAPTION_HEIGHT);
    bg.setFill(Color.web("#fff"));
    content.getChildren().add(bg);
  }

  public void show(Form form) {
    if (content.getChildren().size() == 2) {
      content.getChildren().remove(1);
    }
    content.getChildren().add(form.build());
  }

  public void show() {
    primaryStage.centerOnScreen();
    primaryStage.show();
  }
}