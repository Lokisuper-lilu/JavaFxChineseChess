package application;

import java.io.File;

import application.Qizi.CanNotMoveToException;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

class Shi extends Qizi {
	Shi(int x, int y, int isred) {
		super(x, y, isred);
		Main.points[x][y] = this;
		try {
			loadimage();
		} catch (Exception e) {
			System.out.println(e);
		}
		this.setOnMouseClicked(e -> {
			if (e.getButton() == MouseButton.PRIMARY && Main.scelet == null) {
				Sceletced();
			} else if (e.getButton() == MouseButton.SECONDARY && Main.scelet == this) {
				UnSceletced();
			}
			loadimage();
		});
	}

	protected void die() {
		super.die();
		this.setVisible(false);
		this.setDisable(true);
	}

	protected void Sceletced() {
		super.Sceletced();
		isSceleted = true;
		Main.scelet = this;
	}

	protected void UnSceletced() {
		super.UnSceletced();
		isSceleted = false;
		Main.scelet = null;
	}

	protected void loadimage() {
		super.loadimage();
		Image image;
		if (isRed == 1) {
			super.name = 13;
			if (isSceleted) {
				image = new Image(new File("Æå×ÓÍ¼Æ¬\\RAS.GIF").toURI().toString());
			} else {
				image = new Image(new File("Æå×ÓÍ¼Æ¬\\RA.GIF").toURI().toString());
			}
		} else {
			super.name = 14;
			if (isSceleted) {
				image = new Image(new File("Æå×ÓÍ¼Æ¬\\BAS.GIF").toURI().toString());
			} else {
				image = new Image(new File("Æå×ÓÍ¼Æ¬\\BA.GIF").toURI().toString());
			}
		}
		this.setFill(new ImagePattern(image));
	}

}
