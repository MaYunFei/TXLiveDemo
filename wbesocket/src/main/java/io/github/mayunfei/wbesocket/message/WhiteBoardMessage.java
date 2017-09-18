package io.github.mayunfei.wbesocket.message;

import java.util.List;

/**
 * Created by mayunfei on 17-9-18.
 */

public abstract class WhiteBoardMessage extends LiveMessage {

    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "DrawLineMessage{" +
                "data=" + data +
                '}';
    }

    public static class Data {
        private String method;
        private List<Point> points;
        private Opt opt;

        public String getMethod() {
            return method;
        }

        public void setMethod(String method) {
            this.method = method;
        }

        public List<Point> getPoints() {
            return points;
        }

        public void setPoints(List<Point> points) {
            this.points = points;
        }

        public Opt getOpt() {
            return opt;
        }

        public void setOpt(Opt opt) {
            this.opt = opt;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "method='" + method + '\'' +
                    ", points=" + points +
                    ", opt=" + opt +
                    '}';
        }
    }


    public static class Point{
        /**
         * initX : 769.1042015012986
         * initY : 877.5659041588103
         * x : 772.5194777473408
         * y : 880.9811804048525
         */

        private float initX;
        private float initY;
        private float x;
        private float y;

        public float getInitX() {
            return initX;
        }

        public void setInitX(float initX) {
            this.initX = initX;
        }

        public float getInitY() {
            return initY;
        }

        public void setInitY(float initY) {
            this.initY = initY;
        }

        public float getX() {
            return x;
        }

        public void setX(float x) {
            this.x = x;
        }

        public float getY() {
            return y;
        }

        public void setY(float y) {
            this.y = y;
        }

        @Override
        public String toString() {
            return "Point{" +
                    "initX=" + initX +
                    ", initY=" + initY +
                    ", x=" + x +
                    ", y=" + y +
                    '}';
        }
    }

    public static class Opt{

        /**
         * width : 6.8305524920843865
         * color : #ff0000
         * lineCap : round
         * isSend : true
         * isDown : false
         * gco : source-over
         */

        private float width;
        private String color;
        private String lineCap;
        private boolean isSend;
        private boolean isDown;
        private String gco;
        private List<String> val;

        public float getWidth() {
            return width;
        }

        public void setWidth(float width) {
            this.width = width;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public String getLineCap() {
            return lineCap;
        }

        public void setLineCap(String lineCap) {
            this.lineCap = lineCap;
        }

        public boolean isIsSend() {
            return isSend;
        }

        public void setIsSend(boolean isSend) {
            this.isSend = isSend;
        }

        public boolean isIsDown() {
            return isDown;
        }

        public void setIsDown(boolean isDown) {
            this.isDown = isDown;
        }

        public String getGco() {
            return gco;
        }

        public void setGco(String gco) {
            this.gco = gco;
        }

        public List<String> getVal() {
            return val;
        }

        public void setVal(List<String> val) {
            this.val = val;
        }

        @Override
        public String toString() {
            return "Opt{" +
                    "width=" + width +
                    ", color='" + color + '\'' +
                    ", lineCap='" + lineCap + '\'' +
                    ", isSend=" + isSend +
                    ", isDown=" + isDown +
                    ", gco='" + gco + '\'' +
                    ", val=" + val +
                    '}';
        }
    }

}
