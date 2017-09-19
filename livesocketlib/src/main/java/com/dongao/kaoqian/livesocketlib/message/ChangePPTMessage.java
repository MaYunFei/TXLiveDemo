package com.dongao.kaoqian.livesocketlib.message;


import com.dongao.kaoqian.livesocketlib.live.ILiveManager;

/**
 * Created by mayunfei on 17-9-18.
 */

public class ChangePPTMessage extends LiveMessage {

    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public static class Data{
        private String current;
        private boolean action;

        public boolean isAction() {
            return action;
        }

        public void setAction(boolean action) {
            this.action = action;
        }

        public String getCurrent() {
            return current;
        }

        public void setCurrent(String current) {
            this.current = current;
        }
    }

    @Override
    public void handleMessage(ILiveManager liveManager) {
        liveManager.getWriteBoard().changePPPT(this);
    }
}
