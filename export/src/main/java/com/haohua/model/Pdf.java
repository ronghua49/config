package com.haohua.model;

public class Pdf implements Comparable<Pdf> {
    private Integer index;

    private String key;

    private String code;

    private String value;

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public int compareTo(Pdf o) {
        int flag = this.index.compareTo(o.index);
        return flag;
    }
}
