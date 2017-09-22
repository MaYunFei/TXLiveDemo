package com.dongao.kaoqian.livesocketlib.message;


import com.dongao.kaoqian.livesocketlib.live.ILiveManager;

import java.util.List;

/**
 * 写字
 * Created by mayunfei on 17-9-18.
 */

public class DrawTextSaveMessage extends LiveMessage {

    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    @Override
    public void handleMessage(ILiveManager liveManager) {
        liveManager.getWriteBoard().drawTextSave(this);
    }

    public static class Data {
        private String method;
        private WhiteBoardMessage.Point point;
        private WhiteBoardMessage.Opt opt;

        public WhiteBoardMessage.Point getPoint() {
            return point;
        }

        public void setPoint(WhiteBoardMessage.Point point) {
            this.point = point;
        }

        public String getMethod() {
            return method;
        }

        public void setMethod(String method) {
            this.method = method;
        }

        public WhiteBoardMessage.Opt getOpt() {
            return opt;
        }

        public void setOpt(WhiteBoardMessage.Opt opt) {
            this.opt = opt;
        }

    }
}
