package com.dippy.dto;

/**
 * 是否锁定（0被锁定   1正常   2作废）
 */
public interface Status {

    int DISABLED = 0;// 0被锁定

    int VALID = 1;// 1正常

    int LOCKED = 2;// 2作废
}
