package com.nuoshi.console.view;

import com.nuoshi.console.common.Resources;




/**
 * Author: CHEN Liang <alinous@gmail.com>
 * Date: 2009-8-22
 * Time: 20:53:19
 */
public class Checkbox {

    public String getLabel() {
        if (this.label != null) {
            return label;
        } else {
            if (getResourcePrefix()!= null) {
                this.label = Resources.getString(getResourcePrefix());
            }
            return this.label;
        }
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
//        assert(no >= 0);
        this.no = no;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }


    public String getResourcePrefix() {
        return resourcePrefix;
    }

    public void setResourcePrefix(String resourcePrefix) {
        this.resourcePrefix = resourcePrefix;
    }

    public Checkbox() {

    }

    public Checkbox(int no) {
        this.no = no;
    }

    private String  resourcePrefix=null;
    private String  label=null;
    private int no =0;
    private boolean checked=false;

}
