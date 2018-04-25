package com.example.student.p447;

public class Item {
    String  name;
    String mobile;
    int age;
    int resId;

    public Item() {
    }

    public Item(String name, String mobile, int age, int resId) {
        this.name = name;
        this.mobile = mobile;
        this.age = age;
        this.resId = resId;
    }

    public String getName() {
        return name;
    }

    public String getMobile() {
        return mobile;
    }

    public int getAge() {
        return age;
    }

    public int getResId() {
        return resId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }
}
