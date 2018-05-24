package com.zust.playandroid.bean.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 作 者： ZUST_YTH
 * 日 期： 2018/5/21
 * 时 间： 10:48
 * 项 目： PlayAndroid
 * 描 述：
 */


@Entity
public class History {
    @Id
    private Long id;
    @NotNull
    private String content;
    @NotNull
    private String time;
    @Generated(hash = 329933795)
    public History(Long id, @NotNull String content, @NotNull String time) {
        this.id = id;
        this.content = content;
        this.time = time;
    }
    @Generated(hash = 869423138)
    public History() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getContent() {
        return this.content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public String getTime() {
        return this.time;
    }
    public void setTime(String time) {
        this.time = time;
    }
}
