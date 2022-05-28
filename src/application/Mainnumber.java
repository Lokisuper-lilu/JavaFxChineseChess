package application;

public class Mainnumber {
	final static int ge=55;
	final static int xtoleft=30;
	final static int ytotop=30;
	final static int xtoright=30+8*ge;
	final static int ytobottom=30+9*ge;
	  //将棋盘上的坐标转化为像素值
    static double xToPx(int x) {
        return xtoleft + x * ge;
    }

    static double yToPx(int y) {
        return ytotop + y * ge;
    }

    //将像素值转化为最近的坐标值
    static int pxToX(double x_Px) {
        Double t = (x_Px - xtoleft) / ge;
        long ans = Math.round(t);
        return (int) ans;
    }

    static int pxToY(double y_Px) {
        Double t = (y_Px - ytotop) / ge;
        long ans = Math.round(t);
        return (int) ans;
    }
}


