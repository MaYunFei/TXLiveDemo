package com.dongao.kaoqian.livesocketlib.message;


import com.dongao.kaoqian.livesocketlib.live.ILiveManager;

/**
 * 课间休息
 * Created by mayunfei on 17-9-18.
 */

public class ClassRestMessage extends LiveMessage {

    Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public static class Data{
        private int status;

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }

    @Override
    public void handleMessage(ILiveManager liveManager) {
        liveManager.classRest(this);
    }
}
