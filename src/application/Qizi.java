package application;

import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class Qizi extends Rectangle {
	Qizi(int x, int y, int isred) {
		setWidth(4.0 * Mainnumber.ge / 5.0);
		setHeight(4.0 * Mainnumber.ge / 5.0);
		this.x = x;
		this.y = y;
		isRed = isred;
		setX(Mainnumber.xToPx(x) - getWidth() / 2);
		setY(Mainnumber.yToPx(y) - getHeight() / 2);
		setVisible(true);
	}

	int x, y;
	int isRed;
	boolean isSceleted = false;
	int name;
	int way;
	protected void die() {
		this.setVisible(false);
		this.setDisable(true);
	}

	protected void Sceletced() {
		isSceleted = true;
		Main.scelet = this;
		loadimage();
	}

	protected void UnSceletced() {
		isSceleted = false;
		Main.scelet = null;
		loadimage();
	}

	protected void loadimage() {

	}

	protected void moveTo(int dstX, int dstY) throws CanNotMoveToException {

	}

	static class CanNotMoveToException extends Exception {
	}
}
