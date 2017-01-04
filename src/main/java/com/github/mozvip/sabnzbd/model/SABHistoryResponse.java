package com.github.mozvip.sabnzbd.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SABHistoryResponse {

	private List<SabNzbdResponseSlot> slots;
	private String diskspace2_norm;
	private String active_lang;
	private String session;
	private float speedlimit_abs;

	public List<SabNzbdResponseSlot> getSlots() {
		return slots;
	}

	public void setSlots(List<SabNzbdResponseSlot> slots) {
		this.slots = slots;
	}

	public String getDiskspace2_norm() {
		return diskspace2_norm;
	}

	public void setDiskspace2_norm(String diskspace2_norm) {
		this.diskspace2_norm = diskspace2_norm;
	}

	public String getActive_lang() {
		return active_lang;
	}

	public void setActive_lang(String active_lang) {
		this.active_lang = active_lang;
	}

	public String getSession() {
		return session;
	}

	public void setSession(String session) {
		this.session = session;
	}

	public float getSpeedlimit_abs() {
		return speedlimit_abs;
	}

	public void setSpeedlimit_abs(float speedlimit_abs) {
		this.speedlimit_abs = speedlimit_abs;
	}

}
