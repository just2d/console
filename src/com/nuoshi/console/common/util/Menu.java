package com.nuoshi.console.common.util;

public class Menu{
		private String id;
		private String pId;
		private String name;
		private String url;
		private String target;
		private boolean open = false;
		private boolean checked = false;
		
		public boolean isChecked() {
			return checked;
		}
		public void setChecked(boolean checked) {
			this.checked = checked;
		}
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getpId() {
			return pId;
		}
		public void setpId(String pId) {
			this.pId = pId;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getUrl() {
			return url;
		}
		public void setUrl(String url) {
			this.url = url;
		}
		public String getTarget() {
			return target;
		}
		public void setTarget(String target) {
			this.target = target;
		}
		public boolean isOpen() {
			return open;
		}
		public void setOpen(boolean open) {
			this.open = open;
		}
}