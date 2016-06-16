package com.example.august.sortkeylist;

/**
 * Created by August on 16/5/11.
 */
public class DataBean implements Comparable<DataBean> {
    private String data;
    private char firstLetter;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
        firstLetter = ChineseToEnglish.getFirstSpell(data).charAt(0);
    }

    public char getFirstLetter() {
        return firstLetter;
    }


    /**
     * 使用Comparable接口,使数据可排序
     * @param dataBean
     * @return
     */
    @Override
    public int compareTo(DataBean dataBean) {
        if (firstLetter > dataBean.firstLetter) {
            return 1;
        } else if (firstLetter < dataBean.firstLetter) {
            return -1;
        } else {
            return 0;
        }
    }
}
