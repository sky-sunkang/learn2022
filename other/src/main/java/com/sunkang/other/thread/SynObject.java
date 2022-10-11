package com.sunkang.other.thread;

import java.util.ArrayList;
import java.util.List;

public class SynObject {
    private static List<String> list = new ArrayList<>();

    public void addList(String str) {
        list.add(str);
    }

    public synchronized void addListSyn(String str) {
        list.add(str);
    }

    public List<String> getList() {
        return this.list;
    }
}
