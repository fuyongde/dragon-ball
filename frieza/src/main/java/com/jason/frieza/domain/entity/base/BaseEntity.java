package com.jason.frieza.domain.entity.base;

import java.time.LocalDateTime;

/**
 * 基类
 * Created by fuyongde on 2016/11/12.
 */
public class BaseEntity {

  private Boolean deleted = Boolean.FALSE;
  private LocalDateTime created;
  private LocalDateTime updated;

  public Boolean getDeleted() {
    return deleted;
  }

  public void setDeleted(Boolean deleted) {
    this.deleted = deleted;
  }

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
}
