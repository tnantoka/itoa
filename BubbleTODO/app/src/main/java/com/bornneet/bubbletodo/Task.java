package com.bornneet.bubbletodo;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * Created by tnantoka on 8/19/16.
 */
public class Task extends RealmObject {
    public int id;
    public String name;

    public Task() {
        super();
    }

    public Task(String name) {
        super();
        this.name = name;
        this.id = getNextId();
    }

    private int getNextId() {
        Realm realm = Realm.getDefaultInstance();
        Number max = realm.where(getClass()).max("id");
        if (max == null) {
            return 1;
        } else {
            return max.intValue() + 1;
        }
    }

    static RealmResults<Task> all() {
        Realm realm = Realm.getDefaultInstance();
        RealmQuery<Task> query = realm.where(Task.class);
        RealmResults<Task> tasks = query.findAll();
        return tasks;
    }
}
