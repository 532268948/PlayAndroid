package com.zust.playandroid.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 作 者： ZUST_YTH
 * 日 期： 2018/5/17
 * 时 间： 18:58
 * 项 目： PlayAndroid
 * 描 述：
 */


public class KnowledgeTreeBean implements Serializable {

    private List<KnowledgeTreeBean> children;
    private int courseId;
    private int id;
    private String name;
    private int order;
    private int parentChapterId;
    private int visible;

    public KnowledgeTreeBean(List<KnowledgeTreeBean> children, int courseId, int id, String name, int order, int parentChapterId, int visible) {
        this.children = children;
        this.courseId = courseId;
        this.id = id;
        this.name = name;
        this.order = order;
        this.parentChapterId = parentChapterId;
        this.visible = visible;
    }

    public List<KnowledgeTreeBean> getChildren() {
        return children;
    }

    public void setChildren(List<KnowledgeTreeBean> children) {
        this.children = children;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public int getParentChapterId() {
        return parentChapterId;
    }

    public void setParentChapterId(int parentChapterId) {
        this.parentChapterId = parentChapterId;
    }

    public int getVisible() {
        return visible;
    }

    public void setVisible(int visible) {
        this.visible = visible;
    }
}
