package com.nuoshi.console.view.multiselect;

import java.util.ArrayList;
import java.util.Iterator;

import org.apache.log4j.Logger;

import com.nuoshi.console.view.Checkbox;

/**
 * Author: CHEN Liang <alinous@gmail.com> Date: 2009-8-24 Time: 13:46:41
 */
abstract public class CheckboxGroup {

    private ArrayList<Checkbox> items = new ArrayList<Checkbox>();
    protected Logger log = Logger.getLogger(this.getClass());

    public int getValue() {
        int value = 0;
        for (Checkbox item : items) {
            if (item.isChecked()) {
                value = value | (1 << item.getNo());
            }
        }
        return value;
    }

    public void setValue(int value) {
        int tmp = value;
        for (int i = 0; i < 32 && i < items.size(); ++i) {
            Checkbox item = items.get(i);
            if (item != null) {
                int mask = 1 << item.getNo();
                boolean checked = ((mask & tmp) == mask);
                item.setChecked(checked);
            }
        }
    }

    public ArrayList<Checkbox> getItems() {
        return items;
    }

    public void setItems(ArrayList<Checkbox> items) {
        this.items = items;
    }

    abstract public String getResourcePrefix();

    abstract public int getItemCount();

    public void addItem(int no, boolean checked) {
        Checkbox item = new Checkbox();
        item.setResourcePrefix(getResourcePrefix() + "." + no);
        item.setNo(no);
        item.setChecked(checked);
        getItems().add(item);
    }

    protected Checkbox findCheckbox(int no) {
        for (int i = 0; i < 32 && i < items.size(); ++i) {
            Checkbox item = items.get(i);
            if (item != null && item.getNo() == no) {
                return item;
            }
        }
        return null;
    }

    protected void setItemValue(int no, boolean checked) {
        Checkbox item = findCheckbox(no);
        if (item != null) {
            item.setChecked(checked);
        }
    }

    public void setChecked(int no) {
        setItemValue(no, true);
    }

    public void setUnchecked(int no) {
        setItemValue(no, false);
    }

    public void setChecked(int[] arr) {
        for (int no : arr) {
            setChecked(no);
        }
    }

    public CheckboxGroup() {
        for (int i = 0; i < getItemCount(); ++i) {
            addItem(i, false);
        }
    }

    public String toString() {
        if (getValue() ==0) {
            return "毛坯";
        }
        StringBuffer sb = new StringBuffer();
        boolean first = true;
        String seperator = "、";
        Iterator<Checkbox> it = getItems().iterator();
        while (it.hasNext()) {
            Checkbox i = it.next();
            if (i.isChecked()) {
                sb.append(i.getLabel());
                if (it.hasNext()) {
                    sb.append(seperator);
                }
            }
       }
       return sb.toString();
    }

}
