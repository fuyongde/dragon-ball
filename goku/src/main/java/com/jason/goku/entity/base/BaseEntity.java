package com.jason.goku.entity.base;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.time.LocalDateTime;

public abstract class BaseEntity implements Serializable {
    private LocalDateTime created;
    private LocalDateTime updated;
    private Boolean deleted = DeletedEmun.NORMAL.value;

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public LocalDateTime getUpdated() {
        return updated;
    }

    public void setUpdated(LocalDateTime updated) {
        this.updated = updated;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}

enum DeletedEmun {
    DELETED(true, "已删除"),
    NORMAL(false, "正常");

    boolean value;
    String desc;

    DeletedEmun(boolean value, String desc) {
        this.value = value;
        this.desc = desc;
    }
}