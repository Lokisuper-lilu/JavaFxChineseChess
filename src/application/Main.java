package application;

import java.io.File;

import application.Qizi.CanNotMoveToException;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.layout.*;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

public class Main extends Application {
	private Pane pane = new Pane();
	static Qizi scelet;
	static Qizi points[][] = new Qizi[15][15];
	String record;
	int whos = 1;
	static int winner = 0;
	Label label = new Label("当前为红方回合");
	int clicktime = 1;
	String Savemove = "";
	TextArea Savemoves = new TextArea();

	@Override
	public void start(Stage primaryStage) {
		drawLines();
		insetQizi();
		Savemoves.setWrapText(true);
		Savemoves.setEditable(false);
		pane.setOnMouseClicked(e -> {
			int awsl = isWin();
			if (awsl == 0) {
				if (e.getButton() == MouseButton.SECONDARY) {
					scelet = null;
				}
				if (scelet != null) {
					if (whos == 1 && scelet.isRed != 1) {
						scelet.UnSceletced();
						return;
					} else if (whos == 0 && scelet.isRed == 1) {
						scelet.UnSceletced();
						return;
					}
					if (clicktime == 2) {
						trytomove(e.getX(), e.getY());
						if (whos == 0) {
							label.setText("当前是黑方回合");
						} else if (whos == 1) {
							label.setText("当前是红方回合");
						}
					} else {
						clicktime = 2;
					}
				}
			} else if (awsl == 1) {
				label.setText("红方胜利");
				label.setFont(Font.font("Times New Roman", FontWeight.BLACK, FontPosture.ITALIC, 60));
			}
		});
		Button restart = new Button("重启游戏");
		restart.setOnAction(e -> {
			restart();
		});
		ScrollPane domoves = new ScrollPane(Savemoves);
		BorderPane panes = new BorderPane();
		HBox tops=new HBox();
		tops.getChildren().addAll(restart,label);
		tops.setPadding(new Insets(5,5,5,5));
		panes.setLeft(pane);
		panes.setTop(tops);
		panes.setRight(domoves);
		domoves.setPrefSize(300, 600);
		panes.setPadding(new Insets(5, 5, 5, 5));
		Scene scene = new Scene(panes, 800, 600);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	private void moveBing(Qizi the, int x, int y, int tox, int toy) {
		if (the.way != 1 && tox != x) {
			playMusic2();
			return;
		}
		if (toy == y + 1) {
			playMusic2();
			return;
		}
		if (toy < y - 1) {
			playMusic2();
			return;
		}
		if (points[tox][toy] != null) {
			if (points[tox][toy].isRed != 1) {
				points[tox][toy].die();
			} else {
				playMusic2();
				return;
			}
		}
		points[x][y] = null;
		points[tox][toy] = the;
		the.x = tox;
		the.y = toy;
		the.setX(Mainnumber.xToPx(tox) - the.getWidth() / 2);
		the.setY(Mainnumber.yToPx(toy) - the.getHeight() / 2);
		if (toy == 4) {
			the.way = 1;
		}
		the.UnSceletced();
		whos = 0;
		playMusic1();
		clicktime = 1;
		Savemove = "红方:兵" + x + "平" + tox + "," + y + "进" + toy + "\n" + Savemove;
		Savemoves.setText(Savemove);
	}

	private void moveZu(Qizi the, int x, int y, int tox, int toy) {

		if (the.way != 1 && tox != x) {
			playMusic2();
			return;
		}
		if (toy == y - 1) {
			playMusic2();
			return;
		}
		if (toy > y + 1) {
			playMusic2();
			return;
		}
		if (points[tox][toy] != null) {
			if (points[tox][toy].isRed != 0) {
				points[tox][toy].die();
			} else {
				playMusic2();
				return;
			}
		}
		points[x][y] = null;
		points[tox][toy] = the;
		the.x = tox;
		the.y = toy;
		the.setX(Mainnumber.xToPx(tox) - the.getWidth() / 2);
		the.setY(Mainnumber.yToPx(toy) - the.getHeight() / 2);
		the.UnSceletced();
		if (toy == 5) {
			the.way = 1;
		}
		whos = 1;
		playMusic1();
		clicktime = 1;
		Savemove = "黑方:卒" + x + "平" + tox + "," + y + "进" + toy + "\n" + Savemove;
		Savemoves.setText(Savemove);
	}

	private void moveShuai(Qizi the, int x, int y, int tox, int toy) {
		if (tox < 3 || tox > 5) {
			playMusic2();
			return;
		}
		if (toy < 7) {
			playMusic2();
			return;
		}
		if (points[tox][toy] != null) {
			if (points[tox][toy].isRed != 1) {
				points[tox][toy].die();
			} else {
				playMusic2();
				return;
			}
		}
		points[x][y] = null;
		points[tox][toy] = the;
		the.x = tox;
		the.y = toy;
		the.setX(Mainnumber.xToPx(tox) - the.getWidth() / 2);
		the.setY(Mainnumber.yToPx(toy) - the.getHeight() / 2);
		the.UnSceletced();
		whos = 0;
		playMusic1();
		clicktime = 1;
		Savemove = "红方:帅" + x + "平" + tox + "," + y + "进" + toy + "\n" + Savemove;
		Savemoves.setText(Savemove);
	}

	private void moveJiang(Qizi the, int x, int y, int tox, int toy) {
		if (tox < 3 || tox > 5) {
			playMusic2();
			return;
		}
		if (toy > 2) {
			playMusic2();
			return;
		}
		if (points[tox][toy] != null) {
			if (points[tox][toy].isRed != 0) {
				points[tox][toy].die();
			} else {
				playMusic2();
				return;
			}
		}
		points[x][y] = null;
		points[tox][toy] = the;
		the.x = tox;
		the.y = toy;
		the.setX(Mainnumber.xToPx(tox) - the.getWidth() / 2);
		the.setY(Mainnumber.yToPx(toy) - the.getHeight() / 2);
		the.UnSceletced();
		whos = 1;
		playMusic1();
		clicktime = 1;
		Savemove = "黑方:将" + x + "平" + tox + "," + y + "进" + toy + "\n" + Savemove;
		Savemoves.setText(Savemove);
	}

	private void moveredma(Qizi the, int x, int y, int tox, int toy) {
		boolean ans = false;
		if (tox < 0 || tox > 8 || toy < 0 || toy > 9) {
			playMusic2();
			return;
		} else if (Main.points[tox][toy] != null && Main.points[tox][toy].isRed == 1) {
			playMusic2();
			return;
		} else if (((Math.abs(tox - x) == 2 && Math.abs(toy - y) == 1)
				|| (Math.abs(tox - x) == 1 && Math.abs(toy - y) == 2))) {
			if (Math.abs(tox - x) == 2 && Math.abs(toy - y) == 1) {
				if (tox > x && Main.points[x + 1][y] == null) {
					ans = true;
				} else if (tox < x && Main.points[x - 1][y] == null) {
					ans = true;
				} else {
					playMusic2();
					return;
				}
			} else {
				if (toy > y && Main.points[x][y + 1] == null) {
					ans = true;
				} else if (toy < y && Main.points[x][y - 1] == null) {
					ans = true;
				} else {
					playMusic2();
					return;
				}
			}
		} else {
			playMusic2();
			return;
		}
		if (ans) {
			if (points[tox][toy] != null) {
				if (points[tox][toy].isRed == 1) {
					playMusic2();
					return;
				} else {
					points[tox][toy].die();
				}
			}
			points[x][y] = null;
			points[tox][toy] = the;
			the.x = tox;
			the.y = toy;
			the.setX(Mainnumber.xToPx(tox) - the.getWidth() / 2);
			the.setY(Mainnumber.yToPx(toy) - the.getHeight() / 2);
			the.UnSceletced();
			playMusic1();
			clicktime = 1;
			whos = 0;
			Savemove = "红方:馬" + x + "平" + tox + "," + y + "进" + toy + "\n" + Savemove;
			Savemoves.setText(Savemove);
		}
	}

	private void moveblackma(Qizi the, int x, int y, int tox, int toy) {
		boolean ans = false;
		if (tox < 0 || tox > 8 || toy < 0 || toy > 9) {
			playMusic2();
			return;
		} else if (Main.points[tox][toy] != null && Main.points[tox][toy].isRed != 1) {
			playMusic2();
			return;
		} else if (((Math.abs(tox - x) == 2 && Math.abs(toy - y) == 1)
				|| (Math.abs(tox - x) == 1 && Math.abs(toy - y) == 2))) {
			if (Math.abs(tox - x) == 2 && Math.abs(toy - y) == 1) {
				if (tox > x && Main.points[x + 1][y] == null) {
					ans = true;
				} else if (tox < x && Main.points[x - 1][y] == null) {
					ans = true;
				} else {
					playMusic2();
					return;
				}
			} else {
				if (toy > y && Main.points[x][y + 1] == null) {
					ans = true;
				} else if (toy < y && Main.points[x][y - 1] == null) {
					ans = true;
				} else {
					playMusic2();
					return;
				}
			}
		} else {
			playMusic2();
			return;
		}
		if (ans) {
			if (points[tox][toy] != null) {
				if (points[tox][toy].isRed != 1) {
					playMusic2();
					return;
				} else {
					points[tox][toy].die();
				}
			}
			points[x][y] = null;
			points[tox][toy] = the;
			the.x = tox;
			the.y = toy;
			the.setX(Mainnumber.xToPx(tox) - the.getWidth() / 2);
			the.setY(Mainnumber.yToPx(toy) - the.getHeight() / 2);
			the.UnSceletced();
			playMusic1();
			clicktime = 1;
			whos = 1;
			Savemove = "黑方:馬" + x + "平" + tox + "," + y + "进" + toy + "\n" + Savemove;
			Savemoves.setText(Savemove);
		}
	}

	private void moveredju(Qizi the, int x, int y, int tox, int toy) {
		int count = 0;
		if (tox != x && toy != y) {
			playMusic2();
			return;
		} else {

			// 没有移动
			if (tox == the.x && toy == the.y)
				count = 1;
			// 对角线移动
			if (tox != the.x && toy != the.y)
				count = 1;
			// 判断中间有子。情况一
			if (tox > the.x && toy == the.y) {
				for (int temp = the.x + 1; temp < tox; temp++) {
					if (Main.points[temp][y] != null)
						count = 1;
				}
			}
			// 情况二
			if (tox < the.x && toy != the.y) {
				for (int temp = the.x - 1; temp > tox; temp--) {
					if (Main.points[temp][y] != null)
						count = 1;
				}
			}
			// 情况三
			if (tox == the.x && toy > the.y) {
				for (int temp = the.y + 1; temp < toy; temp++) {
					if (Main.points[x][temp] != null)
						count = 1;
				}
			}
			// 情况四
			if (tox == the.x && toy < the.y) {
				for (int temp = the.y - 1; temp > toy; temp--) {
					if (Main.points[x][temp] != null)
						count = 1;
				}
			}
			// count==0，可以移动，且目标点不为己方子

		}
		if (count == 0) {
			if (points[tox][toy] != null) {
				if (points[tox][toy].isRed == 1) {
					playMusic2();
					return;
				} else {
					points[tox][toy].die();
				}
			}
			points[x][y] = null;
			points[tox][toy] = the;
			the.x = tox;
			the.y = toy;
			the.setX(Mainnumber.xToPx(tox) - the.getWidth() / 2);
			the.setY(Mainnumber.yToPx(toy) - the.getHeight() / 2);
			the.UnSceletced();
			playMusic1();
			clicktime = 1;
			whos = 0;
			Savemove = "红方:車" + x + "平" + tox + "," + y + "进" + toy + "\n" + Savemove;
			Savemoves.setText(Savemove);
		} else if (count == 1) {
			playMusic2();
			return;
		}
	}

	private void moveblackju(Qizi the, int x, int y, int tox, int toy) {
		int count = 0;
		if (tox != x && toy != y) {
			playMusic2();
			return;
		} else {

			// 没有移动
			if (tox == the.x && toy == the.y)
				count = 1;
			// 对角线移动
			if (tox != the.x && toy != the.y)
				count = 1;
			// 判断中间有子。情况一
			if (tox > the.x && toy == the.y) {
				for (int temp = the.x + 1; temp < tox; temp++) {
					if (Main.points[temp][y] != null)
						count = 1;
				}
			}
			// 情况二
			if (tox < the.x && toy != the.y) {
				for (int temp = the.x - 1; temp > tox; temp--) {
					if (Main.points[temp][y] != null)
						count = 1;
				}
			}
			// 情况三
			if (tox == the.x && toy > the.y) {
				for (int temp = the.y + 1; temp < toy; temp++) {
					if (Main.points[x][temp] != null)
						count = 1;
				}
			}
			// 情况四
			if (tox == the.x && toy < the.y) {
				for (int temp = the.y - 1; temp > toy; temp--) {
					if (Main.points[x][temp] != null)
						count = 1;
				}
			}
			// count==0，可以移动，且目标点不为己方子

		}
		if (count == 0) {
			if (points[tox][toy] != null) {
				if (points[tox][toy].isRed != 1) {
					playMusic2();
					return;
				} else {
					points[tox][toy].die();
				}
			}
			points[x][y] = null;
			points[tox][toy] = the;
			the.x = tox;
			the.y = toy;
			the.setX(Mainnumber.xToPx(tox) - the.getWidth() / 2);
			the.setY(Mainnumber.yToPx(toy) - the.getHeight() / 2);
			the.UnSceletced();
			playMusic1();
			clicktime = 1;
			whos = 1;
			Savemove = "黑方:車" + x + "平" + tox + "," + y + "进" + toy + "\n" + Savemove;
			Savemoves.setText(Savemove);
		} else if (count == 1) {
			playMusic2();
			return;
		}
	}

	private void moveredpao(Qizi the, int x, int y, int tox, int toy) {
		int count = 0;// 记录两点之间棋子个数
		// 下列为6种情况
		if (tox != the.x && toy != the.y) {
			playMusic2();
			return;
		}
		if (tox == the.x && toy > the.y) {
			for (int tem = the.y + 1; tem < toy; tem++) {
				if (Main.points[x][tem] != null) {
					count++;
				}
			}
		}
		if (tox == the.x && toy < the.y) {
			for (int tem = toy + 1; tem < the.y; tem++) {
				if (Main.points[x][tem] != null) {
					count++;
				}
			}
		}
		if (toy == the.y && tox > the.x) {
			for (int tem = the.x + 1; tem < tox; tem++) {
				if (Main.points[tem][y] != null) {
					count++;
				}
			}

		}
		if (toy == the.y && tox < the.x) {
			for (int tem = tox + 1; tem < the.x; tem++) {
				if (Main.points[tem][y] != null) {
					count++;
				}
			}
		}
		if (Main.points[tox][toy] == null) {// 目的地是否有棋子以及是否为己方棋子
			if (count != 0) {
				playMusic2();
				return;
			}
		} else {
			if (count != 1 || Main.points[tox][toy].isRed == 1) {
				playMusic2();
				return;
			} else if (count == 1) {
				Main.points[tox][toy].die();
				count = 0;
			}
		}
		if (count == 0) {
			points[x][y] = null;
			points[tox][toy] = the;
			the.x = tox;
			the.y = toy;
			the.setX(Mainnumber.xToPx(tox) - the.getWidth() / 2);
			the.setY(Mainnumber.yToPx(toy) - the.getHeight() / 2);
			the.UnSceletced();
			playMusic1();
			clicktime = 1;
			whos = 0;
			Savemove = "红方:炮" + x + "平" + tox + "," + y + "进" + toy + "\n" + Savemove;
			Savemoves.setText(Savemove);
		} else {
			playMusic2();
			return;
		}
	}

	private void moveblackpao(Qizi the, int x, int y, int tox, int toy) {
		int count = 0;// 记录两点之间棋子个数
		// 下列为6种情况
		if (tox != the.x && toy != the.y) {
			playMusic2();
			return;
		}
		if (tox == the.x && toy > the.y) {
			for (int tem = the.y + 1; tem < toy; tem++) {
				if (Main.points[x][tem] != null) {
					count++;
				}
			}
		}
		if (tox == the.x && toy < the.y) {
			for (int tem = toy + 1; tem < the.y; tem++) {
				if (Main.points[x][tem] != null) {
					count++;
				}
			}
		}
		if (toy == the.y && tox > the.x) {
			for (int tem = the.x + 1; tem < tox; tem++) {
				if (Main.points[tem][y] != null) {
					count++;
				}
			}

		}
		if (toy == the.y && tox < the.x) {
			for (int tem = tox + 1; tem < the.x; tem++) {
				if (Main.points[tem][y] != null) {
					count++;
				}
			}
		}
		if (Main.points[tox][toy] == null) {// 目的地是否有棋子以及是否为己方棋子
			if (count != 0) {
				playMusic2();
				return;
			}
		} else {
			if (count != 1 || Main.points[tox][toy].isRed == 0) {
				playMusic2();
				return;
			} else if (count == 1) {
				Main.points[tox][toy].die();
				count = 0;
			}
		}
		if (count == 0) {
			points[x][y] = null;
			points[tox][toy] = the;
			the.x = tox;
			the.y = toy;
			the.setX(Mainnumber.xToPx(tox) - the.getWidth() / 2);
			the.setY(Mainnumber.yToPx(toy) - the.getHeight() / 2);
			the.UnSceletced();
			playMusic1();
			clicktime = 1;
			whos = 1;
			Savemove = "黑方:炮" + x + "平" + tox + "," + y + "进" + toy + "\n" + Savemove;
			Savemoves.setText(Savemove);
		} else {
			playMusic2();
			return;
		}
	}

	private void moveredxiang(Qizi the, int x, int y, int tox, int toy) {
		int midx = (tox + x) / 2;
		int midy = (toy + y) / 2;
		if (points[midx][midy] != null) {
			playMusic2();
			return;
		}
		if (toy <= 4) {
			playMusic2();
			return;
		}
		if ((tox - x == 2 && toy - y == 2) || (tox - x == 2 && toy - y == -2) || (tox - x == -2 && toy - y == 2)
				|| (tox - x == -2 && toy - y == -2)) {
			if (points[tox][toy] != null) {
				if (points[tox][toy].isRed == 1) {
					playMusic2();
					return;
				} else {
					points[tox][toy].die();
				}
			}
			points[x][y] = null;
			points[tox][toy] = the;
			the.x = tox;
			the.y = toy;
			the.setX(Mainnumber.xToPx(tox) - the.getWidth() / 2);
			the.setY(Mainnumber.yToPx(toy) - the.getHeight() / 2);
			the.UnSceletced();
			playMusic1();
			clicktime = 1;
			whos = 0;
			Savemove = "红方:象" + x + "," + y + "填" + tox + "," + toy + "\n" + Savemove;
			Savemoves.setText(Savemove);
		}
	}

	private void moveblackxiang(Qizi the, int x, int y, int tox, int toy) {
		int midx = (tox + x) / 2;
		int midy = (toy + y) / 2;
		if (points[midx][midy] != null) {
			playMusic2();
			return;
		}
		if (toy >= 5) {
			playMusic2();
			return;
		}
		if ((tox - x == 2 && toy - y == 2) || (tox - x == 2 && toy - y == -2) || (tox - x == -2 && toy - y == 2)
				|| (tox - x == -2 && toy - y == -2)) {
			if (points[tox][toy] != null) {
				if (points[tox][toy].isRed == 1) {
					playMusic2();
					return;
				} else {
					points[tox][toy].die();
				}
			}
			points[x][y] = null;
			points[tox][toy] = the;
			the.x = tox;
			the.y = toy;
			the.setX(Mainnumber.xToPx(tox) - the.getWidth() / 2);
			the.setY(Mainnumber.yToPx(toy) - the.getHeight() / 2);
			the.UnSceletced();
			playMusic1();
			clicktime = 1;
			whos = 1;
			Savemove = "黑方:象" + x + "," + y + "填" + tox + "," + toy + "\n" + Savemove;
			Savemoves.setText(Savemove);
		}
	}

	private void moveredshi(Qizi the, int x, int y, int tox, int toy) {
		if (tox < 3 || tox > 5) {
			playMusic2();
			return;
		}
		if (toy < 7) {
			playMusic2();
			return;
		}
		if (tox == x || toy == y) {
			playMusic2();
			return;
		}
		if (Math.abs(tox - x) + Math.abs(toy - y) != 2) {
			playMusic2();
			return;
		}
		if (points[tox][toy] != null) {
			if (points[tox][toy].isRed != 1) {
				points[tox][toy].die();
			} else {
				playMusic2();
				return;
			}
		}
		points[x][y] = null;
		points[tox][toy] = the;
		the.x = tox;
		the.y = toy;
		the.setX(Mainnumber.xToPx(tox) - the.getWidth() / 2);
		the.setY(Mainnumber.yToPx(toy) - the.getHeight() / 2);
		the.UnSceletced();
		playMusic1();
		clicktime = 1;
		whos = 0;
		Savemove = "红方:士" + x + "," + y + "斜" + tox + "," + toy + "\n" + Savemove;
		Savemoves.setText(Savemove);
	}

	private void moveblackshi(Qizi the, int x, int y, int tox, int toy) {
		if (tox < 3 || tox > 5) {
			playMusic2();
			return;
		}
		if (toy > 2) {
			playMusic2();
			return;
		}
		if (tox == x || toy == y) {
			playMusic2();
			return;
		}
		if (Math.abs(tox - x) + Math.abs(toy - y) != 2) {
			playMusic2();
			return;
		}
		if (points[tox][toy] != null) {
			if (points[tox][toy].isRed == 1) {
				points[tox][toy].die();
			} else {
				playMusic2();
				return;
			}
		}
		points[x][y] = null;
		points[tox][toy] = the;
		the.x = tox;
		the.y = toy;
		the.setX(Mainnumber.xToPx(tox) - the.getWidth() / 2);
		the.setY(Mainnumber.yToPx(toy) - the.getHeight() / 2);
		the.UnSceletced();
		playMusic1();
		clicktime = 1;
		whos = 1;
		Savemove = "黑方:士" + x + "," + y + "斜" + tox + "," + toy + "\n" + Savemove;
		Savemoves.setText(Savemove);
	}

	private int isWin() {
		if (winner == 1) {
			return 1;
		}
		if (winner == 2) {
			label.setText("黑方胜利");
			label.setTranslateX(250);
			label.setTranslateY(275);
			label.setFont(Font.font("Times New Roman", FontWeight.BLACK, FontPosture.ITALIC, 60));
			return 2;
		}
		return 0;
	}

	private void trytomove(double s, double b) {
		int x, y;
		switch (scelet.name) {
		case 1:
			x = scelet.x;
			y = scelet.y;
			moveBing(scelet, x, y, Mainnumber.pxToX(s), Mainnumber.pxToY(b));
			break;
		case 2:
			x = scelet.x;
			y = scelet.y;
			moveZu(scelet, x, y, Mainnumber.pxToX(s), Mainnumber.pxToY(b));
			break;
		case 3:
			x = scelet.x;
			y = scelet.y;
			moveShuai(scelet, x, y, Mainnumber.pxToX(s), Mainnumber.pxToY(b));
			break;
		case 4:
			x = scelet.x;
			y = scelet.y;
			moveJiang(scelet, x, y, Mainnumber.pxToX(s), Mainnumber.pxToY(b));
			break;
		case 5:
			x = scelet.x;
			y = scelet.y;
			moveredma(scelet, x, y, Mainnumber.pxToX(s), Mainnumber.pxToY(b));
			break;
		case 6:
			x = scelet.x;
			y = scelet.y;
			moveblackma(scelet, x, y, Mainnumber.pxToX(s), Mainnumber.pxToY(b));
			break;
		case 7:
			x = scelet.x;
			y = scelet.y;
			moveredju(scelet, x, y, Mainnumber.pxToX(s), Mainnumber.pxToY(b));
			break;
		case 8:
			x = scelet.x;
			y = scelet.y;
			moveblackju(scelet, x, y, Mainnumber.pxToX(s), Mainnumber.pxToY(b));
			break;
		case 9:
			x = scelet.x;
			y = scelet.y;
			moveredpao(scelet, x, y, Mainnumber.pxToX(s), Mainnumber.pxToY(b));
			break;
		case 10:
			x = scelet.x;
			y = scelet.y;
			moveblackpao(scelet, x, y, Mainnumber.pxToX(s), Mainnumber.pxToY(b));
			break;
		case 11:
			x = scelet.x;
			y = scelet.y;
			moveredxiang(scelet, x, y, Mainnumber.pxToX(s), Mainnumber.pxToY(b));
			break;
		case 12:
			x = scelet.x;
			y = scelet.y;
			moveblackxiang(scelet, x, y, Mainnumber.pxToX(s), Mainnumber.pxToY(b));
			break;
		case 13:
			x = scelet.x;
			y = scelet.y;
			moveredshi(scelet, x, y, Mainnumber.pxToX(s), Mainnumber.pxToY(b));
			break;
		case 14:
			x = scelet.x;
			y = scelet.y;
			moveblackshi(scelet, x, y, Mainnumber.pxToX(s), Mainnumber.pxToY(b));
			break;
		}
	}

	private void drawLines() {
		final int ge = Mainnumber.ge;
		final int xtoright = Mainnumber.xtoright;
		final int xtoleft = Mainnumber.xtoleft;
		final int ytotop = Mainnumber.ytotop;
		final int ytobottom = Mainnumber.ytobottom;
		for (int xh1 = ytotop; xh1 <= ytobottom; xh1 = xh1 + ge) {
			Line newLine = new Line();
			newLine.setStartX(xtoleft);
			newLine.setStartY(xh1);
			newLine.setEndX(xtoright);
			newLine.setEndY(xh1);
			pane.getChildren().add(newLine);
		}
		for (int xh1 = xtoleft; xh1 <= xtoright; xh1 = xh1 + ge) {
			Line newtopLine = new Line();
			newtopLine.setStartY(ytotop);
			newtopLine.setStartX(xh1);
			newtopLine.setEndY(ytotop + 4 * ge);
			newtopLine.setEndX(xh1);
			Line newbottomLine = new Line();
			newbottomLine.setStartY(ytotop + 5 * ge);
			newbottomLine.setStartX(xh1);
			newbottomLine.setEndY(ytobottom);
			newbottomLine.setEndX(xh1);
			pane.getChildren().addAll(newtopLine, newbottomLine);
		}
		Line line = new Line(xtoleft, ytotop + 4 * ge, xtoleft, ytotop + 5 * ge);
		pane.getChildren().add(line);
		line = new Line(xtoright, ytotop + 4 * ge, xtoright, ytotop + 5 * ge);
		pane.getChildren().add(line);

		Line crossLine1 = new Line(xtoleft + 3 * ge, ytotop, xtoleft + 5 * ge, ytotop + 2 * ge);
		Line crossLine2 = new Line(xtoleft + 3 * ge, ytotop + 2 * ge, xtoleft + 5 * ge, ytotop);
		pane.getChildren().addAll(crossLine1, crossLine2);
		crossLine1 = new Line(xtoleft + 3 * ge, ytobottom, xtoleft + 5 * ge, ytobottom - 2 * ge);
		crossLine2 = new Line(xtoleft + 3 * ge, ytobottom - 2 * ge, xtoleft + 5 * ge, ytobottom);
		pane.getChildren().addAll(crossLine1, crossLine2);
	}

	private void insetQizi() {
		Jiang shuai = new Jiang(4, 9, 1);
		Jiang jiang = new Jiang(4, 0, 0);
		Shi redshi1 = new Shi(3, 9, 1);
		Shi redshi2 = new Shi(5, 9, 1);
		Ju redju1 = new Ju(0, 9, 1);
		Ju redju2 = new Ju(8, 9, 1);
		Pao redpao2 = new Pao(7, 7, 1);
		Pao redpao1 = new Pao(1, 7, 1);
		Xiang redxiang1 = new Xiang(2, 9, 1);
		Xiang redxiang2 = new Xiang(6, 9, 1);
		Ma redma1 = new Ma(1, 9, 1);
		Ma redma2 = new Ma(7, 9, 1);
		Bing bing1 = new Bing(0, 6, 1);
		Bing bing2 = new Bing(2, 6, 1);
		Bing bing3 = new Bing(4, 6, 1);
		Bing bing4 = new Bing(6, 6, 1);
		Bing bing5 = new Bing(8, 6, 1);
		Shi hshi1 = new Shi(3, 0, 0);
		Shi hshi2 = new Shi(5, 0, 0);
		Ju hju1 = new Ju(0, 0, 0);
		Ju hju2 = new Ju(8, 0, 0);
		Pao hpao2 = new Pao(7, 2, 0);
		Pao hpao1 = new Pao(1, 2, 0);
		Xiang hxiang1 = new Xiang(2, 0, 0);
		Xiang hxiang2 = new Xiang(6, 0, 0);
		Ma hma1 = new Ma(1, 0, 0);
		Ma hma2 = new Ma(7, 0, 0);
		Bing zu1 = new Bing(0, 3, 0);
		Bing zu2 = new Bing(2, 3, 0);
		Bing zu3 = new Bing(4, 3, 0);
		Bing zu4 = new Bing(6, 3, 0);
		Bing zu5 = new Bing(8, 3, 0);
		pane.getChildren().addAll(jiang, shuai, redshi1, redshi2, redju1, redju2, redpao1, redpao2, redxiang1,
				redxiang2, redma2, redma1, bing1, bing2, bing3, bing4, bing5, hma2, hma1, hxiang2, hxiang1, hpao1,
				hpao2, hju1, hju2, hshi2, hshi1, zu1, zu5, zu4, zu3, zu2);

	}

	static void playMusic1() {
		try {

			Media xq = new Media("Bgm/go.mp3");
			MediaPlayer bf = new MediaPlayer(xq);
			bf.play();
		} catch (Exception e) {
			System.out.print(e);
		}
	}

	static void playMusic2() {
		String s1 = new File("Bgm/nomove.mp3").toURI().toString();
		Media xq = new Media(s1);
		MediaPlayer bf = new MediaPlayer(xq);
		bf.play();
	}

	private void restart() {
		pane.getChildren().clear();
		drawLines();
		insetQizi();
		Savemove = "";
		Savemoves.setText(Savemove);
		label.setText("当前是红方回合");
		label.setFont(Font.font("Time New Roman",FontWeight.BOLD,FontPosture.REGULAR,20));
		clicktime=1;
		whos = 1;
	}

	public static void main(String[] args) {
		launch(args);
	}
}
