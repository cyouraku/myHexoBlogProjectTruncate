package com.lzj.springbatch.model;

import java.util.Date;

public class MenuChannelLinkTbl {

	private Integer menuId;
	private Integer channelId;
	private String deleteFlag;
	private Date updateDate;
	private String updatetUser;
	private Date registDate;
	private String registUser;

	public Integer getMenuId() {
		return menuId;
	}
	public void setMenuId(Integer menuId) {
		this.menuId = menuId;
	}
	public Integer getChannelId() {
		return channelId;
	}
	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}
	public String getDeleteFlag() {
		return deleteFlag;
	}
	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getUpdateUser() {
		return updatetUser;
	}
	public void setUpdateUser(String updatetUser) {
		this.updatetUser = updatetUser;
	}

	public Date getRegistDate() {
		return registDate;
	}
	public void setRegistDate(Date registDate) {
		this.registDate = registDate;
	}
	public String getRegistUser() {
		return registUser;
	}
	public void setRegistUser(String registUser) {
		this.registUser = registUser;
	}

    @Override
    public String toString() {
        return "MenuChannelLinkTbl{" +
                "menuId=" + menuId +
                ", channelId='" + channelId + '\'' +
                ", deleteFlag=" + deleteFlag +
                ", updateDate=" + updateDate +
                ", updatetUser=" + updatetUser +
                ", registDate=" + registDate +
                ", registUser=" + registUser +
                '}';
    }

}
